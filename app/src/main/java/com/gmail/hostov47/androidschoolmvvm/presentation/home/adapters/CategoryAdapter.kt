/**
 * Created by Ilia Shelkovenko on 04.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.hostov47.androidschoolmvvm.databinding.ItemCategoryBinding

class CategoryAdapter(private val onClickListener: OnMovieItemClick) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    fun addItem(item: CategoryItem){
        itemsList.add(item)
        notifyItemInserted(itemsList.indexOf(item))
    }

    private val itemsList: MutableList<CategoryItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding, onClickListener)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount(): Int = itemsList.size

    fun clearList() {
        itemsList.clear()
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding, private val onClickListener: OnMovieItemClick) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = MoviesAdapter(listOf(), onClickListener)

        fun bind(item: CategoryItem) {
            with(binding) {
                titleTextView.text = item.title
                adapter.submitItems(item.movies)
                itemsContainer.adapter = adapter
            }
        }
    }
}