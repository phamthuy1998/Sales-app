package com.example.appbanhang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DienThoaiAdapter;
import com.example.appbanhang.adapter.GioHangApdapter;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHang extends AppCompatActivity {

    public ListView listViewGioHang;
    public LinearLayout layoutchitietsp;
    TextView textViewThongBao;
    public static LinearLayout layouTien;
    static TextView textViewTongTien;
    public static Button buttonThanhToan;
    public Button buttonTiepTucMua;
    Toolbar toolbarGioHang;
    public static final String TAG = chiTietDonHangActivity.class.getSimpleName();
    boolean giohang1 = false;
    GioHangApdapter gioHangApdapter;
    public Button btndelete;
    static ArrayList<SanPham> mangsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        actionToolbar();
        checkData();
        eventUltil();
        catchOnItemListView();
        eventButton();
    }

    private ArrayList<SanPham> getData() {
        final ArrayList<SanPham> mang= new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanAllSP;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenDT = "";
                int giaDT = 0;
                String hinhAnhDT = "";
                String moTaDT = "";
                int idDT = 0;
                int soLuong = 0;

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tenDT = jsonObject.getString("tensp");
                        giaDT = jsonObject.getInt("giasp");
                        hinhAnhDT = jsonObject.getString("hinhanhsp");
                        moTaDT = jsonObject.getString("motasp");
                        idDT = jsonObject.getInt("idsp");
                        soLuong = jsonObject.getInt("soluongsp");
                        mang.add(new SanPham(id, tenDT, giaDT, hinhAnhDT, moTaDT, idDT, soLuong));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                return param;
            }
        };
        requestQueue.add(stringRequest);
        return mang;
    }

    private void eventButton() {
        if (MainActivity.mangGioHang.size() == 0) {
            buttonThanhToan.setVisibility(View.GONE);
            layouTien.setVisibility(View.GONE);
        }
        buttonTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        buttonThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.isLogin == false) {
                    MainActivity.thanhtoan = true;
                }
                if (MainActivity.mangGioHang.size() > 0) {
                    boolean pass = true;
                    String mangHetHang = "";
                    String mangKhongDu = "";


                    System.out.println("size mang: " + mangsp.size());

                    for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                        for (int j = 0; j < mangsp.size(); j++) {
                            if (MainActivity.mangGioHang.get(i).getIdSP() == mangsp.get(j).getId() && mangsp.get(j).getSoLuongSP() == 0) {
                                pass = false;
                                mangHetHang += MainActivity.mangGioHang.get(i).getTenSP() + "\n";

                            }else if (MainActivity.mangGioHang.get(i).getIdSP() == mangsp.get(j).getId() && MainActivity.mangGioHang.get(i).getSoLuongSP() > mangsp.get(j).getSoLuongSP()) {
                                pass = false;
                                mangKhongDu += MainActivity.mangGioHang.get(i).getTenSP() + " chỉ còn: " + mangsp.get(i).getSoLuongSP() +" sản phẩm\n";

                            }
                        }

                    }
                    if(mangHetHang.length()!=0){
                        mangHetHang +=" đã hết hàng";
                    }
                    if (pass == true) {
                        if (MainActivity.isLogin == true) {
                            Intent intent = new Intent(getApplicationContext(), ThongTinKhachHang.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    } else {

                        if (mangHetHang.length() != 0|| mangKhongDu.length()!=0) {
                            android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(GioHang.this);
                            dialog.setMessage( mangHetHang +"\n"+ mangKhongDu);
//                            if (mangKhongDu.length() != 0) {
//                                dialog.setMessage("Sản phẩm " + mangHetHang + " đã hết hàng" + "\n" + "Sản phẩm " + mangKhongDu + " không đủ số lượng");
//                            }
                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(GioHang.this, GioHang.class);
                                    startActivity(intent);
                                }
                            });
                            dialog.create().show();
                        }
//
                    }


                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void catchOnItemListView() {
        listViewGioHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int i, long l) {
                giohang1 = false;
                getSanPhamClick(MainActivity.mangGioHang.get(i).getIdSP());

            }
        });
    }

    private void getSanPhamClick(final int idSP) {
        StringRequest registerRequest = new StringRequest(Request.Method.POST, Server.duongDanSanPham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        int id = 0;
                        String tenDT = "";
                        int giaDT = 0;
                        String hinhAnhDT = "";
                        String moTaDT = "";
                        int idDT = 0;
                        int soluong = 0;

                        try {
                            SanPham sp;
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            id = jsonObject.getInt("id");
                            tenDT = jsonObject.getString("tensp");
                            giaDT = jsonObject.getInt("giasp");
                            hinhAnhDT = jsonObject.getString("hinhanhsp");
                            moTaDT = jsonObject.getString("motasp");
                            idDT = jsonObject.getInt("idsp");
                            soluong = jsonObject.getInt("soluongsp");
                            sp = new SanPham(id, tenDT, giaDT, hinhAnhDT, moTaDT, idDT, soluong);

                            Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                            intent.putExtra("thongtinsanpham", sp);
                            startActivity(intent);

                            System.out.println("ten san pham:" + sp.getTenSP());
                        } catch (JSONException error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            System.out.println("lỗi gì đó");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
//                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idsp", String.valueOf(idSP));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(registerRequest);
    }


    public static void eventUltil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
            tongTien += MainActivity.mangGioHang.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewTongTien.setText(decimalFormat.format(tongTien) + "đ");
    }

    private void checkData() {
        if (MainActivity.mangGioHang.size() <= 0) {
            gioHangApdapter.notifyDataSetChanged();
            textViewThongBao.setVisibility(View.VISIBLE);
            listViewGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangApdapter.notifyDataSetChanged();
            textViewThongBao.setVisibility(View.INVISIBLE);
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThongTinKhachHang.btntrove == true) {
                    ThongTinKhachHang.btntrove = false;
                    Intent intent = new Intent(GioHang.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }

            }
        });
    }

    private void anhXa() {
        layouTien = (LinearLayout) findViewById(R.id.textvewtien);
        layoutchitietsp = (LinearLayout) findViewById(R.id.layoutchitietsp);
        listViewGioHang = (ListView) findViewById(R.id.listviewgiohang);
        textViewThongBao = (TextView) findViewById(R.id.textviewthongbao);
        textViewTongTien = (TextView) findViewById(R.id.textviewtongtien);
        buttonThanhToan = (Button) findViewById(R.id.buttonthanhtoangiohang);
        buttonTiepTucMua = (Button) findViewById(R.id.buttontieptucmuahang);
        btndelete = (Button) findViewById(R.id.buttonDelete);
        toolbarGioHang = (Toolbar) findViewById(R.id.toolbargiohang);
        gioHangApdapter = new GioHangApdapter(GioHang.this, MainActivity.mangGioHang);
        listViewGioHang.setAdapter(gioHangApdapter);
        mangsp = getData();
    }
}
