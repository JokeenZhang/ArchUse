package com.zzq.archuse.retrofit;

public class GankManager {

    static final Object monitor = new Object();
    private static GankApiService mGankApiInterface;

    public static GankApiService getGankService() {
        synchronized (monitor) {
            if (mGankApiInterface == null) {
                mGankApiInterface = new GankRetrofit().getGankApiService();
            }
            return mGankApiInterface;
        }
    }
}
