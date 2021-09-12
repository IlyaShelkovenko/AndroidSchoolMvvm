/**
 * Created by Ilia Shelkovenko on 12.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.data.viewmodels

import android.content.SharedPreferences
import android.content.res.Resources
import com.gmail.hostov47.androidschoolmvvm.domain.interactors.MoviesInteractor
import com.gmail.hostov47.androidschoolmvvm.presentation.home.HomeViewModel
import com.gmail.hostov47.androidschoolmvvm.utils.schedulers.SchedulersProviderImplStub
import io.mockk.mockk
import org.junit.Before

class HomeViewModelTest {
    lateinit var viewModel: HomeViewModel
    private val interactor: MoviesInteractor = mockk()
    private val prefs: SharedPreferences = mockk()
    private val res: Resources = mockk()

    @Before
    fun setup(){
        viewModel = HomeViewModel(interactor, SchedulersProviderImplStub(), prefs, res)
    }
}
