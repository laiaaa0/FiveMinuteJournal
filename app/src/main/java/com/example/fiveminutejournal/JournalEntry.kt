package com.example.fiveminutejournal

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.fiveminutejournal.databinding.FragmentSecondBinding
import org.json.JSONObject
import java.io.File

import java.util.Date

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class JournalEntry : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
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



        morningSaveButton.setOnClickListener {

            // Create a JSON object
            val json = JSONObject()
            json.put("gratitude", gratitudeInput.text.toString())
            json.put("intentions", intentionsInput.text.toString())
            json.put("affirmation", affirmInput.text.toString())

            DataManager.onAddEntry(json, kMorning);
            // Disable the button
            morningSaveButton.isEnabled = false
        }

        eveningSaveButton.setOnClickListener {

            // Create a JSON object
            val json = JSONObject()
            json.put("highlights", highlightsInput.text.toString())
            json.put("improvements", improvementsInput.text.toString())

            DataManager.onAddEntry(json, kEvening);

            // Disable the button
            eveningSaveButton.isEnabled = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}