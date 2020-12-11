package com.calvindo.aldi.sutanto.tubes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditActivity extends AppCompatActivity {
    //firebase
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //init layout component
    private TextInputEditText editEmail, editPassword, editNama, editUsername, editNoTelp, editAlamat;
    private MaterialButton btnSave, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        //init edit text
        editEmail = findViewById(R.id.input_email);
        editNama = findViewById(R.id.input_nama);
        editUsername = findViewById(R.id.input_username);
        editNoTelp = findViewById(R.id.input_notelp);
        editAlamat = findViewById(R.id.input_alamat);
        editPassword = findViewById(R.id.input_password);
        btnCancel = findViewById(R.id.cancel_btn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://tubes-pbp-68a32.appspot.com/");

        getDataFromFirebase();

        btnSave = findViewById(R.id.save_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(editEmail.getText().toString(),editNama.getText().toString(),
                        editUsername.getText().toString(),editNoTelp.getText().toString(),
                        editAlamat.getText().toString(),editPassword.getText().toString());

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void updateUser(String email, String nama, String username, String notelp,String alamat, String password) {

        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    databaseReference.child(user.getUid()).child("email").setValue(email);
                    databaseReference.child(user.getUid()).child("nama").setValue(nama);
                    databaseReference.child(user.getUid()).child("username").setValue(username);
                    databaseReference.child(user.getUid()).child("notelp").setValue(notelp);
                    databaseReference.child(user.getUid()).child("alamat").setValue(alamat);
                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EditActivity.this,"User Profile Updated!", Toast.LENGTH_SHORT).show();
                                Toast.makeText(EditActivity.this, "" + auth.getCurrentUser().isEmailVerified() + "", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        }
                    });
                }else{
                    Toast.makeText(EditActivity.this,"Update Profile Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDataFromFirebase(){
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //mengambil data dari database
                    String alamat = ""+ds.child("alamat").getValue();
                    String email = ""+ds.child("email").getValue();
                    String nama = ""+ds.child("nama").getValue();
                    String notelp = ""+ds.child("notelp").getValue();
                    String username = ""+ds.child("username").getValue();

                    //set data yang akan ditampilkan di profil
                    editEmail.setText(email);
                    editUsername.setText(username);
                    editNama.setText(nama);
                    editAlamat.setText(alamat);
                    editNoTelp.setText(notelp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
