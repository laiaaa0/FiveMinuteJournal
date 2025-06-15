package com.example.fiveminutejournal

import org.json.JSONObject
import java.util.Calendar
import java.util.Date

enum class JournalEntryStatus {
    NONE,
    MORNING_COMPLETE,
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

fun makeStatus(todayEntry : JSONObject, currentTime: Date) : JournalEntryStatus{

    val hasMorning = todayEntry.has("morning") && todayEntry.getString("morning")!=""&& todayEntry.getString("morning")!="{}"
    val hasEvening = todayEntry.has("evening")&& todayEntry.getString("evening")!="" && todayEntry.getString("evening")!="{}"

    if (hasMorning && hasEvening){
        return JournalEntryStatus.COMPLETE;
    }
    else if (hasMorning && !hasEvening){
        return JournalEntryStatus.MORNING_COMPLETE
    }
    else if (hasEvening && !hasMorning){
        return JournalEntryStatus.EVENING_COMPLETE
    }
    return JournalEntryStatus.NONE
}
fun toString(status:JournalEntryStatus):String{
    when(status){
        JournalEntryStatus.COMPLETE->return "all"
        JournalEntryStatus.NONE-> return "none"
        JournalEntryStatus.MORNING_COMPLETE->return "morning"
        JournalEntryStatus.EVENING_COMPLETE->return "evening"
    }
}