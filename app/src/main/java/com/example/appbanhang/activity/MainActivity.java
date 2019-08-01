package com.example.appbanhang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaiSpAdapter;
import com.example.appbanhang.adapter.SanPhamAdapter;
import com.example.appbanhang.model.Account;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.LoaiSP;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    int id = 0;
    String tenLoaiSP = "";
    String hinhAnhLoaiSP = "";

    public static ArrayList<LoaiSP> mangLoaiSP;
    LoaiSpAdapter loaiSpAdapter;

    ArrayList<SanPham> mangSP;
    SanPhamAdapter sanPhamAdapter;
    MenuItem menuItem;


    public static ArrayList<GioHang> mangGioHang;
    public static boolean isLogin = false;
    public static boolean xemDonHang = false;

    public static int idLogin = 0;
    public static boolean forgotpass = false;
    public static Account account;
    public static boolean thanhtoan = false;
    public static String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionToolBar();
            actionViewFlipper();
            getDuLieuLoaiSP();
            getDuLieuDonHang();
            catchOnItemListView();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        int cart_count= MainActivity.mangGioHang.size();
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this,cart_count,R.drawable.ic_cart));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count = mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
         menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_cart));
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

    private void catchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int check = 2;
                switch (i) {

                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            if (isLogin == false) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, ResultLogin.class);
                                startActivity(intent);
                            }

                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            title = mangLoaiSP.get(i).getTenLoaiSP();
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisp", mangLoaiSP.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            title = mangLoaiSP.get(i).getTenLoaiSP();
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisp", mangLoaiSP.get(i).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            if (isLogin == true) {
                                xemDonHang = true;
                                Intent intent = new Intent(MainActivity.this, donhangActivity.class);
                                startActivity(intent);
                            } else {
                                xemDonHang = true;
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn phải đăng nhập trước");
                            }

                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void getDuLieuDonHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanSanPhamMoiNhat,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            int id = 0;
                            String tenSP = "";
                            Integer giaSP = 0;
                            String hinhAnhSP = "";
                            String moTaSP = "";
                            int idSP = 0;
                            int soLuong = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    tenSP = jsonObject.getString("tensp");
                                    giaSP = jsonObject.getInt("giasp");
                                    hinhAnhSP = jsonObject.getString("hinhanhsp");
                                    moTaSP = jsonObject.getString("motasp");
                                    idSP = jsonObject.getInt("idsp");
                                    soLuong = jsonObject.getInt("soluongsp");
                                    mangSP.add(new SanPham(id, tenSP, giaSP, hinhAnhSP, moTaSP, idSP, soLuong));
                                    sanPhamAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                if (response != null) {
                    int i;
                    for (i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSP = jsonObject.getString("tenloaisp");
                            hinhAnhLoaiSP = jsonObject.getString("hinhanhloaisp");
                            mangLoaiSP.add(new LoaiSP(id, tenLoaiSP, hinhAnhLoaiSP));
                            loaiSpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangLoaiSP.add(i + 2, new LoaiSP(0, "Quản lý đơn hàng", "https://cdn1.iconfinder.com/data/icons/hawcons/32/698653-icon-137-document-certificate-512.png"));
                    mangLoaiSP.add(i + 3, new LoaiSP(0, "Liên Hệ", "http://www.farma-alimenta.com/wp-content/uploads/2018/03/tel.png"));
                    mangLoaiSP.add(i + 4, new LoaiSP(0, "Thông Tin", "https://midwesterninsurance.com/wp-content/uploads/Info-icon.png"));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void actionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/22_02_2019_10_32_57_Galaxy-S10-800-300.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/09_02_2019_22_58_10_iphone-master-800-300.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/24_02_2019_22_27_39_M20-800-300.png");
        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }


    private void anhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mangLoaiSP = new ArrayList<>();
        if (isLogin == false) {
            mangLoaiSP.add(0, new LoaiSP(0, "Tài khoản                          Đăng nhập, đăng xuất", "https://png.pngtree.com/svg/20161201/user_management_25775.png"));
        } else {

            String ten = account.getUserName();
            String mail = account.getEmail();
            LoaiSP loaiSP = new LoaiSP(0, ten + "                        " + mail, "https://png.pngtree.com/svg/20161201/user_management_25775.png");
            mangLoaiSP.add(0, loaiSP);
        }
        mangLoaiSP.add(1, new LoaiSP(0, "Trang chính", "https://www.materialui.co/materialIcons/action/home_grey_192x192.png"));
        loaiSpAdapter = new LoaiSpAdapter(mangLoaiSP, getApplicationContext());
        listView.setAdapter(loaiSpAdapter);

        mangSP = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangSP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanPhamAdapter);

        if (mangGioHang != null && isLogin == true) {
            mangGioHang.clear();
            getDuLieugioHang();
        } else if(mangGioHang==null){
            mangGioHang = new ArrayList<>();
        }
    }

    private void getDuLieugioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanGetGioHang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenSP = "";
                int giaSP = 0;
                String hinhAnhSP = "";
                int soLuong = 0;

                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (MainActivity.mangGioHang.size() > 0) {
                            MainActivity.mangGioHang.clear();
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenSP = jsonObject.getString("ten");
                            giaSP = jsonObject.getInt("gia");
                            hinhAnhSP = jsonObject.getString("hinhanh");
                            soLuong = jsonObject.getInt("soluong");


                            boolean exists = false;
                            for (int j = 0; j < MainActivity.mangGioHang.size(); j++) {
                                if (MainActivity.mangGioHang.get(j).getIdSP() == id) {
                                    MainActivity.mangGioHang.get(j).setSoLuongSP(MainActivity.mangGioHang.get(j).getSoLuongSP() + soLuong);
                                    if (MainActivity.mangGioHang.get(j).getSoLuongSP() >= 10) {
                                        MainActivity.mangGioHang.get(j).setSoLuongSP(10);
                                    }
                                    MainActivity.mangGioHang.get(j).setGiaSP(giaSP * MainActivity.mangGioHang.get(j).getSoLuongSP());
                                    exists = true;
                                }
                            }
                            if (exists == false) {
                                long giaMoi = soLuong * giaSP;
                                MainActivity.mangGioHang.add(new GioHang(id, tenSP, giaMoi, hinhAnhSP, soLuong));
                            }
                            System.out.println("thêm vào mảng giỏ hàng: " + MainActivity.mangGioHang.size());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
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
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


}
