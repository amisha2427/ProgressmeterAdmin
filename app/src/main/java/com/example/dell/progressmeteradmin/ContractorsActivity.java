package com.example.dell.progressmeteradmin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dell.progressmeteradmin.Adapters.ContractorListAdapter;
import com.example.dell.progressmeteradmin.Modal.Contractor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContractorsActivity extends AppCompatActivity {

    //views
    private RecyclerView rvContractor;

    //adapters
    private LinearLayoutManager llmContractor;
    private ContractorListAdapter contractorListAdapter;

    //firebase
    private DatabaseReference mDbReference;

    //lists
    List<Contractor> contractorList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractors);
        //initalize views
        intialize();

        //fetch the list of contractors and put it into the adapter
        fetchContractors();


    }

    private void intialize() {
        rvContractor = findViewById(R.id.rv_contractor);
        mDbReference = FirebaseDatabase.getInstance().getReference().child("Contractors");
    }

    private void fetchContractors() {
        contractorList.clear();
        mDbReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Contractor contractor = dataSnapshot1.getValue(Contractor.class);
                    contractorList.add(0, contractor);
                }
                contractorListAdapter = new ContractorListAdapter(contractorList);
                llmContractor = new LinearLayoutManager(ContractorsActivity.this);
                rvContractor.setAdapter(contractorListAdapter);
                rvContractor.setLayoutManager(llmContractor);
                rvContractor.scrollToPosition(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String error = databaseError.toException().toString();
                Toast.makeText(ContractorsActivity.this, "Error:- " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

}
