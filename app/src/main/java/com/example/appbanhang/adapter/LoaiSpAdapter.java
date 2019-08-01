package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSpAdapter extends BaseAdapter {

    ArrayList<LoaiSP> arrayListLoaiSP;
    Context context;

    public LoaiSpAdapter(ArrayList<LoaiSP> arrayListLoaiSP, Context context) {
        this.arrayListLoaiSP = arrayListLoaiSP;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLoaiSP.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLoaiSP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class viewHolder{
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder viewHolder = null;
        if (view == null){
            viewHolder =  new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.txtTenLoaiSP = (TextView) view.findViewById(R.id.textviewloaisp);
            viewHolder.imgLoaiSP = (ImageView) view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (viewHolder) view.getTag();
        }
        LoaiSP loaisp = (LoaiSP) getItem(i);
        viewHolder.txtTenLoaiSP.setText(loaisp.getTenLoaiSP());
        Picasso.with(context).load(loaisp.getHinhAnhLoaiSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imgLoaiSP);
        return view;
    }
}
