package com.charlye934.countries.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.charlye934.countries.R


fun getProgressDrawable(view: View): CircularProgressDrawable {
    return CircularProgressDrawable(view.context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

@BindingAdapter("setImage")
fun loadImage(view: ImageView, url: String?){
    val progressDrawable = getProgressDrawable(view)
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(view.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(view)
}

@BindingAdapter("setVisibility")
fun setVisibility(view: View, value: Boolean){
    view.visibility = if(value) View.VISIBLE else View.GONE
}