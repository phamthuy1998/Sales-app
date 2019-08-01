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
import com.example.appbanhang.adapter.DienThoaiAdapter;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public Toolbar toolbarDT;
    ListView listViewDT;
    View footerView;
    SearchView searchViewTimKiem;
    MenuItem menuItem;

    DienThoaiAdapter dienThoaiAdapter;
    public static ArrayList<SanPham> mangDT;

    int idDT = 0;
    int page = 1;
    boolean isLoading = false;
    boolean limitData = false;
    public static boolean dienthoai = false;
    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            getIdLoaiSP();
            actionToolbar();
            getData(page);
            loadMoreData();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        int cart_count= MainActivity.mangGioHang.size();
        menuItem.setIcon(Converter.convertLayoutToImage(DienThoaiActivity.this,cart_count,R.drawable.ic_cart));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count= MainActivity.mangGioHang.size();
        
        getMenuInflater().inflate(R.menu.menu, menu);
         menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(DienThoaiActivity.this,cart_count,R.drawable.ic_cart));

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

    private void loadMoreData() {
        listViewDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                dienthoai=true;
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangDT.get(i));
                startActivity(intent);
            }
        });
        listViewDT.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanDienThoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenDT = "";
                int giaDT = 0;
                String hinhAnhDT = "";
                String moTaDT = "";
                int idDT = 0;
                int soLuong=0;

                if (response != null && response.length() != 2 ){
                    listViewDT.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenDT = jsonObject.getString("tensp");
                            giaDT = jsonObject.getInt("giasp");
                            hinhAnhDT = jsonObject.getString("hinhanhsp");
                            moTaDT = jsonObject.getString("motasp");
                            idDT = jsonObject.getInt("idsp");
                            soLuong = jsonObject.getInt("soluongsp");

                            mangDT.add(new SanPham(id, tenDT, giaDT, hinhAnhDT, moTaDT, idDT,soLuong));
//                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),mangDT);
                        listViewDT.setAdapter(dienThoaiAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else{
                    limitData = true;
                    listViewDT.removeFooterView(footerView);
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
                param.put("idsanpham", String.valueOf(idDT));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarDT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getIdLoaiSP() {
        idDT = getIntent().getIntExtra("idloaisp", -1);
        Log.d("giatriloaisp", idDT +"");
    }

    private void anhXa(){
        toolbarDT = (Toolbar) findViewById(R.id.toolbardienthoai);
        toolbarDT.setTitle(MainActivity.title);
        listViewDT = (ListView) findViewById(R.id.listviewdienthoai);

        mangDT = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        myHandler = new MyHandler();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        CheckConnection.ShowToast_Short(getApplicationContext(), s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        dienThoaiAdapter.filter(newText.trim());
        return true;
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewDT.addFooterView(footerView);
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
