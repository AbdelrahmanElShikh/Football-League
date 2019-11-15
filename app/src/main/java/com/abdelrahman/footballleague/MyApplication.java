package com.abdelrahman.footballleague;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @author Abdel-Rahman El-Shikh on 15-Nov-19.
 * This class is responsible for the network checking function of the app.
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if(instance == null)
            instance = this;
    }

    public static MyApplication getInstance(){
        return instance;
    }
    public static boolean hasNetwork(){
        return instance.isNetworkAvailable();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
