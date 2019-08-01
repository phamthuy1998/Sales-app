package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    Context context;
    ArrayList<SanPham> arraySanPham;

    public SanPhamAdapter(Context context, ArrayList<SanPham> arraySanPham) {
        this.context = context;
        this.arraySanPham = arraySanPham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        SanPham sanPham = arraySanPham.get(i);
        itemHolder.textViewTenSanPham.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.textViewGiaSanPham.setText("Giá: "+ decimalFormat.format(sanPham.getGiaSP()) +"đ");
        Picasso.with(context).load(sanPham.getHinhAnhSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(itemHolder.imageViewHinhSanPham);
    }

    @Override
    public int getItemCount() {
        return arraySanPham.size();
    }

    public class ItemHolder extends  RecyclerView.ViewHolder{
        public ImageView imageViewHinhSanPham;
        public TextView textViewTenSanPham,textViewGiaSanPham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageViewHinhSanPham = (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            textViewTenSanPham = (TextView) itemView.findViewById(R.id.textviewtensanpham);
            textViewGiaSanPham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham", arraySanPham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, arraySanPham.get(getPosition()).getTenSP());
                    context.startActivity(intent);
                }
            });
        }
    }
}
