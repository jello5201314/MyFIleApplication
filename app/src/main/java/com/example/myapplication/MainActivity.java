package com.example.myapplication;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.library.DatePickerController;
import com.example.myapplication.library.DayPickerView;
import com.example.myapplication.library.SimpleMonthAdapter;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.pm.PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

public class MainActivity extends AppCompatActivity implements DatePickerController {
    private ActivityManager activityManager;
    private PackageManager packageManager;
    private boolean setIcon = true;
    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityManager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        packageManager = getPackageManager();
        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.init(this);
        dayPickerView.setController(this);

        TextView foldernowTv = (TextView) findViewById(R.id.folder_now);
        foldernowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("2222".equals(getTitle())) {
                    setIcon = true;
                } else {
                    setIcon = false;
                }

                packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "com.example.myapplication.MainActivity"),
                        setIcon == true ? COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);

                packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "com.example.myapplication.changeAfter"),
                        setIcon == true ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }
        });
        TextView foldernowTv2 = (TextView) findViewById(R.id.folder_now2);
        foldernowTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("2222".equals(getTitle())) {
                    setIcon = true;
                } else {
                    setIcon = false;
                }

                packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "com.example.myapplication.MainActivity"),
                        setIcon == true ? COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                        PackageManager.DONT_KILL_APP);

                packageManager.setComponentEnabledSetting(new ComponentName(MainActivity.this, "com.example.myapplication.changeAfter"),
                        setIcon == true ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getMaxYear()
    {
        return 2020;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays)
    {

        Log.e("Date range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}