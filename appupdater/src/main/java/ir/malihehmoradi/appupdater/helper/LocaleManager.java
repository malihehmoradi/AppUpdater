package ir.malihehmoradi.appupdater.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.*;

public class LocaleManager extends ContextWrapper {


    private String languageCode;
    private String language;
    private Configuration configuration;

    public LocaleManager(Context base, String languageCode) {
        super(base);
        this.languageCode = languageCode;
    }

    public Context wrap() {

        languageCode = "fa";//ToDo

        Resources res = getBaseContext().getResources();
        configuration = new Configuration(res.getConfiguration());
        DisplayMetrics dm = res.getDisplayMetrics();
        Locale newLocale = new Locale(languageCode);

        if (newLocale != null) {
            // Configuration.setLocale is added after 17 and Configuration.locale is deprecated
            // after 24
            if (Build.VERSION.SDK_INT >= 17) {
                configuration.setLocale(newLocale);
            } else {
                configuration.locale = newLocale;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Set<Locale> set = new LinkedHashSet<>();
            // bring the target locale to the front of the list
            set.add(newLocale);
            LocaleList all = LocaleList.getDefault();
            for (int i = 0; i < all.size(); i++) {
                // append other locales supported by the user
                set.add(all.get(i));
            }
            Locale[] locales = set.toArray(new Locale[0]);
            configuration.setLocales(new LocaleList(locales));
            res.updateConfiguration(configuration, dm);
            return getBaseContext().createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.locale = newLocale;
            configuration.setLocale(newLocale);
            configuration.setLayoutDirection(newLocale);
            res.updateConfiguration(configuration, dm);
            return getBaseContext().createConfigurationContext(configuration);
        }
        return getBaseContext();
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
