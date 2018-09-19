package com.zzq.archuse.bypaging;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.zzq.archuse.ArchUseApp;

public class FuliDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<FuliDataSource> mutableLiveData;
    private ArchUseApp mArchUseApp;
    public FuliDataSourceFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    public FuliDataSourceFactory(ArchUseApp app) {
        mArchUseApp = app;
        this.mutableLiveData = new MutableLiveData<>();
    }



    @Override
    public DataSource create() {
        FuliDataSource dataSource = new FuliDataSource();
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<FuliDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
