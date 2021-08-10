package ir.malihehmoradi.AppUpdater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import ir.malihehmoradi.appupdater.helper.UpdateChecker;

public class MainActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UpdateChecker(
                this,
                "1.0.1",
                "App optimization",
                "http://appcdn.atishahr.net:8080/application/app.apk",
                "1.0.0")
                .setOnFailUpdate(new UpdateChecker.OnFailListener() {
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