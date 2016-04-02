package project.irfananda.sunshine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import project.irfananda.sunshine.ClickListener;
import project.irfananda.sunshine.R;
import project.irfananda.sunshine.adapter.LinearAdapter;
import project.irfananda.sunshine.model.ApiResponse;
import project.irfananda.sunshine.model.Day;
import project.irfananda.sunshine.network.DataService;
import project.irfananda.sunshine.recyclerView.DividerItemDecoration;
import project.irfananda.sunshine.recyclerView.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private Context mContext;
    private RecyclerView rv;
    private List<Day> mData = new ArrayList<>();
    private LinearAdapter linearAdapter;
    private TextView txt_date;
    private TextView txt_weather;
    private TextView txt_hightemp;
    private ImageView img_icon;

    private String[] dayofWeek = {
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    };

    public ForecastFragment() {
        super();
    }

    public ForecastFragment(TextView txt_date, TextView txt_hightemp, TextView txt_weather, ImageView img_icon) {
        this.txt_date = txt_date;
        this.txt_hightemp = txt_hightemp;
        this.txt_weather = txt_weather;
        this.img_icon = img_icon;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        loadData();
        linearAdapter = new LinearAdapter(mData,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(linearAdapter);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return rootView;
    }

    private boolean loadData() {
        final boolean[] success = new boolean[1];
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
                if(response.isSuccessful()){
                    int indexDay = 0;
                    ApiResponse apiResponse = response.body();

                    String weekDay;
                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

                    Calendar calendar = Calendar.getInstance();
                    weekDay = dayFormat.format(calendar.getTime());

                    switch (weekDay){
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

                    for (int i = 0; i < apiResponse.getList().size(); i++){
                        Day day = apiResponse.getList().get(i);

                        if(i==0){
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c.getTime());
                            txt_date.setText(formattedDate+", "+apiResponse.getCity().getName());
                            txt_weather.setText(day.getWeather().get(0).getDescription());
                            txt_hightemp.setText((int)day.getTemp().getMax() + "° C");
                            Picasso.with(mContext)
                                    .load(DataService.IMG_URL+day.getWeather().get(0).getIcon()+".png")
                                    .into(img_icon);
                        }

                        day.setWeekDay(dayofWeek[indexDay]);

                        indexDay++;
                        if(indexDay>6){
                            indexDay = 0;
                        }

                        mData.add(day);
                    }
                    linearAdapter.notifyDataSetChanged();

                    success[0] = true;
                }else{
                    Snackbar.make(getView(), "Failed accessing server", Snackbar.LENGTH_LONG)
                            .setAction("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                    Log.i("infoirfan","Fail response");
                    success[0] = false;
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
                success[0] = false;
            }
        });

        return success[0];


    }


}
