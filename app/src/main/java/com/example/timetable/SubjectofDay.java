package com.example.timetable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Day.Day;
import Subject.Subject;
import Subject.SubjectItemAdapter;
import SubjectofDay.SubjectDAapter;
import SubjectofDay.SubjectD;

public class SubjectofDay extends AppCompatActivity {
    ListView lvSubject;
    SubjectDAapter adapter;
    ArrayList<SubjectD> listofDay = new ArrayList<>();
    TextView  thu;
    FloatingActionButton btnadd;
    SubjectItemAdapter subjectItemAdapter;
    String nameSubject ;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectof_day);

        getSubjectofDay();
        Anhxa();
        Day day = (Day) getIntent().getSerializableExtra("object");
        thu.setText(day.getTittle());
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });

    }
    private void openDialog(int gravity){
       final Dialog dialog = new Dialog(this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.layout_dialog);
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

        Spinner spnSubject = dialog.findViewById(R.id.spnmon);
       subjectItemAdapter = new SubjectItemAdapter(this,R.layout.item_subject_selected,getSubject());
        spnSubject.setAdapter(subjectItemAdapter);
        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 nameSubject = subjectItemAdapter.getItem(i).getTittle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        EditText editstartTime = dialog.findViewById(R.id.dia_startitme);
        EditText editendTime = dialog.findViewById(R.id.dia_endtime);
        Button btnAdd = dialog.findViewById(R.id.dialogadd);
        Button btnCancel = dialog.findViewById(R.id.dialogcancel);


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listofDay.add(new SubjectD(1,2,3, nameSubject,"Hình ảnh",
                            editstartTime.getText().toString(),editendTime.getText().toString()));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
    dialog.show();
    }
    private void Back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }
    private List<Subject> getSubject() {
        List<Subject> list = new ArrayList<>();
        list.add(new Subject(1,"Môn toán",""));
        list.add(new Subject(1,"Môn lí",""));
        list.add(new Subject(1,"Môn hóa",""));
    return  list;
    }

    private void Anhxa() {
        thu =findViewById(R.id.thu);
        btnadd = findViewById(R.id.btnadd);
        getSupportActionBar().hide();
        back = findViewById(R.id.back1);

    }

    public void Delete(final  int position){
        listofDay.remove(position);
        adapter.notifyDataSetChanged();
    }
    public void Update(final  int position){
        final  SubjectD subject = listofDay.get(position);
        openDialogEdit(Gravity.CENTER,subject);
    }

    private void openDialogEdit(int gravity,SubjectD subject){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);
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


        Button btnAdd = dialog.findViewById(R.id.dialogadd);
        Button btnCancel = dialog.findViewById(R.id.dialogcancel);
        EditText editStart = dialog.findViewById(R.id.dia_startitme);
        EditText editEnd = dialog.findViewById(R.id.dia_endtime);
        Spinner spnSubject = dialog.findViewById(R.id.spnmon);

        editEnd.setText(subject.getEndTime());
        editStart.setText(subject.getStartTime());


        subjectItemAdapter = new SubjectItemAdapter(this,R.layout.item_subject_selected,getSubject());
        spnSubject.setAdapter(subjectItemAdapter);
        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nameSubject = subjectItemAdapter.getItem(i).getTittle();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject.setTittle(nameSubject);
                subject.setStartTime(editStart.getText().toString());
                subject.setEndTime(editEnd.getText().toString());

                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getSubjectofDay() {
        lvSubject = findViewById(R.id.lvsubjectofDay);

        listofDay.add(new SubjectD(1,2,3,"Môn Toán","Hình ảnh","09:00 AM","10:00 AM"));
        listofDay.add(new SubjectD(1,2,3,"Môn Toán","Hình ảnh","09:00 AM","10:00 AM"));
        listofDay.add(new SubjectD(1,2,3,"Môn Toán","Hình ảnh","09:00 AM","10:00 AM"));
        listofDay.add(new SubjectD(1,2,3,"Môn Toán","Hình ảnh","09:00 AM","10:00 AM"));



        adapter = new SubjectDAapter(SubjectofDay.this,listofDay,R.layout.item_subject_ofday);
        lvSubject.setAdapter(adapter);
    }



}