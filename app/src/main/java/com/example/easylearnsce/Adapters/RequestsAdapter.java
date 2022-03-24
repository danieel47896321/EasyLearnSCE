package com.example.easylearnsce.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Request;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    private Context context;
    private List<Request> requests;
    public RequestsAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }
    @NonNull
    @Override
    public RequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_permission_item, parent, false);
        return new RequestsAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.ID.setText(""+(position+1)+".");
        holder.FullName.setText(holder.FullName.getText() + ": " + request.getFirstName()+ " " + request.getLastName());
        holder.EmailRequest.setText(holder.EmailRequest.getText() + ": " + request.getEmail());
        holder.Type.setText(holder.Type.getText() + ": " + request.getType());
        holder.Request.setText(holder.Request.getText() + ": " + request.getRequest());
        if(request.getDetails().equals("null")) {
            holder.Details.setVisibility(View.GONE);
            holder.TextInputLayoutAnswer.setVisibility(View.GONE);
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getId());
            holder.RequestApproved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { Approved(request); }
            });
            holder.RequestDenied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { Denied(request); }
            });
        }
        else {
            holder.Details.setText(holder.Details.getText() + ": " + request.getDetails());
            if(holder.TextInputLayoutAnswer.getEditText().getText().toString().equals(""))
                holder.TextInputLayoutAnswer.setHelperText(context.getResources().getString(R.string.Required));
            else {
                holder.RequestApproved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Approved(request);
                    }
                });
                holder.RequestDenied.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Denied(request);
                    }
                });
            }
        }
       // holder.TextInputLayoutAnswer.setText(context.getResources().getString(R.string.Request) + ": " + request.getRequest());
        /*
        if (!request.getDetails().equals("null"))
            holder.Details.setText(context.getResources().getString(R.string.Details) + ": " + request.getDetails());
        else
            holder.Details.setVisibility(View.GONE);
        if (request.getState().equals("Approved") || request.getAnswer().equals("הבקשה אושרה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.green));
        else if (request.getState().equals("Denied") || request.getAnswer().equals("הבקשה נדחתה"))
            holder.State.setTextColor(context.getResources().getColor(R.color.red));
        else
            holder.State.setTextColor(context.getResources().getColor(R.color.white));
        holder.State.setText(context.getResources().getString(R.string.State) + ": " + request.getState());
        if (!request.getAnswer().equals(""))
            holder.Reason.setText(context.getResources().getString(R.string.Answer) + ": " + request.getAnswer());
        else
            holder.Reason.setVisibility(View.GONE);*/
    }
    public void Approved(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Request request1 = dataSnapshot.getValue(Request.class);
                    if(request.getRequest().equals(request1.getRequest()) && request.getDetails().equals(request1.getDetails()) && request.getState().equals(request1.getState()) && request.getAnswer().equals(request1.getAnswer())) {
                        request.setState(context.getResources().getString(R.string.Approved));
                        databaseReference.child(dataSnapshot.getKey() + "").setValue(request);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    public void Denied(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Request request1 = dataSnapshot.getValue(Request.class);
                    if(request.getRequest().equals(request1.getRequest()) && request.getDetails().equals(request1.getDetails()) && request.getState().equals(request1.getState()) && request.getAnswer().equals(request1.getAnswer())) {
                        request.setState(context.getResources().getString(R.string.Denied));
                        databaseReference.child(dataSnapshot.getKey() + "").setValue(request);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    @Override
    public int getItemCount() { return requests.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ID, FullName, EmailRequest, Type, Request, Details;
        public Button RequestApproved, RequestDenied;
        public TextInputLayout TextInputLayoutAnswer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.ID);
            FullName = itemView.findViewById(R.id.FullName);
            EmailRequest = itemView.findViewById(R.id.EmailRequest);
            Type = itemView.findViewById(R.id.Type);
            Request = itemView.findViewById(R.id.Request);
            Details = itemView.findViewById(R.id.Details);
            TextInputLayoutAnswer = itemView.findViewById(R.id.TextInputLayoutAnswer);
            RequestApproved = itemView.findViewById(R.id.RequestApproved);
            RequestDenied = itemView.findViewById(R.id.RequestDenied);
        }
    }
}