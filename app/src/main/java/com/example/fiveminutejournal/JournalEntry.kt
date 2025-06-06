package com.example.fiveminutejournal

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fiveminutejournal.databinding.FragmentSecondBinding
import com.example.fiveminutejournal.DataManager
import org.json.JSONObject
import java.io.File
import java.util.Calendar

import java.util.Date

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

fun isTimeInRange(date:Date, startHour :Int, endHour:Int):Boolean{
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hour = calendar.get(Calendar.HOUR_OF_DAY)  // 0-23
    return hour in startHour..endHour
}


class JournalEntry : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var dataManager: DataManager
    private var showMorning = true;
    private var showEvening = true;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        dataManager = DataManager(requireContext())
        val currentTime = Date();
        val todayEntry = dataManager.onRetrieveEntry(currentTime)

        showMorning = todayEntry.isNull("morning")  && isTimeInRange(currentTime, 0, 11);
        showEvening = todayEntry.isNull("evening") && isTimeInRange(currentTime, 12, 23)

        if (!showMorning && !showEvening){
            Toast.makeText(requireContext(), "You've completed your 5 minute journal today", Toast.LENGTH_LONG).show()

        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val gratitudeInput = view.findViewById<EditText>(R.id.gratitude_input)
        val intentionsInput = view.findViewById<EditText>(R.id.today_great_input)
        val affirmInput = view.findViewById<EditText>(R.id.affirmation_input)
        val morningSaveButton = view.findViewById<Button>(R.id.saveMorning)

        val highlightsInput = view.findViewById<EditText>(R.id.amazing_things_input)
        val improvementsInput = view.findViewById<EditText>(R.id.better_today_input)
        val eveningSaveButton = view.findViewById<Button>(R.id.saveEvening)


        val morningLayout = view.findViewById<LinearLayout>(R.id.morning_layout)
        val eveningLayout = view.findViewById<LinearLayout>(R.id.evening_layout)

        morningLayout.visibility = if (showMorning) View.VISIBLE else View.GONE;
        eveningLayout.visibility = if (showEvening) View.VISIBLE else View.GONE;

        morningSaveButton.setOnClickListener {

            // Create a JSON object
            val json = JSONObject()
            json.put("gratitude", gratitudeInput.text.toString())
            json.put("intentions", intentionsInput.text.toString())
            json.put("affirmation", affirmInput.text.toString())
            dataManager.onAddNewEntry(json, EntryType.kMorning);
            // Disable the button
            morningSaveButton.isEnabled = false
        }

        eveningSaveButton.setOnClickListener {

            // Create a JSON object
            val json = JSONObject()
            json.put("highlights", highlightsInput.text.toString())
            json.put("improvements", improvementsInput.text.toString())
            dataManager.onAddNewEntry(json, EntryType.kEvening);

            // Disable the button
            eveningSaveButton.isEnabled = false
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}