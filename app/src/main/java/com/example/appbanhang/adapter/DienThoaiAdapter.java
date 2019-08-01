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

public class DienThoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<SanPham> arrayDienThoai;
    ArrayList<SanPham> arrayList;

    public  DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
        this.arrayList = arrayList;
        this.arrayList = new ArrayList<SanPham>();
        this.arrayList.addAll(arrayDienThoai);
    }

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView textViewTenDienThoai, textViewGiaDienThoai, textViewMoTaDienThoai;
        public ImageView imageViewDienThoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.textViewTenDienThoai = (TextView) view.findViewById(R.id.textviewtendienthoai);
            viewHolder.textViewGiaDienThoai = (TextView) view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.textViewMoTaDienThoai = (TextView) view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imageViewDienThoai = (ImageView) view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.textViewTenDienThoai.setText(sanPham.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewGiaDienThoai.setText("Giá: " + decimalFormat.format(sanPham.getGiaSP()) + "đ");
        viewHolder.textViewMoTaDienThoai.setMaxLines(2);
        viewHolder.textViewMoTaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewMoTaDienThoai.setText(sanPham.getMoTaSP());
        Picasso.with(context).load(sanPham.getHinhAnhSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageViewDienThoai);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrayDienThoai.clear();
        if (charText.length() == 0) {
            arrayDienThoai.addAll(arrayList);
        } else {
            for (SanPham sp : arrayList) {
                if (sp.getTenSP().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    arrayDienThoai.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
