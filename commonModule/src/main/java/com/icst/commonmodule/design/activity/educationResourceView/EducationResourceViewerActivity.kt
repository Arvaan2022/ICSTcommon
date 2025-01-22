package com.icst.commonmodule.design.activity.educationResourceView

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityEducationResourceViewerBinding
import com.icst.commonmodule.utils.Constant.RESOURCE_PDF_LINK
import com.icst.commonmodule.utils.Constant.RESOURCE_URL_LINK
import java.io.InputStream

class EducationResourceViewerActivity : ActivityBase() {


    val binding: ActivityEducationResourceViewerBinding by binding(R.layout.activity_education_resource_viewer)



    private var myDownloadId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@EducationResourceViewerActivity
        initViews()
        onClickListeners()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        intent!!.apply {
            if (hasExtra(RESOURCE_URL_LINK)) {
                val url = getStringExtra(RESOURCE_URL_LINK).toString()
                Log.e(EducationResourceViewerActivity::class.java.simpleName, "initViews: $url")
                binding.wvResourceViewer.visibility = View.VISIBLE
                binding.wvResourceViewer.settings.javaScriptEnabled = true
                binding.wvResourceViewer.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                binding.wvResourceViewer.webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                       binding.progress.isVisible = true
                        progressBarTouchable(false)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        binding.progress.isVisible = false
                        progressBarTouchable(true)
                    }
                }
                binding.wvResourceViewer.loadUrl(url)
            }

            if (hasExtra(RESOURCE_PDF_LINK)) {

                binding.pdfViewResourceViewer.visibility = View.VISIBLE
                val url: String = intent.getStringExtra(RESOURCE_PDF_LINK).toString().trim()
                val request = DownloadManager.Request(Uri.parse(url))
                    .setTitle(url.fileName())
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setAllowedOverMetered(true)

                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                myDownloadId = dm.enqueue(request)
                binding.progress.isVisible = true
                progressBarTouchable(false)
            }
        }

        val br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val mIntentId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (mIntentId == myDownloadId) {
                    val downloadManager =
                        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    try {
                        loadPdf(downloadManager.getUriForDownloadedFile(myDownloadId) as Uri)
                    } catch (e: Exception) {
                        Log.e(
                            EducationResourceViewerActivity::class.java.simpleName,
                            "onReceive: ${e.message}"
                        )
                        Toast.makeText(this@EducationResourceViewerActivity, "Something went wrong, Please try again", Toast.LENGTH_SHORT).show()

                        binding.progress.isVisible = false
                        onBackPress()
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.registerReceiver(this ,br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), ContextCompat.RECEIVER_EXPORTED)
        }else{
            registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        }
    }

    override fun onStop() {
        super.onStop()
        binding.progress.isVisible = false
    }

    private fun loadPdf(uri: Uri?) {
        val inputStream: InputStream = contentResolver.openInputStream(uri!!)!!
        val paint = Paint(
            ContextCompat.getColor(
                this@EducationResourceViewerActivity,
                R.color.color_pdf_divider_line
            )
        )
        binding.pdfViewResourceViewer.fromStream(inputStream)
            .onDraw { canvas, pageWidth, pageHeight, _ ->
                canvas!!.drawLine(0F, 0F, pageWidth, 0F, paint)
                canvas.drawLine(0F, pageHeight, pageWidth, pageHeight, paint)
                canvas.drawLine(0F, 0F, 0F, pageHeight, paint)
                canvas.drawLine(pageWidth, 0F, pageWidth, pageHeight, paint)
            }.onLoad {
                // Pdf loaded completely
                binding.progress.isVisible = false
                progressBarTouchable(true)
                //  tv_report_viewer_page_number.visibility = View.VISIBLE
            }.onPageError { page, _ ->
                Log.e(
                    EducationResourceViewerActivity::class.java.simpleName,
                    "Can not load page $page"
                )
            }.onError { t ->
                t?.printStackTrace()
                Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
                binding.progress.isVisible = false
                progressBarTouchable(true)
            }.load()
    }

    private fun onClickListeners() {

    }

    private fun String?.fileName(): String {
        return this?.split("/")!!.reversed()[0]
            .replace("-", "_").replace(" ", "_")
    }
}