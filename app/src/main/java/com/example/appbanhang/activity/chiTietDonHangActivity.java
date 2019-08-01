package com.example.appbanhang.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.appbanhang.adapter.ChiTietDonHangAdapter;
import com.example.appbanhang.adapter.DienThoaiAdapter;
import com.example.appbanhang.model.Account;
import com.example.appbanhang.model.ChiTietDonHang;
import com.example.appbanhang.model.DonHang;
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

public class chiTietDonHangActivity extends AppCompatActivity {
    ListView listViewDH;
    Button btnhuyDonHang;
    TextView textViewTongTien;
    TextView textViewTinhTrangDH;
    Toolbar toolbarChiTietDH;
    private ProgressDialog pDialog;

    public static final String TAG = chiTietDonHangActivity.class.getSimpleName();
    ChiTietDonHangAdapter chiTietDonHangAdapter;
    ArrayList<ChiTietDonHang> mangChiTietDH;
    boolean chitietdonhang=false;
    SanPham sp;

    View footerView;

    int maDH = 0;
    int idDonHang;
    int trangThaiDH;
    DonHang donHang;
    int idSP = 0;
    boolean isLoading = false;
    boolean limitData = false;


//    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            huyDonHang();
            actionToolbar();
            getData();

             action();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }
    }

    private void huyDonHang() {
        idDonHang = donHang.getId();
        trangThaiDH = donHang.getTrangThaiDonHang();
        String trangThai = "";

        if (trangThaiDH == 4) {
            trangThai = "Đã hủy";
        }else if(trangThaiDH == 3){
            trangThai = "Giao hàng thành công" ;
        }
        else if(trangThaiDH == 2){
            trangThai = "Đang giao hàng";
        }
        else if(trangThaiDH == 1){
            trangThai = "Đã tiếp nhận đơn hàng";
        }
        else if(trangThaiDH == 0){
            trangThai = "Đang xử lý";
        }
        textViewTinhTrangDH.setText("Trạng thái đơn hàng: "+trangThai);
        if (trangThaiDH == 0 || trangThaiDH == 1) {
            btnhuyDonHang.setVisibility(View.VISIBLE);
            btnhuyDonHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(chiTietDonHangActivity.this);
                    dialog.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này không?");
                    dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            huydonHang(idDonHang);
                            finish();
                        }
                    });
                    dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();

                        }

                    });
                    dialog.create().show();


                    //chiTietDonHangAdapter.notifyDataSetChanged();
                }
            });
        } 
        else {
            btnhuyDonHang.setVisibility(View.INVISIBLE);
        }

    }

    private void huydonHang(final int id) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang lưu...");
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, Server.duongDanHuyDonHang,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        String message = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                message = jsonObject.getString("message");
                                Toast.makeText(chiTietDonHangActivity.this, message, Toast.LENGTH_LONG).show();
                                System.out.println("Hủy thành công");

                                //Start LoginActivity
                                Toast.makeText(chiTietDonHangActivity.this, "Đã lưu thay đổi", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(chiTietDonHangActivity.this, donhangActivity.class);
                                startActivity(intent);

                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(chiTietDonHangActivity.this, message, Toast.LENGTH_LONG).show();
                                System.out.println("chỉnh sửa không thành công");
                            }
                        } catch (JSONException error) {
                            VolleyLog.d(TAG, "Error: " + error.getMessage());
                            System.out.println("lỗi gì đó");
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(registerRequest);

    }


    public void eventUltil() {
        long tongTien = 0;
        for (int i = 0; i < mangChiTietDH.size(); i++) {
            tongTien += mangChiTietDH.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewTongTien.setText(decimalFormat.format(tongTien) + "đ");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count= MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(chiTietDonHangActivity.this,cart_count,R.drawable.ic_cart));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void action() {
        listViewDH.setOnItemClickListener(  new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                SanPham sanPham= new SanPham();

                idSP = mangChiTietDH.get(i).getIdSanPham();
                System.out.println("ma sản phẩm :" + idSP);
                chitietdonhang =true;
                 getSanPhamClick();

            }
        });

    }

    private void getSanPhamClick() {
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
                        int soluong=0;

                        try {
                            System.out.println("Thuy khugn : " );
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            id = jsonObject.getInt("id");
                            tenDT = jsonObject.getString("tensp");
                            giaDT = jsonObject.getInt("giasp");
                            hinhAnhDT = jsonObject.getString("hinhanhsp");
                            moTaDT = jsonObject.getString("motasp");
                            idDT = jsonObject.getInt("idsp");

                            soluong= jsonObject.getInt("soluongsp");
                            sp = new SanPham(id, tenDT, giaDT, hinhAnhDT, moTaDT, idDT,soluong);

                            Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                            intent.putExtra("thongtinsanpham", sp);
                            startActivity(intent);

                            System.out.println("ten san pham:"+sp.getTenSP());
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

    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang1;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idDonHang = 0;
                int idSanPham = 0;
                String tenSanPham = "";
                Integer giaSP = 0;
                Integer soLuongSP = 0;
                String hinhAnhCT = "";
                if (response != null && response.length() != 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idDonHang = jsonObject.getInt("iddonhang");
                            idSanPham = jsonObject.getInt("idsanpham");
                            tenSanPham = jsonObject.getString("tensanpham");
                            giaSP = jsonObject.getInt("giasp");
                            soLuongSP = jsonObject.getInt("slsp");
                            hinhAnhCT = jsonObject.getString("hasp");
                            mangChiTietDH.add(new ChiTietDonHang(id, idDonHang, idSanPham, tenSanPham, giaSP, soLuongSP, hinhAnhCT));
                            if (mangChiTietDH.size() > 0) {
                                eventUltil();
                            }
                        }
                        chiTietDonHangAdapter = new ChiTietDonHangAdapter(getApplicationContext(), mangChiTietDH);
                        listViewDH.setAdapter(chiTietDonHangAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    limitData = true;
                    listViewDH.removeFooterView(footerView);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("madonhang", String.valueOf(maDH));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarChiTietDH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietDH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void anhXa() {
        btnhuyDonHang = (Button) findViewById(R.id.buttonhuydonhang);
        btnhuyDonHang.setVisibility(View.INVISIBLE);
        toolbarChiTietDH = (Toolbar) findViewById(R.id.toolbarchitietdonhang);
        listViewDH = (ListView) findViewById(R.id.listviewchitietdonhang);
        textViewTongTien = (TextView) findViewById(R.id.textviewtongtienchitietdonhang);
        textViewTinhTrangDH=(TextView) findViewById(R.id.textviewtinhtrangdh);
        mangChiTietDH = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        maDH = (int) getIntent().getSerializableExtra("madonhang");
        donHang = (DonHang) getIntent().getSerializableExtra("donhang");
        sp = new SanPham();

    }

}