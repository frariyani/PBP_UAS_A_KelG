package com.calvindo.aldi.sutanto.tubes;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.databinding.FragmentProfileBinding;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.calvindo.aldi.sutanto.tubes.models.User;
import com.calvindo.aldi.sutanto.tubes.models.UserDummy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment{
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    private String cameraPermissions[];
    private String storagePermissions[];

    //firebase
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //profile
    private ImageView avatarIv;
    private TextView namaTv, usernameTv, emailTv, notelpTv, alamatTv;
    private MaterialButton logoutBtn;

    //upload avatar
    private Uri PickedImgUri;
    private String storagePath = "Avatar_Images/";

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
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl("gs://tubes-pbp-68a32.appspot.com/");

        //init views
        namaTv = view.findViewById(R.id.namaView);
        usernameTv = view.findViewById(R.id.usernameView);
        emailTv = view.findViewById(R.id.emailView);
        notelpTv = view.findViewById(R.id.notelpView);
        alamatTv = view.findViewById(R.id.alamatView);

        //init array permission
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        getDataFromFirebase();

        logoutBtn = view.findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
            }
        });

        avatarIv = view.findViewById(R.id.avatarView);
        avatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUploadOptions();
            }
        });

        return view;
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        requestPermissions(storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean resultCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resultWrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultCamera && resultWrite;
    }

    private void requestCameraPermission(){
        requestPermissions( cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeAccepted){
                        openCamera();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0){
                    boolean writeAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(writeAccepted){
                        openGallery();
                    }
                }
            }
            break;
        }
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Description");
        PickedImgUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, PickedImgUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                PickedImgUri = data.getData();

                uploadAvatar(PickedImgUri);
            }
            if(requestCode == IMAGE_PICK_CAMERA_CODE){
                uploadAvatar(PickedImgUri);
            }
        }
    }

    private void uploadAvatar(Uri uri){
        //private ImageView avatarIv;
        //private Uri PickedImgUri;
        //private String storagePath = "Avatar_Images/";

        String filePathName = storagePath+""+user.getUid();
        StorageReference mStorage = storageReference.child(filePathName);
        mStorage.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        if(uriTask.isSuccessful()){
                            HashMap<String, Object> results = new HashMap<>();
                            results.put(storagePath, downloadUri.toString());

                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void showUploadOptions(){
        String options[] = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Pick Image From");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    //upload lewat kamera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        openCamera();
                    }
                }else if(i == 1){
                    //upload lewat gallery
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        openGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    //private void openGallery(){
    //    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
    //    galleryIntent.setType("image/*");
    //    startActivityForResult(galleryIntent, GALLERY_REQUEST);
    //}

//    @BindingAdapter("bind:loadDrawable")
//    public static void loadDrawable(ImageView imageView, String imageURL) {
//        int drawableResourceId = imageView.getResources().getIdentifier(imageURL, "drawable", imageView.getContext().getPackageName());
//        Log.i("tag","profile.id:" + drawableResourceId);
//        imageView.setImageResource(drawableResourceId);
//    }

    private void getDataFromFirebase(){
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //mengambil data dari database
                    String alamat = ""+ds.child("alamat").getValue();
                    String avatar = ""+ds.child("Avatar_Images").getValue();
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
                    Glide.with(getContext())
                            .load(avatar)
                            .apply(RequestOptions.circleCropTransform())
                            .into(avatarIv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}