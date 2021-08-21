/**
 * Created by Ilia Shelkovenko on 21.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentProfileBinding
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import javax.inject.Inject
import javax.inject.Named

class ProfileFragment
    : BindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_fragment_to_settings_fragment)
        }
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        binding.tvLogin.text = prefs.getString(resources.getString(R.string.pref_key_login), resources.getString(R.string.default_login))
        binding.ivAvatar.visibility = if(prefs.getBoolean(resources.getString(R.string.pref_key_show_avatar), true)) View.VISIBLE else View.GONE
    }

}