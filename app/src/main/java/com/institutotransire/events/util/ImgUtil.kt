package com.institutotransire.events.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.institutotransire.events.R
import com.squareup.picasso.Picasso

object ImgUtil {
    @JvmStatic
    fun requestImg(context: Context?, mImage: ImageView, imagePath: String?) {
        val url = GlideUrl(imagePath)
        Glide.with(context!!).asDrawable().load(url).timeout(8000).centerCrop()
                .apply(RequestOptions().override(600, 400))
                .into(object : SimpleTarget<Drawable?>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
                        mImage.setImageDrawable(resource)
                    }
                })
    }

    fun requestImgWeb(context: Context?, mImage: ImageView?, imagePath: String?) {
        Picasso.get()
                .load(imagePath)
                .resize(600, 500)
                .error(R.drawable.imgdefault)
                .centerCrop()
                .into(mImage)
    }
}