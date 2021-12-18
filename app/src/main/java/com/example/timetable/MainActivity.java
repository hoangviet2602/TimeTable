package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import javax.security.auth.Subject;

import Day.Day;
import Day.DayAdapter;
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ListView lvDay;
    DayAdapter  adapter;
    Day day;
    ArrayList<Day> listDay = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                            startActivity(new Intent(getApplicationContext(), ProfileeActivity.class));
                            overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.item:
                        startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        getDay();
        lvDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), ""+listDay.get(i).getTittle(), Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), SubjectofDay.class));

                Intent intent = new Intent(getApplicationContext(), SubjectofDay.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object",listDay.get(i));
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    private void getDay() {
        lvDay = findViewById(R.id.lvday);

        listDay.add(new Day(1,"D://IT//Adroid/Photo/noti_logo.png","Thứ 2"));
        listDay.add(new Day(2,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Thứ 3"));
        listDay.add(new Day(3,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Thứ 4"));
        listDay.add(new Day(4,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Thứ 5"));
        listDay.add(new Day(5,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Thứ 6"));
        listDay.add(new Day(1,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Thứ 7"));
        listDay.add(new Day(1,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Chủ nhật"));
        listDay.add(new Day(1,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Chủ nhật"));listDay.add(new Day(1,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Chủ nhật"));
        listDay.add(new Day(1,"D:\\IT\\Adroid\\Photo\\noti_logo.png","Chủ nhật"));

        adapter = new DayAdapter(MainActivity.this,listDay,R.layout.item_day);
        lvDay.setAdapter(adapter);
    }
}