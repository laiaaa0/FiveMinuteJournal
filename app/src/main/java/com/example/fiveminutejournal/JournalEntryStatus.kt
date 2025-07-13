package com.example.fiveminutejournal

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.json.JSONObject
import java.util.Calendar
import java.util.Date

enum class JournalEntryStatus {
    NONE,
    TIME_FOR_MORNING_ENTRY,
    MORNING_COMPLETE,
    TIME_FOR_EVENING_ENTRY,
    EVENING_COMPLETE,
    COMPLETE
}

fun fromString(s:String):JournalEntryStatus {
    val lowerString = s.lowercase();
    if (lowerString.contains("morning")){
        return JournalEntryStatus.MORNING_COMPLETE;
    }
    else if (lowerString.contains("evening")){
        return JournalEntryStatus.EVENING_COMPLETE
    }
    else if (lowerString.contains("all")){
        return JournalEntryStatus.COMPLETE
    }
    return JournalEntryStatus.NONE;
}

fun isTimeInRange(date:Date, startHour :Int, endHour:Int):Boolean{
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hour = calendar.get(Calendar.HOUR_OF_DAY)  // 0-23
    return hour in startHour..endHour
}

fun getPreferredTime(key:String, default:String, prefs: SharedPreferences):Int{
    val time = prefs.getString(key,default)
    val parts = time!!.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()
    return hour
}

fun makeStatus(todayEntry : JSONObject, currentTime: Date, preferences:SharedPreferences) : JournalEntryStatus{

    val hasMorning = todayEntry.has("morning") && todayEntry.getString("morning")!=""&& todayEntry.getString("morning")!="{}"
    val hasEvening = todayEntry.has("evening")&& todayEntry.getString("evening")!="" && todayEntry.getString("evening")!="{}"

    val morningStart = getPreferredTime("morning_start","06:00", preferences)
    val morningEnd = getPreferredTime("morning_end", "14:00", preferences)
    val eveningStart = getPreferredTime("evening_start","19:00", preferences)
    val eveningEnd = getPreferredTime("evening_end", "23:00", preferences)

    val isTimeForMorning = isTimeInRange(currentTime, morningStart,morningEnd);
    val isTimeForEvening = isTimeInRange(currentTime, eveningStart,eveningEnd);

    if (hasMorning && hasEvening){
        return JournalEntryStatus.COMPLETE;
    }
    if (hasMorning && !isTimeForEvening){
       return JournalEntryStatus.MORNING_COMPLETE;
    }
    if (hasEvening){
        return JournalEntryStatus.EVENING_COMPLETE;
    }

    // None of the entries
    if (isTimeForMorning) {return JournalEntryStatus.TIME_FOR_MORNING_ENTRY;}
    if (isTimeForEvening) {return JournalEntryStatus.TIME_FOR_EVENING_ENTRY;}
    return JournalEntryStatus.NONE
}
fun toString(status:JournalEntryStatus):String{
    when(status){
        JournalEntryStatus.COMPLETE->return "all"
        JournalEntryStatus.NONE-> return "none"
        JournalEntryStatus.MORNING_COMPLETE->return "morning"
        JournalEntryStatus.EVENING_COMPLETE->return "evening"
        JournalEntryStatus.TIME_FOR_EVENING_ENTRY->return "enter_e"
        JournalEntryStatus.TIME_FOR_MORNING_ENTRY->return "enter_m"
    }
}