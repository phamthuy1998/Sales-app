package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class  GioHangApdapter extends BaseAdapter {

    Context context;
    ArrayList<GioHang> arrayGioHang;

    public GioHangApdapter(Context context, ArrayList<GioHang> arrayGioHang) {
        this.context = context;
        this.arrayGioHang = arrayGioHang;
    }

    @Override
    public int getCount() {
        return arrayGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView textViewTenGioHang, textViewGiaGioHang;
        public ImageView imageViewGioHang;
        public Button buttonMinus, buttonValues, buttonPlus;
        public Button buttonDelete;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.textViewTenGioHang = (TextView) view.findViewById(R.id.textviewtengiohang);
            viewHolder.textViewGiaGioHang = (TextView) view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imageViewGioHang = (ImageView) view.findViewById(R.id.imageviewgiohang);
            viewHolder.buttonMinus = (Button) view.findViewById(R.id.buttonminus);
            viewHolder.buttonValues = (Button) view.findViewById(R.id.buttonvalues);
            viewHolder.buttonPlus = (Button) view.findViewById(R.id.buttonplus);
            viewHolder.buttonDelete = (Button) view.findViewById(R.id.buttonDelete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.textViewTenGioHang.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewGiaGioHang.setText(decimalFormat.format(gioHang.getGiaSP()) + "đ");
        Picasso.with(context).load(gioHang.getHinhAnhSP())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageViewGioHang);
        viewHolder.buttonValues.setText(gioHang.getSoLuongSP() + "");
        int sl = Integer.parseInt(viewHolder.buttonValues.getText().toString());
        if (sl >= 10) {
            viewHolder.buttonPlus.setVisibility(View.INVISIBLE);
            viewHolder.buttonMinus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewHolder.buttonMinus.setVisibility(View.INVISIBLE);
            viewHolder.buttonPlus.setVisibility(View.VISIBLE);
        } else if (sl >= 1) {
            viewHolder.buttonMinus.setVisibility(View.VISIBLE);
            viewHolder.buttonPlus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuongMoiNhat = Integer.parseInt(finalViewHolder.buttonValues.getText().toString()) + 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(i).getSoLuongSP();
                long giaHienTai = MainActivity.mangGioHang.get(i).getGiaSP();
                MainActivity.mangGioHang.get(i).setSoLuongSP(soLuongMoiNhat);
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                MainActivity.mangGioHang.get(i).setGiaSP(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + "đ");
                com.example.appbanhang.activity.GioHang.eventUltil();
                if (soLuongMoiNhat > 9) {
                    finalViewHolder.buttonPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(soLuongMoiNhat));
                } else {
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(soLuongMoiNhat));
                }
            }
        });
        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMoiNhat = Integer.parseInt(finalViewHolder1.buttonValues.getText().toString()) - 1;
                int soLuongHienTai = MainActivity.mangGioHang.get(i).getSoLuongSP();
                long giaHienTai = MainActivity.mangGioHang.get(i).getGiaSP();
                MainActivity.mangGioHang.get(i).setSoLuongSP(soLuongMoiNhat);
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                MainActivity.mangGioHang.get(i).setGiaSP(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.textViewGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + "đ");
                com.example.appbanhang.activity.GioHang.eventUltil();
                if (soLuongMoiNhat < 2) {
                    finalViewHolder1.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder1.buttonMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder1.buttonValues.setText(String.valueOf(soLuongMoiNhat));
                } else {
                    finalViewHolder1.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder1.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder1.buttonValues.setText(String.valueOf(soLuongMoiNhat));
                }
            }
        });
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("click xóa giỏ hàng");
                int soLuongMoiNhat = 0;
                int soLuongHienTai = MainActivity.mangGioHang.get(i).getSoLuongSP();
                long giaHienTai = MainActivity.mangGioHang.get(i).getGiaSP();
                MainActivity.mangGioHang.get(i).setSoLuongSP(soLuongMoiNhat);
                long giaMoiNhat = (giaHienTai * soLuongMoiNhat) / soLuongHienTai;
                MainActivity.mangGioHang.get(i).setGiaSP(giaMoiNhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewGiaGioHang.setText(decimalFormat.format(giaMoiNhat) + "đ");
                com.example.appbanhang.activity.GioHang.eventUltil();
                if(MainActivity.isLogin==true){

                    xoaGioHang(MainActivity.mangGioHang.get(i).getIdSP());
                }

                MainActivity.mangGioHang.remove(i);
                if(MainActivity.mangGioHang.size() == 0){
                    com.example.appbanhang.activity.GioHang.buttonThanhToan.setVisibility(View.GONE);
                    com.example.appbanhang.activity.GioHang.layouTien.setVisibility(View.GONE);
                }
               GioHangApdapter.this.notifyDataSetChanged();
            }

        });
        return view;
    }
    public  void xoaGioHang(final int idsanpham) {
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String duongDan = Server.duongDanXoaSanPhamGioHang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        message = jsonObject.getString("message");
                    } else {
                        message = jsonObject.getString("message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
               param.put("idkhachhang", String.valueOf(MainActivity.account.getId()));
                param.put("idsanpham", String.valueOf(idsanpham));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

}
