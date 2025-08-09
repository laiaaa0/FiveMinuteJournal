package com.example.fiveminutejournal

import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.TimePicker
import androidx.preference.DialogPreference
import java.util.Calendar

class TimePreference(
    context: Context,
    attrs: AttributeSet?
) : DialogPreference(context, attrs), TimePickerDialog.OnTimeSetListener {

    private var calendar = Calendar.getInstance()

    override fun onClick() {
        val time = getPersistedTime()
        calendar.set(Calendar.HOUR_OF_DAY, time.first)
        calendar.set(Calendar.MINUTE, time.second)

        TimePickerDialog(
            context,
            this,
            time.first,
            time.second,
            true
        ).show()
    }

    private fun getPersistedTime(): Pair<Int, Int> {
        val value = getPersistedString("08:00") // Default to 8:00 AM
        val parts = value.split(":")
        return Pair(parts[0].toInt(), parts[1].toInt())
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val time = String.format("%02d:%02d", hourOfDay, minute)
        persistString(time)
        summary = time
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        val time = getPersistedString(defaultValue as? String ?: "08:00")
        summary = time
    }
}
