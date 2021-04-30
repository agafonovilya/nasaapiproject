package ru.geekbrains.nasaapiproject.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
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
            SharedPrefValue.THEME_LIGHT -> setting_fragment_chipGroup_theme.check(setting_fragment_chip_light.id)
            SharedPrefValue.THEME_NIGHT -> setting_fragment_chipGroup_theme.check(setting_fragment_chip_night.id)
        }

        setting_fragment_chipGroup_theme.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.setting_fragment_chip_light -> {
                    if (AppSharedPreferences().getSavedTheme() == SharedPrefValue.THEME_LIGHT) {
                        setting_fragment_chipGroup_theme.check(setting_fragment_chip_light.id)
                    } else {
                        AppSharedPreferences().saveLightTheme()
                        activity?.recreate()
                    }
                }
                R.id.setting_fragment_chip_night -> {
                    if (AppSharedPreferences().getSavedTheme() == SharedPrefValue.THEME_NIGHT) {
                        setting_fragment_chipGroup_theme.check(setting_fragment_chip_night.id)
                    } else {
                        AppSharedPreferences().saveNightTheme()
                        activity?.recreate()
                    }
                }
            }
        }

        animateChipGroup()

    }

    private fun animateChipGroup() {
        with(ObjectAnimator.ofFloat(setting_fragment_chipGroup_theme, "rotation", 0f, 360f)) {
            duration = 1000
            start()
        }
    }
}