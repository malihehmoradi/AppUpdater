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
                "بهینه سازی برنامه",
                "http://appcdn.atishahr.net:8080/application/app.apk",
                "1.0.0")
                .check();
    }
}