package project.irfananda.sunshine.network;

import project.irfananda.sunshine.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by irfananda on 02/04/16.
 */
public final class DataService {

    public static final String API_KEY = "63a6f271a80707266b79b0d5850a3acc";
    public static String CITY_NAME = "Bandung";

    public static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/";
    public static final String IMG_URL = "http://openweathermap.org/img/w/";

    public interface OpenWeatherMap {

        //Call<List<Object>> jika jsonnya list ex : List [ { } ]
        //Call<Object> jika jsonnya object biasa ex : {}

        @GET("daily?mode=json&units=metric")
        Call<ApiResponse> getWeather(
                @Query("q") String city,
                @Query("cnt") int count,
                @Query("appid") String appid);
    }
}
