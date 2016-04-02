package project.irfananda.sunshine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.irfananda.sunshine.R;
import project.irfananda.sunshine.model.Day;
import project.irfananda.sunshine.network.DataService;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.MyViewHolder>  {

    private List<Day> list;
    private Context context;

    public LinearAdapter(List<Day> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_forecast, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Day day = list.get(position);
        holder.txt_day.setText(day.getWeekDay());
        holder.txt_forecast.setText(day.getWeather().get(0).getDescription());
        holder.txt_tempHigh.setText((int)day.getTemp().getMax()+"° C");
        Picasso.with(context)
                .load(DataService.IMG_URL+day.getWeather().get(0).getIcon()+".png")
                .into(holder.img_forecast);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_day, txt_forecast, txt_tempHigh;
        public ImageView img_forecast;

        public MyViewHolder(View view) {
            super(view);
            img_forecast= (ImageView) view.findViewById(R.id.img_forecast);
            txt_day= (TextView) view.findViewById(R.id.txt_day);
            txt_forecast= (TextView) view.findViewById(R.id.txt_forecast);
            txt_tempHigh= (TextView) view.findViewById(R.id.txt_tempHigh);
        }
    }

}
