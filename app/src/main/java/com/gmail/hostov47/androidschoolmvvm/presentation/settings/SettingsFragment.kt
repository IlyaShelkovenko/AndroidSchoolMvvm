/**
 * Created by Ilia Shelkovenko on 17.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.settings

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.gmail.hostov47.androidschoolmvvm.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTheme(R.style.SettingStyle)
    }
}