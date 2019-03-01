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
    //firebase related
    private DatabaseReference mDbRef;
    //lists
    List<Project> projectList = new ArrayList<>();
    List<String> projectIds;
    //variables
    private Contractor contractor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_all_projects);

        //Initialization
        init();
        // fetching contractor details from the intent of previous class
        fetchContractorObjectFromGson();
        //fetch projects from firebase
        fetchProjects();


    }

    private void init() {
        rvContractorAllProjects = findViewById(R.id.rv_contractorAllProjects);
        mDbRef=FirebaseDatabase.getInstance().getReference().child("Projects");
    }

    private void fetchContractorObjectFromGson() {
        Bundle extras = getIntent().getExtras();
        String details="";
        if(extras!=null)
            details=extras.getString("ContractorDetails");
        Gson gson = new Gson();

        contractor =gson.fromJson(details,Contractor.class);
        projectIds=contractor.getProjectIds();
    }

    private void fetchProjects() {

        projectList.clear();

        for(int i=1;i<projectIds.size();i++)
        {

            final int finalI = i;
            mDbRef.child(projectIds.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Project project = dataSnapshot.getValue(Project.class);
                    projectList.add(0,project);
                    if(finalI ==projectIds.size()-1)
                    {
                        contractorProjectAdapter = new ContractorProjectAdapter(projectList);
                        llmContractorProjects= new LinearLayoutManager(ContractorAllProjects.this);
                        rvContractorAllProjects.setAdapter(contractorProjectAdapter);
                        rvContractorAllProjects.setLayoutManager(llmContractorProjects);
                        rvContractorAllProjects.scrollToPosition(0);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    String error= databaseError.toException().toString();
                    Toast.makeText(ContractorAllProjects.this,"Error:- "+error,Toast.LENGTH_LONG).show();
                }
            });
        }

    }


}
