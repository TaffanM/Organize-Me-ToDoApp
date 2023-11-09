package com.example.organizeme.fragment

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation.AnimationListener
import android.widget.CheckBox
import android.widget.TextView
import com.example.organizeme.R
import com.example.organizeme.ui.BmiActivity
import com.example.organizeme.ui.DevActivity
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val _view = inflater.inflate(R.layout.fragment_home, container, false)
        val firstCheck = _view.findViewById<CheckBox>(R.id.firstCheck)
        val secondCheck = _view.findViewById<CheckBox>(R.id.secondCheck)
        val thirdCheck = _view.findViewById<CheckBox>(R.id.thirdCheck)
        val fourthCheck = _view.findViewById<CheckBox>(R.id.fourthCheck)
        val buttonBMI = _view.findViewById<MaterialButton>(R.id.bmiBtn)
        val buttonDevProfile = _view.findViewById<MaterialButton>(R.id.profileDevBtn)

        buttonBMI.setOnClickListener {
            val intent = Intent(requireContext(), BmiActivity::class.java)
            startActivity(intent)
        }

        buttonDevProfile.setOnClickListener {
            val intent = Intent(requireContext(), DevActivity::class.java)
            startActivity(intent)
        }

        firstCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            applyStrikethrough(firstCheck, isChecked)
        }

        secondCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            applyStrikethrough(secondCheck, isChecked)
        }

        thirdCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            applyStrikethrough(thirdCheck, isChecked)
        }

        fourthCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            applyStrikethrough(fourthCheck, isChecked)
        }


        // Inflate the layout for this fragment
        return _view
    }



    private fun applyStrikethrough(checkBox: CheckBox, isChecked: Boolean){
        val paint = checkBox.paintFlags
        if (isChecked){
            checkBox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or paint
        } else {
            checkBox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG.inv() and paint
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}