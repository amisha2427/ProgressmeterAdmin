package com.example.dell.progressmeteradmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.progressmeteradmin.ContractorAllProjects;
import com.example.dell.progressmeteradmin.Modal.Contractor;
import com.example.dell.progressmeteradmin.R;
import com.google.gson.Gson;

import java.util.List;

public class ContractorListAdapter extends RecyclerView.Adapter<ContractorListAdapter.ContractorViewHolder> {

    List<Contractor> contractorList;
    LayoutInflater layoutInflater;
    Context mContext;

    public ContractorListAdapter(List<Contractor> contractorList) {
        this.contractorList = contractorList;
    }

    @NonNull
    @Override
    public ContractorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = layoutInflater.from(parent.getContext()).inflate(R.layout.contractors_list, parent, false);
        return new ContractorViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContractorViewHolder holder, final int position) {
        final String name = contractorList.get(position).getName();
        String uid = contractorList.get(position).getUid();

        holder.tvName.setText(name);
        holder.tvId.setText(uid);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contractor contractor = contractorList.get(position);

                Gson gson = new Gson();
                String jsonObj = gson.toJson(contractor);
                Intent allProjectsIntent = new Intent(holder.cardView.getContext(), ContractorAllProjects.class);
                allProjectsIntent.putExtra("ContractorDetails", jsonObj);
                holder.cardView.getContext().startActivity(allProjectsIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return contractorList.size();
    }

    public class ContractorViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvId;
        private CardView cardView;

        public ContractorViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.contractor_name);
            tvId = itemView.findViewById(R.id.contractor_id);
            cardView = itemView.findViewById(R.id.contractor_cardView);

        }
    }
}
