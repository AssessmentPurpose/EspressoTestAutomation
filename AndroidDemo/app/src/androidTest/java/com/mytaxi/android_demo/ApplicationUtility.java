package com.mytaxi.android_demo;

import android.app.Activity;
import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getTargetContext;

public class ApplicationUtility {

    public static void clearAppCache() {
        try
        {
            File root = getTargetContext().getFilesDir().getParentFile();
            String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
            if(sharedPreferencesFileNames.length > 0) {
                for (String fileName : sharedPreferencesFileNames) {
                    getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Trying to clear cache, but cache is not found");
        }
    }
}
