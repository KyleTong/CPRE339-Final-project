package com.application.kyle.infinet_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private View mMainFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Logout_button:
        ImageButton LogoutImageButton = (ImageButton)findViewById(R.id.logoutButton);
        if (LogoutImageButton != null) {
            LogoutImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog();

                }
            });
        }

        ImageButton StatusImageButton = (ImageButton)findViewById(R.id.statusButton);
        if (StatusImageButton != null) {
            StatusImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, MachineActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageButton DataImageButton = (ImageButton)findViewById(R.id.dataButton);


        ImageButton CheckinImageButton = (ImageButton)findViewById(R.id.checkinButton);
        if (DataImageButton != null) {
            CheckinImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CheckinActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    //Logout button to Open Dialog:
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Exit？");
        builder.setTitle("Alert");

        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }


}
