package com.example.fiveminutejournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


/**
 * A simple [Fragment] subclass.
 * Use the [EntryComplete.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntryComplete : Fragment() {
    private val args: EntryCompleteArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry_complete, container, false)

        val entryStatus : JournalEntryStatus = fromString(args.status)
        when(entryStatus){
            JournalEntryStatus.COMPLETE->{
                view.findViewById<TextView>(R.id.textView).setText(R.string.all_complete)
                view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.logo)
            }
            JournalEntryStatus.MORNING_COMPLETE->{
                view.findViewById<TextView>(R.id.textView).setText(R.string.morning_complete)
                view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.morning)

            }
            JournalEntryStatus.EVENING_COMPLETE->{
                view.findViewById<TextView>(R.id.textView).setText(R.string.evening_complete)
                view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.night)
            }
            JournalEntryStatus.TIME_FOR_MORNING_ENTRY->{}
            JournalEntryStatus.TIME_FOR_EVENING_ENTRY->{}
            JournalEntryStatus.NONE->{}
        }
        val homeButton = view.findViewById<Button>(R.id.homeButton)
        homeButton.setOnClickListener {
            val action = EntryCompleteDirections.actionEntryCompleteToLandingPage()
            findNavController().navigate(action)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()

    }
    override fun onDestroyView() {
        super.onDestroyView()

        // Show the toolbar again
        (activity as AppCompatActivity).supportActionBar?.show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EntryComplete.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EntryComplete()
    }
}