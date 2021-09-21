package ir.malihehmoradi.AppUpdater;

import android.os.Bundle;

import com.google.gson.Gson;

import ir.malihehmoradi.appupdater.helper.Helper;
import ir.malihehmoradi.appupdater.helper.UpdateChecker;
import ir.malihehmoradi.appupdater.model.ApplicationConfig;


public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApplicationConfig applicationConfig = new Gson().fromJson(Helper.readJsonFileFromAssets(getApplicationContext()), ApplicationConfig.class);


        new UpdateChecker(this, applicationConfig).check();

    }


}