package ir.malihehmoradi.AppUpdater;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.malihehmoradi.appupdater.helper.UpdateChecker;
import ir.malihehmoradi.appupdater.model.AppConfig;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppConfig appConfig = new AppConfig();
        appConfig.versionCode = 1;
        appConfig.versionName = "1.0.1";
        appConfig.necessaryVersion = "1.0.1";
        appConfig.appUrl = "http://appcdn.atishahr.net:8080/application/app.apk";

        List<AppConfig.Change> changes = new ArrayList<>();

        AppConfig.Change change = new AppConfig.Change();
        change.date = "1400/05/20";
        change.desciption = "افزودن ماژول آپدیت درون برنامه ای\nرفع مشکلات جزئی";
        changes.add(change);

        change = new AppConfig.Change();
        change.date = "1400/05/12";
        change.desciption = "اتصال وب اپلیکیشن به صفحه اصلی \nرفع مشکلات جزئی";
        changes.add(change);

        change = new AppConfig.Change();
        change.date = "1400/05/06";
        change.desciption = "افزودن صفحه معرفی به برنامه \nافزودن صفحه ثبت نام و ورود\n افزودن صفحه اصلی به برنامه";
        changes.add(change);
        appConfig.changes = changes;

        new UpdateChecker(this, appConfig)
                .setOnUpdateListener(new UpdateChecker.OnUpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Update canceled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .check();

    }


}