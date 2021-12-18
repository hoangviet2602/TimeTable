package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


import Subject.Subject;
import Subject.SubjectAdapter;

public class SubjectActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ListView lvSubject;
    ArrayList<Subject> listSubject = new ArrayList<>();
    SubjectAdapter adapter;
    FloatingActionButton btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item:
                        return true;

                }
                return false;
            }
        });

        getSubject();
        btnadd = findViewById(R.id.btnaddSubject);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });
        lvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //openDialog(Gravity.CENTER);
            }
        });


    }
    public void Delete(final  int position){
        listSubject.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void Update(final  int position){
        final  Subject subject = listSubject.get(position);
        openDialogEdit(Gravity.CENTER,subject);
    }


    private void getSubject() {
        lvSubject = findViewById(R.id.lvsubject);

        listSubject.add(new Subject(1,"Môn Toán",""));
        listSubject.add(new Subject(1,"Môn Lí",""));
        listSubject.add(new Subject(1,"Môn Hóa",""));
        listSubject.add(new Subject(1,"Môn Sinh",""));
        listSubject.add(new Subject(1,"Môn Sử",""));
        listSubject.add(new Subject(1,"Môn Địa",""));

        adapter = new SubjectAdapter(SubjectActivity.this,listSubject,R.layout.item_subject);
        lvSubject.setAdapter(adapter);
    }
    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog2);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        EditText subjectname = dialog.findViewById(R.id.dia_subjectname);
        Button btnAdd = dialog.findViewById(R.id.dialogaddS);
        Button btnCancel = dialog.findViewById(R.id.dialogcancelS);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSubject.add(new Subject(listSubject.size()+1,subjectname.getText().toString(),""));
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void openDialogEdit(int gravity,Subject subject){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog2);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        }
        else{
            dialog.setCancelable(false);
        }

        EditText subjectname = dialog.findViewById(R.id.dia_subjectname);
        Button btnAdd = dialog.findViewById(R.id.dialogaddS);
        Button btnCancel = dialog.findViewById(R.id.dialogcancelS);

        subjectname.setText(subject.getTittle());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject.setTittle(subjectname.getText().toString().trim());
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}