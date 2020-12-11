package com.calvindo.aldi.sutanto.tubes;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.calvindo.aldi.sutanto.tubes.API.ApiClient;
import com.calvindo.aldi.sutanto.tubes.API.ApiInterface;
import com.calvindo.aldi.sutanto.tubes.API.KostResponse;
import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.KostClientAccess;
import com.calvindo.aldi.sutanto.tubes.adapter.KostRecyclerAdapter;
import com.calvindo.aldi.sutanto.tubes.adapter.RecyclerViewAdapter;
import com.calvindo.aldi.sutanto.tubes.databinding.FragmentHomeBinding;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    FragmentHomeBinding fragmentHomeBinding;
    private Button button_map;
    private SharedPreferences preferences;
    public static final int mode = Activity.MODE_PRIVATE;
    private ProgressBar progressBar;

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
//        ListKost.add(new Kost("Kost Premium", "Gombong",
//                "Jl. Potongan", "-7.607468", "109.515752", "082112134121",
//                2000000.00 , "https://pbs.twimg.com/media/EjuODzyVkAAaMNT?format=jpg&name=large", 0));
//        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
//                "Jl. Potongan", "-7.797556", "110.337681", "082152134121",
//                3000000.00 , "https://pbs.twimg.com/media/EjuOb1YVcAEh__k?format=jpg&name=900x900",0));
//        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
//                        "Jl. Potongan", "-7.825174", "110.367202", "082152134121",
//                        3000000.00 , "https://pbs.twimg.com/media/EjuOeDTUYAEkWVc?format=jpg&name=medium",0));
//        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
//                        "Jl. Potongan", "-7.784592", "110.407222", "082152134121",
//                        3000000.00 , "https://pbs.twimg.com/media/EjuOhDEVkAA1Nrv?format=jpg&name=small",0));
//        ListKost.add(new Kost("Kost Pertamax", "Yogyakarta",
//                        "Jl. Potongan", "-7.783312", "110.391650", "082152134121",
//                        3000000.00 , "https://pbs.twimg.com/media/EjuOjU5VkAAETkt?format=jpg&name=900x900",0));

    }

    public void initKost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseClient
                        .getInstance(getContext()).getDatabase()
                        .kostDAO().insertList(ListKost);
            }
        }).start();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_kost);
        refreshLayout = v.findViewById(R.id.refresh);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getKost();
                refreshLayout.setRefreshing(false);
            }
        });

        progressBar = v.findViewById(R.id.progressBar);


        getKost();
        return v;
    }

    public void generateList(List<KostClientAccess> kostList){
        adapter = new RecyclerViewAdapter(getContext(), kostList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setAdapter(adapter);

    }

    public void getKost(){
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<KostResponse> call = apiService.getAllKost();

        call.enqueue(new Callback<KostResponse>() {
            @Override
            public void onResponse(Call<KostResponse> call, Response<KostResponse> response) {
                generateList(response.body().getKost());
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<KostResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void getKost(){
//        class GetKost extends AsyncTask<Void,Void,List<Kost>>{
//
//            @Override
//            protected List<Kost> doInBackground(Void... voids) {
//                List<Kost> kosts = DatabaseClient
//                        .getInstance(getContext())
//                        .getDatabase().kostDAO().getAll();
//                return kosts;
//            }
//            @Override
//            protected void onPostExecute(List<Kost> kost) {
//                super.onPostExecute(kost);
//                adapter = new RecyclerViewAdapter(getContext(), kost);
//                myRecyclerView.setAdapter(adapter);
//                if (kost.isEmpty()){
//                    initKost();
//                    Toast.makeText(getContext(), "Empty List" + kost, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        GetKost get = new GetKost();
//        get.execute();
//    }
}