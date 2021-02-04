package ru.geekbrains.nasaapiproject.ui

import android.content.Intent
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.LeadingMarginSpan
import android.text.style.URLSpan
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_pod.*
import ru.geekbrains.nasaapiproject.R
import ru.geekbrains.nasaapiproject.ui.pod.PictureOfTheDayData
import ru.geekbrains.nasaapiproject.ui.pod.PictureOfTheDayViewModel

class PictureOfTheDayFragment : Fragment() {

    private var isExpanded = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pod_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData()
                .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))

        pod_fragment_input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${pod_fragment_input_edit_text.text.toString()}")
            })
        }

        setImageZoomClickListener()

    }

    private fun setImageZoomClickListener() {
        pod_fragment_image_view.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                main, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )

            val params: ViewGroup.LayoutParams = pod_fragment_image_view.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
            pod_fragment_image_view.layoutParams = params
            pod_fragment_image_view.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP else ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }


    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                        if(serverResponseData.mediaType == "video") {
                            pod_fragment_webview.clearCache(true)
                            pod_fragment_webview.clearHistory()
                            pod_fragment_webview.settings.javaScriptEnabled = true
                            pod_fragment_webview.settings.javaScriptCanOpenWindowsAutomatically = true
                            pod_fragment_webview.loadUrl(url)
                        } else {
                            pod_fragment_image_view.load(url) {
                                lifecycle(this@PictureOfTheDayFragment)
                                error(R.drawable.ic_load_error_vector)
                                placeholder(R.drawable.ic_no_photo_vector)
                            }
                        }

                    setLinkedTitle(serverResponseData.title)
                    setDescription(serverResponseData.explanation)
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun setDescription(description: String?) {
        description?.let {
            val spannableString = SpannableString(it)
            spannableString.setSpan(
                    LeadingMarginSpan.Standard(100, 0),
                    0,
                    it.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bottom_sheet_description.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
            }
            bottom_sheet_description.text = spannableString
        }
    }

    private fun setLinkedTitle(title: String?) {
        title?.let {
            val spannableString = SpannableString(it)
            spannableString.setSpan(
                    URLSpan("https://en.wikipedia.org/wiki/${it}"),
                    0,
                    it.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            bottom_sheet_description_header.text = spannableString
            bottom_sheet_description_header.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
    }
}