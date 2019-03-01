package ru.prabo.barcodehook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.widget.TextView;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BarcodeHook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);
        Log.d(TAG, "Receiver registered");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
        Log.d(TAG, "Receiver unregistered");
    }

    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NotNull Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();

            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                try {
                    displayScanResult(intent);
                }
                catch (Exception e){
                    Log.d(TAG, "Error display result");
                }
            }
        }
    };

    private void displayScanResult(@NotNull Intent initiatingIntent) {

        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));

        final TextView tvBarcode = (TextView) findViewById(R.id.tvBarcode);

        tvBarcode.setText(decodedData);

        Log.d(TAG, "Success: ".concat(decodedData));
    }

}
