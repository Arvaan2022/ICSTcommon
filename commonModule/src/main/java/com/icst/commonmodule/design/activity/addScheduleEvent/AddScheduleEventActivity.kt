package com.icst.commonmodule.design.activity.addScheduleEvent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.common.DayMultiSelectionSpinner
import com.icst.commonmodule.databinding.ActivityAddScheduleEventBinding
import com.icst.commonmodule.design.activity.addScheduleEvent.adapter.ActivityScheduleSelectionSearchAdapter
import com.icst.commonmodule.design.activity.addScheduleEvent.adapter.SecondDemoAdapter
import com.icst.commonmodule.model.ScheduleEventModel
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_DATE
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_END_DATE
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_END_TIME
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_EVENT_ID
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_EVENT_NAME
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_ID
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_INTENT_TYPE
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_LOCATION
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_SCHEDULE
import com.icst.commonmodule.utils.Constant.KEY_SCHEDULE_SCHEDULE_DAY
import com.icst.commonmodule.utils.Constant.SCHEDULE_INTENT_TYPE_EDIT
import com.icst.commonmodule.utils.Constant.SCHEDULE_INTENT_TYPE_VIEW
import com.icst.commonmodule.utils.Constant.SELECTED_NEVER
import com.icst.commonmodule.utils.Constant.SELECTED_SCHEDULE
import com.icst.commonmodule.utils.convertStringToDate
import java.util.Calendar

