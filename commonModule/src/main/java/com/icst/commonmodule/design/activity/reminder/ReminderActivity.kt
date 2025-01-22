package com.icst.commonmodule.design.activity.reminder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.icst.commonmodule.R
import com.icst.commonmodule.app.App
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityReminderBinding
import com.icst.commonmodule.design.activity.addScheduleEvent.AddScheduleEventActivity
import com.icst.commonmodule.design.activity.reminder.adapter.CalandarEventAdapter
import com.icst.commonmodule.model.CommonResponse
import com.icst.commonmodule.model.ListScheduleModel
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
import com.icst.commonmodule.utils.convertDate
import com.icst.commonmodule.utils.convertStringToWelshDate

class ReminderActivity : ActivityBase(), CalendarView.OnMonthChangeListener, View.OnClickListener,
    CalendarView.OnViewChangeListener, CalendarView.OnCalendarSelectListener,
    CalandarEventAdapter.OnDeleteItemClick, CalandarEventAdapter.OnEditCLick {

    private val binding: ActivityReminderBinding by binding(R.layout.activity_reminder)
    val  viewModel= ReminderViewModel()

    

    private var currentMonth: String = ""
    private var currentYear: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel.activityBase.set(this)

        observer()
        initViews()

    }

    private fun initViews() {
        binding.include.toolbarBack.visibility = View.INVISIBLE
        binding.include.toolbarLogo.visibility = View.GONE
        binding.include.toolbarTitle.visibility = View.VISIBLE
        binding.include.toolbarProfile.visibility = View.INVISIBLE
        binding.include.toolbarTitle.text = buildString { append("Reminder") }
        initData()
        initListeners()
    }
    private fun initData() {

        currentYear = binding.calendarView.curYear.toString()
        currentMonth = binding.calendarView.curMonth.toString()

        binding.textMonth.text = buildString { 
            append(convertStringToWelshDate(currentMonth, "MM", "MMMM"))
            append(" ")
            append(currentYear)
        }
        binding.calendarView.setOnCalendarSelectListener(this)

        binding.calendarView.setOnCalendarSelectListener(this)
    }

    private fun initListeners() {
        binding.calendarView.setOnMonthChangeListener(this)
        binding.calendarView.setOnViewChangeListener(this)
        binding.icAddAppoinmnet.setOnClickListener(this)
        binding.recyclerCalendar.layoutManager = LinearLayoutManager(this)
        binding.include.toolbarBack.setOnClickListener(this)

    }


    override fun onMonthChange(year: Int, month: Int) {
        currentYear = year.toString()
        currentMonth = month.toString()
        viewModel.listScheduleApiCall(year = currentYear.toInt(), month = currentMonth.toInt())
        binding.textMonth.text = buildString { 
            append(convertStringToWelshDate(month.toString(), "MM", "MMMM"))
            append(" ")
            append(currentYear)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.icAddAppoinmnet -> {
                App.setAppointmentAdd(false)
                val intent = Intent(this, AddScheduleEventActivity::class.java)
                intent.putExtra(KEY_SCHEDULE_EVENT_NAME, "")
                intent.putExtra(KEY_SCHEDULE_DATE, "")
                intent.putExtra(KEY_SCHEDULE_END_DATE, "")
                intent.putExtra(KEY_SCHEDULE_END_TIME, "")
                intent.putExtra(KEY_SCHEDULE_LOCATION, "")
                intent.putExtra(KEY_SCHEDULE_SCHEDULE, "")
                intent.putExtra(KEY_SCHEDULE_SCHEDULE_DAY, "")
                intent.putExtra(KEY_SCHEDULE_ID, "")
                startActivity(intent)
            }
        }

    }

    override fun onViewChange(isMonthView: Boolean) {}

    override fun onCalendarOutOfRange(calendar: Calendar?) {}
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {}

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day

        return calendar
    }

    override fun onResume() {
        super.onResume()
        viewModel.listScheduleApiCall(year = currentYear.toInt(), month = currentMonth.toInt())
    }

   

    private fun initCalendarAppointemts(data: List<ListScheduleModel.DataItem>) {

        val map = HashMap<String, Calendar>()

        for (i in data.indices) {

            val year = convertDate(data[i].dayDate, "yyyy-MM-dd", "y").toInt()
            val month = convertDate(data[i].dayDate, "yyyy-MM-dd", "M").toInt()
            val day = convertDate(data[i].dayDate, "yyyy-MM-dd", "d").toInt()

            map[getSchemeCalendar(year, month, day).toString()] =
                getSchemeCalendar(year, month, day)
        }

        binding.calendarView.setSchemeDate(map)
    }


    override fun OnEditCLickitem(adapterPosition: Int) {
        val intent = Intent(this, AddScheduleEventActivity::class.java)
        intent.putExtra(KEY_SCHEDULE_INTENT_TYPE, SCHEDULE_INTENT_TYPE_EDIT)
        intent.putExtra(KEY_SCHEDULE_EVENT_NAME, viewModel.appoinmentList[adapterPosition].reminderName)
        intent.putExtra(KEY_SCHEDULE_DATE, viewModel.appoinmentList[adapterPosition].date)
        intent.putExtra(KEY_SCHEDULE_END_DATE, viewModel.appoinmentList[adapterPosition].endDate)
        intent.putExtra(KEY_SCHEDULE_END_TIME, viewModel.appoinmentList[adapterPosition].time)
        intent.putExtra(KEY_SCHEDULE_LOCATION, viewModel.appoinmentList[adapterPosition].location)
        intent.putExtra(KEY_SCHEDULE_SCHEDULE, viewModel.appoinmentList[adapterPosition].schedule)
        intent.putExtra(KEY_SCHEDULE_SCHEDULE_DAY, viewModel.appoinmentList[adapterPosition].scheduleDay)
        intent.putExtra(KEY_SCHEDULE_ID, viewModel.appoinmentList[adapterPosition].id.toString())
        intent.putExtra(KEY_SCHEDULE_EVENT_ID, viewModel.appoinmentList[adapterPosition].reminderId)
        startActivity(intent)
    }

    override fun onDeleteItem(adapterPosition: Int) {
        viewModel.alertDialog(adapterPosition)
    }

    override fun onDataView(adapterPosition: Int) {
        val intent = Intent(this, AddScheduleEventActivity::class.java)
        intent.putExtra(KEY_SCHEDULE_INTENT_TYPE, SCHEDULE_INTENT_TYPE_VIEW)
        intent.putExtra(KEY_SCHEDULE_EVENT_NAME, viewModel.appoinmentList[adapterPosition].reminderName)
        intent.putExtra(KEY_SCHEDULE_DATE, viewModel.appoinmentList[adapterPosition].date)
        intent.putExtra(KEY_SCHEDULE_END_DATE, viewModel.appoinmentList[adapterPosition].endDate)
        intent.putExtra(KEY_SCHEDULE_END_TIME, viewModel.appoinmentList[adapterPosition].time)
        intent.putExtra(KEY_SCHEDULE_LOCATION, viewModel.appoinmentList[adapterPosition].location)
        intent.putExtra(KEY_SCHEDULE_SCHEDULE, viewModel.appoinmentList[adapterPosition].schedule)
        intent.putExtra(KEY_SCHEDULE_SCHEDULE_DAY, viewModel.appoinmentList[adapterPosition].scheduleDay)
        intent.putExtra(KEY_SCHEDULE_ID, viewModel.appoinmentList[adapterPosition].id.toString())
        intent.putExtra(KEY_SCHEDULE_EVENT_ID, viewModel.appoinmentList[adapterPosition].reminderId)
        startActivity(intent)
    }

    private fun observer(){
        viewModel.listScheduleResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                    val response = it.value as ListScheduleModel

                    viewModel.appoinmentList.clear()
                    viewModel.appoinmentList.addAll(response.data)

                    if (response.data.isNotEmpty()) {
                        binding.recyclerCalendar.visibility = View.VISIBLE
                        binding.recyclerCalendar.adapter =
                            CalandarEventAdapter(
                                this,
                                response.data,
                                this,
                                this
                            )
                        (binding.recyclerCalendar.adapter as CalandarEventAdapter).notifyDataSetChanged()
                        initCalendarAppointemts(response.data)
                    } else {
                        binding.calendarView.clearSchemeDate()
                        binding.recyclerCalendar.visibility = View.GONE
                    }
                }
                is Resource.Failure -> {
                    Constant.handlerApiError(it, this, binding.root)
                }
                else -> {
                }
            }
        }
        viewModel.deleteScheduleResponse.observe(this){
            when (it) {
                is Resource.Success -> {
                    val response = it.value as CommonResponse

                    response.message?.let { it1 ->
                        showSnackBar(binding.recyclerCalendar,
                            it1, ACTIONSNACKBAR.DISMISS)
                    }

                    viewModel.listScheduleApiCall(year = currentYear.toInt(), month = currentMonth.toInt())


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