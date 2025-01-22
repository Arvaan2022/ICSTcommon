package com.icst.commonmodule.design.activity.addScheduleEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icst.commonmodule.R
import com.icst.commonmodule.common.ActivityBase
import com.icst.commonmodule.databinding.ActivityAddScheduleEventBinding
import com.icst.commonmodule.design.activity.addScheduleEvent.adapter.ActivityScheduleSelectionSearchAdapter
import com.icst.commonmodule.model.ScheduleEventModel
import com.icst.commonmodule.repository.addScheduleEvent.AddScheduleEventRepository
import com.icst.commonmodule.retrofit.Resource
import com.icst.commonmodule.utils.Constant
import com.icst.commonmodule.utils.convertDate
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddScheduleEventViewModel : ViewModel() {
    private var addScheduleRepository = AddScheduleEventRepository.getInstance()
    val activityBase: ObservableField<ActivityBase> = ObservableField()
    var mActivityScheduleId = ""
    var startDate: String = ""
    var endDate: String = ""
    var isSelected = Constant.SELECTED_SCHEDULE
    var mActivityScheduleName = ""
     var startDateCalender: Calendar = Calendar.getInstance()

    private val _getScheduleResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val getScheduleResponse: LiveData<Resource<Any?>>
        get() {
            return _getScheduleResponse
        }

    private val _addScheduleResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val addScheduleResponse: LiveData<Resource<Any?>>
        get() {
            return _addScheduleResponse
        }

    private val _editScheduleResponse: MutableLiveData<Resource<Any?>> = MutableLiveData()
    val editScheduleResponse: LiveData<Resource<Any?>>
        get() {
            return _editScheduleResponse
        }


    fun getScheduleApiCall() {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _getScheduleResponse.value = addScheduleRepository.getScheduleApiCall(activityBase.get()!!)
//            activityBase.get()!!.dismissProgress()
        }
    }

    private fun addScheduleApiCall(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        scheduleDay: String
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _addScheduleResponse.value = addScheduleRepository.addScheduleApiCall(
                reminder = reminder,
                location = location,
                date = date,
                endDate = endDate,
                time = time,
                schedule = schedule,
                scheduleDay = scheduleDay,
                other = other,
                context= activityBase.get()!!
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    private fun editScheduleApiCall(
        reminder: String,
        location: String,
        date: String,
        endDate: String,
        time: String,
        schedule: String,
        other: String,
        id: String,
        scheduleDay: String
    ) {
//        activityBase.get()!!.showProgress()
        viewModelScope.launch {
            _editScheduleResponse.value = addScheduleRepository.editScheduleApiCall(
                reminder = reminder,
                location = location,
                date = date,
                endDate = endDate,
                time = time,
                schedule = schedule,
                scheduleDay = scheduleDay,
                other = other,
                id = id,
                context=activityBase.get()!!
            )
//            activityBase.get()!!.dismissProgress()
        }
    }

    fun setUpToolBar(binding: ActivityAddScheduleEventBinding) {
        binding.newAppInclude.toolbarBack.visibility = View.VISIBLE
        binding.newAppInclude.toolbarLogo.visibility = View.GONE
        binding.newAppInclude.toolbarTitle.visibility = View.VISIBLE
        binding.newAppInclude.toolbarProfile.visibility = View.INVISIBLE
        binding.newAppInclude.toolbarTitle.text =
            activityBase.get()!!.getString(R.string.nav_reminder)
    }


    fun setUpEventSelection(binding: ActivityAddScheduleEventBinding) {
        binding.actAddScheduleEventActivity.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, arg2, _ ->

                mActivityScheduleId = ""
                val adapter =
                    binding.actAddScheduleEventActivity.adapter as ActivityScheduleSelectionSearchAdapter
                mActivityScheduleId =
                    (adapter.getItem(arg2) as ScheduleEventModel.Data).id.toString()


                val imm = activityBase.get()!!
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    binding.actAddScheduleEventActivity.windowToken,
                    0
                )
            }
    }

     fun requestApiScheduleEvent(binding: ActivityAddScheduleEventBinding) {
        val selectedDays: String = if (binding.spAddScheduleChooseSchedule
                .selectedStringDays.isNotEmpty()
        ) {
            binding.spAddScheduleChooseSchedule
                .selectedStringDays.joinToString(",")
        } else {
            ""
        }

        mActivityScheduleId.ifEmpty {
            mActivityScheduleId = ""
        }

        addScheduleApiCall(
            reminder = mActivityScheduleId,
            location = binding.edtAddScheduleLocation.text.toString(),
            date = startDate,
            endDate = endDate,
            time = AddScheduleEventActivity.time,
            schedule = isSelected,
            other = binding.actAddScheduleEventActivity.text.toString(),
            scheduleDay = selectedDays
        )
    }

     fun requestApiEditSchedule(
        currentId: String,
        binding: ActivityAddScheduleEventBinding
    ) {
        val selectedDays: String =
            if (binding.spAddScheduleChooseSchedule.selectedStringDays.isNotEmpty()) {
                binding.spAddScheduleChooseSchedule
                    .selectedStringDays.joinToString(",")
            } else {
                ""
            }
        if (mActivityScheduleId.isEmpty()) {
            mActivityScheduleId = ""
        } else {
            if (mActivityScheduleName != binding.actAddScheduleEventActivity.text.toString()) {
                mActivityScheduleId = ""
            } else {
                mActivityScheduleId
            }
        }

        editScheduleApiCall(
            reminder = mActivityScheduleId,
            location = binding.edtAddScheduleLocation.text.toString(),
            date = startDate,
            endDate = endDate,
            time = AddScheduleEventActivity.time,
            schedule = isSelected,
            other = binding.actAddScheduleEventActivity.text.toString(),
            id = currentId,
            scheduleDay = selectedDays
        )

    }
    private lateinit var mCalendar: Calendar
     fun startDatePicker( binding: ActivityAddScheduleEventBinding) {
        val todayCalender = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        if (binding.edtAddScheduleStartDate.text != "") {
            val setYear = convertDate(
                binding.edtAddScheduleStartDate.text.toString(),
                "yyyy-MM-dd", "yyyy"
            )
            val setMonth = convertDate(
                binding.edtAddScheduleStartDate.text.toString(),
                "yyyy-MM-dd", "MM"
            )
            val setDay = convertDate(
                binding.edtAddScheduleStartDate.text.toString(),
                "yyyy-MM-dd", "dd"
            )
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    startDateCalender.set(Calendar.YEAR, year)
                    startDateCalender.set(Calendar.MONTH, monthOfYear)
                    startDateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.edtAddScheduleStartDate.text = sdf.format(startDateCalender.time)
                    startDate = sdf.format(startDateCalender.time)
                }

            val dialog = DatePickerDialog(
                activityBase.get()!!,
                datePickerOnDataSetListener,
                setYear.toInt(),
                setMonth.toInt() - 1,
                setDay.toInt()
            )
            dialog.datePicker.minDate = todayCalender.timeInMillis
            dialog.show()
        } else {
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    startDateCalender.set(Calendar.YEAR, year)
                    startDateCalender.set(Calendar.MONTH, monthOfYear)
                    startDateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.edtAddScheduleStartDate.text = sdf.format(startDateCalender.time)
                    startDate = sdf.format(startDateCalender.time)
                }

            val dialog = DatePickerDialog(
                activityBase.get()!!,
                datePickerOnDataSetListener,
                startDateCalender.get(Calendar.YEAR),
                startDateCalender.get(Calendar.MONTH),
                startDateCalender.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.minDate = todayCalender.timeInMillis
            dialog.show()
        }
    }

     fun endDatePicker( binding: ActivityAddScheduleEventBinding) {
        mCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        if (binding.edtAddScheduleEndDate.text != "") {
            val setYear = convertDate(
                binding.edtAddScheduleEndDate.text.toString(),
                "yyyy-MM-dd", "yyyy"
            )
            val setMonth = convertDate(
                binding.edtAddScheduleEndDate.text.toString(),
                "yyyy-MM-dd", "MM"
            )
            val setDay = convertDate(
                binding.edtAddScheduleEndDate.text.toString(),
                "yyyy-MM-dd", "dd"
            )
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    mCalendar.set(Calendar.YEAR, year)
                    mCalendar.set(Calendar.MONTH, monthOfYear)
                    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.edtAddScheduleEndDate.text = sdf.format(mCalendar.time)
                    endDate = sdf.format(mCalendar.time)
                }

            val dialog = DatePickerDialog(
                activityBase.get()!!,
                datePickerOnDataSetListener,
                setYear.toInt(),
                setMonth.toInt() - 1,
                setDay.toInt()
            )
            dialog.datePicker.minDate = startDateCalender.timeInMillis
            dialog.show()
        } else {
            val datePickerOnDataSetListener =
                DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                    mCalendar.set(Calendar.YEAR, year)
                    mCalendar.set(Calendar.MONTH, monthOfYear)
                    mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    binding.edtAddScheduleEndDate.text = sdf.format(mCalendar.time)
                    endDate = sdf.format(mCalendar.time)
                }

            val dialog = DatePickerDialog(
                activityBase.get()!!,
                datePickerOnDataSetListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.minDate = startDateCalender.timeInMillis
            dialog.show()
        }

    }

     fun timePicker(binding: ActivityAddScheduleEventBinding) {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        if (binding.edtAddScheduleTime.text != "") {
            val selectedHour = convertDate(
                binding.edtAddScheduleTime.text.toString(),
                "hh:mm a", "HH"
            )
            val selectedMinute = convertDate(
                binding.edtAddScheduleTime.text.toString(),
                "hh:mm a", "mm"
            )

            val timePickerDialog = TimePickerDialog(
                activityBase.get()!!,
                { _, hourOfDay, minutes ->

                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minutes)
                    /* if (::startDateCalender.isInitialized) {

                     }*/
                    selectedTime.set(
                        Calendar.DAY_OF_YEAR,
                        startDateCalender.get(Calendar.DAY_OF_YEAR)
                    )

                    if (selectedTime.timeInMillis < calendar.timeInMillis) {
                        activityBase.get()!!.showSnackBar(
                            binding.newAppParent,
                           "Please select valid time.",
                            ActivityBase.ACTIONSNACKBAR.DISMISS
                        )
                    } else {
                        binding.edtAddScheduleTime.text = simpleDateFormat.format(selectedTime.time)
                        AddScheduleEventActivity.time = simpleDateFormat.format(selectedTime.time)
                    }
                }, selectedHour.toInt(), selectedMinute.toInt(), false
            )
            timePickerDialog.show()
        } else {
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                activityBase.get()!!,
                { _, hourOfDay, minutes ->

                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minutes)

                    selectedTime.set(
                        Calendar.DAY_OF_YEAR,
                        startDateCalender.get(Calendar.DAY_OF_YEAR)
                    )

                    if (selectedTime.timeInMillis < calendar.timeInMillis) {
                        activityBase.get()!!.showSnackBar(
                            binding.newAppParent,
                           "new_appointment_select_valid_time",
                            ActivityBase.ACTIONSNACKBAR.DISMISS
                        )
                    } else {
                        binding.edtAddScheduleTime.text = simpleDateFormat.format(selectedTime.time)
                        AddScheduleEventActivity.time = simpleDateFormat.format(selectedTime.time)
                    }
                }, currentHour, currentMinute, false
            )
            timePickerDialog.show()
        }

    }


     fun isValidate(binding: ActivityAddScheduleEventBinding): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var dateStart = Date()
        var dateEnd = Date()
        if (startDate != "" && endDate != "") {
            dateStart = sdf.parse(startDate)!!
            dateEnd = sdf.parse(endDate)!!
        }
        val selectedDays = binding.spAddScheduleChooseSchedule.selectedStringIds.joinToString(",")
        return when {
            binding.actAddScheduleEventActivity.text.toString().isEmpty() -> {
                Toast.makeText(activityBase.get()!!, "Please select activity or event", Toast.LENGTH_SHORT).show()
                false
            }

            binding.edtAddScheduleLocation.text.toString().isEmpty() -> {
                Toast.makeText(activityBase.get()!!, "Please select location", Toast.LENGTH_SHORT).show()
                false
            }

            binding.edtAddScheduleStartDate.text.toString().isEmpty() -> {
                Toast.makeText(activityBase.get()!!, "Please select date", Toast.LENGTH_SHORT).show()
                false
            }

            binding.edtAddScheduleStartDate.text.toString().isEmpty() -> {
                Toast.makeText(activityBase.get()!!, "Please select date", Toast.LENGTH_SHORT).show()
                false
            }

            binding.edtAddScheduleTime.toString().isEmpty() -> {
                Toast.makeText(activityBase.get()!!, "Please enter time", Toast.LENGTH_SHORT).show()
                false
            }

            isSelected == Constant.SELECTED_SCHEDULE && selectedDays == "" -> {
                Toast.makeText(activityBase.get()!!, "Please select days", Toast.LENGTH_SHORT).show()
                false
            }

            startDate != "" && endDate != "" && dateStart.after(dateEnd) -> {
                Toast.makeText(activityBase.get()!!, "Please select end date after to start date", Toast.LENGTH_SHORT).show()
                false
            }

            else -> {
                true
            }
        }
    }


}