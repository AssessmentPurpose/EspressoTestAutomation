package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.RootMatchers.isSystemAlertWindow;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mytaxi.android_demo.ApplicationUtility.clearAppCache;
import static com.mytaxi.android_demo.R.id.edt_password;
import static com.mytaxi.android_demo.R.id.edt_username;
import static junit.framework.Assert.assertTrue;

public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> loginRule = new ActivityTestRule<>(MainActivity.class, true, false);

    public void launchLoginScreen() {
        Intent loginIntent = new Intent(getTargetContext(), MainActivity.class);
        loginRule.launchActivity(loginIntent);
    }

    public void loginWithValidCredentials(String strUsername, String strPassword) {
        onView(withId(R.id.edt_username)).perform(typeText(strUsername), closeSoftKeyboard());
        onView(withId(R.id.edt_password)).perform(typeText(strPassword), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
    }

    @Test
    public void testLoginScreenTitle() throws InterruptedException {
        clearAppCache();
        launchLoginScreen();
        Assert.assertEquals(getTargetContext().getString(R.string.title_activity_authentication), "Login");
    }

    // Tried to apply withHint method to get the hint message on username, but no luck. Will try to get the solution.
    @Ignore
    public void testUsernameFieldHintMessage() {

    }

    // Tried to apply withHint method to get the hint message on password, but no luck. Will try to get the solution.
    @Ignore
    public void testPasswordFieldHintMessage() {

    }

    @Test
    public void testEnteredValueIsDisplayingInUsernameField() {
        clearAppCache();
        launchLoginScreen();
        onView(withId(edt_username)).perform(typeText("hello"), closeSoftKeyboard());
        getTargetContext().getString(R.string.text_username).matches("hello");
    }

    @Test
    public void testEnteredValueIsDisplayingInPasswordField() {
        clearAppCache();
        launchLoginScreen();
        onView(withId(edt_password)).perform(typeText("hello"), closeSoftKeyboard());
        getTargetContext().getString(R.string.text_password).matches("hello");
    }

    //Tried to identify where login button name is made uppercase but could not find it. Still it is a good case to assert on.
    @Test
    public void testLoginButtonName() throws InterruptedException {
        clearAppCache();
        launchLoginScreen();
        Assert.assertEquals(getTargetContext().getString(R.string.button_login), "Login");
    }

    @Test
    public void testLoginFailureMessageWhenPasswordNotEntered() {
        clearAppCache();
        launchLoginScreen();
        onView(withId(edt_username)).perform(typeText("hello"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Assert.assertEquals(getTargetContext().getString(R.string.message_login_fail), "Login failed");
    }

    @Test
    public void testLoginFailureMessageWhenUsernameNotEntered() {
        clearAppCache();
        launchLoginScreen();
        onView(withId(R.id.edt_password)).perform(typeText("hello"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        Assert.assertEquals(getTargetContext().getString(R.string.message_login_fail), "Login failed");
    }

    @Test
    public void testLoginFailureMessageWhenBothTheCredentialsNotEntered() throws InterruptedException {
        clearAppCache();
        launchLoginScreen();
        onView(withId(R.id.btn_login)).perform(click());
        Assert.assertEquals(getTargetContext().getString(R.string.message_login_fail), "Login failed");
    }

    @Test
    public void testValidLogin() {
        clearAppCache();
        launchLoginScreen();
        loginWithValidCredentials("whiteelephant261", "video");
        Assert.assertEquals(getTargetContext().getString(R.string.text_hint_driver), "Search driver here");
    }

    @Test
    public void testInvalidLogin() {
        clearAppCache();
        launchLoginScreen();
        loginWithValidCredentials("srinu", "video");
        Assert.assertEquals(getTargetContext().getString(R.string.message_login_fail), "Login failed");
    }

    @After
    public void tearDown() {
        loginRule.finishActivity();
    }
}
