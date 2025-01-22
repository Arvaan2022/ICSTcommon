package com.icst.commonmodule.design.activity.educationVideoList

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityEducationVideoListBinding
import com.icst.commonmodule.model.EducationList
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.hideSoftKeyboard
import com.icst.commonmodule.utils.showSoftKeyboard

class EducationVideoListActivity: ActivityBase() {

    val binding : ActivityEducationVideoListBinding by binding(R.layout.activity_education_video_list)
    private val viewModel = EducationVideoListViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this

        viewModel.binding=binding
        viewModel.activityBase.set(this)

        viewModel.educationSearchName = ArrayList()
        viewModel. educattionHomeAlldata = ArrayList()

        initToolbars()
        initListeners()
        observer()
    }

    private fun initToolbars() {
        binding.include7.toolbarProfile.isVisible = false
        binding.include7.toolbarBack.isVisible = true
        binding.include7.toolbarLogo.isVisible = false
        binding.include7.toolbarTitle.isVisible = true
        binding.include7.toolbarTitle.text = buildString { append("Education") }

        getVideoList()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        binding.include7.toolbarBack.setOnClickListener {
            finish()
        }


        binding.buttonGo.setOnClickListener {
            hideSoftKeyboard(this)
            if (binding.editSearch.text!!.isNotEmpty()) {
                getVideoList()
            } else {
                Toast.makeText(this, "Please enter valid search...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageClose.setOnClickListener {
            binding.editSearch.isCursorVisible = false
            hideSoftKeyboard(this)
            binding.editSearch.setText("")
            binding.imageClose.visibility = View.GONE
            binding.editSearch.clearFocus()
         getVideoList()
        }



        binding.editSearch.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.editSearch.isCursorVisible = true
                    binding.editSearch.isFocusable = true
                    binding.editSearch.requestFocus()
                    showSoftKeyboard(this)
                }
                MotionEvent.ACTION_UP -> v.performClick()
                else -> {
                }
            }
            true
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(search_hint: Editable?) {
                if (search_hint!!.isEmpty()) {
                    binding.imageClose.visibility = View.GONE
                } else {
                    binding.imageClose.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        binding.editSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideSoftKeyboard(this)
                getVideoList()
                return@OnEditorActionListener true
            }
            false
        })

    }


    private fun getVideoList() {
        if (binding.editSearch.text.toString().isNotEmpty()){
            viewModel.getEducationVideoList(
                name=binding.editSearch.text.toString(),
                context = this
            )
        }else{
            viewModel.getEducationVideoList(
                name="",
                context = this
            )
        }


       /* var gifDrawable: pl.droidsonroids.gif.GifDrawable? = null
        when (SELECTED_LANGUAGE) {
            "en" -> {
                gifDrawable =
                    pl.droidsonroids.gif.GifDrawable(resources, R.drawable.ic_englsih_background)
            }
            "wl" -> {
                gifDrawable =
                    pl.droidsonroids.gif.GifDrawable(resources, R.drawable.ic_welsh_background)
            }
        }
        binding.imgGifLoader.setImageDrawable(gifDrawable)*/

    }




    private fun observer(){
        viewModel.educationVideoListResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                    val response = it.value as EducationList
                    viewModel.setData(response)
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