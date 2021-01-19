package ru.geekbrains.nasaapiproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.geekbrains.nasaapiproject.R
import ru.geekbrains.nasaapiproject.ui.preferences.AppSharedPreferences
import ru.geekbrains.nasaapiproject.ui.preferences.SharedPrefValue

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(AppSharedPreferences().getSavedTheme()) {
            SharedPrefValue.THEME_LIGHT -> chipGroup_theme.check(chip_light.id)
            SharedPrefValue.THEME_NIGHT -> chipGroup_theme.check(chip_night.id)
        }

        chipGroup_theme.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.chip_light -> {
                    if (AppSharedPreferences().getSavedTheme() == SharedPrefValue.THEME_LIGHT) {
                        chipGroup_theme.check(chip_light.id)
                    } else {
                        AppSharedPreferences().saveLightTheme()
                        activity?.recreate()
                    }
                }
                R.id.chip_night -> {
                    if (AppSharedPreferences().getSavedTheme() == SharedPrefValue.THEME_NIGHT) {
                        chipGroup_theme.check(chip_night.id)
                    } else {
                        AppSharedPreferences().saveNightTheme()
                        activity?.recreate()
                    }
                }
            }
        }
    }
}