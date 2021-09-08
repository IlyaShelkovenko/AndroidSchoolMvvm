/**
 * Created by Ilia Shelkovenko on 21.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile

import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentProfileBinding
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.adapters.ProfileAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment
    : BindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProfileBinding::inflate

    private lateinit var profileTabLayoutTitles: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()

        val prefs =
            PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        binding.tvLogin.text = prefs.getString(
            resources.getString(R.string.pref_key_login),
            resources.getString(R.string.default_login)
        )
        binding.ivAvatar.visibility = if (prefs.getBoolean(
                resources.getString(R.string.pref_key_show_avatar),
                true
            )
        ) View.VISIBLE else View.GONE

        profileTabLayoutTitles = resources.getStringArray(R.array.tab_titles)
        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        doppelgangerViewPager.adapter = profileAdapter

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            val title = profileTabLayoutTitles[position]
            /*val spannableStringTitle = SpannableString(title)
            spannableStringTitle.setSpan(RelativeSizeSpan(2f), 0, number.count(), 0)*/
            tab.text = title
        }.attach()
    }

    private fun setupActionBar() {
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        binding.appBarLayout.bringToFront()
        binding.toolbar.title = ""
    }

    companion object {
        const val MOVIES_SPAN_COUNT = 4
    }

}