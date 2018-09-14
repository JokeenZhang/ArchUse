package com.zzq.archuse;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zzq.archuse.data.CustomViewModel;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private CustomViewModel mCustomViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        final Button btnClick = findViewById(R.id.btn_click);
        final TextView tvText = findViewById(R.id.text_main);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomViewModel.setNotifiedText(tvText.getText().toString()).observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        btnClick.setText(s);
                    }
                });
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tvText.setText(getRandomString(4));
            }
        },5000,2000);
    }

    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
