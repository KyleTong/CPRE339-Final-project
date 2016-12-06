package com.application.kyle.infinet_project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private AutoCompleteTextView sEmailView;
    private AutoCompleteTextView sJobCodeView;
    private EditText sPasswordView;
    private EditText sConfirmView;
    private View sSignUpView;
    private View sProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        sJobCodeView = (AutoCompleteTextView) findViewById(R.id.jobcode);
        sEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        sPasswordView = (EditText) findViewById(R.id.password);
        sConfirmView = (EditText) findViewById(R.id.confirm);

        //click 'Confirm Signup' to send request:
        Button mEmailSignInButton = (Button) findViewById(R.id.submit_signup);
        if (mEmailSignInButton != null) {
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitRegistration();
                }
            });
        }

        sSignUpView = findViewById(R.id.signup_form);
        sProgressView = findViewById(R.id.signup_progress);
    }

    private void submitRegistration() {
        //Reset error handling:
        sEmailView.setError(null);
        sJobCodeView.setError(null);
        sPasswordView.setError(null);
        sConfirmView.setError(null);
        boolean cancel = false;
        View focusView = null;

        //Set the IDs to String:
        String jobCode = sJobCodeView.getText().toString();
        String email = sEmailView.getText().toString();
        String password = sPasswordView.getText().toString();
        String confirm = sConfirmView.getText().toString();

        //Handle empty case:
        if (TextUtils.isEmpty(jobCode)){
            sJobCodeView.setError(getString(R.string.empty_input));
            focusView = sJobCodeView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            sEmailView.setError(getString(R.string.empty_input));
            focusView = sEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            sPasswordView.setError(getString(R.string.empty_input));
            focusView = sPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirm)) {
            sConfirmView.setError(getString(R.string.empty_input));
            focusView = sConfirmView;
            cancel = true;
        }

        //Handle invalid case:
        /*if (!isValidID(jobCode)) {
            sJobCodeView.setError(getString(R.string.invalid_id));
            focusView = sJobCodeView;
            cancel = true;
        }*/
        if (!isEmailValid(email)) {
            sEmailView.setError(getString(R.string.error_input_email));
            focusView = sEmailView;
            cancel = true;
        }
        /*if (!isEmailExisted(email)) {
            sEmailView.setError(getString(R.string.error_existed_email));
            focusView = sEmailView;
            cancel = true;
        }*/
       /* if (!isEmailRegistered(email)) {
            sEmailView.setError(getString(R.string.error_registered_email));
            focusView = sEmailView;
            cancel = true;
        }*/
        if (!isPasswordValid(password)) {
            sPasswordView.setError(getString(R.string.improper_input));
            focusView = sPasswordView;
            cancel = true;
        }
        if (!isPasswordMatch(password,confirm)) {
            sConfirmView.setError(getString(R.string.error_password_mismatch));
            focusView = sConfirmView;
            cancel = true;
        }
        if (!isPasswordLength(password)) {
            sPasswordView.setError(getString(R.string.error_short_password));
            focusView = sPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();

        }else {

            showProgress(true);
            dialog();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        // 隐藏界面并显示等待进度：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            sSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
            sSignUpView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            sProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            sProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    sProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            sProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            sSignUpView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isValidID(String jobCode) {
        //TODO:
        //1. Connnect to http for result;
        //2. need to figure out whether the ID exists based on database information.
        return true;
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@") && email.contains(".com")) {
            return true;
        }
        return false;
    }


    /*private boolean isEmailExisted(String email) {
        //TODO:
        //Need to connect http of email database for knowing whehter email exists.
        return true;
    }

    private boolean isEmailRegistered(String email) {
        //TODO:
        //Need to connect http for knowing whehter email exists.
        return true;
    }*/

    private boolean isPasswordValid(String password) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+");
        Matcher m = p.matcher(password);
        if (m.matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isPasswordLength(String password) {
        if (!(password.length() < 6)) {
            return true;
        }
        return false;
    }


    private boolean isPasswordMatch(String password, String confirm) {
        if (password.compareTo(confirm) == 0) {
            return true;
        }
        return false;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);

        builder.setMessage("SignUp successfully");
        builder.setTitle("Message");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SignUpActivity.this.finish();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        builder.create().show();
    }
}
