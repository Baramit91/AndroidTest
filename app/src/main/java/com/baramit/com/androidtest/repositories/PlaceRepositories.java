package com.baramit.com.androidtest.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;


import com.baramit.com.androidtest.Network.ApiClient;
import com.baramit.com.androidtest.Network.PlacesApi;

import java.util.List;

public class PlaceRepositories {

    private ApiClient<PlacesApi> mApiClient;
    private MediatorLiveData<List<Place>> mPlaces;
    private MediatorLiveData<Place> mPlace;
    private static PlaceRepositories mInstance;

    public static PlaceRepositories getInstance() {
        if (mInstance == null)
            mInstance = new PlaceRepositories();
        return mInstance;
    }

    private PlaceRepositories() {
        mApiClient = ApiClient.getInstance(PlacesApi.class);
        initMediators();
    }

    private void initMediators() {
        mPlace = new MediatorLiveData<>();
        mPlaces = new MediatorLiveData<>();

        LiveData<List<Place>> placesApiSource = mApiClient.getLiveData3();

        mPlaces.addSource(placesApiSource, new Observer<List<Place>>() {
            @Override
            public void onChanged(@Nullable List<Place> places) {
                if (places != null) {
                    mPlaces.setValue(places);
                }
            }
        });

        LiveData<Place> placeApiSource = mApiClient.getLiveData1();

        mPlace.addSource(placeApiSource, new Observer<Place>() {
            @Override
            public void onChanged(@Nullable Place place) {
                if (place != null)
                    mPlace.setValue(place);
            }
        });

    }

    public ApiClient<PlacesApi> getClient() {
        return mApiClient;
    }

    public PlacesApi calls() {
        return mApiClient.ApiCalls();
    }

    public MediatorLiveData<List<Place>> getPlaces() {
        return mPlaces;
    }

    public MediatorLiveData<Place> getPlace() {
        return mPlace;
    }

}
