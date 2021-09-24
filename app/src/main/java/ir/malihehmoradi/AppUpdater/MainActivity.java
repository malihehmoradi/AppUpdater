package ir.malihehmoradi.AppUpdater;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import ir.malihehmoradi.appupdater.helper.Helper;
import ir.malihehmoradi.appupdater.helper.UpdateChecker;
import ir.malihehmoradi.appupdater.model.AppConfig;

public class MainActivity extends ParentActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppConfig appConfig = new Gson().fromJson(Helper.readJsonFileFromAssets(getApplicationContext()), AppConfig.class);


        new UpdateChecker(this, appConfig)
                .setOnUpdateListener(new UpdateChecker.OnUpdateListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancel: ");
                    }

                    @Override
                    public void onFail() {
                        Log.d(TAG, "onFail: ");
                    }
                }).check();

    }


}