package com.example.appbanhang.activity;

import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DonHangAdapter;
import com.example.appbanhang.model.DonHang;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class donhangActivity extends AppCompatActivity {
    private Button buttondangxuly;
    private Button buttondatiepnhan;
    private Button buttondanggiaohang;
    private Button buttondagiao;
    private Button buttondahuy;


    Toolbar toolbarDH;
    ListView listViewDH;
    View footerView;
    TextView textViewThongBao;
    DonHangAdapter donHangAdapter;
    ArrayList<DonHang> mangDH;

    int idKH = 0;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang);
        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionToolbar();
            loadMoreData();
            getDuLieu();
            buttondangxuly.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
            getData();

        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }
    }

    private void getDuLieu() {

        buttondangxuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttondangxuly.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
                buttondatiepnhan.setBackground(null);
                buttondanggiaohang.setBackground(null);
                buttondagiao.setBackground(null);
                buttondahuy.setBackground(null);
                getData();
            }
        });
        buttondatiepnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttondatiepnhan.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
                buttondangxuly.setBackground(null);
                buttondanggiaohang.setBackground(null);
                buttondagiao.setBackground(null);
                buttondahuy.setBackground(null);
                getData1();
            }
        });
        buttondanggiaohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttondanggiaohang.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
                buttondatiepnhan.setBackground(null);
                buttondangxuly.setBackground(null);
                buttondagiao.setBackground(null);
                buttondahuy.setBackground(null);
                getData2();
            }
        });
        buttondagiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttondagiao.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
                buttondatiepnhan.setBackground(null);
                buttondangxuly.setBackground(null);
                buttondanggiaohang.setBackground(null);
                buttondahuy.setBackground(null);
                getData3();
            }
        });
        buttondahuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttondahuy.setBackground(getResources().getDrawable(R.color.btn_click_loai_don_hang));
                buttondatiepnhan.setBackground(null);
                buttondangxuly.setBackground(null);
                buttondanggiaohang.setBackground(null);
                buttondagiao.setBackground(null);
                getData4();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count = MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(donhangActivity.this, cart_count, R.drawable.ic_cart));
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


    private void loadMoreData() {
        listViewDH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), chiTietDonHangActivity.class);
                intent.putExtra("madonhang", mangDH.get(i).getId());
                intent.putExtra("donhang", mangDH.get(i));
                startActivity(intent);
            }
        });

    }

    private void getData4() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang6;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idkhachhang = 0;
                String sdt = "";
                String ngaydat = "";
                String diachigiao = "";
                int trangthaidonhang = 0;
                if (response != null && response.length() > 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        mangDH = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idkhachhang = jsonObject.getInt("idkhachhang");
                            sdt = jsonObject.getString("sdt");
                            ngaydat = jsonObject.getString("ngaydat");
                            diachigiao = jsonObject.getString("diachigiao");
                            trangthaidonhang = jsonObject.getInt("trangthaidonhang");
                            mangDH.add(new DonHang(id, idkhachhang, sdt, ngaydat, diachigiao, trangthaidonhang));
                            textViewThongBao.setVisibility(View.INVISIBLE);
                            donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDH);
                            listViewDH.setAdapter(donHangAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    textViewThongBao.setVisibility(View.VISIBLE);
                    if(mangDH.size()>0){
                        mangDH.clear();
                        donHangAdapter.notifyDataSetChanged();

                    }
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData3() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang5;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idkhachhang = 0;
                String sdt = "";
                String ngaydat = "";
                String diachigiao = "";
                int trangthaidonhang = 0;
                if (response != null && response.length() > 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        mangDH = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idkhachhang = jsonObject.getInt("idkhachhang");
                            sdt = jsonObject.getString("sdt");
                            ngaydat = jsonObject.getString("ngaydat");
                            diachigiao = jsonObject.getString("diachigiao");
                            trangthaidonhang = jsonObject.getInt("trangthaidonhang");
                            mangDH.add(new DonHang(id, idkhachhang, sdt, ngaydat, diachigiao, trangthaidonhang));
                            textViewThongBao.setVisibility(View.INVISIBLE);
                            donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDH);
                            listViewDH.setAdapter(donHangAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    textViewThongBao.setVisibility(View.VISIBLE);
                    if(mangDH.size()>0){
                        mangDH.clear();
                        donHangAdapter.notifyDataSetChanged();

                    }
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData2() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang4;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idkhachhang = 0;
                String sdt = "";
                String ngaydat = "";
                String diachigiao = "";
                int trangthaidonhang = 0;
                if (response != null && response.length() > 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        mangDH = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idkhachhang = jsonObject.getInt("idkhachhang");
                            sdt = jsonObject.getString("sdt");
                            ngaydat = jsonObject.getString("ngaydat");
                            diachigiao = jsonObject.getString("diachigiao");
                            trangthaidonhang = jsonObject.getInt("trangthaidonhang");
                            mangDH.add(new DonHang(id, idkhachhang, sdt, ngaydat, diachigiao, trangthaidonhang));
                            textViewThongBao.setVisibility(View.INVISIBLE);
                            donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDH);
                            listViewDH.setAdapter(donHangAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    textViewThongBao.setVisibility(View.VISIBLE);
                    if(mangDH.size()>0){
                        mangDH.clear();
                        donHangAdapter.notifyDataSetChanged();

                    }
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData1() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang3;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idkhachhang = 0;
                String sdt = "";
                String ngaydat = "";
                String diachigiao = "";
                int trangthaidonhang = 0;
                if (response != null && response.length() > 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        mangDH = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idkhachhang = jsonObject.getInt("idkhachhang");
                            sdt = jsonObject.getString("sdt");
                            ngaydat = jsonObject.getString("ngaydat");
                            diachigiao = jsonObject.getString("diachigiao");
                            trangthaidonhang = jsonObject.getInt("trangthaidonhang");
                            mangDH.add(new DonHang(id, idkhachhang, sdt, ngaydat, diachigiao, trangthaidonhang));
                            textViewThongBao.setVisibility(View.INVISIBLE);
                            donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDH);
                            listViewDH.setAdapter(donHangAdapter);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    textViewThongBao.setVisibility(View.VISIBLE);
                    if(mangDH.size()>0){
                        mangDH.clear();
                        donHangAdapter.notifyDataSetChanged();

                    }
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDonHang2;//+ String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                int idkhachhang = 0;
                String sdt = "";
                String ngaydat = "";
                String diachigiao = "";
                int trangthaidonhang = 0;
                if (response != null && response.length() > 2) {
                    listViewDH.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        mangDH = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            idkhachhang = jsonObject.getInt("idkhachhang");
                            sdt = jsonObject.getString("sdt");
                            ngaydat = jsonObject.getString("ngaydat");
                            diachigiao = jsonObject.getString("diachigiao");
                            trangthaidonhang = jsonObject.getInt("trangthaidonhang");
                            mangDH.add(new DonHang(id, idkhachhang, sdt, ngaydat, diachigiao, trangthaidonhang));
                            textViewThongBao.setVisibility(View.INVISIBLE);
                            donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDH);
                            listViewDH.setAdapter(donHangAdapter);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    textViewThongBao.setVisibility(View.VISIBLE);
                    if(mangDH.size()>0){
                        mangDH.clear();
                        donHangAdapter.notifyDataSetChanged();
                    }
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarDH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donhangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        toolbarDH = (Toolbar) findViewById(R.id.toolbardonhang);
        listViewDH = (ListView) findViewById(R.id.listviewdonhang);
        buttondatiepnhan = (Button) findViewById(R.id.buttondatiepnhan);
        buttondangxuly = (Button) findViewById(R.id.buttondangxuly);
        buttondanggiaohang = (Button) findViewById(R.id.buttondanggiaohang);
        buttondagiao = (Button) findViewById(R.id.buttondagiao);
        buttondahuy = (Button) findViewById(R.id.buttondahuy);
        mangDH = new ArrayList<>();

        textViewThongBao = (TextView) findViewById(R.id.textviewbao);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);

    }

}
