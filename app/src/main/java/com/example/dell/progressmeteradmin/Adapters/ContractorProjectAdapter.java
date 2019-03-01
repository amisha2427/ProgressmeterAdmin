package com.example.dell.progressmeteradmin.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.progressmeteradmin.Modal.Project;
import com.example.dell.progressmeteradmin.R;

import java.util.List;

public class ContractorProjectAdapter extends RecyclerView.Adapter<ContractorProjectAdapter.ContractorProjectViewHolder> {

    List<Project> contractorProjectList;
    public ContractorProjectAdapter(List<Project> Projects) {
        contractorProjectList = Projects;
    }

    @NonNull
    @Override
    public ContractorProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contractors_list, parent, false);

        return new ContractorProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractorProjectViewHolder holder, int position) {
        String title = contractorProjectList.get(position).getTitle();
        String duration = contractorProjectList.get(position).getDuration();
        holder.tvName.setText(title);
        holder.tvId.setText(duration);

    }

    @Override
    public int getItemCount() {
        return contractorProjectList.size();
    }

    public class ContractorProjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvId;

        public ContractorProjectViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.contractor_name);
            tvId = itemView.findViewById(R.id.contractor_id);
        }
    }
}
