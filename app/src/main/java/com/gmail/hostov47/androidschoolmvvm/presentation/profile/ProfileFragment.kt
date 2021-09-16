/**
 * Created by Ilia Shelkovenko on 21.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
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
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()

        prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity().applicationContext)
        binding.tvLogin.text = prefs.getString(
            resources.getString(com.gmail.hostov47.androidschoolmvvm.R.string.pref_key_login),
            resources.getString(com.gmail.hostov47.androidschoolmvvm.R.string.default_login)
        )
        binding.ivAvatar.visibility = if (prefs.getBoolean(
                resources.getString(com.gmail.hostov47.androidschoolmvvm.R.string.pref_key_show_avatar),
                true
            )
        ) View.VISIBLE else View.GONE

        binding.ivAvatar.setOnClickListener {
            val i = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, RESULT_CHOOSE_IMAGE)
        }
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (binding.ivAvatar.isVisible && isPermissionGranted) {
            val imageUriString = prefs.getString(AVATAR_IMAGE_URI_KEY, "") ?: ""
            setImageFromUriString(imageUriString, requireContext())
        }

        profileTabLayoutTitles =
            resources.getStringArray(com.gmail.hostov47.androidschoolmvvm.R.array.tab_titles)
        val profileAdapter = ProfileAdapter(
            this,
            profileTabLayoutTitles.size
        )
        doppelgangerViewPager.adapter = profileAdapter

        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
            val title = profileTabLayoutTitles[position]
            tab.text = title
        }.attach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CHOOSE_IMAGE && resultCode == RESULT_OK && null != data) {
            val selectedImage: Uri = data.data ?: "".toUri()
            prefs.edit().putString(AVATAR_IMAGE_URI_KEY, selectedImage.toString()).apply()
            setImageFromUriString(selectedImage.toString(), requireContext())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        permissions[0]
                    )
                ) {
                    Toast.makeText(requireContext(), "I need this permission", Toast.LENGTH_LONG)
                        .show()
                }
                checkPermissions()
            }
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        }
    }

    private fun setupActionBar() {
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        binding.appBarLayout.bringToFront()
        binding.toolbar.title = ""
    }

    private fun setImageFromUriString(imageUriString: String, context: Context) {
        if (imageUriString.isNotEmpty()) {
            Glide.with(context)
                .load(Uri.parse(imageUriString))
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }

    companion object {
        const val MOVIES_SPAN_COUNT = 4
        const val RESULT_CHOOSE_IMAGE = 111
        const val REQUEST_CODE = 777
        const val AVATAR_IMAGE_URI_KEY = "AVATAR_IMAGE_URI_KEY"
    }

}