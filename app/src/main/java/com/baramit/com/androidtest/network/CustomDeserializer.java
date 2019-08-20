package com.baramit.com.androidtest.network;

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

    public static final Type INTEGER = Integer.class;
    public static final Type INTEGER_ARRAY = new TypeToken<List<Integer>>() { }.getType();
    private ArrayList<Type> deserializationTypes;

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (typeOfT.toString().equals(INTEGER.toString())) {
            return deserializeInteger(json.getAsJsonObject());
        } else if (typeOfT.toString().equals(INTEGER_ARRAY.toString())) {
            return deserializeIntegerList(json.getAsJsonObject());
        }

        return json.getAsJsonObject();
    }

    private Integer deserializeInteger(JsonObject response) {
        JsonObject numberJson = null;

        if (response.has("numbers") && response.getAsJsonObject().get("numbers").isJsonArray()) {
            //handle response that gets a single Integer with Integer Type and not List<Integer>
            JsonArray jsonArray = response.get("numbers").getAsJsonArray();
            numberJson = jsonArray.get(0).getAsJsonObject();
        } else
            //handle JSON from List<Integer> method call
            numberJson = response;

        Integer number = numberJson.get("number").getAsInt();

        return number;
    }


    private List<Integer> deserializeIntegerList(JsonObject response) {
        List<Integer> numbers = new ArrayList<>();

        if (response.has("numbers") && response.get("numbers").isJsonArray()) {
            JsonArray data = response.getAsJsonArray("numbers");
            for (int i = 0; i < data.size(); i++) {
                JsonObject numberJson = data.get(i).getAsJsonObject();
                Integer number = deserializeInteger(numberJson);
                numbers.add(number);
            }

            return numbers;
        }

        return null;
    }

    public ArrayList<Type> deserializationTypes() {
        if (deserializationTypes == null) {
            deserializationTypes = new ArrayList<>();
            deserializationTypes.add(INTEGER);
            deserializationTypes.add(INTEGER_ARRAY);
        }

        return deserializationTypes;
    }
}
