package com.application.kyle.infinet_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

public class CheckinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        String[] staffs = {"Kyle Tong -- software developer", "Cindy Rose -- Human resource manager", "Ryan Tyler -- administrator"};
        ListAdapter staffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, staffs);
        ListView staffView = (ListView) findViewById(R.id.nameLists);
        staffView.setAdapter(staffAdapter);

        staffView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            //note that: staff's attendance status is hard code here. In real time,
            //the status will be based on the boolean value returned from attendance machine
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String staff = String.valueOf(parent.getItemAtPosition(position));
                if (staff == "Kyle Tong -- software developer") {
                    Toast.makeText(CheckinActivity.this, "Attend on time", Toast.LENGTH_LONG).show();
                }
                if (staff == "Cindy Rose -- Human resource manager") {
                    Toast.makeText(CheckinActivity.this, "Attend late", Toast.LENGTH_LONG).show();
                }
                if (staff == "Ryan Tyler -- administrator") {
                    Toast.makeText(CheckinActivity.this, "Absence", Toast.LENGTH_LONG).show();
                }


            }
        }

        );
    }
}
