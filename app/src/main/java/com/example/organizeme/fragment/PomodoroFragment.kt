package com.example.organizeme.fragment

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import com.example.organizeme.R
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PomodoroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PomodoroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var timerDurationInMillis: Long = 0
    private var timeCountDown : CountDownTimer? = null
    private var isTimerRunning = false
    private var pauseOffSet: Long = 0
    private var currentProgressBar: Int = 0
    private var isTimerPaused = false

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
        val _view = inflater.inflate(R.layout.fragment_pomodoro, container, false)
        val playBtn = _view.findViewById<ImageButton>(R.id.playBtn)
        val stopBtn = _view.findViewById<ImageButton>(R.id.stopBtn)
        val pauseBtn = _view.findViewById<ImageButton>(R.id.pauseBtn)
        val resumeBtn = _view.findViewById<ImageButton>(R.id.resumeBtn)
        val progressBar = _view.findViewById<ProgressBar>(R.id.pbTimer)
        val timerText = _view.findViewById<TextView>(R.id.timerText)
        val textFocused = _view.findViewById<TextView>(R.id.textFocused)

        timerText.text = "25:00"

        playBtn.setOnClickListener {
            startTimerSetup( progressBar,  25, timerText )
            playBtn.isInvisible = true
            pauseBtn.isInvisible = false
            stopBtn.isInvisible = false
            textFocused.text = "Stay Focused"
        }

        pauseBtn.setOnClickListener {
            pause(pauseBtn, progressBar)
            pauseBtn.isInvisible = true
            resumeBtn.isInvisible = false
            textFocused.text = "Take Your Time"
        }

        resumeBtn.setOnClickListener {
            resumeTimer(resumeBtn, timerText, progressBar, pauseOffSet)
            resumeBtn.isInvisible = true
            pauseBtn.isInvisible = false
            textFocused.text = "Stay Focused"
        }


        stopBtn.setOnClickListener {
            stopTimer(timerText, progressBar)
            stopBtn.isInvisible = true
            pauseBtn.isInvisible = true
            playBtn.isInvisible = false
            resumeBtn.isInvisible = true
            textFocused.text = "Start Pomodoro"
        }

        return _view
    }

    private fun startTimer(timerTextView: TextView, progressBar: ProgressBar, durationInMillis: Long) {
        val initialDuration = if (isTimerPaused) pauseOffSet else durationInMillis
        timeCountDown = object : CountDownTimer(initialDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = millisUntilFinished
                val elapsedMillis = initialDuration - millisUntilFinished
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                currentProgressBar = ((elapsedMillis / initialDuration.toDouble()) * progressBar.max).toInt()
                progressBar.progress = currentProgressBar
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                progressBar.progress = progressBar.max
            }
        }.start()

        isTimerRunning = true
        isTimerPaused = false
    }

    private fun startTimerSetup(progressBar: ProgressBar, minutes: Int, timerTextView: TextView) {
        timerDurationInMillis = TimeUnit.MINUTES.toMillis(minutes.toLong())
        startTimer(timerTextView ,progressBar, timerDurationInMillis - (currentProgressBar * timerDurationInMillis / progressBar.max))
    }

    private fun pause(buttonPause: ImageButton, progressBar: ProgressBar) {
        if (isTimerRunning) {
            timeCountDown?.cancel()
            isTimerRunning = false
            isTimerPaused = true
            buttonPause.setImageResource(R.drawable.pause)
        }
    }

    private fun resumeTimer(buttonResume: ImageButton, timerTextView: TextView, progressBar: ProgressBar, pauseOffSet: Long) {
        if (isTimerPaused) {
            isTimerRunning = true
            isTimerPaused = false

            val remainingTime = pauseOffSet
            val initialDuration = timerDurationInMillis
            val elapsedMillis = initialDuration - remainingTime

            // Calculate the progress based on remaining time
            currentProgressBar =
                ((elapsedMillis / initialDuration.toDouble()) * progressBar.max).toInt()

            startTimer(timerTextView, progressBar, remainingTime)

            val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(remainingTime) % 60
            timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            progressBar.progress = currentProgressBar
            buttonResume.setImageResource(R.drawable.play)
        }
    }

    private fun stopTimer(timerTextView: TextView, progressBar: ProgressBar) {
        if (timeCountDown != null) {
            timeCountDown!!.cancel()
            isTimerRunning = false
            progressBar.progress = progressBar.min
            timerTextView.text = "25:00"
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PomodoroFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PomodoroFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}