package com.calvindo.aldi.sutanto.tubes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.Database.DatabaseClient;
import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.calvindo.aldi.sutanto.tubes.R;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    Context context;
    List<TransaksiClientAccess> transactionsList;
    AutoCompleteTextView edLamaSewa;
    private final String[] saLamaSewa = new String[]{ "1", "2", "3", "4", "5"};
    private int lamaSewa=1;
    int id;

    public TransaksiAdapter(Context context, List<TransaksiClientAccess> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @NonNull
    @Override
    public TransaksiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi, parent,false);

        return new TransaksiViewHolder(view);
    }

    public class TransaksiViewHolder extends RecyclerView.ViewHolder{
        private TextView twNamaUser, twNamaKost, twDurasi, twTotal;
        private MaterialButton mbEdit;

        public TransaksiViewHolder(@NonNull View itemView) {
            super(itemView);
//            twNamaUser = itemView.findViewById(R.id.twNamaUser);
            mbEdit = itemView.findViewById(R.id.btnEdit);
            twNamaKost = itemView.findViewById(R.id.twNamaKost);
            twDurasi = itemView.findViewById(R.id.twDurasi);
            twTotal = itemView.findViewById(R.id.twTotalBayar);



        }
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.TransaksiViewHolder holder, int position) {
        final TransaksiClientAccess transactions = transactionsList.get(position);

        holder.twNamaKost.setText(transactions.getId_kost());
        holder.twTotal.setText(String.valueOf(transactions.getTotal_pembayaran()));
        holder.twDurasi.setText(String.valueOf(transactions.getLama_sewa()));

        holder.mbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());

                View dialogV = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.dialog_booking, null);

                edLamaSewa = dialogV.findViewById(R.id.edLamaSewa);

                final ArrayAdapter<String> adapterSewa = new ArrayAdapter<>(context,
                        R.layout.dd_list, R.id.dd_list, saLamaSewa);

                edLamaSewa.setAdapter(adapterSewa);
                edLamaSewa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lamaSewa = Integer.parseInt(saLamaSewa[i]);
                    }
                });

                builder.setView(dialogV);
                builder.setCancelable(true);
                builder.show();

//                id = transactionsList.get()

                MaterialButton button = dialogV.findViewById(R.id.btnBooking);

//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
////                    public void onClick(View view) {
////                        update(transactions.getId_kost());
////                    }
//                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public void update(final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Transactions transactions = DatabaseClient.getInstance(context)
                                    .getDatabase().transDAO().findtransbyid(id);

                DatabaseClient.getInstance(context).getDatabase()
                        .transDAO().update(transactions);
            }
        }).start();
        Toast.makeText(context, "id kost: "+id+"lama sewa: "+lamaSewa+"total: ", Toast.LENGTH_LONG).show();
    }
}
