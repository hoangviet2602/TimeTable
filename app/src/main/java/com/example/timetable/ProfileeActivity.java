package com.example.timetable;

import static com.example.timetable.LoginActivity.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText txtName,txtPhone,txtDob;
    Button btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilee);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item:
                        startActivity(new Intent(getApplicationContext(), SubjectActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
        txtDob = findViewById(R.id.ngaysinh);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.sdt);
        btnSignOut = findViewById(R.id.btnsignout);

        txtDob.setFocusable(false);
        txtName.setFocusable(false);
        txtPhone.setFocusable(false);

        info();
        //txtPhone.setText("Số điện thoại: " + Users.get(0).get);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Users.clear();
                Intent intent = new Intent(ProfileeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void info(){
        txtName.setText("Họ và tên: " + Users.get(0).getFullName());
        txtDob.setText("Ngày sinh: "+Users.get(0).getDob());
        txtPhone.setText("Số điện thoại: "+ Users.get(0).getPhone());
    }
}