package ir.malihehmoradi.appupdater.helper;

import android.content.Intent;
import android.content.pm.PackageInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ir.malihehmoradi.appupdater.model.ApplicationConfig;

public class UpdateChecker {

    private final AppCompatActivity activity;
    private final ApplicationConfig appConfig;
    private OnFailListener onFailListener;

    /***
     *
     * @param  activity
     */
    public UpdateChecker(AppCompatActivity activity, ApplicationConfig appConfig) {
        this.activity = activity;
        this.appConfig = appConfig;
    }

    public UpdateChecker setOnFailUpdate(OnFailListener onFailListener) {
        this.onFailListener = onFailListener;
        return this;
    }

    public interface OnFailListener {

        void onSuccess();

        void onCancel();

        void onFail();

    }

    public void check() {

        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            if (Helper.compareVersionNames(packageInfo.versionName, appConfig.versionName) < 0) {

                try {
                    Intent intent = new Intent(activity, Class.forName("ir.malihehmoradi.appupdater.activity.InAppUpdateActivity"));
                    intent.putExtra("AppConfig", new Gson().toJson(appConfig));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
