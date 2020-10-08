package com.calvindo.aldi.sutanto.tubes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.adapter.RecyclerViewAdapter;
import com.calvindo.aldi.sutanto.tubes.databinding.FragmentHomeBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.calvindo.aldi.sutanto.tubes.models.ListKost;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link } subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Kost> ListKost;
    private RecyclerView myRecyclerView;
    FragmentHomeBinding fragmentHomeBinding;
    private Button button_map;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListKost = new ArrayList<>();
        ListKost.add(new Kost("Kost Premium", "Gombong",
                "Jl. Potongan", "-7.607468", "109.515752", "082112134121",
                2000000.00 , "https://pbs.twimg.com/media/EjuODzyVkAAaMNT?format=jpg&name=large", 0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                3000000.00 , "https://pbs.twimg.com/media/EjuOb1YVcAEh__k?format=jpg&name=900x900",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                        "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                        3000000.00 , "https://pbs.twimg.com/media/EjuOeDTUYAEkWVc?format=jpg&name=medium",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                        "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                        3000000.00 , "https://pbs.twimg.com/media/EjuOhDEVkAA1Nrv?format=jpg&name=small",0));
        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
                        "Jl. Potongan", "-7.607468", "109.515752", "082152134121",
                        3000000.00 , "https://pbs.twimg.com/media/EjuOjU5VkAAETkt?format=jpg&name=900x900",0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_kost);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),ListKost);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}