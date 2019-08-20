package com.baramit.com.androidtest.Network;

import com.baramit.com.retrofitplay.models.Trip;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TripsApi {

    //TODO
    String PLACES_GET = "places/get/22/{parameter}";
    String PLACES_POST = "admin/post";
    String RANDOM_PLACES = "places/get/4";

    @GET(PLACES_GET)
    Call<Trip> getTrip(@Path("parameter") int placeId);

    @POST(PLACES_POST)
    Call<JsonObject> addTrip(@Body Trip trip);

    @POST(PLACES_POST)
    Call<JsonObject> updatePlace(@Body Trip trip);

    @DELETE(PLACES_POST)
    Call<Integer> deletePlace(int tripId);

    @GET(RANDOM_PLACES)
    Call<List<Trip>> getRandomPlaces();

}
