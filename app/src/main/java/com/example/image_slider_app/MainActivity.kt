 package com.example.image_slider_app

import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

 class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private val sliderHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager2 = findViewById(R.id.viewpager_slider)

        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.addAll(
            arrayOf(SliderItem(R.drawable.user1),
                    SliderItem(R.drawable.user_2),
                    SliderItem(R.drawable.user_3),
                    SliderItem(R.drawable.user_5),
                    SliderItem(R.drawable.user_6),
                    SliderItem(R.drawable.user_7),
                    SliderItem(R.drawable.user_8)
            )
        )

        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(30))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r*0.25f
        }

        viewPager2.setPageTransformer(compositePageTransformer)

        viewPager2.registerOnPageChangeCallback(
            object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 3000)
                }
            }
        )
    }

     private val sliderRunnable = Runnable {
         viewPager2.currentItem += 1
     }
}