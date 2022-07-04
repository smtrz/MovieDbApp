package com.zenjob.android.browsr.helpers

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelpers {

    fun convertStringToDate(date: String): Date? {
        if(date.equals("")){
            return null

        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.parse(date)

    }

    fun getCurrentDate(): Date? {
        return convertStringToDate(
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Date())
        )


    }

    fun getDaysDifference(fromDate: Date?, toDate: Date?): Int {
        return if (fromDate == null || toDate == null) 0 else ((fromDate.time - toDate.time) / (1000 * 60 * 60 * 24)).toInt()
    }

}