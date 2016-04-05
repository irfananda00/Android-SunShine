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
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import project.irfananda.sunshine.ClickListener;
import project.irfananda.sunshine.R;
import project.irfananda.sunshine.activity.MainActivity;
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
    private LinearAdapter linearAdapter;
    private List<Day> mData = new ArrayList<>();
    private Day today;
    private ApiResponse apiResponse;

    public ForecastFragment(List<Day> mData, Day today, ApiResponse apiResponse) {
        this.mData = mData;
        this.today = today;
        this.apiResponse = apiResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        linearAdapter = new LinearAdapter(mData,getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setNestedScrollingEnabled(false);

        rv.setAdapter(linearAdapter);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy",Locale.ENGLISH);
        String formattedDate = df.format(c.getTime());
        getActivity().setTitle(formattedDate + ", " + apiResponse.getCity().getName());
        MainActivity.txt_weather.setText(today.getWeather().get(0).getDescription());
        MainActivity.txt_hightemp.setText((int) today.getTemp().getMax() + "° C");
        Picasso.with(mContext)
                .load(DataService.IMG_URL + today.getWeather().get(0).getIcon() + ".png")
                .into(MainActivity.img_icon);

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


}
