package ir.malihehmoradi.appupdater.helper;

import android.content.Intent;
import android.content.pm.PackageInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import ir.malihehmoradi.appupdater.activity.InAppUpdateActivity;
import ir.malihehmoradi.appupdater.model.AppConfig;

public class UpdateChecker {

    private final AppCompatActivity activity;
    private final AppConfig appConfig;
    private OnUpdateListener onUpdateListener;

    /***
     *
     * @param  activity
     * @param appConfig
     */
    public UpdateChecker(AppCompatActivity activity, AppConfig appConfig) {
        this.activity = activity;
        this.appConfig = appConfig;
    }

    public UpdateChecker setOnUpdateListener(OnUpdateListener onUpdateListener) {
        this.onUpdateListener = onUpdateListener;
        return this;
    }

    public interface OnUpdateListener {

        void onSuccess();

        void onCancel();

        void onFail();

    }

    public void check() {

        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            if (Helper.compareVersionNames(packageInfo.versionName, appConfig.versionName) < 0) {

                Intent intent = new Intent(activity, InAppUpdateActivity.class);
                intent.putExtra("AppConfig",new Gson().toJson(appConfig));
                activity.startActivity(intent);


            } else {
                if (onUpdateListener != null) {
                    onUpdateListener.onCancel();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (onUpdateListener != null) {
                onUpdateListener.onCancel();
            }
        }
    }


}
