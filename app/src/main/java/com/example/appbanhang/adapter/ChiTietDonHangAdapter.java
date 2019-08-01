package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.ChiTietDonHang;
import com.example.appbanhang.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietDonHangAdapter extends BaseAdapter {
    Context context;
        ArrayList<ChiTietDonHang> arrChiTietDH;



    public ChiTietDonHangAdapter(Context context, ArrayList<ChiTietDonHang> arrChiTietDH) {
        this.context = context;
        this.arrChiTietDH = arrChiTietDH;
    }

    @Override
    public int getCount() {
        return arrChiTietDH.size();
    }

    @Override
    public Object getItem(int i) {
        return arrChiTietDH.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView textViewTenIDDH, textViewTenSPDH, textViewGiaSPDH, textViewSLSP;
        public ImageView imageViewDH;

    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ChiTietDonHangAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ChiTietDonHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_chi_tiet_don_hang, null);
            viewHolder.textViewTenIDDH = (TextView) view.findViewById(R.id.textviewmadonhangct);
            viewHolder.textViewTenSPDH = (TextView) view.findViewById(R.id.textviewtenspdonhang);
            viewHolder.imageViewDH = (ImageView) view.findViewById(R.id.imageviewhinhanhctdonhang);
            viewHolder.textViewGiaSPDH = (TextView) view.findViewById(R.id.textviewgiadonhang);
            viewHolder.textViewSLSP = (TextView) view.findViewById(R.id.textviewsoluongsp);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ChiTietDonHangAdapter.ViewHolder) view.getTag();
        }
        ChiTietDonHang chiTietDonHang = (ChiTietDonHang) getItem(i);
        viewHolder.textViewTenSPDH.setText(chiTietDonHang.getTenSanPham());
        viewHolder.textViewTenIDDH.setText( "Mã đơn hàng: "+(chiTietDonHang.getIdDonHang()));
        viewHolder.textViewSLSP.setText( "Số lượng sản phẩm: "+(chiTietDonHang.getSoLuongSP()));

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewGiaSPDH.setText("Giá: "+decimalFormat.format( chiTietDonHang.getGiaSP()) + "đ");
        Picasso.with(context).load(chiTietDonHang.getHinhAnhCT())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageViewDH);
        return view;
    }

}
