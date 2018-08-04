package com.benjamingbaxter.androidtv.circleavatars

import android.support.annotation.DrawableRes
import android.support.v17.leanback.widget.Presenter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class PersonImageCardViewPresenter(private val circularImage: Boolean) : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.cardview_personimage, null, false)
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        return PersonImageCardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val personImage = item as @DrawableRes Int

        val vh = requireNotNull(viewHolder) as PersonImageCardViewHolder

        val context = requireNotNull(vh.personImageView.context)

        val requestOptions =
            if( circularImage ) {
                RequestOptions
                    .circleCropTransform()
            } else {
                RequestOptions
                    .centerCropTransform()
            }
//        if( ! circularImage ) {
            vh.imageBackgroundView.background = null
//        }

        Glide.with(context)
            .load(personImage)
            .apply(requestOptions)
            .into(vh.personImageView)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

    }

    class PersonImageCardViewHolder(view: View) : ViewHolder(view) {
        val personImageView = view.findViewById<ImageView>(R.id.personImage)
        val imageBackgroundView = view.findViewById<ViewGroup>(R.id.imageBackground)
    }
}