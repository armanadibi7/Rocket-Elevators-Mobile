package com.example.rocketelevatorsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoggedIn extends AppCompatActivity {


    private BottomNavigationView bottomNaviationView;


    TextView elevatorInfo;
    JSONArray JARoot;
    Integer selectedElevatorId;
    OkHttpClient client = new OkHttpClient();
    RequestQueue requestQueue;
    private Button logout;
    TextView welcome;
    Bundle bundle = new Bundle();;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
    ArrayList<JSONObject> elevatorObject = new ArrayList<JSONObject>();
    ArrayList<String> appendArray = new ArrayList<String>();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        setContentView(R.layout.activity_logged_in);
        String email = getIntent().getStringExtra("email");
         welcome = findViewById(R.id.textView);
        welcome.setText("Welcome:\n " + email +
                "\n \n Click on the elevator button below to get Started");
        bundle.putString("user", email);
        System.out.println(bundle);
        bottomNaviationView = findViewById(R.id.bottom);


        bottomNaviationView.setOnNavigationItemSelectedListener(bottomNavMethod);
                logout = findViewById(R.id.btnLogout);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    welcome.setText("");
                    Fragment fragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_inactive:
                            fragment = new ElevatorFragment();
                            fragment.setArguments(bundle);
                            break;


                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();


                    return true;
                }
            };
}


