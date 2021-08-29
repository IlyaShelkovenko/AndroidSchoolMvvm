/**
 * Created by Ilia Shelkovenko on 28.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.profile.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.favorite.FavoriteMoviesFragment
import com.gmail.hostov47.androidschoolmvvm.presentation.profile.watchlist.WatchListFragment

class ProfileAdapter(fragment: Fragment, private val itemsCount: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FavoriteMoviesFragment()
            1 -> WatchListFragment()
            else -> FavoriteMoviesFragment()
        }
    }
}