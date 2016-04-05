package project.irfananda.sunshine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import project.irfananda.sunshine.R;
import project.irfananda.sunshine.model.ApiResponse;
import project.irfananda.sunshine.model.Day;
import project.irfananda.sunshine.network.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by irfananda on 04/04/16.
 */
public class LoadingFragment extends Fragment {

    private List<Day> mData = new ArrayList<>();
    private Day today;
    private ApiResponse apiResponse;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    private String[] dayofWeek = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
    };

    public LoadingFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_loading, container, false);

        avLoadingIndicatorView = (AVLoadingIndicatorView) rootView.findViewById(R.id.avloadingIndicatorView);

        loadData();
        
        return rootView;
    }

    private void loadData() {
        startAnim();
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(DataService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService.OpenWeatherMap simpleService = retrofit.create(
                DataService.OpenWeatherMap.class);

        Call<ApiResponse> listCall = simpleService.getWeather(
                DataService.CITY_NAME,7,DataService.API_KEY);

        listCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    int indexDay = 0;
                    apiResponse = response.body();

                    String weekDay;
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

                    Calendar calendar = Calendar.getInstance();
                    weekDay = dayFormat.format(calendar.getTime());

                    switch (weekDay) {
                        case "Sunday":
                            indexDay = 0;
                            break;
                        case "Monday":
                            indexDay = 1;
                            break;
                        case "Tuesday":
                            indexDay = 2;
                            break;
                        case "Wednesday":
                            indexDay = 3;
                            break;
                        case "Thursday":
                            indexDay = 4;
                            break;
                        case "Friday":
                            indexDay = 5;
                            break;
                        case "Saturday":
                            indexDay = 6;
                            break;
                    }

                    for (int i = 0; i < apiResponse.getList().size(); i++) {
                        Day day = apiResponse.getList().get(i);

                        if (i == 0) {
                            today = day;
                        }

                        day.setWeekDay(dayofWeek[indexDay]);

                        indexDay++;
                        if (indexDay > 6) {
                            indexDay = 0;
                        }

                        mData.add(day);
                    }
                    stopAnim(true);
                } else {
                    Snackbar.make(getView(), "Fail access server", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                    Log.i("infoirfan", "Fail response");
                    stopAnim(false);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Snackbar.make(getView(), "No internet connection", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
                Log.i("infoirfan", t.getMessage());
                stopAnim(false);
            }
        });
    }

    void startAnim(){
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
    }

    void stopAnim(boolean success){
        avLoadingIndicatorView.setVisibility(View.GONE);
        if(success){
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment, new ForecastFragment(mData,today,apiResponse))
                    .commit();
        }
    }

}
