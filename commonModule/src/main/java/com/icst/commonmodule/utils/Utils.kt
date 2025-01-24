package com.icst.commonmodule.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.icst.commonmodule.R
import java.text.SimpleDateFormat
import java.util.Locale


fun checkNetworkAvailableOrNot(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

fun ImageView.loadImage(
    imgUrl: String,
    @DrawableRes placeholder: Int = R.drawable.ic_launcher_background
) {
    Glide
        .with(this)
        .load(imgUrl)
        .placeholder(placeholder)
        .into(this)

}


 fun setMinuteText(hour: String, min: String, sec: String): String {

    val hours = if (hour.isNotEmpty()) {
        if (hour.length == 1) {
            "0$hour"
        } else {
            hour
        }
    } else {
        "00"
    }

    val minutes = if (min.isNotEmpty()) {
        if (min.length == 1) {
            "0$min"
        } else {
            min
        }
    } else {
        "00"
    }

    val seconds = if (sec.isNotEmpty()) {
        if (sec.length == 1) {
            "0$sec"
        } else {
            sec
        }
    } else {
        "00"
    }
    return if (hours.toInt() > 0) {
        "$hours:$minutes:$seconds minutes"
    } else {
        "$minutes:$seconds minutes"
    }
}

fun loadImage(
    context: Context?,
    imgView: AppCompatImageView?,
    serverUrl: String?,
    doCircleCrop: Boolean
) {
    if (doCircleCrop) {
        Glide.with(context!!)
            .load(serverUrl) /*.placeholder(R.drawable.ic_default_pump)*/
            .apply(RequestOptions.circleCropTransform())
            .into(imgView!!)
    } else {
        Glide.with(context!!)
            .load(serverUrl) /*  .placeholder(R.drawable.ic_default_pump)*/
            .into(imgView!!)
    }
}

enum class TAG(var myTag: String) {
    EDUCATION_FRAGMENT("EDUCATION_FRAGMENT"), EDUCATION_SUB_ADAPTER("EDUCATION_SUB_ADAPTER"),
    MANAGE_ASTHMA_ACTIVITY("MANAGE_ASTHMA_ACTIVITY"), MY_INFORMATION_ACTIVITY("MY_INFORMATION_ACTIVITY"),
    MY_MEDICATION_DETAIL_ACTIVITY("MY_MEDICATION_DETAIL_ACTIVITY")
}

fun hideSoftKeyboard(activity: Activity) {
    try {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    } catch (e: Exception) {
        Log.e("KEYBOARD", "Keyboard hideSoftKeyboard: Exception $e")
        e.message
    }

}

fun showSoftKeyboard(activity: Activity) {
    try {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    } catch (e: Exception) {
        Log.e("KEYBOARD", "Keyboard showSoftKeyboard: Exception $e")
        e.printStackTrace()
    }

}

fun convertStringToDate(dateString: String, format: String, newFormat: String): String {

    val df = SimpleDateFormat(format, Locale.ENGLISH)
    val df1 = SimpleDateFormat(newFormat, Locale.ENGLISH)
    val date = df.parse(dateString)
    date.apply {
        Locale.ENGLISH
    }
    val outputString = date?.let { df1.format(it) }
    return outputString?:""
}

fun convertDate(date: String, inputFormatString: String, outputFormatString: String): String {
    try {
        val inputFormat = SimpleDateFormat(inputFormatString, Locale.getDefault())
        val date1 = inputFormat.parse(date)
        val outputFormat = SimpleDateFormat(outputFormatString, Locale.getDefault())
        return date1?.let { outputFormat.format(it) }?:""
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("convertDate", "error = " + e.message)
    }
    return ""
}

fun convertStringToWelshDate(dateString: String, format: String, newFormat: String): String {
    val df = SimpleDateFormat(format, Locale.ENGLISH)
    val df1 = SimpleDateFormat(newFormat, Locale.getDefault())
    val date = df.parse(dateString)
    return df1.format(date!!)
}

fun generateCircleTag(context: Context, bool: Boolean): GradientDrawable {
//    val rnd = Random()
//    val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

    val brown = Color.parseColor("#651612")
    val green = Color.parseColor("#226512")

    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    if (bool) {
        shape.setColor(brown)
    } else {
        shape.setColor(green)
    }
    shape.setStroke(3, null)
    shape.setSize(
        context.resources.getDimension(com.intuit.sdp.R.dimen._40sdp).toInt(),
        context.resources.getDimension(com.intuit.sdp.R.dimen._40sdp).toInt()
    )
    return shape
}