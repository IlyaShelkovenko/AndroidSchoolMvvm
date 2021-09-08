/**
 * Created by Ilia Shelkovenko on 07.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.gmail.hostov47.androidschoolmvvm.ImdbApp
import com.gmail.hostov47.androidschoolmvvm.databinding.FragmentSearchBinding
import com.gmail.hostov47.androidschoolmvvm.presentation.base.BindingFragment
import io.reactivex.Observable
import kotlinx.android.synthetic.main.search_toolbar.*
import javax.inject.Inject

class SearchFragment: BindingFragment<FragmentSearchBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentSearchBinding::inflate

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    private lateinit var searchObservable: Observable<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as ImdbApp).appComponent.getSearchComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchObservable = Observable.create { emitter ->
            search_edit_text.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emitter.onNext(query.toString())
                }
            })
        }
        viewModel.handleSearch(searchObservable)
    }
}