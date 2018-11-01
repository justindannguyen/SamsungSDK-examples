/**
 * Copyright (C) 2018, Justin Nguyen
 */

package com.justin.tvcontrol;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.samsung.multiscreen.Error;
import com.samsung.multiscreen.Result;
import com.samsung.multiscreen.Search;
import com.samsung.multiscreen.Service;

/**
 * @author tuan3.nguyen@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void wakeUpTVAndConnect(View view) {
        EditText editText = findViewById(R.id.editText);
        String macAddress = editText.getText().toString();
        if (macAddress.isEmpty()) {
            macAddress = "D8:E0:E1:92:36:83";
        }

        String url = getString(R.string.service_uri);
        Log.i(LOG_TAG, "Wake TV up");
        Service.WakeOnWirelessAndConnect(macAddress, Uri.parse(url), 5000, new Result<Service>() {
            @Override
            public void onSuccess(Service service) {
                String serviceName = service.getName();
                Log.i(LOG_TAG, "Wake up successful, service name: " + serviceName);
                ((TextView) findViewById(R.id.serviceName)).setText(serviceName);
            }

            @Override
            public void onError(Error error) {
                Log.e(LOG_TAG, String.format("Could not wake TV up: %s, retry", error.getMessage()));
                wakeUpTVAndConnect(null);
            }
        });
    }
}
