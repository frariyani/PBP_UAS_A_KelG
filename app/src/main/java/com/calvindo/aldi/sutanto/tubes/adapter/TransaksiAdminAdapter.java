package com.calvindo.aldi.sutanto.tubes.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.calvindo.aldi.sutanto.tubes.Database.TransaksiClientAccess;
import com.calvindo.aldi.sutanto.tubes.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class TransaksiAdminAdapter extends RecyclerView.Adapter<TransaksiAdminAdapter.AdminViewHolder>{

    private Context context;
    private List<TransaksiClientAccess> listTransaksi;

    public TransaksiAdminAdapter(Context context, List<TransaksiClientAccess> listTransaksi) {
        this.context = context;
        this.listTransaksi = listTransaksi;
    }


    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi_admin, parent, false);

        return new AdminViewHolder(view);
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder{
        private TextView tvIdTrans, tvIdUser, tvIdKost, tvDurasi, tvTotalBayar;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdTrans = itemView.findViewById(R.id.twIdtrans);
            tvIdKost = itemView.findViewById(R.id.twIdKost);
            tvDurasi = itemView.findViewById(R.id.twDurasi);
            tvTotalBayar = itemView.findViewById(R.id.twTotalBayar);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdminAdapter.AdminViewHolder holder, int position) {
        final TransaksiClientAccess trans = listTransaksi.get(position);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String harga = kursIndonesia.format(trans.getTotal_pembayaran());

        holder.tvIdTrans.setText(trans.getId());
        holder.tvIdKost.setText(trans.getId_kost());
        holder.tvDurasi.setText(String.valueOf(trans.getLama_sewa()));
        holder.tvTotalBayar.setText(harga);
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }
}
