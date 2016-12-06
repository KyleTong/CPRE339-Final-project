package com.application.kyle.infinet_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity    {

    //private static final int REQUEST_READ_CONTACTS = 0;

    /*private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox checkPassword; //remember password
    private Button mEmailSignInButton;  //Login button
    private SharedPreferences sharedpref; //save email & password
    private SharedPreferences.Editor edit;
    private ProgressDialog proDialog;   //waiting on...
    private Handler loginHandler;   //information processing
    private String email;
    private String password;
    private TextView mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Setup the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(submitListener);

        //Go to signup page:
        mSignUpButton = (TextView) findViewById(R.id.sign_up_button);
        if (mSignUpButton != null) {
            mSignUpButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
            });
        }


        loginHandler = new Handler() {
            //handle http login condition:

            @Override
            public void handleMessage(Message msg) {
                if (mEmailView.getText().toString() == null) {
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
                if (mPasswordView.getText().toString() == null)  {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                }

                    Bundle bundle = msg.getData();
                    boolean loginResult = (Boolean) bundle.get("isNetError");

                    if (proDialog != null) {
                        proDialog.dismiss();
                    }

                    boolean isNet = isNetworkAvailable(LoginActivity.this);
                    if (!isNet) {
                        Toast.makeText(LoginActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                    if (!loginResult) {
                        Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                        System.out.println(String.format("%s, %s", email, password));

                    }

                super.handleMessage(msg);
            }

        };
    }

    //check whether network is available:
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private OnClickListener submitListener = new OnClickListener() {

        public void onClick(View v) {
            email = mEmailView.getText().toString();
            password=mPasswordView.getText().toString();

            proDialog = ProgressDialog.show(LoginActivity.this,"Hold on","Login now...", true, true);
            Thread loginThread = new Thread(new LoginFailureHandler());
            loginThread.start();
        }
    };




    private class LoginFailureHandler implements Runnable{

        @Override
        public void run() {
            boolean loginState = sendRequestGet(email,password);
            if(loginState){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                Bundle bun = new Bundle();
                bun.putString("username",email);
                bun.putString("password",password);
                intent.putExtras(bun);
                startActivity(intent);
                proDialog.dismiss();
            }else{
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putBoolean("isNetError",loginState);
                message.setData(bundle);
                loginHandler.sendMessage(message);

            }
        }

    }



    //connect http（用户名与密码在和json数据之间的交互）:
    private boolean sendRequestGet(String userName, String Password) {
        boolean result = false;
        BufferedInputStream in = null;
        HttpURLConnection c = null;

        //Set url connection（建立连接）：
        String url = "http://finalrinoa-tomcat.daoapp.io/User/api/login.do?id=1&username=%s&password=%s";
        String fullUrl = String.format(url, userName, Password);
        //String fullUrl = "http://www.5ocloud.com.cn:82/User/api/login.do?id=1&username=admin&password=123456";
        try {
            URL httpURL = new URL(fullUrl);
            c = (HttpURLConnection) httpURL.openConnection();
            //List all properties（设置通用属性）：
            c.setRequestProperty("accept", "*/*");
            c.setRequestProperty("connection", "Keep-Alive");
            c.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            c.setRequestMethod("GET");
            c.setConnectTimeout(5000);
            c.connect();

            //Define BufferReader for reading the url to response（定义BufferReader来获取url的响应）
            in = new BufferedInputStream(c.getInputStream());
            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                byte[] body = new byte[1024];
                in.read(body);
                String loginState = new String(body).trim();
                if (loginState.equals("ok")) {
                    result = true;

                } else {
                    result = false;

                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                    if (c != null) {
                        c.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


}

