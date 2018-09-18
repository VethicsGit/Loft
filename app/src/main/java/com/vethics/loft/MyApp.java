package com.vethics.loft;

import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("keydata.realm")
                .schemaVersion(0)
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        // DynamicRealm exposes an editable schema
                        RealmSchema schema = realm.getSchema();
                        // Migrate to version 1: Add a new class.
                        // Example:
                        // public Person extends RealmObject {
                        //     private String name;
                        //     private int age;
                        //     // getters and setters left out for brevity
                        // }
                        if (oldVersion == 0) {
                            schema.create("Person")
                                    .addField("name", String.class)
                                    .addField("age", int.class);
                            oldVersion++;
                        }

                        // Migrate to version 2: Add a primary key + object references
                        // Example:
                        // public Person extends RealmObject {
                        //     private String name;
                        //     @PrimaryKey
                        //     private int age;
                        //     private Dog favoriteDog;
                        //     private RealmList<Dog> dogs;
                        //     // getters and setters left out for brevity
                        // }
                        if (oldVersion == 1) {
                            schema.get("Person")
                                    .addField("id", long.class, FieldAttribute.PRIMARY_KEY)
                                    .addRealmObjectField("favoriteDog", schema.get("Dog"))
                                    .addRealmListField("dogs", schema.get("Dog"));
                            oldVersion++;
                        }
                    }
                })
                //.deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        /*TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/avenir_lt_std_35_light.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

    private Typeface normalFont;
    private Typeface boldFont;

    // -- Fonts -- //
    public void setTypeface(TextView textView) {
        if (textView != null) {
            if (textView.getTypeface() != null && textView.getTypeface().isBold()) {
                textView.setTypeface(getBoldFont());
            } else {
                textView.setTypeface(getNormalFont());
            }
        }
    }

    private Typeface getNormalFont() {
        if (normalFont == null) {
            normalFont = Typeface.createFromAsset(getAssets(), "font/avenirltstd_light.otf");
        }
        return this.normalFont;
    }

    private Typeface getBoldFont() {
        if (boldFont == null) {
            boldFont = Typeface.createFromAsset(getAssets(), "font/avenirltstd_book.otf");
        }
        return this.boldFont;
    }
}