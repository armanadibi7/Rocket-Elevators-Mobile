package com.example.rocketelevatorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;


public class MainActivity extends AppCompatActivity {
    private EditText  etEmail;
    private TextView  txtMessage;
    private Button btLogin;


    OkHttpClient client = new OkHttpClient();
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String baseUrl = "https://api.github.com/users/";  // This is the API base URL (GitHub API)
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        btLogin = findViewById(R.id.btnLogin);
        txtMessage =findViewById(R.id.txtMessage);
        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = etEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter an Email to continue!", Toast.LENGTH_SHORT).show();
                }else{



                      openActivity();

                }
            }
        });
    }
    public void openActivity(){
        String email = etEmail.getText().toString();
        String url = "https://rocketelevatorsrestapi20220113130929.azurewebsites.net/androidapp/verifyuser/" + email;
        requestQueue = Volley.newRequestQueue(this);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(myResponse.contains("Success")) {
                                succes(email);

                            }else{
                                txtMessage.setText("Wrong Email, Try Again");
                            }

                        }
                    });
                }
            }
        });

    }
    public void succes(String email){
         Intent intent = new Intent(this, LoggedIn.class);
        intent.putExtra("email",email);
           startActivity(intent);
    }

}