package com.baramit.com.androidtest.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.baramit.com.androidtest.repositories.PlaceRepositories;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Place>> mPlaces;
    private MutableLiveData<Place> mPlace;
    private PlaceRepositories mRepo;

    public void init() {
        if (mPlaces == null) {
            mRepo = PlaceRepositories.getInstance();
            mPlaces = mRepo.getPlaces();
            mPlace = mRepo.getPlace();
        }
    }

    public LiveData<List<Place>> getMyPlaces() {
        return mPlaces;
    }

    public LiveData<Place> getPlace() {
        return mPlace;
    }

    public void randomPlaces() {
        mRepo.getClient().mutableLiveData3Call(mRepo.calls().getRandomPlaces());
    }

    public void updatePlace(Place place) {
        place.setReq_num(39);
        mRepo.getClient().mutableLiveData3Call(mRepo.calls().updatePlace(place));
    }

    public void addPlace(Place place) {
        place.setReq_num(40);
        mRepo.calls().addPlace(place);
    }

    public void getPlaceObject(int placeId) {
        mRepo.getClient().mutableLiveData1Call(mRepo.calls().getPlace(placeId));
    }

    public Place getPlaceAgain(int placeId) {
        if (mPlaces.getValue() != null) {
            for (Place place : mPlaces.getValue()) {
                if (place.getId() == placeId) {
                    return place;
                }
            }
        }

        //same for loop for local dataBase;
        mRepo.getClient().mutableLiveData1Call(mRepo.calls().getPlace(placeId));

        return mPlace.getValue();
    }

    public void clearPlaces() {
        mPlaces.getValue().clear();
    }

    public boolean validatePlace(Place place) {

        boolean isValid = true;

        if (place.getName().equals("בר"))
            isValid = false;

        return isValid;
    }

    public void handleOnPlaceClick(Place place) {
        mPlace.postValue(place);
    }

}
