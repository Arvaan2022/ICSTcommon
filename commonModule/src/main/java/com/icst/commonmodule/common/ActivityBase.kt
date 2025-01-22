package com.icst.commonmodule.common

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.snackbar.Snackbar
import com.icst.commonmodule.R
import com.icst.commonmodule.app.DataBindingActivity
import com.icst.commonmodule.utils.hideSoftKeyboard
import java.io.Serializable

open class ActivityBase():DataBindingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        backPress()

    }

    private fun backPress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                onBackPress()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        onBackPress()
                    }
                })
        }
    }

    open fun onBackPress() {
        finish()
    }

    fun <T : Serializable?> Intent.getSerializableExtraData(keyName: String?, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            getSerializableExtra(
                keyName,
                clazz
            )
        else
            getSerializableExtra(keyName) as T?
    }


     fun progressBarTouchable(touchable: Boolean) {
        if (touchable)
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        else
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
    }

    fun showSnackBar(view: View, message: String, action: ACTIONSNACKBAR) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(Color.DKGRAY)

        val tv: AppCompatTextView =
            snackBar.view.findViewById(com.google.android.material.R.id.snackbar_text)
        tv.setTextColor(Color.WHITE)
        tv.maxLines = 10
        tv.typeface = ResourcesCompat.getFont(this, R.font.opensans_regular)

        val snackBaraction: AppCompatButton =
            snackBar.view.findViewById(com.google.android.material.R.id.snackbar_action)
        snackBaraction.typeface =
            ResourcesCompat.getFont(this, R.font.opensans_regular)

        snackBar.setAction(action.actionMessage) {
            when (action.actionMessage) {
                ACTIONSNACKBAR.DISMISS.actionMessage -> {
                    snackBar.dismiss()
                }

                ACTIONSNACKBAR.FINISH_ACTIVITY.actionMessage -> {
                    finish()
                }
            }
        }
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.show()
    }

    enum class ACTIONSNACKBAR(val actionMessage: String) {
        DISMISS("Dismiss"), FINISH_ACTIVITY("Done"), NONE("")
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard(this@ActivityBase)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }
}