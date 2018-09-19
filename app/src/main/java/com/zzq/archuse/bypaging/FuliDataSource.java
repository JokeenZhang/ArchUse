package com.zzq.archuse.bypaging;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zzq.archuse.retrofit.GankManager;
import com.zzq.archuse.retrofit.bean.FuliDataBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FuliDataSource extends PageKeyedDataSource<Long,FuliDataBean.ResultsBean>{

    private MutableLiveData mLiveData;
    public FuliDataSource(MutableLiveData liveData) {
        mLiveData = liveData;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, FuliDataBean.ResultsBean> callback) {
//        GankViewModel gankViewModel = ViewModelProviders.of(activity).get(GankViewModel.class);
//        final MutableLiveData<List<FuliDataBean.ResultsBean>> resultsBeanMutableLiveData = gankViewModel.getResultsBeanMutableLiveData();
        GankManager.getGankService().getFuliData(params.requestedLoadSize, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuliDataBean>() {
                    @Override
                    public void accept(FuliDataBean fuliDataBean) throws Exception {
                        callback.onResult(fuliDataBean.getResults(),null,2L);
                        mLiveData.setValue(fuliDataBean.getResults());
//                        resultsBeanMutableLiveData.setValue(fuliDataBean.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, FuliDataBean.ResultsBean> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, FuliDataBean.ResultsBean> callback) {
//        GankViewModel gankViewModel = ViewModelProviders.of(activity).get(GankViewModel.class);
//        final MutableLiveData<List<FuliDataBean.ResultsBean>> resultsBeanMutableLiveData = gankViewModel.getResultsBeanMutableLiveData();
        Log.i("ArchUse", "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        GankManager.getGankService().getFuliData(params.requestedLoadSize,params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FuliDataBean>() {
                    @Override
                    public void accept(FuliDataBean fuliDataBean) throws Exception {

                        /*if (fuliDataBean.getResults() != null){
                            if (fuliDataBean.getResults().size() != 0){
                                callback.onResult(fuliDataBean.getResults(),params.key+1);
                            }else {
                                callback.onResult(null,null);
                            }
                        }else {
                            callback.onResult(null,null);
                        }*/
                        long nextKey = (fuliDataBean.getResults().size()==0) ? null : params.key+1;
                        callback.onResult(fuliDataBean.getResults(),nextKey);
                        mLiveData.setValue(fuliDataBean.getResults());
//                        resultsBeanMutableLiveData.setValue(fuliDataBean.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
