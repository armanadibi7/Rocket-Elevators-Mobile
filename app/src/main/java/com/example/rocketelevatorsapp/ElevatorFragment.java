package com.example.rocketelevatorsapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class ElevatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView loggedInUser;
    private ListView elevatorList;
    private Button btnStatus;
    View view;
    private EditText txtId;
    private EditText txtStatus;
    private TextView loggedUser;
    TextView elevatorInfo;
    JSONArray JARoot;
    Integer selectedElevatorId;
    OkHttpClient client = new OkHttpClient();
    RequestQueue requestQueue;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
    ArrayList<JSONObject> elevatorObject = new ArrayList<JSONObject>();
    ArrayList<String> appendArray = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_elevator,container,false);
//        elevatorInfo = view.findViewById(R.id.elevatorInfo);
        elevatorList = view.findViewById(R.id.elevatorListView);
//        btnStatus = view.findViewById(R.id.btnStatus);
//        txtId = view.findViewById(R.id.changeId);
//        txtStatus = view.findViewById(R.id.changeStatusTxt);
//        String user = getArguments().getString("user");
//        System.out.println(user);
//        loggedInUser =view.findViewById(R.id.loggedUser);
//        loggedInUser.setText(user);
        getElevatorList();
        elevatorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               getElevatorInfo(i);

            }
        });



        // Inflate the layout for this fragment

        return view;
    }








    public void getElevatorList(){

        String url = "https://rocketelevatorsrestapi20220113130929.azurewebsites.net/elevator/inactive" ;

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
                    try {
                        JARoot = new JSONArray(myResponse);
                        Log.d("My App", JARoot.toString());
                        for (int i = 0; i < JARoot.length(); i++) {
                            JSONObject JORoot = JARoot.getJSONObject(i);
//


                            appendArray.add("Elevator : " + JORoot.getString("id"));
                            elevatorObject.add(JORoot);



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("My App", "Could not parse malformed k JSON: \"" + myResponse + "\"");
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateArrayView();
                            arrayThis(elevatorObject);


                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> string11 = new ArrayList<>();
        string11.add("Loading ...");

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,string11);
        elevatorList.setAdapter(arrayAdapter);
    }

    public void populateArrayView(){

       elevatorList.setAdapter(null);

        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,appendArray);
        elevatorList.setAdapter(arrayAdapter);
    }


    public void arrayThis(ArrayList<JSONObject> elevatorObject ){
//            try {
////            System.out.println(JARoot.getJSONObject(0).toString(4));
//            } catch (JSONException e) {
//            e.printStackTrace();
//            }

//        Set<String> keys = arrayList.keySet();  //get all keys
//        for(String i: keys)
//        {
//            System.out.println(arrayList.get(i));
//        }
//

//        for(int i=0;i<list.size();i++){
//
//            String[] myString= new String[2];
//            myString=list.get(i);
//            for(int j=0;j<myString.length;j++){
//                System.out.print(myString[j]);
//            }
//            System.out.print("\n");
//
//        }
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
//        elevatorList.setAdapter(arrayAdapter);
    }



    public void getElevatorInfo(int i){

        try {
            JSONObject JORoot = JARoot.getJSONObject(i);
           selectedElevatorId = Integer.parseInt(JORoot.getString("id"));
//            elevatorInfo.setText("Elevator ID: " + JORoot.getString("id")
//                    + "\nStatus : " + JORoot.getString("status")
//                    + "\nElevator Type : " + JORoot.getString("elevatorType")
//                    + "\nModel : " + JORoot.getString("model")
//                    + "\nSerial Number : " + JORoot.getString("serialNumber")
//
//            );


            Bundle bundle = new Bundle();
            bundle.putString("id",JORoot.getString("id"));
            bundle.putString("status",JORoot.getString("status"));

            Fragment fragment = new StatusFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            System.out.println(JARoot.getJSONObject(i).toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
