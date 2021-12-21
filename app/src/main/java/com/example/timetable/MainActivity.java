package com.example.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
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
    public static User user ;
    public static boolean islogin = false;
    public static String http  = "http://192.168.1.102/"; //Thay đổi  Địa chỉ ip của máy code mới chạy dc
    private static  final String BASE_URL = http+"TimeTable/day.php";
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


                Intent intent = new Intent(getApplicationContext(), SubjectofDay.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object",listDay.get(i));
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
    private  void getDay(){
        lvDay = findViewById(R.id.lvday);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, BASE_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i <=response.length();i++){
                            try{
                                JSONObject object = response.getJSONObject(i);
                                Day day = new Day();
                                day.setId(object.getInt("idDay"));
                                day.setTittle(object.getString("Tittle"));
                                day.setHinhanh(object.getString("Image"));
                                listDay.add(day);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                       adapter = new DayAdapter(MainActivity.this,listDay,R.layout.item_day);
                        lvDay.setAdapter(adapter);
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

}