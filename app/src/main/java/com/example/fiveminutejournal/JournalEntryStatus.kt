package com.example.fiveminutejournal

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

fun makeStatus(todayEntry : JSONObject, currentTime: Date) : JournalEntryStatus{

    val hasMorning = todayEntry.has("morning") && todayEntry.getString("morning")!=""&& todayEntry.getString("morning")!="{}"
    val hasEvening = todayEntry.has("evening")&& todayEntry.getString("evening")!="" && todayEntry.getString("evening")!="{}"

    if (hasMorning && hasEvening){
        return JournalEntryStatus.COMPLETE;
    }
    else if (hasMorning){ // Does not have evening
        if (isTimeInRange(currentTime, 12,23)){
            return JournalEntryStatus.TIME_FOR_EVENING_ENTRY
        }
        return JournalEntryStatus.MORNING_COMPLETE
    }
    else if (hasEvening){ // Does not have morning
        return JournalEntryStatus.EVENING_COMPLETE
    }

    if (isTimeInRange(currentTime, 0,11)){
        return JournalEntryStatus.TIME_FOR_MORNING_ENTRY
    }
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