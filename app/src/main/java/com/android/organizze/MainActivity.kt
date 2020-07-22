  package com.android.organizze

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.organizze.activity.CadastroActivity
import com.android.organizze.activity.LoginActivity
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
            .build());

        addSlide(FragmentSlide.Builder()
            .background(android.R.color.white)
            .fragment(R.layout.intro_cadastro)
            .canGoForward(false)
            .build());
    }

      public fun btEntrar(view: View) {
          val intent = Intent(this,LoginActivity::class.java);
          startActivity(intent);
      }

      public fun btCadastrar(view: View) {
          val intent = Intent(this, CadastroActivity::class.java);
          startActivity(intent);
      }
}
