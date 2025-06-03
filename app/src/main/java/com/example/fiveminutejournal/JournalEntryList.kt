package com.example.fiveminutejournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.util.Calendar
import java.util.Date
/**
 * A simple [Fragment] subclass.
 * Use the [JournalEntryList.newInstance] factory method to
 * create an instance of this fragment.
 */
class JournalEntryList : Fragment() {

    private lateinit var dataManager: DataManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_journal_entry_list, container, false)

        dataManager = DataManager(requireContext())
        // Find the CalendarView
        val calendarView = view.findViewById<CalendarView>(R.id.calendarView)

        // Set date change listener
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth, 0, 0, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            try{



                val entry = dataManager.onRetrieveEntry(calendar.time)
                val parameter = calendar.time
                val parameter2 = entry
                val action = JournalEntryListDirections.actionJournalEntryListToOldJournalEntry()

                findNavController().navigate(action)
            }
            catch (e:Exception){
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }


}