package com.moneydesktop.finance.shared;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.moneydesktop.finance.BaseActivity;
import com.moneydesktop.finance.DebugActivity;
import com.moneydesktop.finance.R;
import com.moneydesktop.finance.data.DataBridge;
import com.moneydesktop.finance.data.DemoData;
import com.moneydesktop.finance.handset.activity.DashboardHandsetActivity;
import com.moneydesktop.finance.model.EventMessage;
import com.moneydesktop.finance.model.User;
import com.moneydesktop.finance.model.EventMessage.LoginEvent;
import com.moneydesktop.finance.tablet.activity.DashboardTabletActivity;
import com.moneydesktop.finance.tablet.activity.LoginTabletActivity;
import com.moneydesktop.finance.util.Animator;
import com.moneydesktop.finance.util.DialogUtils;
import com.moneydesktop.finance.util.Fonts;
import com.moneydesktop.finance.util.UiUtils;
import com.moneydesktop.finance.views.LabelEditText;

import de.greenrobot.event.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LoginBaseActivity extends BaseActivity {

    private final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final String TAG = "LoginActivity";
    
    private ViewFlipper mViewFlipper;
    private LinearLayout mButtonView, credentialView;
    private Button loginViewButton, demoButton, loginButton, cancelButton, submitButton, demoButton2, returnHomeButton;
    private LabelEditText username, password, signupName, signupEmail, signupBank;
    private ImageView logo;
    private TextView signupText, loginText, messageTitle, messageBody, cancelText, needAccount, nagBank, thankYou, tryDemo;
    private Animation inLeft, inRight, outLeft, outRight, inUp, outDown, noMovement;
    private boolean failed = false;
    
    @Override
    public void onBackPressed() {
        if (mViewFlipper.indexOfChild(mViewFlipper.getCurrentView()) == 1 ) {
            cancel(inLeft,outRight);
        } 
        else if(mViewFlipper.indexOfChild(mViewFlipper.getCurrentView()) == 2 || 
                mViewFlipper.indexOfChild(mViewFlipper.getCurrentView()) == 3){
            cancel(outDown, noMovement);
        }
        else {
            super.onBackPressed();
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        toDashboard();
    }
    
    private void toDashboard() {

        DialogUtils.hideProgress();
        
        if (User.getCurrentUser() != null) {
            Intent i;
            if(BaseActivity.isTablet(this)){
                i = new Intent(this, DashboardTabletActivity.class);
            }
            else{
                i = new Intent(this, DashboardHandsetActivity.class);
            }
            startActivity(i);
            overridePendingTransition(R.anim.fade_in_fast, R.anim.none);
        }
    }
    
    protected void addDemoCredentials() {
        
        username.setText("saul.howard@moneydesktop.com");
        password.setText("password123");
    }
    
    @SuppressLint("NewApi")
    protected void setupView() {
        
        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mButtonView = (LinearLayout) findViewById(R.id.button_view);
        credentialView = (LinearLayout) findViewById(R.id.credentials);
        configureFlipper();
        
        username = (LabelEditText) findViewById(R.id.username_field);
        password = (LabelEditText) findViewById(R.id.password_field);
        
        loginViewButton = (Button) findViewById(R.id.login_view_button);
        demoButton = (Button) findViewById(R.id.demo_button);
        demoButton2 = (Button) findViewById(R.id.demo_button2);
        returnHomeButton = (Button) findViewById(R.id.home_view_button);
        loginButton = (Button) findViewById(R.id.login_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        
        logo = (ImageView) findViewById(R.id.logo);
        
        signupText = (TextView) findViewById(R.id.signup);
        loginText = (TextView) findViewById(R.id.login_text);
        thankYou = (TextView) findViewById(R.id.thank_you);
        tryDemo = (TextView) findViewById(R.id.try_demo);
        signupName = (LabelEditText) findViewById(R.id.signup_name);
        signupEmail = (LabelEditText) findViewById(R.id.signup_email);
        signupBank = (LabelEditText) findViewById(R.id.signup_bank);
        messageTitle = (TextView) findViewById(R.id.message_title);
        messageBody = (TextView) findViewById(R.id.message_body);
        cancelText = (TextView) findViewById(R.id.cancel_text);
        submitButton = (Button) findViewById(R.id.submit_button);
        needAccount = (TextView) findViewById(R.id.need_account);
        nagBank = (TextView) findViewById(R.id.nag_bank);
        
        Fonts.applyPrimaryFont(loginViewButton, 20);
        Fonts.applyPrimaryFont(demoButton, 20);
        Fonts.applyPrimaryFont(demoButton2, 20);
        Fonts.applyPrimaryFont(returnHomeButton, 20);
        Fonts.applyPrimaryFont(loginButton, 20);
        Fonts.applyPrimaryFont(cancelButton, 20);
        Fonts.applyPrimaryFont(submitButton, 20);
        Fonts.applyPrimaryFont(signupText, 15);
        Fonts.applyPrimaryFont(loginText, 15);
        Fonts.applyPrimaryFont(cancelText, 15);
        Fonts.applyPrimaryBoldFont(thankYou, 20);
        Fonts.applyPrimaryFont(tryDemo, 20);
        Fonts.applyPrimarySemiBoldFont(username, 25);
        Fonts.applyPrimarySemiBoldFont(password, 25);
        Fonts.applyPrimaryBoldFont(needAccount, 20);
        Fonts.applySecondaryItalicFont(nagBank, 15);
        Fonts.applyPrimaryBoldFont(submitButton,15);
        addListeners();
    }
    
    private void addListeners() {
        cancelText.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                cancel(outDown, noMovement);
            }
        });
        loginViewButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                toLoginView();
            }
        });
        returnHomeButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {               
                cancel(outDown, noMovement);
            }
        });
        demoButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                demoMode();
            }
        });
        demoButton2.setOnClickListener(new OnClickListener() {
                
                public void onClick(View v) {
                    
                    demoMode();
                }
            });
        loginButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                login();
            }
        });
        submitButton.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                  signup();
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                cancel(inLeft,outRight);
            }
        });
        
        logo.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                
                Intent i = new Intent(LoginBaseActivity.this, DebugActivity.class);
                startActivity(i);
            }
        });
        username.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
                
                username.setTextColor(getResources().getColor(hasFocus ? R.color.white : R.color.light_gray1));
            }
        });
        
        password.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
                
                password.setTextColor(getResources().getColor(hasFocus ? R.color.white : R.color.light_gray1));
            }
        });
        
        messageBody.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                
                return true;
            }
        });
        signupName.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
                
                signupName.setTextColor(getResources().getColor(hasFocus ? R.color.white : R.color.light_gray1));
            }
        });
        signupEmail.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
                
                signupEmail.setTextColor(getResources().getColor(hasFocus ? R.color.white : R.color.light_gray1));
            }
        });
        signupBank.setOnFocusChangeListener(new OnFocusChangeListener() {
            
            public void onFocusChange(View v, boolean hasFocus) {
                
                signupBank.setTextColor(getResources().getColor(hasFocus ? R.color.white : R.color.light_gray1));
            }
        });
        messageTitle.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent event) {
                
                return true;
            }
        });
        
        loginText.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.setup_url)));
                startActivity(browserIntent);
            }   
        });
        signupText.setOnClickListener(new OnClickListener() {
                
                public void onClick(View v) {

                    toSignupView();
                }
        });
    }
    
    protected void setupAnimations() {
        
        inLeft = AnimationUtils.loadAnimation(this, R.anim.in_left);
        outRight = AnimationUtils.loadAnimation(this, R.anim.out_right);
        outLeft = AnimationUtils.loadAnimation(this, R.anim.out_left);
        inRight = AnimationUtils.loadAnimation(this, R.anim.in_right);
        inUp = AnimationUtils.loadAnimation(this, R.anim.in_up);
        outDown = AnimationUtils.loadAnimation(this, R.anim.out_down);
        noMovement = AnimationUtils.loadAnimation(this, R.anim.none);
        inLeft.setAnimationListener(new AnimationListener() {
            
            public void onAnimationStart(Animation animation) {

                configureButtons(false);
            }
            
            public void onAnimationRepeat(Animation animation) {}
            
            public void onAnimationEnd(Animation animation) {

                configureButtons(true);
            }
        });
        
        inRight.setAnimationListener(new AnimationListener() {
            
            public void onAnimationStart(Animation animation) {

                configureButtons(false);
            }
            
            public void onAnimationRepeat(Animation animation) {}
            
            public void onAnimationEnd(Animation animation) {

                configureButtons(true);
            }
        });
    }
    
    private void configureFlipper() {

        mViewFlipper.setInAnimation(inRight);
        mViewFlipper.setOutAnimation(outLeft);
    }
    
    private void configureButtons(boolean enabled) {
        
        loginViewButton.setEnabled(enabled);
        demoButton.setEnabled(enabled);
        loginButton.setEnabled(enabled);
        cancelButton.setEnabled(enabled);
    }
    
    private void toLoginView() {
        mViewFlipper.setOutAnimation(outLeft);
        mViewFlipper.setInAnimation(inRight);
        mViewFlipper.setDisplayedChild(1);
    }
    private void toSignupView() {
        mViewFlipper.setOutAnimation(noMovement);
        mViewFlipper.setInAnimation(inUp);
        mViewFlipper.setDisplayedChild(2);
    }
    private void demoMode() {
        
        User.registerDemoUser();
        
        DialogUtils.showProgress(this, getString(R.string.demo_message));
        
        new AsyncTask<Void, Void, Boolean>() {
            
            @Override
            protected Boolean doInBackground(Void... params) {

                DemoData.createDemoData();

                return true;
            }
            
            @Override
            protected void onPostExecute(Boolean result) {

                toDashboard();
            }
            
        }.execute();
    }
    private void signup(){
        if(signupCheck() && validateEmail(username.getText().toString())){
            submit();
        }
        else if(!signupCheck()){
                 DialogUtils.alertDialog(getString(R.string.error_title), getString(R.string.error_login_incomplete), this, null);
            }
        else if(!validateEmail(username.getText().toString())){
            DialogUtils.alertDialog(getString(R.string.error_title), getString(R.string.error_email_invalid), this, null);
        }
    }
    private void submit(){
        //TODO add data submission code here
        toThankYou();
    }
    private void toThankYou(){
        mViewFlipper.setOutAnimation(inLeft);
        mViewFlipper.setInAnimation(outRight);
        mViewFlipper.setDisplayedChild(3);
        hideKeyboard();
    }
    private void login() {
        
        if (failed) {
            
            resetLogin();
            
            return;
        }
        if(!validateEmail(username.toString()))
        {
            DialogUtils.alertDialog(getString(R.string.error_title), getString(R.string.error_email_invalid), this, null);
            return;
        }
        if (loginCheck()) {
            
            authenticate();
            
        } else {
            
            DialogUtils.alertDialog(getString(R.string.error_title), getString(R.string.error_login_incomplete), this, null);
        }
    }
    
    private void resetLogin() {
        
        toggleAnimations(true);
        
        loginButton.setText(getString(R.string.button_login));
        cancelButton.setText(getString(R.string.button_cancel));
        
        failed = false;
    }
    private boolean signupCheck(){
        return !signupName.getText().toString().equals("") && !signupEmail.getText().toString().equals("") && !signupBank.getText().toString().equals("");
    }
    private boolean loginCheck() {
        
        return !username.getText().toString().equals("") && !password.getText().toString().equals("");
    }
    private boolean validateEmail(String email) {
        
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        
        return matcher.matches();
    }
    private void authenticate() {

        DialogUtils.showProgress(this, getString(R.string.loading));
        
        new AsyncTask<Void, Void, Boolean>() {
            
            @Override
            protected Boolean doInBackground(Void... params) {

                DataBridge dataBridge = DataBridge.sharedInstance();
                
                try {
                    
                    dataBridge.authenticateUser(username.getText().toString(), password.getText().toString());
                    
                } catch (Exception e) {
                    
                    Log.e(TAG, "Error Authenticating", e);
                    return false;
                }

                return true;
            }
            
            @Override
            protected void onPostExecute(Boolean result) {

                DialogUtils.hideProgress();
                
                failed = !result;
                
                if (!result) {
                    
                    messageTitle.setText(getString(R.string.error_login_failed));
                    messageBody.setText(getString(R.string.error_login_invalid));
                            
                    toggleAnimations(false);
                    
                    loginButton.setText(getString(R.string.button_return_login));
                    cancelButton.setText(getString(R.string.button_setup));
                
                } else {
                    
                    EventBus eventBus = EventBus.getDefault();
                    eventBus.post(new EventMessage().new LoginEvent());
                    
                    toDashboard();
                }
            }
            
        }.execute();
    }
    
    private void toggleAnimations(boolean reset) {
        
        int offset = (int) (.20 * UiUtils.getScreenMeasurements(this)[1]) * (reset ? -1 : 1);
        long duration = 750;
        long delay = reset ? 250 : 0;
        
        Animator.translateView(mButtonView, new float[] {0, offset}, duration);
        Animator.fadeView(loginText, !reset, duration, delay);
        Animator.fadeView(credentialView, !reset, duration, delay);
        Animator.fadeView(username, !reset, duration, delay);
        Animator.fadeView(password, !reset, duration, delay);
        
        delay = reset ? 0 : 250;
        
        Animator.fadeView(messageTitle, reset, duration, delay);
        Animator.fadeView(messageBody, reset, duration, delay);
        
        Animator.startAnimations();
    }
   
    private void cancel(Animation out, Animation in) {
        
        if (failed) {
            
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.setup_url)));
            startActivity(browserIntent);
            resetLogin();
            
            return;
        }
        
        username.clearFocus();
        password.clearFocus();
        hideKeyboard();
        mViewFlipper.setOutAnimation(out);
        mViewFlipper.setInAnimation(in);
        mViewFlipper.setDisplayedChild(0);
    }
    
    private void hideKeyboard() {

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(mViewFlipper.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public String getActivityTitle() {
        return null;
    }

}
