package project.irfananda.sunshine.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import project.irfananda.sunshine.dialog.CityDialog;
import project.irfananda.sunshine.fragment.ForecastFragment;
import project.irfananda.sunshine.R;
import project.irfananda.sunshine.fragment.LoadingFragment;

public class MainActivity extends AppCompatActivity {

    public static TextView txt_weather;
    public static TextView txt_hightemp;
    public static ImageView img_icon;

    private final int PERMISSION_INTERNET = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET},PERMISSION_INTERNET);

        img_icon = (ImageView) findViewById(R.id.img_icon);
        txt_weather = (TextView) findViewById(R.id.txt_weather);
        txt_hightemp = (TextView) findViewById(R.id.txt_hightemp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, new LoadingFragment())
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

            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragment)).commit();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new LoadingFragment())
                        .commit();
            return true;
        }else if(id == R.id.action_city){
            CityDialog cityDialog= new CityDialog(new CityDialogDismissHandler());
            cityDialog.show(getSupportFragmentManager(),"city dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_INTERNET:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private class CityDialogDismissHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.fragment)).commit();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, new LoadingFragment())
                        .commit();
        }
    }

}
