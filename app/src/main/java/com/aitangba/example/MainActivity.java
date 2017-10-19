package com.aitangba.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aitangba.pickdatetime.DatePickDialog;
import com.aitangba.pickdatetime.OnSureListener;
import com.aitangba.pickdatetime.bean.DateParams;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY,
                        DateParams.TYPE_HOUR, DateParams.TYPE_MINUTE);
            }
        });

        findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(DateParams.TYPE_YEAR, DateParams.TYPE_MONTH, DateParams.TYPE_DAY);
            }
        });

        findViewById(R.id.time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDialog(DateParams.TYPE_HOUR, DateParams.TYPE_MINUTE);
            }
        });
    }

    private void showDatePickDialog(@DateParams.Type int... style) {
        Calendar todayCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        endCal.add(Calendar.YEAR, 6);

        new DatePickDialog.Builder()
                .setTypes(style)
                .setCurrentDate(todayCal.getTime())
                .setStartDate(startCal.getTime())
                .setEndDate(endCal.getTime())
                .setOnSureListener(new OnSureListener() {
                    @Override
                    public void onSure(Date date) {
                        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                })
                .show(this);
    }
}
