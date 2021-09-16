/**
 * Created by Ilia Shelkovenko on 05.04.2021.
 */
package com.gmail.hostov47.androidschoolmvvm.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.gmail.hostov47.androidschoolmvvm.BuildConfig
import com.gmail.hostov47.androidschoolmvvm.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation

fun ImageView.load(url: String){
    if(url.isNotEmpty())
        Picasso.get()
            .load(url)
            .into(this)
}

fun ImageView.loadBlur(url: String){
    if(url.isNotEmpty())
        Picasso.get()
            .load(url)
            .transform(BlurTransformation(this.context, 25, 1))
            .into(this)
}

fun ImageView.load(@DrawableRes res: Int = R.drawable.ic_image_not_supported){
    Picasso.get()
        .load(res)
        .into(this)
}

fun ImageView.loadPlaceHolder(@DrawableRes placeholder: Int = R.drawable.ic_image_not_supported){
    Picasso.get()
        .load(placeholder)
        .placeholder(placeholder)
        .into(this)
}