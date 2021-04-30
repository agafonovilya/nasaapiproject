package ru.geekbrains.nasaapiproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.nasaapiproject.R
import ru.geekbrains.nasaapiproject.ui.notes.NotesFragment
import ru.geekbrains.nasaapiproject.ui.preferences.AppSharedPreferences
import ru.geekbrains.nasaapiproject.ui.preferences.SharedPrefValue


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        when(AppSharedPreferences().getSavedTheme()) {
            SharedPrefValue.THEME_LIGHT -> setTheme(R.style.Theme_NasaApiProject_Light)
            SharedPrefValue.THEME_NIGHT -> setTheme(R.style.Dark)
        }

        super.onCreate(savedInstanceState)

        main_activity_bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_pod -> if (selectedFragment?.javaClass != PictureOfTheDayFragment::class.java) {
                    selectedFragment = PictureOfTheDayFragment.newInstance()
                }
                R.id.menu_item_other -> if (selectedFragment?.javaClass != ContainerForRandomlyPictureFragment::class.java) {
                    selectedFragment = ContainerForRandomlyPictureFragment.newInstance()
                }
                R.id.menu_item_notes -> if (selectedFragment?.javaClass != NotesFragment::class.java){
                    selectedFragment = NotesFragment.newInstance()
                }
                R.id.menu_item_settings -> if (selectedFragment?.javaClass != SettingsFragment::class.java){
                    selectedFragment = SettingsFragment.newInstance()
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
                .replace(R.id.main_activity_container, fragment)
                .commit()
    }
}