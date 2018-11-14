package com.narunas.comics.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.narunas.comics.R


class BaseImageView: ImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    init {

        scaleType = ImageView.ScaleType.FIT_XY

    }

    fun imageSource(path: String, fade: Boolean){

        val options = RequestOptions()
            .centerCrop()
            .error(R.drawable.ic_launcher_foreground)

        if(fade) {
            Glide.with(context)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(options)
                .into(this)

        } else {

            Glide.with(context)
                .load(path)
                .apply(options)
                .into(this)
        }
    }
}