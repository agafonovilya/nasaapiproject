package ru.geekbrains.nasaapiproject.ui.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager,
                       private val date: ArrayList<String>,
                       private val title: ArrayList<String>,
                       private val imageUrl: ArrayList<String>,
                       private val description: ArrayList<String>) :
        FragmentStatePagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        val arguments = Bundle()
        arguments.putString("title", title[position])
        arguments.putString("description", description[position])
        arguments.putString("imageUrl", imageUrl[position])

        val fragment = RandomlyPictureFragment()
        fragment.arguments = arguments

        return fragment
    }

    override fun getCount(): Int {
        return date.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return date[position]
    }

}
