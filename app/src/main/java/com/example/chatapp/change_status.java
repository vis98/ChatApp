package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class change_status extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextInputLayout mstatus;
    private Button msavechanges;
    private DatabaseReference mdatabase;
    private FirebaseUser mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        mCurrentuser=FirebaseAuth.getInstance().getCurrentUser();
        String curr_user=mCurrentuser.getUid();

        mdatabase= FirebaseDatabase.getInstance().getReference().child("users").child(curr_user);

        mToolbar=(Toolbar)findViewById(R.id.status_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mstatus=(TextInputLayout)findViewById(R.id.chnginput);
        msavechanges=(Button)findViewById(R.id.savechng);
        msavechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = mstatus.getEditText().getText().toString();
                mdatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(change_status.this,"successfully changed",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(change_status.this,"error",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}
