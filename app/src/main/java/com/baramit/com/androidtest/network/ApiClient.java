package com.baramit.com.androidtest.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient<T> {

    private static final int DELAY_TIME = 3000;

    private static ApiClient mInstance;
    private static Retrofit ApiClient;
    private static ScheduledExecutorService mNetworkIO;
    private static String BASE_URL = "https://pastebin.com/";

    private MutableLiveData mutableLiveData1;
    private MutableLiveData mutableLiveData2;
    private MutableLiveData mutableLiveData3;

    private T ApiInterface;
    private CustomDeserializer customDeserializer;
    private JsonSerializer customSerializer;
    private Class interfaceClass;
    private Object asyncResponse;

    public static <E extends Class> ApiClient getInstance(E ApiInterface) {
        if (mInstance == null) {
            mInstance = new ApiClient();
            mNetworkIO = Executors.newScheduledThreadPool(3);
        }

        mInstance.interfaceClass = ApiInterface;

        initDeserializer();
        initClient();

        return mInstance;
    }

    private ApiClient() {
        mutableLiveData1 = new MutableLiveData<>();
        mutableLiveData2 = new MutableLiveData<>();
        mutableLiveData3 = new MutableLiveData<>();
    }

    private static void initClient() {
        ApiClient = new Retrofit.Builder()
                .addConverterFactory(createGsonConverter(mInstance.customDeserializer))
                .baseUrl(BASE_URL)
                .build();

        mInstance.ApiInterface = ApiClient.create(mInstance.interfaceClass);
    }

    private static void initDeserializer() {
        //init SPECIFIC custom deserializers with the need to set them manually
        if (mInstance.interfaceClass == ApiCalls.class)
            mInstance.customDeserializer = new CustomDeserializer();

    }

    private static Converter.Factory createGsonConverter(CustomDeserializer ApiDeserializer) {
        if (ApiDeserializer == null) //if no custom deserializer provided nor found, init default GsonConverterFactory.
            return GsonConverterFactory.create();

        GsonBuilder gsonBuilder = new GsonBuilder();

        for (Type mType : ApiDeserializer.deserializationTypes())
            gsonBuilder.registerTypeAdapter(mType, ApiDeserializer);

        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        initClient();
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Instance Methods
     **/

    public T ApiCalls() {
        return ApiInterface;
    }

    public <E extends Object> LiveData<E> getLiveData1() {
        return mutableLiveData1;
    }

    public <E extends Object> LiveData<E> getLiveData2() {
        return mutableLiveData2;
    }

    public <E extends Object> LiveData<E> getLiveData3() {
        return mutableLiveData3;
    }

    public <E extends Object> void mutableLiveData1Call(final Call<E> request) {
        final Future handler = mNetworkIO.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<E> response = request.execute();

                    handleResponse(response, mutableLiveData1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mNetworkIO.schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, DELAY_TIME, TimeUnit.MILLISECONDS);

    }

    public <E extends Object> void mutableLiveData2Call(final Call<E> request) {
        final Future handler = mNetworkIO.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<E> response = request.execute();

                    handleResponse(response, mutableLiveData2);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mNetworkIO.schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, DELAY_TIME, TimeUnit.MILLISECONDS);

    }

    public <E extends Object> void mutableLiveData3Call(final Call<E> request) {
        final Future handler = mNetworkIO.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<E> response = request.execute();

                    handleResponse(response, mutableLiveData3);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mNetworkIO.schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    private <E extends Object> void handleResponse(Response<E> response, MutableLiveData mutableLiveData) {
        if (response.isSuccessful()) {
            E mObject = response.body();

            if (mObject instanceof JsonObject) {
                Log.e("My Json Response:", mObject.toString());
            } else if (mObject instanceof List) {
                if (mutableLiveData.getValue() == null)
                    mutableLiveData.postValue(mObject);
                else {
                    ((List) mutableLiveData.getValue()).addAll((List) mObject);
                    mutableLiveData.postValue(mutableLiveData.getValue());
                }
            } else {
                mutableLiveData.postValue(mObject);
            }
        }
    }

    public <E extends Object> Callback<E> asyncCallbacks(final Class<E> type) {
        return new Callback<E>() {
            @Override
            public void onResponse(Call<E> call, Response<E> response) {
                if (response.isSuccessful()) {
                    mInstance.asyncResponse = response.body();
                } else {
                    Log.d("Response failure", response.raw().toString());
                }
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                Log.e("Error:", t.getMessage());
            }
        };
    }

    public <E extends Object> E getAsyncResult(Class<E> type) {
        return type.cast(asyncResponse);
    }

    public JsonDeserializer getJsonDeserializer() {
        return customDeserializer;
    }

    public void setJsonDeserializer(CustomDeserializer customDeserializer) {

        this.customDeserializer = customDeserializer;
        initClient();
    }

    public JsonSerializer getJsonSerializer() {
        return customSerializer;
    }

    public void setJsonSerializer(JsonSerializer customSerializer) {
        this.customSerializer = customSerializer;
    }

}