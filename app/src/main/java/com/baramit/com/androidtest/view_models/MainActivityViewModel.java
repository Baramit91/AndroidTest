package com.baramit.com.androidtest.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.baramit.com.androidtest.repositories.IntegerRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Integer>> mNumbers;
    private MutableLiveData<Integer> mNumber;
    private IntegerRepository mRepo;

    public void init() {
        if (mRepo == null) {
            mRepo = IntegerRepository.getInstance();
            mNumbers = mRepo.getNumbers();
            mNumber = mRepo.getNumber();
        }
    }

    public LiveData<List<Integer>> getMyNumbers() {
        return mNumbers;
    }

    public LiveData<Integer> getMySingleNumber() {
        return mNumber;
    }

    public void makeNumbersRequest() {
        mRepo.getClient().mutableLiveData1Call(mRepo.calls().getNumbers());
    }


}
