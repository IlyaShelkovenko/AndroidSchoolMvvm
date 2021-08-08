/**
 * Created by Ilia Shelkovenko on 05.04.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.R
import com.squareup.picasso.Picasso

fun ImageView.load(url: String, @DrawableRes placeholder: Int = R.drawable.ic_image_not_supported){
    Picasso.get()
        .load("${BuildConfig.POSTER_PATH}$url")
        //.placeholder(placeholder)
        .into(this)
}