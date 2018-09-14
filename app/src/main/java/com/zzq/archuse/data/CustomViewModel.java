package com.zzq.archuse.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class CustomViewModel extends ViewModel{

    private MutableLiveData<String> mStringMutableLiveData;

    public CustomViewModel() {
        mStringMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<String> setNotifiedText(String text) {
        mStringMutableLiveData.setValue(text);
        return mStringMutableLiveData;
    }
}
