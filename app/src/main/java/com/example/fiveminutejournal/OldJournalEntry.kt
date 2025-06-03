package com.example.fiveminutejournal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "date"
private const val ARG_PARAM2 = "content"

/**
 * A simple [Fragment] subclass.
 * Use the [OldJournalEntry.newInstance] factory method to
 * create an instance of this fragment.
 */
class OldJournalEntry : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val args: OldJournalEntryArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_old_journal_entry, container, false)

        val jsonObject = JSONObject(args.content)
        if (jsonObject.has("morning")){
            val morningObject = JSONObject(jsonObject.getString("morning"))

            val gratitude = morningObject.optString("gratitude")
            val intentions = morningObject.optString("intentions")
            val affirmation = morningObject.optString("affirmation")
            view.findViewById<TextView>(R.id.gratitude_text).setText(gratitude)
            view.findViewById<TextView>(R.id.today_great_text).setText(intentions)
            view.findViewById<TextView>(R.id.affirmation_text).setText(affirmation)

        }
        if (jsonObject.has("evening")){
            val eveningObject = JSONObject(jsonObject.getString("evening"))
            val highlight = eveningObject.optString("highlights")
            val improvement = eveningObject.optString("improvements")

            view.findViewById<TextView>(R.id.amazing_things_text).setText(highlight)
            view.findViewById<TextView>(R.id.better_today_text).setText(improvement)

        }
        //Toast.makeText(requireContext(), "Date is ${args.date} and content is ${args.content}", Toast.LENGTH_LONG).show()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OldJournalEntry.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OldJournalEntry().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}