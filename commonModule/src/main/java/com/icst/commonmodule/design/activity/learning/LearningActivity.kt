package com.icst.commonmodule.design.activity.learning

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityLearningBinding
import com.icst.commonmodule.model.CategoryVideo
import com.icst.commonmodule.model.EducationContent
import com.icst.commonmodule.model.GetVideoCountModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.loadImage

class LearningActivity : ActivityBase() {
    val binding: ActivityLearningBinding by binding(R.layout.activity_learning)
    private val viewModel = LearningViewModel()


    private lateinit var educationFuture: EducationContent.Data.Educationfuture

    private lateinit var videoId: String
    private var watchCompleted: Boolean = false
    private var currentType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this@LearningActivity

        viewModel.activityBase.set(this)

        initToolbars()
        initListeners()
        initData()
        observer()
    }

    private fun initData() {

        val isFromReadArticle =
            intent.getBooleanExtra(getString(R.string.key_is_full_article), false)
        val isPDFData = intent.getBooleanExtra(getString(R.string.key_from_sub_adapter), false)

        if (isFromReadArticle) {
            educationFuture =
                intent.getSerializableExtraData(
                    getString(R.string.key_pdf_data),
                    EducationContent.Data.Educationfuture::class.java
                ) as EducationContent.Data.Educationfuture
            currentType = "educationFuture"
            Log.e("TAG", "initData: Education Future")
            //getVideoCount(educationFuture.cat.toString())
        } else {
            if (isPDFData) {
                viewModel.categoryVideo = intent.getSerializableExtraData(
                    getString(R.string.key_pdf_data),
                    CategoryVideo::class.java
                ) as CategoryVideo
                currentType = "categoryVideo"
                Log.e("TAG", "initData: categoryVideo")
                viewModel.getVideoCount(
                    id = viewModel.categoryVideo.categoryId.toString(),
                    slug = "ALL",
                    context = this
                )
            } else {
                // NOT COMING FROM ANY WHERE
                viewModel.educationData = intent.getSerializableExtraData(
                    getString(R.string.key_pdf_data),
                    EducationContent.Data.EducationData::class.java
                ) as EducationContent.Data.EducationData
                currentType = "educationData"
                viewModel.getVideoCount(
                    id = viewModel.educationData.categoryId.toString(),
                    slug = "ALL",
                    context = this
                )
                Log.e("TAG", "initData: educationData")
            }
        }

        if (isFromReadArticle) {
            loadImage(
                binding.imgTriggerDetailThumb.context,
                binding.imgTriggerDetailThumb,
                educationFuture.imageUrl,
                false
            )
        } else {
            if (isPDFData) {
                loadImage(
                    binding.imgTriggerDetailThumb.context,
                    binding.imgTriggerDetailThumb,
                    viewModel.categoryVideo.videoThumb,
                    false
                )
            } else {
                loadImage(
                    binding.imgTriggerDetailThumb.context,
                    binding.imgTriggerDetailThumb,
                    viewModel.educationData.videoThumb,
                    false
                )
            }
        }

        if (isFromReadArticle) {
            binding.tvLearningSeen.visibility = View.GONE
            binding.ivLearningSeen.visibility = View.GONE
            binding.textLearningTitle.text = educationFuture.subTitle
            binding.textLearningSubtitle.text = educationFuture.title
            binding.textLearningBrief.text =
                educationFuture.content?.let {
                    HtmlCompat.fromHtml(
                        it,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                }

        } else {
            if (isPDFData) {
                binding.textLearningTitle.text = viewModel.categoryVideo.subTitle
                binding.textLearningSubtitle.text = viewModel.categoryVideo.name
                binding.textLearningBrief.text =
                    HtmlCompat.fromHtml(
                        viewModel.categoryVideo.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                videoId = viewModel.categoryVideo.id.toString()
            } else {
                binding.tvLearningSeen.visibility = View.GONE
                binding.ivLearningSeen.visibility = View.VISIBLE
                binding.textLearningTitle.text = viewModel.educationData.subTitle
                binding.textLearningSubtitle.text = viewModel.educationData.name
                binding.textLearningBrief.text =
                    HtmlCompat.fromHtml(
                        viewModel.educationData.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                videoId = viewModel.educationData.id.toString()
            }

            if (watchCompleted) {
                binding.ivLearningSeen.setColorFilter(0)
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@LearningActivity,
                        R.drawable.ic_green_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = false
            } else {
                binding.ivLearningSeen.setColorFilter(
                    ContextCompat.getColor(this, R.color.Grey_400),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                binding.ivLearningSeen.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@LearningActivity,
                        R.drawable.ic_circle_tick
                    )
                )
                binding.ivLearningSeen.isEnabled = true
            }
        }
    }

    private fun initToolbars() {
        binding.include8.toolbarProfile.isVisible = false
        binding.include8.toolbarBack.isVisible = true
        binding.include8.toolbarLogo.isVisible = false
        binding.include8.toolbarTitle.isVisible = true
        binding.include8.toolbarTitle.text = buildString { append("Education") }
    }

    private fun initListeners() {

        binding.include8.toolbarBack.setOnClickListener {
            finish()
        }
        binding.ivLearningSeen.setOnClickListener {
            viewModel.storeVideoCount(
                id = videoId,
                context = this
            )
        }
    }

    private fun observer() {
        viewModel.getVideoCountResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as GetVideoCountModel
                    viewModel.setVideoCountResponse(binding, response)
                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
        viewModel.storeVideoCountResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    onBackPress()
                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
    }


}