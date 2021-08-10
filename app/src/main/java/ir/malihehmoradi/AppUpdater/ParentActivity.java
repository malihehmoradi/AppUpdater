package ir.malihehmoradi.AppUpdater;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ParentActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        String language = "fa";
        LocaleManager localeManager = new LocaleManager(newBase, language);
        super.attachBaseContext(localeManager.wrap());
        applyOverrideConfiguration(localeManager.getConfiguration());
    }


    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        super.applyOverrideConfiguration(overrideConfiguration);
    }

}
