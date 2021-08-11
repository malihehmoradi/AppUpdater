package ir.malihehmoradi.appupdater.helper;

import android.content.pm.PackageInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import ir.malihehmoradi.appupdater.fragment.UpdateFragment;
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
