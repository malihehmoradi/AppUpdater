package ir.malihehmoradi.appupdater.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;

import com.google.gson.Gson;

import ir.malihehmoradi.appupdater.activity.UpdateActivity;
import ir.malihehmoradi.appupdater.dialog.UpdaterDialog;
import ir.malihehmoradi.appupdater.model.ApplicationConfig;

public class UpdateChecker {

    private final Activity activity;
    private final String versionName;
    private final String changes;
    private final String appUrl;
    private final String necessaryVersion;
    private OnFailListener onFailListener;

    /***
     *
     * @param  activity
     * @param versionName
     * @param changes
     * @param appUrl
     * @param necessaryVersion
     */
    public UpdateChecker(Activity activity, String versionName, String changes, String appUrl, String necessaryVersion) {
        this.activity = activity;
        this.versionName = versionName;
        this.changes = changes;
        this.appUrl = appUrl;
        this.necessaryVersion = necessaryVersion;
    }

    public UpdateChecker setOnFailUpdate(OnFailListener onFailListener) {
        this.onFailListener = onFailListener;
        return this;
    }

    public interface OnFailListener {
        void onFail();
    }

    public void check() {

        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

            if (Helper.compareVersionNames(packageInfo.versionName, versionName) < 0) {

                //Display update
//                UpdaterDialog updateDialog = new UpdaterDialog(activity, versionName, changes,appUrl,necessaryVersion );
//                updateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//
//                        if (onFailListener != null) {
//                            onFailListener.onFail();
//                        }
//
//                    }
//                });
//                updateDialog.show();

                ApplicationConfig appConfig=new ApplicationConfig();
                appConfig.versionName=versionName;
                appConfig.changes=changes;
                appConfig.appUrl=appUrl;
                appConfig.necessaryVersion=necessaryVersion;



                Intent intent = new Intent(activity, UpdateActivity.class);
                intent.putExtra("AppConfig",new Gson().toJson(appConfig));
                activity.startActivity(intent);


            } else {
                if (onFailListener != null) {
                    onFailListener.onFail();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

            if (onFailListener != null) {
                onFailListener.onFail();
            }
        }
    }


}
