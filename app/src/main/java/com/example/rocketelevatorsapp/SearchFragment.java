package com.example.rocketelevatorsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class SearchFragment extends Fragment {


    Button btn;
    TextView txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        btn =(Button) view.findViewById(R.id.btnClick);
        txt =(TextView) view.findViewById(R.id.txtSearch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt.setText("yoyo");
            }
        });

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}