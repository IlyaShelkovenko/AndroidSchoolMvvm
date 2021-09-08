/**
 * Created by Ilia Shelkovenko on 07.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentSearchBinding
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment

class SearchFragment: BindingFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

}