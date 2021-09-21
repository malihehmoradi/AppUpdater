package ir.malihehmoradi.appupdater.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import ir.malihehmoradi.appupdater.R;
import ir.malihehmoradi.appupdater.helper.Helper;
import ir.malihehmoradi.appupdater.model.ApplicationConfig;

public class InAppUpdateActivity extends AppCompatActivity {


    private static final String TAG = "InAppUpdateActivity";
    private static ApplicationConfig appConfig;
    private ProgressBar progressBar;
    private LinearLayout lnr_percent;
    private TextView txt_percent;
    private TextView txt_error;
    private Button btn_send;
    private TextView txt_cancel;
    private OnUpdateListener onUpdateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIntentData();
        setContentView(R.layout.activity_in_app_update);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(getResources().getString(R.string.update_app));


        initView();

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        String appConfigStr = bundle.getString("AppConfig");

        appConfig = new Gson().fromJson(appConfigStr, ApplicationConfig.class);

    }

    private void initView() {
        TextView txt_appVersion = findViewById(R.id.txt_appVersion);
        TextView txt_description = findViewById(R.id.txt_description);
        progressBar = findViewById(R.id.progressBar);
        txt_cancel = findViewById(R.id.txt_cancel);
        btn_send = findViewById(R.id.btn_send);
        lnr_percent = findViewById(R.id.lnr_percent);
        txt_percent = findViewById(R.id.txt_percent);
        txt_error = findViewById(R.id.txt_error);
        TextView txt_appLink = findViewById(R.id.txt_appLink);


        //App Version
        String currentVersion = Helper.getVersionName(getApplicationContext());
        txt_appVersion.setText(String.format(getResources().getString(R.string.message_new_version), currentVersion, appConfig.versionName));

        //Recent changes
//        txt_description.setText(appConfig.recentChanges);

        //App link
        txt_appLink.setText(appConfig.appUrl);

        //Cancel
        if (Helper.compareVersionNames(appConfig.necessaryVersion, Helper.getVersionName(getApplicationContext())) <= 0) {
            txt_cancel.setVisibility(View.VISIBLE);
        } else {
            txt_cancel.setVisibility(View.GONE);
        }
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onUpdateListener != null) {
                    finish();
                    onUpdateListener.onCancel();
                }
            }
        });

        //Send Button
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStorageGranted()) {
                    checkStorageGranted();
                } else {
                    downloadApp(appConfig.appUrl);
                }

            }
        });
    }

    private void downloadApp(String appUrl) {

        String fileName = "Vahram" + "_v" + appConfig.versionName + ".apk";
        String destinationPath = Environment.getExternalStorageDirectory() + "/" + "Vahram" + "/" + fileName;

        FileDownloadListener fileDownloadListener = new FileDownloadListener() {

            @Override
            protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                Log.d(TAG, "connected: ");
            }

            @Override
            protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
                super.retry(task, ex, retryingTimes, soFarBytes);
                Log.d(TAG, "retry: ");
            }

            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d(TAG, "pending: ");
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                final float percent = soFarBytes / (float) totalBytes;
                Log.d(TAG, "progress: soFarBytes=" + soFarBytes + "  totalBytes=" + totalBytes + "  curentPersent=" + (int) (percent * 100));

                if (totalBytes == -1) {
                    progressBar.setIndeterminate(true);
                } else {
                    progressBar.setMax(100);
                    progressBar.setProgress((int) (percent * 100));
                }


                //Show ProgressBar
                btn_send.setVisibility(View.GONE);
                txt_cancel.setVisibility(View.GONE);

                //Show Percent
                progressBar.setVisibility(View.VISIBLE);
                lnr_percent.setVisibility(View.VISIBLE);
                txt_percent.setText((int) (percent * 100) + "");

                //Hide error
                txt_error.setVisibility(View.GONE);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d(TAG, "completed: ");

                progressBar.setIndeterminate(false);


                //Show successful message
                txt_error.setTextColor(Color.GREEN);
                txt_error.setText(getResources().getString(R.string.message_success_update));


                //Install app
                Helper.installApk(InAppUpdateActivity.this, new File(destinationPath));


                if (onUpdateListener != null) {
                    finish();
                    onUpdateListener.onSuccess();
                }
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.d(TAG, "paused: ");

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d(TAG, "error: ");
                e.printStackTrace();
                FileDownloader.getImpl().clearAllTaskData();
                progressBar.setIndeterminate(false);


                //Show Error
                txt_error.setVisibility(View.VISIBLE);
                txt_error.setTextColor(Color.RED);
                txt_error.setText("Error in update");


                //Show Btn Resume and Cancel
                btn_send.setVisibility(View.VISIBLE);
                btn_send.setText(getResources().getString(R.string.retry));
                txt_cancel.setVisibility(View.VISIBLE);
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.d(TAG, "warn: ");
                progressBar.setIndeterminate(false);
            }

        };


        //First clear all task data in FileDownloader
        FileDownloader.getImpl().clearAllTaskData();


        //Start again download
        FileDownloader.getImpl().create(appUrl)
                .setPath(destinationPath)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(fileDownloadListener)
                .start();
    }

    public void checkStorageGranted() {
        // Get permission of read and write on files
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public boolean isStorageGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface OnUpdateListener {

        void onSuccess();

        void onCancel();

        void onFail();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 1) {
            if (isStorageGranted()) {
                downloadApp(appConfig.appUrl);
            }
        }

    }
}