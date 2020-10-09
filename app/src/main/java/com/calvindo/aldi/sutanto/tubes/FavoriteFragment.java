package com.calvindo.aldi.sutanto.tubes;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.adapter.FavoriteAdapter;
import com.calvindo.aldi.sutanto.tubes.databinding.FragmentFavoriteBinding;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView myRecyclerView;
    private FavoriteAdapter adapter;
    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void getKost(){
        class GetKost extends AsyncTask<Void,Void,List<Kost>>{

            @Override
            protected List<Kost> doInBackground(Void... voids) {
                List<Kost> kosts = DatabaseClient
                        .getInstance(getContext())
                        .getDatabase().kostDAO().getAllFav();
                return kosts;
            }
            @Override
            protected void onPostExecute(List<Kost> kosts) {
                super.onPostExecute(kosts);
                adapter = new FavoriteAdapter(getContext(), kosts);
                myRecyclerView.setAdapter(adapter);
                if (kosts.isEmpty()){
                    Toast.makeText(getContext(), "Empty List " + kosts, Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetKost get = new GetKost();
        get.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        FragmentFavoriteBinding fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(getLayoutInflater());
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_fav);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getKost();
        return v;
    }
}