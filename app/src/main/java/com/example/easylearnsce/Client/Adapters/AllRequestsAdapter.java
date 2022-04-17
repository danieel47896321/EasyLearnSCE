package com.example.easylearnsce.Client.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Client.Class.Request;
import com.example.easylearnsce.R;

import java.util.ArrayList;
import java.util.List;

public class AllRequestsAdapter extends RecyclerView.Adapter<AllRequestsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Request> requests;
    private String type;
    public AllRequestsAdapter(Context context, ArrayList<Request> requests, String type ){
        this.context = context;
        this.requests = requests;
        this.type = type;
    }
    @NonNull
    @Override
    public AllRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_request_view, parent, false);
        return new AllRequestsAdapter.ViewHolder(view);
    }
    private void DetailsAndAnswer(@NonNull AllRequestsAdapter.ViewHolder holder, Request request){
        if(request.getRequest().equals("Other") || request.getRequest().equals("אחר") || request.getRequest().equals("Report Problem") || request.getRequest().equals("דיווח על תקלה")) {
            holder.Details.setText(context.getResources().getString(R.string.Details) + ": " + request.getDetails());
            holder.Reason.setText(context.getResources().getString(R.string.Answer) + ": " + request.getAnswer());
        }
        else {
            holder.Details.setVisibility(View.GONE);
            holder.Reason.setVisibility(View.GONE);
        }
    }
    private void StatusColor(@NonNull AllRequestsAdapter.ViewHolder holder, Request request){
        if(request.getStatus().equals("Approved") || request.getStatus().equals("הבקשה אושרה") || request.getStatus().equals("Fixed") || request.getStatus().equals("תוקן"))
            holder.Status.setTextColor(context.getResources().getColor(R.color.green));
        else if(request.getStatus().equals("Denied") || request.getStatus().equals("הבקשה נדחתה") || request.getStatus().equals("Not Fixed") || request.getStatus().equals("לא תוקן"))
            holder.Status.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.Status.setTextColor(context.getResources().getColor(R.color.white));
        holder.Status.setText(context.getResources().getString(R.string.Status) + ": " + getStatus(request.getStatus()));
    }
    @Override
    public void onBindViewHolder(@NonNull AllRequestsAdapter.ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.Request.setText(context.getResources().getString(R.string.Request) +": " + getRequest(request.getRequest()));
        holder.FullName.setText(context.getResources().getString(R.string.FullName) +": " + request.getFirstName() + " " + request.getLastName());
        holder.Email.setText(context.getResources().getString(R.string.Email) +": " + request.getEmail());
        holder.UserType.setText(context.getResources().getString(R.string.UserType) +": " + getUserType(request.getType()));
        DetailsAndAnswer(holder, request);
        StatusColor(holder, request);
    }
    public String getRequest(String request){
        if(request.equals("Get Teacher Permission") || request.equals("קבלת הרשאות מרצה"))
            return context.getResources().getString(R.string.TeacherPermission);
        else if(request.equals("Report Problem") || request.equals("דיווח על תקלה"))
            return context.getResources().getString(R.string.ReportProblem);
        else
            return context.getResources().getString(R.string.Other);
    }
    public String getUserType(String type){
        if(type.equals("Teacher") || type.equals("מרצה"))
            return context.getResources().getString(R.string.Teacher);
        else
            return context.getResources().getString(R.string.Student);
    }
    public String getStatus(String status){
        if(status.equals("Approved") || status.equals("הבקשה אושרה"))
            return context.getResources().getString(R.string.Approved);
        else if(status.equals("Fixed") || status.equals("תוקן"))
            return context.getResources().getString(R.string.Fixed);
        else if(status.equals("Denied") || status.equals("הבקשה נדחתה"))
            return context.getResources().getString(R.string.Denied);
        else if(status.equals("Not Fixed") || status.equals("לא תוקן"))
            return context.getResources().getString(R.string.NotFixed);
        else if(status.equals("Pending Approval") || status.equals("ממתין לתשובה"))
            return context.getResources().getString(R.string.PendingApproval);
        else
            return context.getResources().getString(R.string.NotFixed);
    }
    @Override
    public int getItemCount() { return requests.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Request;
        public TextView FullName;
        public TextView Email;
        public TextView UserType;
        public TextView Details;
        public TextView Status;
        public TextView Reason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Request = itemView.findViewById(R.id.Request);
            FullName = itemView.findViewById(R.id.FullName);
            Email = itemView.findViewById(R.id.Email);
            UserType = itemView.findViewById(R.id.UserType);
            Details = itemView.findViewById(R.id.Details);
            Status = itemView.findViewById(R.id.Status);
            Reason = itemView.findViewById(R.id.Reason);
        }
    }
}