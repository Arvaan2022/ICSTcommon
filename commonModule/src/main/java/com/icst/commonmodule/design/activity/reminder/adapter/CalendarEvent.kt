package com.icst.commonmodule.design.activity.reminder.adapter

import androidx.annotation.Keep
import java.io.Serializable

/**
 * 一个简单的bean
 * Created by huanghaibin on 2017/12/4.
 */
@Keep
data class CalendarEvent(var id: Int = 0,
                         var title: String? = null,
                         var content: String? = null,
                         var imgUrl: String? = null,
                         var desc: String? = null,
                         var date: String? = null,
                         var type: Int = 0) : Serializable