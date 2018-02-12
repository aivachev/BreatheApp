package com.example.andrew.breatheapp;

import android.content.Intent;
import android.graphics.pdf.PdfRenderer;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrew.breatheapp.util.Prefs;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;


public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView breatheTxt, timeTxt, sessionTxt, guideTxt;
    private Button startButton;
    private Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        breatheTxt = findViewById(R.id.breatheTakenTxt);
        timeTxt = findViewById(R.id.lastBreatheTXT);
        sessionTxt = findViewById(R.id.todayMinutesTXT);
        guideTxt = findViewById(R.id.guideTXT);
        prefs = new Prefs(this);

        startIntroAnimation();

        sessionTxt.setText(MessageFormat.format("{0} min today", prefs.getSessions()));
        breatheTxt.setText(MessageFormat.format("{0} Breaths", prefs.getBreaths()));
        timeTxt.setText(prefs.getDate());

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
    }

    private void startIntroAnimation() {
        ViewAnimator
                .animate(guideTxt)
                .scale(0,1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Breathe");
                    }
                })
                .start();
    }

    private void startAnimation() {
        ViewAnimator
                .animate(imageView)
                .alpha(0,1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideTxt.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(6)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideTxt.setText("Good job");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);


                        prefs.setSession(prefs.getSessions() + 1);
                        prefs.setBreaths(prefs.getBreaths() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long l) {
                                //TODO show tick
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();
                    }
                })
                .start();
    }
}
