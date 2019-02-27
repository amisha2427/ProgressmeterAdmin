package com.example.dell.progressmeteradmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.progressmeteradmin.Adapters.ContractorProjectAdapter;
import com.example.dell.progressmeteradmin.Modal.Contractor;
import com.example.dell.progressmeteradmin.Modal.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ContractorAllProjects extends AppCompatActivity
{

    //views
    private RecyclerView rvContractorAllProjects;

    //adapters
    private ContractorProjectAdapter contractorProjectAdapter;
    private LinearLayoutManager llmContractorProjects;
    private ValueEventListener ContractorProjectListener;

    //firebase related
    private DatabaseReference mDbRef;
    List<Project> projectList = new ArrayList<>();

    //lists
    List<String> projectIds;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_all_projects);

        rvContractorAllProjects = findViewById(R.id.rv_contractorAllProjects);
        mDbRef=FirebaseDatabase.getInstance().getReference().child("Projects");

        // fetching contractor details
        Bundle extras = getIntent().getExtras();
        String details="";
        if(extras!=null)
            details=extras.getString("ContractorDetails");
        Gson gson = new Gson();
        Contractor contractor=gson.fromJson(details,Contractor.class);
        projectIds=contractor.getProjectIds();
        contractorProjectAdapter = new ContractorProjectAdapter(projectList);
        llmContractorProjects= new LinearLayoutManager(ContractorAllProjects.this);
        rvContractorAllProjects.setAdapter(contractorProjectAdapter);
        rvContractorAllProjects.setLayoutManager(llmContractorProjects);
        rvContractorAllProjects.scrollToPosition(0);

    }

    @Override
    public void onStart() {

        super.onStart();
        projectList.clear();
        Log.d("hello", "onStart: ");
        ContractorProjectListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Project project = dataSnapshot.getValue(Project.class);
                projectList.add(project);
                contractorProjectAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                String error= databaseError.toException().toString();
                Toast.makeText(ContractorAllProjects.this,"Error:- "+error,Toast.LENGTH_LONG).show();
            }
        };
        for(int i=1;i<projectIds.size();i++)
        {
            mDbRef.child(projectIds.get(i)).addValueEventListener(ContractorProjectListener);
        }


    }
    @Override
    public void onStop() {

        super.onStop();
        if(ContractorProjectListener!=null)
        {
            mDbRef.removeEventListener(ContractorProjectListener);
        }
    }

}
