package com.baramit.com.androidtest.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;


import com.baramit.com.androidtest.network.ApiCalls;
import com.baramit.com.androidtest.network.ApiClient;

import java.util.List;

public class IntegerRepository {

    private ApiClient<ApiCalls> mApiClient;
    private MediatorLiveData<List<Integer>> mNumbers;
    private MediatorLiveData<Integer> mNumber;
    private static IntegerRepository mInstance;

    public static IntegerRepository getInstance() {
        if (mInstance == null)
            mInstance = new IntegerRepository();
        return mInstance;
    }

    private IntegerRepository() {
        mApiClient = ApiClient.getInstance(ApiCalls.class);
        initMediators();
    }

    private void initMediators() {
        mNumber = new MediatorLiveData<>();
        mNumbers = new MediatorLiveData<>();

        LiveData<List<Integer>> integerApiSource = mApiClient.getLiveData1();

        mNumbers.addSource(integerApiSource, new Observer<List<Integer>>() {
            @Override
            public void onChanged(@Nullable List<Integer> numbers) {
                if (numbers != null) {
                    mNumbers.setValue(numbers);
                }
            }
        });

    }

    public ApiClient<ApiCalls> getClient() {
        return mApiClient;
    }

    public ApiCalls calls() {
        return mApiClient.ApiCalls();
    }

    public MediatorLiveData<List<Integer>> getNumbers() {
        return mNumbers;
    }

    public MediatorLiveData<Integer> getNumber() {
        return mNumber;
    }

}
