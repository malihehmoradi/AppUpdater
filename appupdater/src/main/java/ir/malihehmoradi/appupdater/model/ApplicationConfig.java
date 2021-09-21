package ir.malihehmoradi.appupdater.model;

import java.util.List;

public class ApplicationConfig {

    public Integer versionCode;
    public String versionName;
    public String necessaryVersion;
    public List<RecentChange> recentChanges;
    public String appUrl;

    public class RecentChange{
        public String date;
        public String description;
    }

}
