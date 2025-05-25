package com.example.fiveminutejournal
import org.json.JSONObject
import java.io.File

import java.util.Date

enum class EntryType{
    kUnknown,
    kMorning,
    kEvening
}

class DataManager{
    fun onAddNewEntry(entry: JSONObject, type: EntryType){
        // Save text to internal storage
        val dateStr: String = SimpleDateFormat("yyyyMMdd").format(Date())
        val subdirectory = File(requireContext().filesDir, dateStr)

        // Create the directory if it doesn't exist
        if (!subdirectory.exists()) {
            subdirectory.mkdirs()
        }

        val file = File(subdirectory, "morning_$dateStr.json")
        file.writeText(json.toString())

    }
    fun onRetrieveEntry(date : Date){

        // Save text to internal storage
        val dateStr: String = SimpleDateFormat("yyyyMMdd").format(date)
        val subdirectory = File(requireContext().filesDir, dateStr)

        // Create the directory if it doesn't exist
        if (!subdirectory.exists()) {
            throw("No entries")
        }

        val morning_file = File(subdirectory, "morning_$dateStr.json")
        val morningJson = JSONObject(); // file.readText()

        val evening_file = File(subdirectory, "evening_$dateStr.json")
        val eveningJson = JSONObject(); // file.readText()

        return JSONObject(
            "morning":morningJson,
            "evening":eveningJson
        )

    }
    fun getAvailableEntries(){
        val subdirectory = requireContext().filesDir
        if (!subdirectory.exists()) {
            return {}
        }
        // Iterate over directories
        val listDirs = subdirectory.listDirectoryEntries();

        val availableEntries = {}
        for dir in listDirs{
            availableEntries.add(SimpleDateFormat("yyyyMMdd").parse(dir))
        }

        val morning_file = File(subdirectory, "morning_$dateStr.json")
        val morningJson = JSONObject(); // file.readText()

        val evening_file = File(subdirectory, "evening_$dateStr.json")
        val eveningJson = JSONObject(); // file.readText()

        return JSONObject(
            "morning":morningJson,
            "evening":eveningJson
        )

    }
}