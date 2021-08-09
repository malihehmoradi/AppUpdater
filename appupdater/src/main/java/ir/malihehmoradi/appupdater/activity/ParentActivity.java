package ir.malihehmoradi.appupdater.activity;

import android.content.Context;
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
        super.attachBaseContext(newBase);

//        String language = "fa";
//        val localeManager = LocaleManager(newBase, language)
//        super.attachBaseContext(ViewPumpContextWrapper.wrap(localeManager.wrap() !!))
//        applyOverrideConfiguration(localeManager.getConfiguration())
    }



//    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
//        super.applyOverrideConfiguration(overrideConfiguration)
//    }

}
