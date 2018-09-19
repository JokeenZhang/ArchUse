package com.zzq.archuse;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzq.archuse.bypaging.FuliDataViewModel;
import com.zzq.archuse.retrofit.bean.FuliDataBean;
import com.zzq.archuse.view.MainPictureAdapter;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private RecyclerView rvMainPict;
    private MainPictureAdapter mPictureAdapter;
    private LinearLayoutManager mLayoutManager;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMainPict = findViewById(R.id.rv_main_picture);

        mLayoutManager = new LinearLayoutManager(this);
        rvMainPict.setLayoutManager(mLayoutManager);
        mPictureAdapter = new MainPictureAdapter();
        rvMainPict.setAdapter(mPictureAdapter);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(1000);
        defaultItemAnimator.setRemoveDuration(1000);
        rvMainPict.setItemAnimator(defaultItemAnimator);

        FuliDataViewModel fuliDataViewModel = ViewModelProviders.of(this).get(FuliDataViewModel.class);
        fuliDataViewModel.getFuliResultLiveData().observe(this, new Observer<PagedList<FuliDataBean.ResultsBean>>() {
            @Override
            public void onChanged(@Nullable PagedList<FuliDataBean.ResultsBean> resultsBeans) {
                mPictureAdapter.submitList(resultsBeans);
            }
        });

        rvMainPict.setAdapter(mPictureAdapter);
    }

}
