package com.zzq.archuse;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.zzq.archuse.data.CustomViewModel;
import com.zzq.archuse.retrofit.GankManager;
import com.zzq.archuse.retrofit.bean.FuliDataBean;
import com.zzq.archuse.retrofit.bean.MeizhiBean;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private CustomViewModel mCustomViewModel;
    private Handler mHandler;
    private TextView mTvText;
    private Button mBtnClick;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCustomViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mBtnClick = findViewById(R.id.btn_click);
        mTvText = findViewById(R.id.text_main);
        mHandler = new Handler();

        GankManager.getGankService().getFuliData(10, 1)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<FuliDataBean, ObservableSource<FuliDataBean.ResultsBean>>() {
                    @Override
                    public ObservableSource<FuliDataBean.ResultsBean> apply(FuliDataBean fuliDataBean) throws Exception {
                        return Observable.fromIterable(fuliDataBean.getResults());
                    }
                })
                .delay(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuliDataBean.ResultsBean>() {
                    @Override
                    public void accept(FuliDataBean.ResultsBean resultsBean) throws Exception {
                        Toast.makeText(MainActivity.this,resultsBean.getUrl(),Toast.LENGTH_LONG).show();
                        Log.e("ArchUse", "url = " + resultsBean.getUrl());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        mCustomViewModel.getLiveData().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                LogUtil.e("发生了改变 " + s);
                mBtnClick.setText(s);
            }
        });

        mHandler.postDelayed(mRunnable, 2000);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTvText.setText(getRandomString(4));
            mCustomViewModel.setChangeValue(mTvText.getText().toString().trim());
            mHandler.postDelayed(mRunnable, 2500);
        }
    };

    public String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
