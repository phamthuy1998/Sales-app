package com.example.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DonHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<DonHang> arrayDonHang;
    ArrayList<DonHang> arrayList;

    public DonHangAdapter(Context context, ArrayList<DonHang> arrayDonHang) {
        this.context = context;
        this.arrayDonHang = arrayDonHang;
        this.arrayList = arrayList;
        this.arrayList = new ArrayList<DonHang>();
        this.arrayList.addAll(arrayDonHang);
    }

    @Override
    public int getCount() {
        return arrayDonHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDonHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView textViewMaDH, textViewsdt, textViewNgayDat, textViewDiaChiGiao, textViewTrangThaiDH;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DonHangAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new DonHangAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_don_hang, null);
            viewHolder.textViewMaDH = (TextView) view.findViewById(R.id.textviewmadonhang1);
            viewHolder.textViewsdt = (TextView) view.findViewById(R.id.textviewsodienthoai);
            viewHolder.textViewNgayDat = (TextView) view.findViewById(R.id.textviewngaydat);
            viewHolder.textViewDiaChiGiao = (TextView) view.findViewById(R.id.textviewdiachigiao);
            viewHolder.textViewTrangThaiDH = (TextView) view.findViewById(R.id.textviewtrangthaidonhang);


            view.setTag(viewHolder);
        } else {
            viewHolder = (DonHangAdapter.ViewHolder) view.getTag();
        }


        DonHang donHang = (DonHang) getItem(i);
        String trangThai = "";
        if (donHang.getTrangThaiDonHang() == 4) {
            trangThai = "Đơn hàng đã hủy";
        }else if(donHang.getTrangThaiDonHang() == 3){
            trangThai = "Giao hàng thành công" ;
        }
        else if(donHang.getTrangThaiDonHang() == 2){
            trangThai = "Đang giao hàng";
        }
        else if(donHang.getTrangThaiDonHang() == 1){
            trangThai = "Đã tiếp nhận đơn hàng";
        }
        else if(donHang.getTrangThaiDonHang() == 0){
            trangThai = "Đang xử lý đơn hàng";
        }

        String date = donHang.getNgayDat();
        TemporalAccessor temporal = DateTimeFormatter
                .ofPattern("yyyy-MM-dd")
                .parse(date); // use parse(date, LocalDateTime::from) to get LocalDateTime
        String output = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(temporal);

        viewHolder.textViewMaDH.setText("Mã đơn hàng: " + donHang.getId());
        viewHolder.textViewsdt.setText("Số điện thoại: " + donHang.getSdt());
        viewHolder.textViewNgayDat.setText("Ngày đặt: " +output);
        viewHolder.textViewDiaChiGiao.setText("Địa chỉ giao: " + donHang.getDiaChiGiao());
        viewHolder.textViewTrangThaiDH.setText("Trạng thái đơn hàng: " + trangThai);


        return view;
    }

    // Filter Class


}

