package com.example.easylearnsce.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Request;
import com.example.easylearnsce.R;

import java.util.List;

public class AllRequestsAdapter extends RecyclerView.Adapter<AllRequestsAdapter.ViewHolder> {
    private Context context;
    private List<Request> requests;
    private String type;
    public AllRequestsAdapter(Context context, List<Request> requests, String type ){
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
    private void setAdmin(@NonNull AllRequestsAdapter.ViewHolder holder, int position, Request request){
        holder.Request.setText(context.getResources().getString(R.string.Request) +": " + request.getRequest());
        if(request.getRequest().equals("Other") || request.getRequest().equals("אחר") || request.getRequest().equals("Report Problem") || request.getRequest().equals("דיווח על תקלה")) {
            holder.Details.setText(context.getResources().getString(R.string.Details) + ": " + request.getDetails());
            holder.Reason.setText(context.getResources().getString(R.string.Answer) + ": " + request.getAnswer());
        }
        else {
            holder.Details.setVisibility(View.GONE);
            holder.Reason.setVisibility(View.GONE);
        }
        if(request.getState().equals("Approved") || request.getAnswer().equals("הבקשה אושרה") || request.getState().equals("Fixed") || request.getAnswer().equals("טופל"))
            holder.State.setTextColor(context.getResources().getColor(R.color.green));
        else if(request.getState().equals("Denied") || request.getAnswer().equals("הבקשה נדחתה") || request.getState().equals("Not Fixed") || request.getAnswer().equals("לא טופל"))
            holder.State.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.State.setTextColor(context.getResources().getColor(R.color.white));
        holder.State.setText(context.getResources().getString(R.string.State) +": " +request.getState());

    }
    private void setOthers(@NonNull AllRequestsAdapter.ViewHolder holder, int position,  Request request){
        holder.Request.setText(context.getResources().getString(R.string.Request) +": " + request.getRequest());
        if(request.getRequest().equals("Other") || request.getRequest().equals("אחר") || request.getRequest().equals("Report Problem") || request.getRequest().equals("דיווח על תקלה")) {
            holder.Details.setText(context.getResources().getString(R.string.Details) + ": " + request.getDetails());
            holder.Reason.setText(context.getResources().getString(R.string.Answer) + ": " + request.getAnswer());
        }
        else {
            holder.Details.setVisibility(View.GONE);
            holder.Reason.setVisibility(View.GONE);
        }
        if(request.getState().equals("Approved") || request.getAnswer().equals("הבקשה אושרה") || request.getState().equals("Request Processed") || request.getAnswer().equals("הבקשה טופלה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.green));
        else if(request.getState().equals("Denied") || request.getAnswer().equals("הבקשה נדחתה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.State.setTextColor(context.getResources().getColor(R.color.white));
        holder.State.setText(context.getResources().getString(R.string.State) +": " +request.getState());

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AllRequestsAdapter.ViewHolder holder, int position) {
        Request request = requests.get(position);
        if(type.equals("Admin"))
            setAdmin(holder, position, request);
        else
            setOthers(holder, position, request);
    }
    @Override
    public int getItemCount() { return requests.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Request;
        public TextView Details;
        public TextView State;
        public TextView Reason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Request = itemView.findViewById(R.id.Request);
            Details = itemView.findViewById(R.id.Details);
            State = itemView.findViewById(R.id.State);
            Reason = itemView.findViewById(R.id.Reason);
        }
    }
}