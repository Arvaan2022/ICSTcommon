package com.icst.commonmodule.common

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.icst.commonmodule.R

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private val toolbarImage: ImageView

    init {
        // Inflate custom toolbar layout
        inflate(context, R.layout.common_module_toolbar, this)
        toolbarImage = findViewById(R.id.toolbar_logo)
    }

    // Function to set the image in the toolbar
    fun setImage(imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .into(toolbarImage)
    }

    fun setImage(imageUrl: Int) {
        Glide.with(context)
            .load(imageUrl)
            .into(toolbarImage)
    }
}