class AddScheduleEventActivity : ActivityBase(),
    DayMultiSelectionSpinner.MultiSpinnerListener {
    private val binding: ActivityAddScheduleEventBinding by binding(R.layout.activity_add_schedule_event)
    val viewModel = AddScheduleEventViewModel()

    private var editScheduleId: String = ""


    private var scheduleDaysBufferId: String = ""


    private var scheduleArrayList = ArrayList<String>()
    private var scheduleDaysChooseName = ArrayList<String>()
    private var scheduleDaysChooseId = ArrayList<String>()
    private var activitySchedules: ArrayList<String> = ArrayList()
    private var activtyTypeDataList: ArrayList<ScheduleEventModel.Data> = ArrayList()
    private lateinit var chooseSelectionActivity: ActivityScheduleSelectionSearchAdapter

    companion object {
        var time: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this@AddScheduleEventActivity
        viewModel.activityBase.set(this)

        viewModel.getScheduleApiCall()

        initViews()
        onClickListeners()

        observer()
    }

    private fun initViews() {
        viewModel.setUpToolBar(binding)
        scheduleSelectionSpinner()
        scheduleDaysSpinner()
        getIntentData()
    }

    private fun getIntentData() {
        val mIntent = intent
        if (mIntent.hasExtra(KEY_SCHEDULE_INTENT_TYPE)) {
            if (mIntent.getStringExtra(KEY_SCHEDULE_INTENT_TYPE) == SCHEDULE_INTENT_TYPE_VIEW) {
                binding.tvAddEventActivityTitle.text = getString(R.string.event_or_activity)
                binding.actAddScheduleEventActivity.isEnabled = false
                binding.edtAddScheduleLocation.isEnabled = false
                binding.edtAddScheduleStartDate.isEnabled = false
                binding.edtAddScheduleEndDate.isEnabled = false
                binding.edtAddScheduleTime.isEnabled = false
                binding.spAddScheduleSchedule.isEnabled = false
                binding.btnAddScheduleSave.visibility = View.GONE
                binding.spAddScheduleChooseSchedule.isEnabled = false
            } else if (mIntent.getStringExtra(KEY_SCHEDULE_INTENT_TYPE) == SCHEDULE_INTENT_TYPE_EDIT) {
                binding.tvAddEventActivityTitle.text = getString(R.string.event_or_activity)
            }
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_ID)) {
            editScheduleId = mIntent.getStringExtra(KEY_SCHEDULE_ID)!!
        }

        if (mIntent.hasExtra(KEY_SCHEDULE_EVENT_NAME)) {
            viewModel.mActivityScheduleName = mIntent.getStringExtra(KEY_SCHEDULE_EVENT_NAME)!!
//
            binding.actAddScheduleEventActivity.setText(viewModel.mActivityScheduleName)
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_LOCATION)) {
            binding.edtAddScheduleLocation.setText(mIntent.getStringExtra(KEY_SCHEDULE_LOCATION)!!)
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_DATE)) {
            binding.edtAddScheduleStartDate.text = mIntent.getStringExtra(KEY_SCHEDULE_DATE)!!
            viewModel.startDate = mIntent.getStringExtra(KEY_SCHEDULE_DATE)!!
            if (viewModel.startDate != "") {
                val intentYear = convertStringToDate(viewModel.startDate, "yyyy-MM-dd", "yyyy")
                val intentMonth = convertStringToDate(viewModel.startDate, "yyyy-MM-dd", "MM")
                val intentDate = convertStringToDate(viewModel.startDate, "yyyy-MM-dd", "dd")
                Log.e("TAG", "Year => $intentYear,  Month => $intentMonth, Date => $intentDate")
                viewModel.startDateCalender.set(Calendar.YEAR, intentYear.toInt())
                viewModel.startDateCalender.set(Calendar.MONTH, intentMonth.toInt() - 1)
                viewModel.startDateCalender.set(Calendar.DAY_OF_MONTH, intentDate.toInt())
            }
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_END_DATE)) {
            binding.edtAddScheduleEndDate.text = mIntent.getStringExtra(KEY_SCHEDULE_END_DATE)!!
            viewModel.endDate = mIntent.getStringExtra(KEY_SCHEDULE_END_DATE)!!
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_END_TIME)) {
            binding.edtAddScheduleTime.text = mIntent.getStringExtra(KEY_SCHEDULE_END_TIME)!!
            time = mIntent.getStringExtra(KEY_SCHEDULE_END_TIME)!!
        }
        if (mIntent.hasExtra(KEY_SCHEDULE_SCHEDULE)) {
            val intentSchedule = mIntent.getStringExtra(KEY_SCHEDULE_SCHEDULE)
            if (intentSchedule!! == SELECTED_SCHEDULE) {
                binding.spAddScheduleSchedule.setSelection(0)
                if (mIntent.hasExtra(KEY_SCHEDULE_SCHEDULE_DAY)) {
                    scheduleDaysBufferId = mIntent.getStringExtra(KEY_SCHEDULE_SCHEDULE_DAY)!!
                }
            } else {
                binding.spAddScheduleSchedule.setSelection(1)
            }
        }
    }


    private fun onClickListeners() {
        binding.newAppInclude.toolbarBack.setOnClickListener {
            finish()
        }

        binding.edtAddScheduleStartDate.setOnClickListener {
            viewModel.startDatePicker(binding)
        }
        binding.edtAddScheduleEndDate.setOnClickListener {
            if (binding.edtAddScheduleStartDate.text.toString() == "") {
                Toast.makeText(this, "Please select start date first", Toast.LENGTH_SHORT).show()

            } else {
                viewModel.endDatePicker(binding)
            }
        }
        binding.edtAddScheduleTime.setOnClickListener {
            viewModel.timePicker(binding)
        }

        binding.btnAddScheduleSave.setOnClickListener {
            if (viewModel.isValidate(binding)) {
                if (editScheduleId != "") {
                    viewModel.requestApiEditSchedule(editScheduleId, binding)
                } else {
                    viewModel.requestApiScheduleEvent(binding)
                }

            }
        }
        setupUI(binding.newAppParent)

    }


    private fun scheduleSelectionSpinner() {
        scheduleArrayList.clear()
        scheduleArrayList.addAll(
            listOf(
                getString(R.string.schedule_schedule),
                getString(R.string.never_schedule)
            )
        )

        val scheduleSpinnerAdapter = ArrayAdapter(
            this@AddScheduleEventActivity,
            R.layout.row_dropdown,
            scheduleArrayList
        )

        scheduleSpinnerAdapter.setDropDownViewResource(R.layout.row_dropdown)
        scheduleSpinnerAdapter.notifyDataSetChanged()

        binding.spAddScheduleSchedule.adapter = scheduleSpinnerAdapter
        binding.spAddScheduleSchedule.setSelection(1)
        binding.spAddScheduleSchedule.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            viewModel.isSelected = SELECTED_SCHEDULE
                            binding.llScheduleSpChoose.visibility = View.VISIBLE
                            scheduleDaysSpinner()
                        }

                        1 -> {
                            viewModel.isSelected = SELECTED_NEVER
                            scheduleDaysBufferId = ""
                            scheduleDaysChooseName.clear()
                            binding.llScheduleSpChoose.visibility = View.GONE
                        }
                    }
                }

            }
    }

    private fun scheduleDaysSpinner() {
        scheduleDaysChooseId.clear()
        scheduleDaysChooseName.clear()
        scheduleDaysChooseName.addAll(
            listOf(
                getString(R.string.Monday),
                getString(R.string.Tuesday),
                getString(R.string.Wednesday),
                getString(R.string.Thursday),
                getString(R.string.Friday),
                getString(R.string.Saturday),
                getString(R.string.Sunday)
            )
        )

        scheduleDaysChooseId.addAll(
            listOf(
                "1", "2", "3", "4", "5", "6", "7"
            )
        )

        val multiSelectionAdapter: SecondDemoAdapter =
            object : SecondDemoAdapter(
                this, R.layout.item_spinner_dropdown,
                scheduleDaysChooseName
            ) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }
            }

        multiSelectionAdapter.setItems(scheduleDaysChooseName, scheduleDaysChooseId)
        binding.spAddScheduleChooseSchedule.setItems(
            scheduleDaysChooseName,
            scheduleDaysBufferId,
            scheduleDaysChooseId,
            getString(R.string.please_select_schedule),
            this
        )
    }

    override fun onItemsSelected(strings: List<String?>?) {

    }

    override fun onItemsSelectedIDIS(strings: List<String?>?) {

    }

    private fun observer() {
        viewModel.getScheduleResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    val response = it.value as ScheduleEventModel

                    if (response.data.isNotEmpty()) {
                        activitySchedules.clear()
                        response.data.mapTo(activitySchedules) { res ->
                            res.id.toString()
                        }

                        activtyTypeDataList.addAll(response.data)
                        chooseSelectionActivity = ActivityScheduleSelectionSearchAdapter(
                            this@AddScheduleEventActivity,
                            R.layout.simple_dropdown_item_line,
                            activtyTypeDataList
                        )
                        binding.actAddScheduleEventActivity.threshold = 3
                        binding.actAddScheduleEventActivity.setAdapter(
                            chooseSelectionActivity
                        )
                        if (intent.hasExtra(KEY_SCHEDULE_EVENT_ID)) {
                            viewModel.mActivityScheduleId =
                                intent.getStringExtra(KEY_SCHEDULE_EVENT_ID)!!
                            try {
                                if (activitySchedules.isNotEmpty()) {
                                    for (eventIndex in activitySchedules.indices) {
                                        if (activitySchedules[eventIndex] == viewModel.mActivityScheduleId) {
                                            binding.actAddScheduleEventActivity.setText(
                                                activtyTypeDataList[eventIndex].name
                                            )
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        viewModel.setUpEventSelection(binding)
                    }

                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
        viewModel.addScheduleResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    finish()

                }

                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }

                else -> {
                }
            }
        }
        viewModel.editScheduleResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    finish()

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