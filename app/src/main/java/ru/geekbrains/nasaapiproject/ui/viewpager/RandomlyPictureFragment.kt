package ru.geekbrains.nasaapiproject.ui.viewpager


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.api.load
import kotlinx.android.synthetic.main.fragment_randomly_picture.*
import ru.geekbrains.nasaapiproject.R

class RandomlyPictureFragment: Fragment(R.layout.fragment_randomly_picture) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments =  arguments
        arguments?.let { setTitle(it.getString("title", "")) }
        arguments?.let { setImage(it.getString("imageUrl", "")) }
        arguments?.let { setText(it.getString("description", "no description")) }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            randomly_fragment_scroll_view.setOnScrollChangeListener { _, _, _, _, _ ->
                randomly_fragment_title_header.isSelected = randomly_fragment_scroll_view.canScrollVertically(-1)
            }
        }
    }

    private fun setImage(url:String) {
        randomly_fragment_image_view.load(url) {
            lifecycle(this@RandomlyPictureFragment)
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_vector)
        }
    }

    private fun setText(text: String) {
        randomly_fragment_text_view.text = text
    }

    private fun setTitle(text: String) {
        randomly_fragment_title.text = text
    }
}