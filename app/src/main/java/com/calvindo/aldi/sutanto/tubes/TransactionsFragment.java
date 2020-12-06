package com.calvindo.aldi.sutanto.tubes;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.TransaksiResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.calvindo.aldi.sutanto.tubes.adapter.FavoriteAdapter;
import com.calvindo.aldi.sutanto.tubes.adapter.TransaksiAdapter;
import com.calvindo.aldi.sutanto.tubes.databinding.FragmentFavoriteBinding;
import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv;
    private TransaksiAdapter transaksiAdapter;
    private String uid;
    private List<TransaksiClientAccess> transactions = new ArrayList<>();

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;




    public TransactionsFragment() {
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
    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
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
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TransaksiResponse> call = apiService.getAllTransaksi(uid);

        call.enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                Toast.makeText(getContext(), "Berhasil tampil data", Toast.LENGTH_SHORT).show();
                generateList(response.body().getTransaksi());
                Log.i("Quda : ", "Masuk RESPONSE ," + response.body().getMessage());
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Log.i("Gagal : ", "Masuk failure ," + t.getMessage());
            }
        });


    }
//
    public void generateList(List<TransaksiClientAccess> transaksiList){
        transaksiAdapter = new TransaksiAdapter(getContext(), transaksiList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(transaksiAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        private RecyclerView rv;
//        private TransaksiAdapter transaksiAdapter;
//        private List<TransaksiClientAccess> transactions = new ArrayList<>();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
        getKost();
        rv = v.findViewById(R.id.recyclerview_fav);
        return v;
    }
}