package com.example.fiveminutejournal
import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat

import java.util.Date
import java.util.Locale

enum class EntryType{
    kUnknown,
    kMorning,
    kEvening
}

class DataManager(private val context: Context){

    fun onAddNewEntry(entry: JSONObject, type: EntryType){
        // Save text to internal storage
        val dateStr: String = SimpleDateFormat("yyyyMMdd").format(Date())
        val subdirectory = File(context.filesDir, dateStr)

        // Create the directory if it doesn't exist
        if (!subdirectory.exists()) {
            subdirectory.mkdirs()
        }
        var fileType = ""
        if (type == EntryType.kMorning){
            fileType = "morning"
        }
        else if (type == EntryType.kEvening){
            fileType = "evening"
        }
        else {
            throw IllegalArgumentException("Cannot recognize $type")
        }
        val file = File(subdirectory, fileType+"_$dateStr.json")
        file.writeText(entry.toString())

    }
    fun onRetrieveEntry(date : Date): JSONObject {

        // Save text to internal storage
        val dateStr: String = SimpleDateFormat("yyyyMMdd").format(date)
        val subdirectory = File(context.filesDir, dateStr)

        // Create the directory if it doesn't exist
        if (!subdirectory.exists()) {
            throw IllegalArgumentException("No entries")
        }

        val morningFile = File(subdirectory, "morning_$dateStr.json")


        val morningJson = if (morningFile.exists()) JSONObject(morningFile.readText()) else JSONObject();

        val eveningFile = File(subdirectory, "evening_$dateStr.json")
        val eveningJson = if (eveningFile.exists()) JSONObject(eveningFile.readText()) else JSONObject()
        val result = JSONObject()
        result.put("morning", morningJson)
        result.put("evening", eveningJson)
        return result;

    }
    fun getAvailableEntries(): List<Date> {
        val subdirectory = context.filesDir
        if (!subdirectory.exists() || !subdirectory.isDirectory) {
            throw IllegalArgumentException("Invalid directory path: $subdirectory")
        }


        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val availableEntries = mutableListOf<Date>()
        val listDirs = subdirectory.listFiles()?.filter { it.isDirectory } ?: emptyList()

        for (dir in listDirs) {
            try {
                val date = dateFormat.parse(dir.name)
                if (date != null) {
                    availableEntries.add(date)
                }
            } catch (e: Exception) {
                Log.w("getAvailableEntries", "Skipping ${dir.name}: ${e.message}")
            }
        }
        return availableEntries

    }
}