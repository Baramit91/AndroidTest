package com.baramit.com.androidtest.Network;

import com.baramit.com.retrofitplay.models.Place;
import com.baramit.com.retrofitplay.models.Trip;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomDeserializer implements JsonDeserializer {

    public static final Type PLACE = Place.class;
    public static final Type TRIP = Trip.class;
    public static final Type PLACE_ARRAY = new TypeToken<List<Place>>() { }.getType();
    public static final Type TRIP_ARRAY = new TypeToken<List<Trip>>() { }.getType();
    private ArrayList<Type> deserializationTypes;

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        if (typeOfT.toString().equals(PLACE.toString())) {
            return deserializePlace(json.getAsJsonObject());
        } else if (typeOfT.toString().equals(PLACE_ARRAY.toString())) {
            return deserializePlaceList(json.getAsJsonObject());
        }

        return json.getAsJsonObject();
    }

    private Place deserializePlace(JsonObject response) {
        JsonObject placeJson = null;

        if (response.has("data") && response.getAsJsonObject().get("data").isJsonArray()) {
            JsonArray jsonArray = response.get("data").getAsJsonArray();
            placeJson = jsonArray.get(0).getAsJsonObject();
        } else
            placeJson = response;

        int id = placeJson.get("id").getAsInt();
        String name = placeJson.get("placeName").getAsString();
        //String address = placeJson.get("address").getAsString();
        //String description = placeJson.get("description").getAsString();
        double price = placeJson.get("price").getAsDouble();
        double stayTime = placeJson.get("estimatedStayTime").getAsDouble();
        //double rating = 10;
        String coordinatesString = placeJson.get("coordinates").getAsString();
        String[] coordinatesArray = coordinatesString.split(", ");

        double[] coordinates = new double[2];
        coordinates[0] = Double.parseDouble(coordinatesArray[0]);
        coordinates[1] = Double.parseDouble(coordinatesArray[1]);
        //String accessability = placeJson.get("accessibility").getAsString();
        String phoneNumber = placeJson.get("phoneNumber").getAsString();
        //ArrayList<String> categories = placeData.get();
        //ArrayList<String> workingHours = placeData.get();
        //ArrayList<String> workingDays = placeData.get();
        String photosURL = placeJson.get("photosURLs").getAsString();
        //String website = placeJson.get("website").getAsString();
//         int votes = placeData.get();
        Place place = new Place();
        place.setId(id);
        place.setName(name);
        //place.setDescription(description);
        place.setPrice(price);
        place.setStayTime(stayTime);
        //place.setRating(rating);
        //place.setAddress(address);
        //place.setCoordinates(coordinates);
        //place.setAccessability(accessability);
        place.setPhoneNumber(phoneNumber);
        place.setPhotosURL(photosURL);
        //place.setWebsite(website);

        return place;
    }


    private List<Place> deserializePlaceList(JsonObject response) {
        List<Place> places = new ArrayList<>();

        if (response.has("data") && response.get("data").isJsonArray()) {
            JsonArray data = response.getAsJsonArray("data");
            for (int i = 0; i < data.size(); i++) {
                JsonObject placeJson = data.get(i).getAsJsonObject();
                Place place = deserializePlace(placeJson);
                places.add(place);
            }

            return places;
        }

        return null;
    }

    public ArrayList<Type> deserializationTypes() {
        if (deserializationTypes == null) {
            deserializationTypes = new ArrayList<>();
            deserializationTypes.add(PLACE_ARRAY);
            deserializationTypes.add(PLACE);
            deserializationTypes.add(TRIP);
            deserializationTypes.add(TRIP_ARRAY);
        }

        return deserializationTypes;
    }
}
