package ir.malihehmoradi.AppUpdater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.malihehmoradi.appupdater.helper.UpdateChecker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UpdateChecker(
                this,
                "1.0.1",
                "بهینه سازی نرم افزار",
                "http://google.com",
                "1.0.0")
                .check();
    }
}