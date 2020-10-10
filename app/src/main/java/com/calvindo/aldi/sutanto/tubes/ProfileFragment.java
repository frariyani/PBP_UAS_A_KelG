package com.calvindo.aldi.sutanto.tubes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.calvindo.aldi.sutanto.tubes.databinding.FragmentProfileBinding;
import com.calvindo.aldi.sutanto.tubes.models.User;
import com.calvindo.aldi.sutanto.tubes.models.UserDummy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;

public class ProfileFragment extends Fragment{
    public static final int REQUEST_CODE = 1;
    //firebase
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    //profile
    private ImageView avatarIv;
    private TextView namaTv, usernameTv, emailTv, notelpTv, alamatTv;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //init firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        //init views
        namaTv = view.findViewById(R.id.namaView);
        usernameTv = view.findViewById(R.id.usernameView);
        emailTv = view.findViewById(R.id.emailView);
        notelpTv = view.findViewById(R.id.notelpView);
        alamatTv = view.findViewById(R.id.alamatView);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //mengambil data dari database
                    String alamat = ""+ds.child("alamat").getValue();
                    String avatar = ""+ds.child("avatar").getValue();
                    String email = ""+ds.child("email").getValue();
                    String nama = ""+ds.child("nama").getValue();
                    String notelp = ""+ds.child("notelp").getValue();
                    String username = ""+ds.child("username").getValue();

                    //set data yang akan ditampilkan di profil
                    namaTv.setText(nama);
                    usernameTv.setText(username);
                    emailTv.setText(email);
                    notelpTv.setText(notelp);
                    alamatTv.setText(alamat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        avatarIv = view.findViewById(R.id.avatarView);
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUEST_CODE);
    }

    //@BindingAdapter("bind:loadDrawable")
    //public static void loadDrawable(ImageView imageView, String imageURL) {
    //    int drawableResourceId = imageView.getResources().getIdentifier(imageURL, "drawable", imageView.getContext().getPackageName());
    //    Log.i("tag","profile.id:" + drawableResourceId);
    //    imageView.setImageResource(drawableResourceId);
    //}
}