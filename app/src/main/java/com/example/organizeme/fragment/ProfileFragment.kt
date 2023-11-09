package com.example.organizeme.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.organizeme.R
import com.example.organizeme.databinding.ActivityBmiBinding
import com.example.organizeme.databinding.FragmentProfileBinding
import com.example.organizeme.databinding.FragmentToDoBinding
import com.example.organizeme.helper.SharedViewModel
import com.example.organizeme.ui.SettingsActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedViewModel: SharedViewModel



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
        val _view = inflater.inflate(R.layout.fragment_profile, container, false)
        val barChartTask = _view.findViewById<BarChart>(R.id.barChartTask)
        val barChartFocus = _view.findViewById<BarChart>(R.id.barChartFocus)
        val settingsBtn = _view.findViewById<MaterialButton>(R.id.settingsBtn)
        val arguments = arguments
        var bmiResultText = _view.findViewById<TextView>(R.id.bmiResultText)

        // inisialisasi SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.bmiResult.observe(viewLifecycleOwner, Observer { result ->
            bmiResultText.text = result
        })

        settingsBtn.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

        if (arguments != null){
            val bmiResult = arguments.getString("bmiResult", "")
            bmiResultText.text = bmiResult
        }



        setBarChartValues(barChartTask)
        setBarChartValuesTwo(barChartFocus)
        return _view
    }

    private fun setBarChartValues(barChart: BarChart) {

        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(1f, 5f))
        barEntries.add(BarEntry(2f, 7f))
        barEntries.add(BarEntry(3f, 3f))
        barEntries.add(BarEntry(4f, 2f))
        barEntries.add(BarEntry(5f, 0f))
        barEntries.add(BarEntry(6f, 1f))

        val barDataSet = BarDataSet(barEntries, "Task finished")
        barDataSet.color = resources.getColor(R.color.mixed_300)

        val data = BarData(barDataSet)

        barChart.data = data
        barChart.animateXY(1000, 1000)
    }

    private fun setBarChartValuesTwo(barChart: BarChart) {

        val barEntries = ArrayList<BarEntry>()
        barEntries.add(BarEntry(1f, 50f))
        barEntries.add(BarEntry(2f, 120f))
        barEntries.add(BarEntry(3f, 140f))
        barEntries.add(BarEntry(4f, 50f))
        barEntries.add(BarEntry(5f, 70f))
        barEntries.add(BarEntry(6f, 80f))

        val barDataSet = BarDataSet(barEntries, "Focus Time (Min)")
        barDataSet.color = resources.getColor(R.color.mixed_100)

        val data = BarData(barDataSet)

        barChart.data = data
        barChart.animateXY(1000, 1000)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}