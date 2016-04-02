package project.irfananda.sunshine.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import project.irfananda.sunshine.R;
import project.irfananda.sunshine.network.DataService;


/**
 * Created by irfananda on 25/03/16.
 */
public class CityDialog extends DialogFragment {

    private Handler handler;

    public CityDialog() {
        super();
    }

    public CityDialog(Handler handler) {
        super();
        this.handler = handler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_city, container,false);

        getDialog().setTitle("City Weather Forecast");
        getDialog().setCancelable(true);

        final EditText edt_city = (EditText) v.findViewById(R.id.edt_city);
        Button btn_ok = (Button) v.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataService.CITY_NAME  = edt_city.getText().toString();
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        handler.sendEmptyMessage(1);
    }


}
