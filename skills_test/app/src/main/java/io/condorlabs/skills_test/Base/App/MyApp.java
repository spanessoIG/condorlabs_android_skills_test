package io.condorlabs.skills_test.Base.App;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by spanesso_dev on 29/06/18.
 */

public class    MyApp extends Application {
    static MyApp instance;
    static Activity mCurrentActivity;


    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("tasky.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public static void setCurrentActivity(Activity mCurrentActivity) {
        MyApp.mCurrentActivity = mCurrentActivity;
    }

    public static Realm getDefaultRealmInstance() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Realm realm = Realm.getDefaultInstance();

        return realm;
    }
}
