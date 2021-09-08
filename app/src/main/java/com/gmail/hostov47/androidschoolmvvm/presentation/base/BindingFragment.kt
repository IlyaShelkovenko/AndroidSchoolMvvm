/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.navOptions
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.R
import com.google.android.material.snackbar.Snackbar

abstract class BindingFragment<out T : ViewBinding> : Fragment() {
    private var _binding: ViewBinding? = null
    protected val binding: T
        get() = _binding as T
    protected abstract val bindingInflater: (LayoutInflater) -> ViewBinding

    protected val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater)
        return _binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun snackBarCancelable(message: String, action: () -> Unit){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setActionTextColor(resources.getColor(R.color.yellow))
            .setAction(getString(R.string.cancel_action), ) {
                action.invoke()
            }.show();
    }

}
