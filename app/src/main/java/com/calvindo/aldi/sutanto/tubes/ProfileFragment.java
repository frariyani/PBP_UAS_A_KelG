package com.calvindo.aldi.sutanto.tubes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.calvindo.aldi.sutanto.tubes.databinding.FragmentProfileBinding;
import com.calvindo.aldi.sutanto.tubes.models.User;
import com.calvindo.aldi.sutanto.tubes.models.UserDummy;

import java.io.IOException;
import java.io.InputStream;

public class ProfileFragment extends Fragment{
    FragmentProfileBinding binding;
    User user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, null, false);
        View view = binding.getRoot();
        user = new UserDummy().USER;
        binding.setUser(user);
        binding.executePendingBindings();

        //ImageView imageView = binding.getRoot().findViewById(R.id.imageView);
        //imageView.setImageResource(R.drawable.);

        return view;
    }

    //@BindingAdapter("bind:loadDrawable")
    //public static void loadDrawable(ImageView imageView, String imageURL) {
    //    int drawableResourceId = imageView.getResources().getIdentifier(imageURL, "drawable", imageView.getContext().getPackageName());
    //    Log.i("tag","profile.id:" + drawableResourceId);
    //    imageView.setImageResource(drawableResourceId);
    //}
}