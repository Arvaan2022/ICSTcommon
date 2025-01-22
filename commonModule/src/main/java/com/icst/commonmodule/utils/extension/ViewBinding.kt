package com.icst.commonmodule.utils.extension

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.icst.commonmodule.R
import com.icst.commonmodule.design.activity.learning.LearningActivity
import com.icst.commonmodule.design.activity.normalVideoPlayer.NormalValueVideoPlayerActivity
import com.icst.commonmodule.design.activity.videoPlayer.VideoPlayerActivity
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.model.NormalValueDataModel
import com.icst.commonmodule.utils.Constant.ASTHMA_VIDEO_LIST
import com.icst.commonmodule.utils.Constant.ASTHMA_VIDEO_POSITION
import com.icst.commonmodule.utils.Constant.FROM_EDUCATION
import com.icst.commonmodule.utils.Constant.FROM_INTENT
import com.icst.commonmodule.utils.TAG


@BindingAdapter("gone")
fun bindGone(view: View, shouldBeGone: Boolean) {
    view.gone(shouldBeGone)
}

@BindingAdapter("gone")
fun bindGone(view: ViewGroup, shouldBeGone: Boolean) {
    view.gone(shouldBeGone)
}


@BindingAdapter("onBackPressed")
fun bindOnBackPressed(view: View, finish: Boolean) {
    val context = view.context
    if (finish && context is Activity) {
        view.setOnClickListener {
            context.onBackPressed()
        }
    }
}


@BindingAdapter("onClick")
fun onClick(view: View, onClick: () -> Unit) {
    view.setOnClickListener {
        onClick()
    }
}





@BindingAdapter("setImageUri")
fun setImageUri(
    imageView: ImageView,
    uri: String?
) {
    Glide.with(imageView.context)
        .load(uri)
        .placeholder(R.color.white)
        .into(imageView)
}

@BindingAdapter("setDrawable")
fun setDrawable(view: ImageView, code: Int) {
    view.setImageResource(code)
}

@BindingAdapter(
    value = ["videoId", "categoryId", "contentType", "educationResponse", "positionArray"],
    requireAll = true
)
fun goToVideoPlayer(
    view: View,
    videoId: Int,
    categoryId: Int,
    contentType: String?,
    educationResponse: EducationContent?,
    positionArray: Int?,
) {
    view.setOnClickListener {
        if (contentType == "video") {
            view.context.startActivity(
                Intent(view.context, VideoPlayerActivity::class.java)
                    .putExtra(view.context.getString(R.string.key_video_id), videoId)
                    .putExtra(view.context.getString(R.string.key_category_id), categoryId)
                    .putExtra(view.context.getString(R.string.key_from_), TAG.EDUCATION_FRAGMENT.myTag)

            )
        } else {
            view.context.startActivity(
                Intent(view.context, LearningActivity::class.java)
                    .putExtra(view.context.getString(R.string.key_is_full_article), false)
                    .putExtra(view.context.getString(R.string.key_position), positionArray)
                    .putExtra(view.context.getString(R.string.key_pdf_data), educationResponse?.data?.educationData?.get(positionArray ?: 0))
            )
        }
    }
}

@BindingAdapter("goToNormalVideoPlayer")
fun goToNormalVideoPlayer(view: View, videoData: ArrayList<NormalValueDataModel.Data.VideoData>?) {
    view.setOnClickListener {
        val intent =
            Intent(view.context, NormalValueVideoPlayerActivity::class.java)
        intent.putExtra(ASTHMA_VIDEO_POSITION, 0)
        intent.putExtra(ASTHMA_VIDEO_LIST, videoData)
        intent.putExtra(FROM_INTENT, FROM_EDUCATION)
        view.context.startActivity(intent)
    }
}


