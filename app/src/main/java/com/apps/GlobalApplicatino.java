package com.apps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.adtrace.sdk.ActivityHandler;
import io.adtrace.sdk.AdTrace;
import io.adtrace.sdk.AdTraceConfig;
import io.adtrace.sdk.LogLevel;

public class GlobalApplicatino extends Application {
    // app token for adtrace application
    private final String appToken = "cn2dajeoy3uu";

    private static final String PREFS_NAME = "adtrace_preferences";
    private final String PREFS_KEY_INSTALL = "install_tracked";
    private final String FILENAME_1="AdTraceIoActivityState";

    /**
     * This variable is determines whether it is the first time app run after user updates to new version
     * or not.
     * the process contains deleting adtrace's local files to force sdk in order to send a new install
     * and update the device. please note that you handle this variable well in your code.
     */

    private boolean isThisFirstTimeRunAfterUpdate = true;

    @Override
    public void onCreate() {
        super.onCreate();

        // remove local files before adtrace init
        getAdTraceLocalParams();

        // adtrace init
        AdTraceConfig config = new AdTraceConfig(this, appToken, AdTraceConfig.ENVIRONMENT_SANDBOX);
        config.setLogLevel(LogLevel.VERBOSE);
        AdTrace.onCreate(config);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                AdTrace.onResume();
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                AdTrace.onPause();
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }


    private void getAdTraceLocalParams() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        try {

            // read from shared prefs if install tracked before
            boolean isInstallTracked = sharedPreferences.getBoolean(PREFS_KEY_INSTALL, false);
            Toast.makeText(this, "install tracked: "+ isInstallTracked + ", isThisFirstTimeRunAfterUpdate: "+isThisFirstTimeRunAfterUpdate, Toast.LENGTH_SHORT).show();

            // condition for deleting the files and force sdk to send another install(sdk click)
            if(isInstallTracked && isThisFirstTimeRunAfterUpdate){
                Toast.makeText(this, "trying to set install_tracked to false ", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(PREFS_KEY_INSTALL,false);
                getApplicationContext().deleteFile(FILENAME_1);
                Toast.makeText(this, "install_tracked set to false ", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "install tracked!", Toast.LENGTH_SHORT).show();


        } catch (Exception exception) {
            Toast.makeText(this, "Error Reading(might not exist yet!)", Toast.LENGTH_SHORT).show();
        }
    }


}
