package com.example.timetable;

import static com.example.timetable.MainActivity.http;
import static com.example.timetable.MainActivity.islogin;
import static com.example.timetable.MainActivity.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Subject.Subject;
import Subject.SubjectAdapter;

public class SubjectActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ListView lvSubject;
    ArrayList<Subject> listSubject = new ArrayList<>();
    SubjectAdapter adapter;
    FloatingActionButton btnadd;
    SearchView searchView;
    int idfinal;
    private static  final String BASE_URL_Subject = http+"TimeTable/subject.php";
    private static  final String BASE_URL_Delete_Subject = http+"TimeTable/DeleteSubject.php";
    private static  final String BASE_URL_Add_Subject = http+"TimeTable/AddSubject.php";
    private static  final String BASE_URL_Update_Subject= http+"TimeTable/UpdateSubject.php";
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
                Update(i);
            }
        });

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    public void Delete(final  int position){
        DeteleSubject(""+listSubject.get(position).getId());
    }

    public void DeteleSubject(String idSubject) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Delete_Subject, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                        if(response.trim().equals("ok")){
                            Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            getSubject();
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
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
    }
    public void AddSubject(int id,String Tittle,String Image ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Add_Subject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("ok")){
                    Toast.makeText(getApplicationContext(), "Thêm  thành công", Toast.LENGTH_SHORT).show();
                    getSubject();
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
                data.put("idSubject", String.valueOf(id));
                data.put("Tittle", String.valueOf(Tittle));
                data.put("Image", String.valueOf(Image));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void UpdateSubject(int id,String Tittle,String Image ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL_Update_Subject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("ok")){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    getSubject();
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
                data.put("idSubject", String.valueOf(id));
                data.put("Tittle", String.valueOf(Tittle));
                data.put("Image", String.valueOf(Image));
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void Update(final  int position){
        final  Subject subject = listSubject.get(position);
        //UpdateSubject(int id,String Tittle,String Image );
        openDialogEdit(Gravity.CENTER,subject,position);
    }

    private  void getSubject(){
        lvSubject = findViewById(R.id.lvsubject);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL_Subject, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listSubject.clear();

                        for(int i = 0 ; i <=response.length();i++){
                            try{
                                JSONObject object = response.getJSONObject(i);

                                listSubject.add(new Subject(object.getInt("idMon"),object.getString("Tittle"),http+
                                        object.getString("Image")));
                                idfinal = object.getInt("idMon");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText(getApplicationContext(), idfinal, Toast.LENGTH_SHORT).show();

                        adapter = new SubjectAdapter(SubjectActivity.this,listSubject,R.layout.item_subject);
                        lvSubject.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag","onErrorRespone: " +error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);

    }


    private void openDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog1);
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

        EditText subjectname = dialog.findViewById(R.id.dia_subjectname1);
        Button btnAdd1 = dialog.findViewById(R.id.dialogaddS1);
        Button btnCancel1 = dialog.findViewById(R.id.dialogcancelS1);


        btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listSubject.add(new Subject(listSubject.size()+1,subjectname.getText().toString(),""));

                AddSubject(idfinal + 1,subjectname.getText().toString(),"TimeTable/Image/subject.png");
                getSubject();
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    dialog.show();
    }
    private void openDialogEdit(int gravity,Subject subject,int i){
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
        Button btnAddS = dialog.findViewById(R.id.dialogaddS);
        Button btnCancelS = dialog.findViewById(R.id.dialogcancelS);
        Button btnDeleteS = dialog.findViewById(R.id.deleteS);

        subjectname.setText(subject.getTittle());
        btnCancelS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnAddS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject.setTittle(subjectname.getText().toString().trim());
                UpdateSubject(subject.getId(),subjectname.getText().toString(),subject.getHinhanh());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnDeleteS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete(i);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}