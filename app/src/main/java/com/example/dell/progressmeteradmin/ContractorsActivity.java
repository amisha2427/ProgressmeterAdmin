package com.example.dell.progressmeteradmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.dell.progressmeteradmin.Adapters.ContractorListAdapter;
import com.example.dell.progressmeteradmin.Modal.Contractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContractorsActivity extends AppCompatActivity {

    private RecyclerView rvContractor;
    private LinearLayoutManager llmContractor;
    private DatabaseReference mDbReference;
    private ValueEventListener contractorListener;
    private ContractorListAdapter contractorListAdapter;
    List<Contractor> contractorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);

        rvContractor = findViewById(R.id.rv_contractor);
        mDbReference = FirebaseDatabase.getInstance().getReference().child("Contractors");



    }

    public void onStart() {

        super.onStart();
        contractorList.clear();
        ValueEventListener contractorListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Contractor contractor = dataSnapshot1.getValue(Contractor.class);
                    contractorList.add(contractor);
                }
                contractorListAdapter = new ContractorListAdapter(contractorList);
                llmContractor = new LinearLayoutManager(ContractorsActivity.this);

                rvContractor.setAdapter(contractorListAdapter);
                rvContractor.setLayoutManager(llmContractor);
                rvContractor.scrollToPosition(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                String error= databaseError.toException().toString();
                Toast.makeText(ContractorsActivity.this,"Error:- "+error,Toast.LENGTH_LONG).show();
            }

        };
        mDbReference.addValueEventListener(contractorListener);

    }
    public void onStop() {

        super.onStop();
        if(contractorListener!=null)
        {
            mDbReference.removeEventListener(contractorListener);
        }
    }
}
