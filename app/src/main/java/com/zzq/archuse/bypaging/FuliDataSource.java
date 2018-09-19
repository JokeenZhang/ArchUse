package com.zzq.archuse.bypaging;

import android.annotation.SuppressLint;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zzq.archuse.retrofit.GankManager;
import com.zzq.archuse.retrofit.bean.FuliDataBean;
import com.zzq.archuse.retrofit.bean.MeizhiBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FuliDataSource extends PageKeyedDataSource<Integer,MeizhiBean>{

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MeizhiBean> callback) {
        GankManager.getGankService().getFuliData(params.requestedLoadSize, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuliDataBean>() {
                    @Override
                    public void accept(FuliDataBean fuliDataBean) throws Exception {
                        callback.onResult(fuliDataBean.getResults(),null,2);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MeizhiBean> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MeizhiBean> callback) {
        Log.i("ArchUse", "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        GankManager.getGankService().getFuliData(params.requestedLoadSize,params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuliDataBean>() {
                    @Override
                    public void accept(FuliDataBean fuliDataBean) throws Exception {
                        int nextKey = (fuliDataBean.getResults().size()==0) ? null : params.key+1;
                        callback.onResult(fuliDataBean.getResults(),nextKey);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
