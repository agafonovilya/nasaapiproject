package ru.geekbrains.nasaapiproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.nasaapiproject.R


class MainActivity : AppCompatActivity() {

    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_1 -> if (selectedFragment?.javaClass != PictureOfTheDayFragment::class.java) {
                    selectedFragment = PictureOfTheDayFragment.newInstance()
                }
                R.id.page_2 -> if(selectedFragment?.javaClass != ItemTwoFragment::class.java) {
                    selectedFragment = ItemTwoFragment.newInstance()
                }
            }
            selectedFragment?.let { replaceFragment(it) }
            true
        }

        //Manually displaying the first fragment - one time only
        if (savedInstanceState == null) {
            selectedFragment = PictureOfTheDayFragment.newInstance()
            replaceFragment(selectedFragment as PictureOfTheDayFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit()
    }
}