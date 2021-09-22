package ir.malihehmoradi.appupdater.model;

import java.util.List;

public class AppConfig {

    public Integer versionCode;
    public String versionName;
    public String necessaryVersion;
    public List<Change> recentChanges;
    public String appUrl;



    public static class Change {
        public String date;
        public String description;
    }
}
