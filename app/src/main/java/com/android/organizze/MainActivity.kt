  package com.android.organizze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

  class MainActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        isButtonBackVisible = false;
        isButtonNextVisible = false;

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_1)
            .build());

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_2)
            .build());

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_3)
            .build());

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_4)
            .canGoForward(false)
            .build());

    }
}
