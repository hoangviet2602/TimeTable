package com.example.timetable;

import static com.example.timetable.MainActivity.http;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    int idSuject;
    ImageButton back;
    int t1Hour,t1Minutes;
    int t1Hour2,t1Minutes2;
    List<Subject> listSubject = new ArrayList<>();
    int idDay ;
    private static  final String BASE_URL_Subject = http+"TimeTable/subject.php";
    private static  final String BASE_URL_TKB = http+"TimeTable/TKB.php";
    private static  final String BASE_URL_Add_TKB = http+"TimeTable/AddTKB.php";
    private static  final String BASE_URL_Delete_SubjectD = http+"TimeTable/DeleteSubjectofDay.php";
    private static  final String BASE_URL_Update_SubjectD = http+"TimeTable/UpdateSubjectofDay.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectof_day);
        Back();
        getSubjectofDay();

        Anhxa();
        Day day = (Day) getIntent().getSerializableExtra("object");
        thu.setText(day.getTittle());
        idDay = day.getId();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });
        lvSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Update(i);
            }
        });

    }
    private void openDialog(int gravity){
        getSubjectURL();
       final Dialog dialog = new Dialog(this);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(R.layout.layout_dialog_add);
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

        Spinner spnSubject = dialog.findViewById(R.id.spnmonA);
        subjectItemAdapter = new SubjectItemAdapter(this,R.layout.item_subject_selected,listSubject);
        spnSubject.setAdapter(subjectItemAdapter);
        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 nameSubject = subjectItemAdapter.getItem(i).getTittle();
                idSuject = subjectItemAdapter.getItem(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        Button btnAdd = dialog.findViewById(R.id.dialogaddA);
        Button btnCancel = dialog.findViewById(R.id.dialogcancelA);

        TextView time1 = dialog.findViewById(R.id.tv_time1);
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SubjectofDay.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t1Hour = i;
                        t1Minutes = i1;
                        String time = t1Hour + ":" + t1Minutes;
                        SimpleDateFormat f24Hours =  new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                            time1.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour,t1Minutes);
                timePickerDialog.show();
            }
        });
        TextView time2= dialog.findViewById(R.id.tv_time2);
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SubjectofDay.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t1Hour2 = i;
                        t1Minutes2 = i1;
                        String time = t1Hour2 + ":" + t1Minutes2;
                        SimpleDateFormat f24Hours =  new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                            time2.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour2,t1Minutes2);
                timePickerDialog.show();
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
                    if(CheckTime(time1.getText().toString(),time2.getText().toString()) == false){
                        Toast.makeText(getApplicationContext(), "Vui lòng kiếm tra lại giờ bắt đầu và giờ kết thúc", Toast.LENGTH_SHORT).show();
                    }else{
                        String chuoi1[] = time1.getText().toString().split(" ");
                        String gio1[] = chuoi1[0].split(":");
                        //listofDay.add(new SubjectD(1,2,3, nameSubject,"Hình ảnh",
                                //time1.getText().toString(),time2.getText().toString(),Integer.parseInt(gio1[0])));

                        AddTKB(idSuject,1,idDay,time1.getText().toString(),time2.getText().toString());
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();
    }
    public void AddTKB(int idSubject,int idUSer,int idDay,String StartTime,String EndTime ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Add_TKB, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("ok")){
                    Toast.makeText(getApplicationContext(), "Thêm  thành công", Toast.LENGTH_SHORT).show();
                    getSubjectofDay();
                }else{
                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idSubject", String.valueOf(idSubject));
                data.put("idUser", String.valueOf(idUSer));
                data.put("idDay", String.valueOf(idDay));
                data.put("StartTime", String.valueOf(StartTime));
                data.put("EndTime", String.valueOf(EndTime));

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void Back() {
        back = findViewById(R.id.back1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private  void getSubjectURL(){
        List<Subject> list = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL_Subject, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listSubject.clear();
                        for(int i = 0 ; i <=response.length();i++){
                            try{
                                JSONObject object = response.getJSONObject(i);

                                listSubject.add(new Subject(object.getInt("idMon"),object.getString("Tittle"),
                                        object.getString("Image")));


                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        //getSubject(int id,String tittle,String img);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText( null, "lỗi", Toast.LENGTH_SHORT).show();
                Log.d("tag","onErrorRespone: " +error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);

    }

    private void Anhxa() {
        thu =findViewById(R.id.thu);
        btnadd = findViewById(R.id.btnadd);
        getSupportActionBar().hide();
        back = findViewById(R.id.back1);

    }

    public void Delete(final  int position){
        DeteleSubjectofDay(""+listofDay.get(position).getIdSubject(),""+listofDay.get(position).getIdThu(),""+ listofDay.get(position).getIdUser());

    }
    public void DeteleSubjectofDay(String idSubject,String idDay , String idUser) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Delete_SubjectD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("ok")){
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    getSubjectofDay();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idSubject", String.valueOf(idSubject));
                data.put("idDay", String.valueOf(idDay));
                data.put("idUser", String.valueOf(idUser));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Update(final  int position){
        final  SubjectD subject = listofDay.get(position);
        openDialogEdit(Gravity.CENTER,subject,position);
    }

    public void UpdateSubjectofDay(int idSubjectOld,int idSubjectNew,int idDay,int idUser,String Starttime,String EndTime ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Update_SubjectD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("ok")){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    getSubjectofDay();
                }else{
                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("idSubjectOld", String.valueOf(idSubjectOld));
                data.put("idSubjectNew", String.valueOf(idSubjectNew));
                data.put("idDay", String.valueOf(idDay));
                data.put("idUser", String.valueOf(idUser));
                data.put("Starttime", String.valueOf(Starttime));
                data.put("EndTime", String.valueOf(EndTime));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openDialogEdit(int gravity,SubjectD subject,int i){
        getSubjectURL();
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

        Button btnDelete = dialog.findViewById(R.id.delete);
        Button btnAdd = dialog.findViewById(R.id.dialogadd);
        Button btnCancel = dialog.findViewById(R.id.dialogcancel);

        TextView time1 = dialog.findViewById(R.id.time1E);
        TextView time2 = dialog.findViewById(R.id.Time2E);
        Spinner spnSubject = dialog.findViewById(R.id.spnmon);

        time1.setText(subject.getStartTime());
        time2.setText(subject.getEndTime());

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SubjectofDay.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t1Hour = i;
                        t1Minutes = i1;
                        String time = t1Hour + ":" + t1Minutes;
                        SimpleDateFormat f24Hours =  new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                            time1.setText(f12Hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour,t1Minutes);
                timePickerDialog.show();
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SubjectofDay.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        t1Hour2 = i;
                        t1Minutes2 = i1;
                            String time = t1Hour2 + ":" + t1Minutes2;
                            SimpleDateFormat f24Hours =  new SimpleDateFormat("HH:mm");
                            try {
                                Date date = f24Hours.parse(time);
                                SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                                time2.setText(f12Hours.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                    }
                },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(t1Hour2,t1Minutes2);
                timePickerDialog.show();
            }
        });

        subjectItemAdapter = new SubjectItemAdapter(this,R.layout.item_subject_selected,listSubject);
        spnSubject.setAdapter(subjectItemAdapter);
        spnSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nameSubject = subjectItemAdapter.getItem(i).getTittle();
                idSuject = subjectItemAdapter.getItem(i).getId();

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

                subject.setStartTime(time1.getText().toString());
                subject.setEndTime(time2.getText().toString());

                if(CheckTime(time1.getText().toString(),time2.getText().toString()) == false){
                    Toast.makeText(getApplicationContext(), "Vui lòng kiếm tra lại giờ bắt đầu và giờ kết thúc", Toast.LENGTH_SHORT).show();
                }else{
                    UpdateSubjectofDay(subject.getIdSubject(),idSuject, subject.getIdThu(),
                            subject.getIdUser(), time1.getText().toString(),time2.getText().toString());
                    dialog.dismiss();
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete(i);
                dialog.dismiss();
            }
        });        dialog.show();
    }

    public void getSubjectofDay() {
        lvSubject = findViewById(R.id.lvsubjectofDay);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_TKB, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listofDay.clear();
                    try{
                        JSONObject jsonObject  = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                        if(success.equals("1")){

                            for(int i = 0 ; i < jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                SubjectD subjectD = new SubjectD();
                                subjectD.setTittle(object.getString("Tittle").trim());
                                subjectD.setStartTime(object.getString("Starttime").trim());
                                subjectD.setEndTime(object.getString("EndTime").trim());
                                subjectD.setHinhanh(object.getString("Image").trim());
                                subjectD.setIdSubject(object.getInt("idMon"));
                                subjectD.setIdThu(object.getInt("idDay"));
                                subjectD.setIdUser(object.getInt("idUser"));


                                listofDay.add(subjectD);
                            }
                            adapter = new SubjectDAapter(SubjectofDay.this,listofDay,R.layout.item_subject_ofday);
                            lvSubject.setAdapter(adapter);
                        }

                    }catch(JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Error: " + e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error: " + error.toString(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("idDay", String.valueOf(idDay));
                    data.put("idUser", String.valueOf(1));
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);



    }
    private boolean CheckTime(String time1,String time2 ){
        String chuoi1[] = time1.split(" ");
        String chuoi2[] = time2.split(" ");
        // chuoi1[0] --> 09:00 chuoi1[1] -> AM
        //gio[0] --> 09  gio[1] -> 00
        String gio1[] = chuoi1[0].split(":");
        String gio2[] = chuoi2[0].split(":");

        if(chuoi1[1].equals(chuoi2[1]) ){
            if(Integer.parseInt(gio1[0]) >= Integer.parseInt(gio2[0]) ){
                return false;
            }else if(Integer.parseInt(gio1[0]) == Integer.parseInt(gio2[0])){
                if(Integer.parseInt(gio1[1]) >= Integer.parseInt(gio2[1]) ){
                    return false;
                }
            }
        }else{
            if(chuoi1[1].equals("PM") && chuoi2[1].equals("AM")){
                return  false;
            }
        }
    return true;
    }

}