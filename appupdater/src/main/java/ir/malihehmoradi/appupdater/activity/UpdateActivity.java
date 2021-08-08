package ir.malihehmoradi.appupdater.activity;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ir.malihehmoradi.appupdater.R;
import ir.malihehmoradi.appupdater.helper.Helper;
import ir.malihehmoradi.appupdater.model.ApplicationConfig;

public class UpdateActivity extends AppCompatActivity {

    private ApplicationConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        Helper.fullScreenActivity(this);

        setContentView(R.layout.activity_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getTitle());

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();

        String appConfigStr = bundle.getString("AppConfig");
        appConfig = new Gson().fromJson(appConfigStr, ApplicationConfig.class);
    }




}