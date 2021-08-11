package ir.malihehmoradi.appupdater.model;

import java.util.List;

public class AppConfig {

    public Integer versionCode;
    public String versionName;
    public String necessaryVersion;
    public List<Change> changes;
    public String appUrl;
    public String betaVersion;
    public String betaUrl;
    public Integer adminPass;


    public static class Change {
        public String date;
        public String description;
    }
}
