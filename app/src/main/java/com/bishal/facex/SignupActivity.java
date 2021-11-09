package com.bishal.facex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText emailBox, passwordBox, nameBox;
    Button loginBtn,signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        auth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        nameBox= findViewById(R.id.nameBox);
        emailBox= findViewById(R.id.emailBox);
        passwordBox= findViewById(R.id.passwordBox);
        loginBtn= findViewById(R.id.loginBtn);
        signupBtn= findViewById(R.id.createAccount);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,pass,name;
                name= nameBox.getText().toString();
                email= emailBox.getText().toString();
                pass= passwordBox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);



                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                       if(task.isSuccessful()){

                           database.collection("Users")
                                   .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>(){

                               @Override
                               public void onSuccess(Void aVoid) {
                                   startActivity(new Intent(SignupActivity.this,LoginActivity.class));

                               }
                           });
                           Toast.makeText(SignupActivity.this,"Account Created Succesfully!",Toast.LENGTH_LONG).show();

                       }else{
                           Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                       }

                   }
                });




            }
        });
    }
}