package ir.malihehmoradi.appupdater.app;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadLog;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        fileDownloaderConfig();
    }

    private void fileDownloaderConfig() {
        // just for open the log in this demo project.
        FileDownloadLog.NEED_LOG = true;
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();
    }

}
