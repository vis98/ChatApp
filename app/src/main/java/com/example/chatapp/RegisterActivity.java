package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText UName;
    private TextInputEditText  UEmail;
    private TextInputEditText  UPassword;
    private Button ucreate;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        UName=(TextInputEditText)findViewById(R.id.UName);
        UEmail=(TextInputEditText )findViewById(R.id.UEid);
        UPassword=(TextInputEditText)findViewById(R.id.UPasswd);
        ucreate=(Button)findViewById(R.id.creacc);

        ucreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=UName.getEditableText().toString();
                String email=UEmail.getEditableText().toString();
                String passwd=UPassword.getEditableText().toString();
                register_user(name,email,passwd);
            }
        });

    }

    private void register_user(String name, String email, String passwd) {
        mAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent  main=new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"got error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
