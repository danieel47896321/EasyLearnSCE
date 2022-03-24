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
    public AllRequestsAdapter(Context context, List<Request> requests ){
        this.context = context;
        this.requests = requests;
    }
    @NonNull
    @Override
    public AllRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_request_view, parent, false);
        return new AllRequestsAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AllRequestsAdapter.ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.Request.setText(context.getResources().getString(R.string.Request) +": " + request.getRequest());
        if(!request.getDetails().equals("null"))
            holder.Details.setText(context.getResources().getString(R.string.Details) +": " +request.getDetails());
        else
            holder.Details.setVisibility(View.GONE);
        if(request.getState().equals("Approved") || request.getAnswer().equals("הבקשה אושרה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.green));
        else if(request.getState().equals("Denied") || request.getAnswer().equals("הבקשה נדחתה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.State.setTextColor(context.getResources().getColor(R.color.white));
        holder.State.setText(context.getResources().getString(R.string.State) +": " +request.getState());
        if(!request.getAnswer().equals(""))
            holder.Reason.setText(context.getResources().getString(R.string.Answer) + ": " + request.getAnswer());
        else
            holder.Reason.setVisibility(View.GONE);
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