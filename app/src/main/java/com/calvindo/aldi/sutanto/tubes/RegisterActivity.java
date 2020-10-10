package com.calvindo.aldi.sutanto.tubes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.calvindo.aldi.sutanto.tubes.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText editEmail, editPassword, editNama, editUsername, editNoTelp, editAlamat;
    private MaterialButton registerBtn;
    private FirebaseAuth auth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String getEmail, getPassword;
    private TextView loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init edit text
        editEmail = findViewById(R.id.input_email);
        editPassword = findViewById(R.id.input_password);
        editNama = findViewById(R.id.input_nama);
        editUsername = findViewById(R.id.input_username);
        editNoTelp = findViewById(R.id.input_notelp);
        editAlamat = findViewById(R.id.input_alamat);

        auth = FirebaseAuth.getInstance();

        editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        registerBtn = findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDataValid()){
                    createUserAccount();
                }
            }
        });

        loginLink = findViewById(R.id.login_link);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private boolean isDataValid(){
        getEmail = editEmail.getText().toString();
        getPassword = editPassword.getText().toString();

        if(TextUtils.isEmpty(getEmail) && TextUtils.isEmpty(getPassword)){
            Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }else if(!isEmailValid(getEmail)){
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(getPassword)){
            Toast.makeText(this,"Please Enter Password", Toast.LENGTH_SHORT).show();
        }else if(getPassword.length() < 6){
            Toast.makeText(this, "Password Too Short", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

    private boolean isEmailValid(String email){
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            return false;
        }
    }

    private void createUserAccount(){
        getEmail = editEmail.getText().toString();
        getPassword = editPassword.getText().toString();
        auth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            //rootNode = FirebaseDatabase.getInstance();
                            //reference = rootNode.getReference("User");

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String uid = firebaseUser.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("uid", uid);
                            hashMap.put("email",  editEmail.getText().toString());
                            hashMap.put("username", editUsername.getText().toString());
                            hashMap.put("notelp", editNoTelp.getText().toString());
                            hashMap.put("alamat", editAlamat.getText().toString());
                            hashMap.put("nama", editNama.getText().toString());
                            hashMap.put("Avatar_Images", "https://cdn.jpegmini.com/user/images/slider_puffin_before_mobile.jpg"); //input di edit profile

                            //store hashmap ke database
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference reference = firebaseDatabase.getReference("User");
                            reference.child(uid).setValue(hashMap);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*private void loginUserAccount() {
        getEmail = editEmail.getText().toString();
        getPassword = editPassword.getText().toString();
        auth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }*/
}
