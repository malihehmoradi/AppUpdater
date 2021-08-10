package ir.malihehmoradi.appupdater.helper;

import android.content.pm.PackageInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import ir.malihehmoradi.appupdater.fragment.UpdateFragment;
import ir.malihehmoradi.appupdater.model.ApplicationConfig;

public class UpdateChecker {

    private final AppCompatActivity activity;
    private final String versionName;
    private final String changes;
    private final String appUrl;
    private final String necessaryVersion;
    private OnUpdateListener onUpdateListener;

    /***
     *
     * @param  activity
     * @param versionName
     * @param changes
     * @param appUrl
     * @param necessaryVersion
     */
    public UpdateChecker(AppCompatActivity activity, String versionName, String changes, String appUrl, String necessaryVersion) {
        this.activity = activity;
        this.versionName = versionName;
        this.changes = changes;
        this.appUrl = appUrl;
        this.necessaryVersion = necessaryVersion;
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

            if (Helper.compareVersionNames(packageInfo.versionName, versionName) < 0) {

                ApplicationConfig appConfig = new ApplicationConfig();
                appConfig.versionName = versionName;
                appConfig.changes = changes;
                appConfig.appUrl = appUrl;
                appConfig.necessaryVersion = necessaryVersion;


                UpdateFragment updateFragment = new UpdateFragment(appConfig);
                updateFragment.setOnFailUpdate(new UpdateFragment.OnUpdateListener() {
                    @Override
                    public void onSuccess() {
                        if (onUpdateListener != null) {
                            onUpdateListener.onSuccess();
                        }
                    }

                    @Override
                    public void onCancel() {
                        if (onUpdateListener != null) {
                            onUpdateListener.onCancel();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (onUpdateListener != null) {
                            onUpdateListener.onFail();
                        }
                    }
                });
                FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                updateFragment.show(fragmentTransaction, updateFragment.getTag());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
