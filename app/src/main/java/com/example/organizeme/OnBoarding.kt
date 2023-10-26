package com.example.organizeme

import SliderAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager.widget.ViewPager
import com.example.organizeme.R
import com.google.android.material.progressindicator.BaseProgressIndicator
import com.google.android.material.slider.Slider

class OnBoarding : AppCompatActivity() {
    // variables
    private lateinit var viewPager: ViewPager
    private lateinit var dotsLayout: LinearLayout
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var dots: Array<TextView?>
    private lateinit var nextBtn: Button
    private lateinit var skipBtn: Button
    private lateinit var startBtn : Button
    private var animation: Animation? = null
    private var currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val sliderAdapter = SliderAdapter(this)
        setContentView(R.layout.activity_on_boarding)

        // hooks
        viewPager = findViewById(R.id.slider)
        dotsLayout = findViewById(R.id.dots)
        nextBtn = findViewById(R.id.nextBtn)
        skipBtn = findViewById(R.id.skipBtn)
        startBtn = findViewById(R.id.startBtn)

        // call adapter
        viewPager.adapter = sliderAdapter

        // dots
        addDots(0)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                addDots(position)

                when (position) {
                    0,1 -> startBtn.visibility = View.INVISIBLE
                    else -> {
                        animation = AnimationUtils.loadAnimation(this@OnBoarding, androidx.appcompat.R.anim.abc_popup_enter)
                        startBtn.startAnimation(animation)
                        startBtn.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // no operation
            }
        })

        // button function
        nextBtn.setOnClickListener {
            viewPager.currentItem++
        }

        skipBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        startBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun addDots(position: Int) {
        dotsLayout.removeAllViews()

        val numPages = 3 // Replace with the actual number of pages

        // initialize dots array
        dots = Array(numPages) { null }

        for (i in 0 until numPages) {
            val dot = TextView(this)
            dot.text = "•"
            dot.textSize = 35f

            // Set a padding or margin between dots (optional)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dot.layoutParams = params

            dotsLayout.addView(dot)
            dots[i] = dot

            if (i == position) {
                dots[i]?.setTextColor(getColor(R.color.primaryColor))
            } else {
                dots[i]?.setTextColor(getColor(R.color.dark_300))
            }
        }
    }

}
