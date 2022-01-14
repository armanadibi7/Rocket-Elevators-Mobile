package com.example.rocketelevatorsapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class StatusFragment extends Fragment {
    private Button btnChange;
    private TextView txtId;
    private TextView txtStatus;
    View view;
    JSONArray JARoot;
    OkHttpClient client = new OkHttpClient();
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
    ArrayList<JSONObject> elevatorObject = new ArrayList<JSONObject>();
    ArrayList<String> appendArray = new ArrayList<String>();
    String id;
    String status;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_status, container, false);

        btnChange = view.findViewById(R.id.btnStatus);
        txtId = view.findViewById(R.id.elevatorId);
        txtStatus = view.findViewById(R.id.statusTxt);
        if(getArguments() == null){

            txtId.setText("No Elevator has been selected");
            btnChange.setEnabled(false);

        }else{
            btnChange.setEnabled(true);
            id = getArguments().getString("id");
            txtId.setText("Elevator ID : " + id);
            status = getArguments().getString("status");
            txtStatus.setText(status);

            if(status != "Active"){
                txtStatus.setTextColor(this.getResources().getColor(R.color.red));
            }
        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 changeStatus();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }





        public void changeStatus(){
        txtStatus.setTextColor(this.getResources().getColor(R.color.green));
        String url = "https://rocketelevatorsrestapi20220113130929.azurewebsites.net/elevator" ;


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();

        if(txtStatus.getText() == "Active"){
            try {
                postdata.put("id", id);
                postdata.put("status", "Inactive");
            } catch(JSONException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            try {
                postdata.put("id",id);
                postdata.put("status", "Active");
            } catch(JSONException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }






                try {
                    RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json")
                            .build();
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            String mMessage = e.getMessage().toString();
                            Log.w("failure Response", mMessage);
                            //call.cancel();
                            countDownLatch.countDown();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            countDownLatch.countDown();
                            String mMessage = response.body().string();
                            Log.e("toot", mMessage);
                            updatetext();
                        }
                    });
                    countDownLatch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }






        appendArray.clear();
//        getElevatorList();
//        populateArrayView();
    }

    public void updatetext(){

        if(status.equals("Inactive") || status.equals("Intervention")  ){
            status = "Active";
            txtStatus.setText("Active");
            txtStatus.setTextColor(getActivity().getResources().getColor(R.color.green));
        }else{
            status = "Inactive";
            txtStatus.setText("Inactive");
            txtStatus.setTextColor(getActivity().getResources().getColor(R.color.red));

        }
        System.out.println("Done");
    }
}