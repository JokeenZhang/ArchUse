package com.zzq.archuse.bypaging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.zzq.archuse.ArchUseApp;
import com.zzq.archuse.retrofit.bean.MeizhiBean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FuliDataViewModel extends ViewModel {

    private Executor executor;
    private LiveData<PagedList<MeizhiBean>> fuliResultLiveData;
    private ArchUseApp mArchUseApp;

    public FuliDataViewModel() {
        init();
    }

    public FuliDataViewModel(ArchUseApp app) {
        mArchUseApp = app;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        FuliDataSourceFactory fuliDataSourceFactory = new FuliDataSourceFactory();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        fuliResultLiveData = (new LivePagedListBuilder(fuliDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<MeizhiBean>> getFuliResultLiveData() {
        return fuliResultLiveData;
    }
}
