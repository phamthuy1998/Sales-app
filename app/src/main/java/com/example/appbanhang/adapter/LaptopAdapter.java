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
import com.example.appbanhang.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class LaptopAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayLaptop;
    ArrayList<SanPham> arrayList;


    public LaptopAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayLaptop = arrayDienThoai;
        this.arrayList = arrayList;
        this.arrayList = new ArrayList<SanPham>();
        this.arrayList.addAll(arrayDienThoai);
    }

    @Override
    public int getCount() {
        return arrayLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView textViewTenLaptop, textViewGiaLaptop, textViewMoTaLaptop;
        public ImageView imageViewLaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.textViewTenLaptop = (TextView) view.findViewById(R.id.textviewtenlaptop);
            viewHolder.textViewGiaLaptop = (TextView) view.findViewById(R.id.textviewgialaptop);
            viewHolder.textViewMoTaLaptop = (TextView) view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imageViewLaptop = (ImageView) view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.textViewTenLaptop.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewGiaLaptop.setText("Giá: "+ decimalFormat.format(sanPham.getGiaSP()) +"đ");
        viewHolder.textViewMoTaLaptop.setMaxLines(2);
        viewHolder.textViewMoTaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewMoTaLaptop.setText(sanPham.getMoTaSP());
        Picasso.with(context).load(sanPham.getHinhAnhSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageViewLaptop);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayLaptop.clear();
        if (charText.length() == 0) {
            arrayLaptop.addAll(arrayList);
        } else {
            for (SanPham sp : arrayList) {
                if (sp.getTenSP().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    arrayLaptop.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
