package ru.geekbrains.nasaapiproject.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_container_randomly_picture.*
import ru.geekbrains.nasaapiproject.R
import ru.geekbrains.nasaapiproject.ui.randomlypicture.RandomlyPictureData
import ru.geekbrains.nasaapiproject.ui.randomlypicture.PodSomeQuantityViewModel
import ru.geekbrains.nasaapiproject.ui.viewpager.ViewPagerAdapter


class ContainerForRandomlyPictureFragment : Fragment(R.layout.fragment_container_randomly_picture) {
    companion object {
        fun newInstance(): ContainerForRandomlyPictureFragment {
            return ContainerForRandomlyPictureFragment()
        }
    }

    private val viewModel: PodSomeQuantityViewModel by lazy {
        ViewModelProvider(this).get(PodSomeQuantityViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getData()
                .observe(viewLifecycleOwner, Observer<RandomlyPictureData> { renderData(it) })
    }

    private fun renderData(data: RandomlyPictureData) {
        when (data) {
            is RandomlyPictureData.Success -> {
                val date: ArrayList<String> = ArrayList()
                val imageUrl: ArrayList<String> = ArrayList()
                val description: ArrayList<String> = ArrayList()

                data.serverResponseData.forEach {
                    it.date?.let { it1 -> date.add(it1) }
                    it.url?.let { it1 -> imageUrl.add(it1)}
                    it.explanation?.let { it1 -> description.add(it1) }
                }

                view_pager.adapter = ViewPagerAdapter(childFragmentManager, date, imageUrl, description)
                tab_layout.setupWithViewPager(view_pager)

            }
            is RandomlyPictureData.Loading -> {
                //showLoading()
            }
            is RandomlyPictureData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

}