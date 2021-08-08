package ir.malihehmoradi.appupdater.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import ir.malihehmoradi.appupdater.R;
import ir.malihehmoradi.appupdater.helper.Helper;

public class UpdaterDialog extends Dialog {

    private static final String TAG = UpdaterDialog.class.getSimpleName();


    private Activity activity;
    private ProgressBar progressBar;
    private LinearLayout lnr_percent;
    private TextView txt_percent;
    private TextView txt_error;
    private Button btn_send;
    private TextView txt_cancel;

    /**
     *
     */
    private final String versionName;
    private final String changes;
    private final String appUrl;
    private final String necessaryVersion;


    /***
     *
     * @param activity
     * @param versionName
     * @param changes
     * @param appUrl
     * @param necessaryVersion
     */
    public UpdaterDialog(@NonNull Activity activity, @NonNull String versionName, @NonNull String changes, @NonNull String appUrl, @NonNull String necessaryVersion) {
        super(activity);
        this.activity = activity;
        this.versionName = versionName;
        this.changes = changes;
        this.appUrl = appUrl;
        this.necessaryVersion = necessaryVersion;


        View parentView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        View view = LayoutInflater.from(activity).inflate(R.layout.view_update, (ViewGroup) parentView, false);
        setContentView(view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE);
//        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        initView();
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
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
        String currentVersion = Helper.getVersionName(activity);
        txt_appVersion.setText(String.format(activity.getResources().getString(R.string.message_new_version), currentVersion, versionName));

        //Description
        txt_description.setText(activity.getResources().getString(R.string.app_change) + "\n" + changes);

        //App link
        txt_appLink.setText(appUrl);

        //Cancel
        if (Helper.compareVersionNames(necessaryVersion, Helper.getVersionName(getContext())) <= 0) {
            txt_cancel.setVisibility(View.VISIBLE);
        } else {
            txt_cancel.setVisibility(View.GONE);
        }
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        //Send Button
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadApp(appUrl);

            }
        });
    }

    private void downloadApp(String appUrl) {

        String fileName = "Vahram" + "_v" + versionName + ".apk";
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
                txt_error.setText(activity.getResources().getString(R.string.message_success_update));


                //Install app
                Helper.installApk(activity, new File(destinationPath));
                dismiss();

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
                btn_send.setText(getContext().getResources().getString(R.string.retry));
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

    @Override
    protected void onStop() {
        super.onStop();
        FileDownloader.getImpl().pauseAll();
    }

    @Override
    public void setOnCancelListener(@Nullable DialogInterface.OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }

    @Override
    public void setOnDismissListener(@Nullable DialogInterface.OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

}