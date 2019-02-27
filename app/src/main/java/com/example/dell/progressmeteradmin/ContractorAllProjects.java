package com.example.dell.progressmeteradmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

    private RecyclerView rvContractorAllProjects;
    private ContractorProjectAdapter contractorProjectAdapter;
    private LinearLayoutManager llmContractorProjects;
    private DatabaseReference mDbRef;
    private ValueEventListener ContractorProjectListener;
    List<Project> projectList = new ArrayList<>();

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


    }

    public void onStart() {

        super.onStart();
        projectList.clear();
        ContractorProjectListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Project project = dataSnapshot.getValue(Project.class);
                projectList.add(project);

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
        contractorProjectAdapter = new ContractorProjectAdapter(projectList);
        llmContractorProjects= new LinearLayoutManager(ContractorAllProjects.this);
        rvContractorAllProjects.setAdapter(contractorProjectAdapter);
        rvContractorAllProjects.setLayoutManager(llmContractorProjects);
        rvContractorAllProjects.scrollToPosition(0);

    }
    public void onStop() {

        super.onStop();
        if(ContractorProjectListener!=null)
        {
            mDbRef.removeEventListener(ContractorProjectListener);
        }
    }

}
