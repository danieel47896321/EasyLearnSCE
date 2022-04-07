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
import com.example.easylearnsce.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        View view = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);
        return new RequestsAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RequestsAdapter.ViewHolder holder, int position) {
        Request request = requests.get(position);
        holder.ID.setText(""+(position+1)+".");
        holder.Request.setText(context.getResources().getString(R.string.Request) + ": " + request.getRequest());
        holder.FullName.setText(context.getResources().getString(R.string.FullName) + ": " + request.getFirstName()+ " " + request.getLastName());
        holder.EmailRequest.setText(context.getResources().getString(R.string.Email) + ": " + request.getEmail());
        holder.Type.setText(context.getResources().getString(R.string.UserType) + ": " + request.getType());
        if(request.getRequest().equals("Get Teacher Permission") || request.getRequest().equals("קבלת הרשאות מרצה"))
            TeacherPermission(holder, request);
        else if(request.getRequest().equals("Report Problem") || request.getRequest().equals("דיווח על תקלה"))
            ReportProblem(holder, request);
        else
            Other(holder, request);
    }
    private void TeacherPermission(@NonNull RequestsAdapter.ViewHolder holder, Request request){
        holder.Details.setVisibility(View.GONE);
        holder.TextInputLayoutAnswer.setVisibility(View.GONE);
        holder.RequestApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setStatus(context.getResources().getString(R.string.Approved));
                Approved(request);
            }
        });
        holder.RequestDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setStatus(context.getResources().getString(R.string.Denied));
                Denied(request);
            }
        });
    }
    private void ReportProblem(@NonNull RequestsAdapter.ViewHolder holder, Request request){
        holder.RequestApproved.setText(context.getResources().getString(R.string.Fixed));
        holder.RequestDenied.setText(context.getResources().getString(R.string.NotFixed));
        holder.Details.setText(request.getDetails());
        holder.TextInputLayoutAnswer.setHelperText("");
        holder.RequestApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.TextInputLayoutAnswer.getEditText().getText().toString().equals(""))
                    holder.TextInputLayoutAnswer.setHelperText(context.getResources().getString(R.string.Required));
                else {
                    holder.TextInputLayoutAnswer.setHelperText("");
                    request.setAnswer(holder.TextInputLayoutAnswer.getEditText().getText().toString());
                    request.setStatus(context.getResources().getString(R.string.Fixed));
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getUid()).child(request.getId());
                    databaseReference.setValue(request);
                }
            }
        });
        holder.RequestDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.TextInputLayoutAnswer.getEditText().getText().toString().equals(""))
                    holder.TextInputLayoutAnswer.setHelperText(context.getResources().getString(R.string.Required));
                else {
                    request.setAnswer(holder.TextInputLayoutAnswer.getEditText().getText().toString());
                    request.setStatus(context.getResources().getString(R.string.NotFixed));
                    Denied(request);
                }
            }
        });
    }
    private void Other(@NonNull RequestsAdapter.ViewHolder holder, Request request){
        holder.Details.setText(request.getDetails());
        holder.TextInputLayoutAnswer.setHelperText("");
        holder.RequestApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.TextInputLayoutAnswer.getEditText().getText().toString().equals(""))
                    holder.TextInputLayoutAnswer.setHelperText(context.getResources().getString(R.string.Required));
                else {
                    request.setAnswer(holder.TextInputLayoutAnswer.getEditText().getText().toString());
                    request.setStatus(context.getResources().getString(R.string.RequestApproved));
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getUid()).child(request.getId());
                    databaseReference.setValue(request);
                }
            }
        });
        holder.RequestDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.TextInputLayoutAnswer.getEditText().getText().toString().equals(""))
                    holder.TextInputLayoutAnswer.setHelperText(context.getResources().getString(R.string.Required));
                else {
                    request.setAnswer(holder.TextInputLayoutAnswer.getEditText().getText().toString());
                    request.setStatus(context.getResources().getString(R.string.RequestDenied));
                    Denied(request);
                }
            }
        });
    }
    private void Approved(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getUid()).child(request.getId());
        databaseReference.setValue(request);
        FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference1 = firebaseDatabase1.getReference().child("Users").child(request.getUid()).child("type");
        databaseReference1.setValue(context.getResources().getString(R.string.Teacher));
    }
    private void Denied(Request request){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(request.getUid()).child(request.getId());
        databaseReference.setValue(request);
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