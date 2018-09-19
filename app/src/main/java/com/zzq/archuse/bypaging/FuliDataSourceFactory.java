package com.zzq.archuse.bypaging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PagedList;

import com.zzq.archuse.ArchUseApp;
import com.zzq.archuse.retrofit.bean.FuliDataBean;

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

    public void setLiveData(LiveData<PagedList<FuliDataBean.ResultsBean>> pagedListLiveData) {

    }

    @Override
    public DataSource create() {
        FuliDataSource dataSource = new FuliDataSource(mutableLiveData);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<FuliDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
