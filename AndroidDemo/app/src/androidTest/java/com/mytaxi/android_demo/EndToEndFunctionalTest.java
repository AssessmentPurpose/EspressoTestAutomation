package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;

import com.mytaxi.android_demo.activities.DriverProfileActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by srinuk on 20/12/17.
 */

public class EndToEndFunctionalTest {

    @Rule
    public ActivityTestRule<MainActivity> loginRule = new ActivityTestRule<>(MainActivity.class, true, false);

    public void clearAppCache() {
        try
        {
            File root = InstrumentationRegistry.getTargetContext().getFilesDir().getParentFile();
            String[] sharedPreferencesFileNames = new File(root, "shared_prefs").list();
            if(sharedPreferencesFileNames.length > 0) {
                for (String fileName : sharedPreferencesFileNames) {
                    InstrumentationRegistry.getTargetContext().getSharedPreferences(fileName.replace(".xml", ""), Context.MODE_PRIVATE).edit().clear().commit();
                }
            }
        }
        catch (Exception e) {
            System.out.println("Trying to clear cache, but cannot find it");
        }
    }

    public void launchLoginScreen() {
        Intent loginIntent = new Intent(getTargetContext(), MainActivity.class);
        loginRule.launchActivity(loginIntent);
    }

    public void loginWithValidCredentials(String strUsername, String strPassword) throws InterruptedException {
            onView(withId(R.id.edt_username)).perform(typeText(strUsername), closeSoftKeyboard());
            onView(withId(R.id.edt_password)).perform(typeText(strPassword), closeSoftKeyboard());
            Thread.sleep(2000);
            onView(withId(R.id.btn_login)).perform(click());
    }

    public void searchAndSelectDriver(String strNameToSearch, String strNameToSelect) throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.textSearch)).perform(typeText(strNameToSearch), closeSoftKeyboard());
        onView(withText(strNameToSelect)).inRoot(isPlatformPopup()).perform(click());
    }

    public void callDriver() {
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void testCallingDriver() throws InterruptedException {
        clearAppCache();
        launchLoginScreen();
        loginWithValidCredentials("whiteelephant261", "video");
        searchAndSelectDriver("sa", "Sarah Friedrich");
        callDriver();
    }
}
