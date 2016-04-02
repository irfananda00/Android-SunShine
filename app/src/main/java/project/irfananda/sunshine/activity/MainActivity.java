package project.irfananda.sunshine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import project.irfananda.sunshine.dialog.CityDialog;
import project.irfananda.sunshine.fragment.ForecastFragment;
import project.irfananda.sunshine.R;

public class MainActivity extends AppCompatActivity {

    private TextView txt_date;
    private TextView txt_weather;
    private TextView txt_hightemp;
    private ImageView img_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img_icon = (ImageView) findViewById(R.id.img_icon);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_weather = (TextView) findViewById(R.id.txt_weather);
        txt_hightemp = (TextView) findViewById(R.id.txt_hightemp);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new ForecastFragment(txt_date,txt_hightemp,txt_weather,img_icon))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new ForecastFragment(txt_date,txt_hightemp,txt_weather,img_icon))
                        .commit();
            return true;
        }else if(id == R.id.action_city){
            CityDialog cityDialog= new CityDialog(new CityDialogDismissHandler());
            cityDialog.show(getFragmentManager(), "Order Dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CityDialogDismissHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

                getSupportFragmentManager().beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.fragment)).commit();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new ForecastFragment(txt_date,txt_hightemp,txt_weather,img_icon))
                        .commit();
        }
    }

}
