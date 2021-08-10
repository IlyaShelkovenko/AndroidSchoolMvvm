/**
 * Created by Ilia Shelkovenko on 04.08.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.presentation.details.adapters

import com.gmail.hostov47.androidschoolmvvm.R
import com.gmail.hostov47.androidschoolmvvm.models.data.dto.CastResponse
import com.gmail.hostov47.androidschoolmvvm.extensions.load
import com.gmail.hostov47.androidschoolmvvm.models.presentation.Cast
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_actor.view.*


/**
 * Адаптер для отображения авторов списка.
 *
 * @author Шелковенко Илья on 2021-08-04
 */
class ActorItem(
        private val content: Cast
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val name = content.name.split(" ")

        viewHolder.containerView.tv_first_name.text = name.first()
        viewHolder.containerView.tv_second_name.text = name.last()
        if(content.profilePoster != null){
            viewHolder.containerView.iv_actor_avatar.load(content.profilePoster!!, R.drawable.ic_person)
        }
    }

    override fun getLayout(): Int = R.layout.item_actor

}