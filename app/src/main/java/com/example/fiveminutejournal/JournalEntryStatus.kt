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
    val isTimeForMorning = isTimeInRange(currentTime, 0,11);
    val isTimeForEvening = isTimeInRange(currentTime, 12,23);

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