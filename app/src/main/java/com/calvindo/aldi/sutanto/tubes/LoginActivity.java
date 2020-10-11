package com.calvindo.aldi.sutanto.tubes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.os.Build.VERSION_CODES.O;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editEmail, editPassword;
    private MaterialButton loginButton;
    private FirebaseAuth auth;
    private String getEmail, getPassword;
    private TextView registerLink;
    private String CHANNEL_ID = "Channe 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init edit text
        editEmail = findViewById(R.id.login_email);
        editPassword = findViewById(R.id.login_password);
        //init material button
        loginButton = findViewById(R.id.login_button);
        //init register link
        registerLink = findViewById(R.id.register_link);

        //init firebase authentication
        auth = FirebaseAuth.getInstance();

        //editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //dari login ke register
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
                createNotification();
                addNotification();
            }
        });

    }

    private void addNotification() {
        if(Build.VERSION.SDK_INT >= O){
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            //notification for System after add kost
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification() {
        //Menambhakan Notif saat menambah favorit kost.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Welcome");
        builder.setContentText("Selamat Datang di Aplikasi kami.");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Membuat Intent yang menampilkan Notification
        Intent notificationIntent = new Intent(this,FavoriteFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        //Menampilkamn Notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    private void loginUserAccount(){
        getEmail = editEmail.getText().toString();
        getPassword = editPassword.getText().toString();
        auth.signInWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}