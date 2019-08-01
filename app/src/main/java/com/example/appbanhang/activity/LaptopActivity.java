package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LaptopAdapter;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar toolbarLt;
    ListView listViewLt;
    View footerView;
    SearchView searchViewTimKiem;

    LaptopAdapter laptopAdapter;
    MenuItem menuItem;
    public static ArrayList<SanPham> mangLt;

    int idLt = 0;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
    boolean laptop = false;

    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhXa();
            getIdLoaiSP();
            actionToolbar();
            getData(page);
            loadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int cart_count= MainActivity.mangGioHang.size();
        menuItem.setIcon(Converter.convertLayoutToImage(LaptopActivity.this,cart_count,R.drawable.ic_cart));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count= MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
         menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(LaptopActivity.this,cart_count,R.drawable.ic_cart));

        getMenuInflater().inflate(R.menu.search_view, menu);
        searchViewTimKiem = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchViewTimKiem.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), com.example.appbanhang.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void anhXa() {
        toolbarLt = (Toolbar) findViewById(R.id.toolbarlaptop);
        toolbarLt.setTitle(MainActivity.title);
        listViewLt = (ListView) findViewById(R.id.listviewlaptop);
        mangLt = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(), mangLt);
        listViewLt.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new MyHandler();
    }

    private void getIdLoaiSP() {
        idLt = getIntent().getIntExtra("idloaisp", -1);
        Log.d("giatriloaisp", idLt +"");
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarLt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDienThoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenLt = "";
                int giaLt = 0;
                String hinhAnhLt = "";
                String moTaLt = "";
                int idLt = 0;
                int soLuong=0;

                if (response != null && response.length() != 2 ){
                    listViewLt.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLt = jsonObject.getString("tensp");
                            giaLt = jsonObject.getInt("giasp");
                            hinhAnhLt = jsonObject.getString("hinhanhsp");
                            moTaLt = jsonObject.getString("motasp");
                            idLt = jsonObject.getInt("idsp");
                            soLuong = jsonObject.getInt("soluongsp");

                            mangLt.add(new SanPham(id, tenLt, giaLt, hinhAnhLt, moTaLt, idLt,soLuong));
//                            laptopAdapter.notifyDataSetChanged();
                        }
                        laptopAdapter = new LaptopAdapter(getApplicationContext(),mangLt);
                        listViewLt.setAdapter(laptopAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    limitData = true;
                    listViewLt.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
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
                param.put("idsanpham", String.valueOf(idLt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void loadMoreData() {
        listViewLt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangLt.get(i));
                laptop=true;
                startActivity(intent);
            }
        });
        listViewLt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        CheckConnection.ShowToast_Short(getApplicationContext(), s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        laptopAdapter.filter(newText.trim());
        return true;
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewLt.addFooterView(footerView);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}
