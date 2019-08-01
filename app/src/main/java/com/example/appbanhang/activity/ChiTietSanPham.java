package com.example.appbanhang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChiTiet;
    ImageView imageViewChiTiet;
    TextView textViewTen, textViewGia, textViewMoTa, textViewSoLuong;
    public Button buttonMinus, buttonValues, buttonPlus;
    Button buttonDatMua;


    Button buttonThemGioHang;

    int id = 0;
    String tenChiTiet = "";
    int giaChiTiet = 0;
    String hinhAnhChiTiet = "";
    String moTaChiTiet = "";
    int idChiTiet = 0;
    int soLuongSanPham = 0;
    boolean chitiet=false;

    SanPham sanPham;
    MenuItem menuItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhXa();
        actionToolbar();
        getInformation();
        eventButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count= MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
        menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(ChiTietSanPham.this,cart_count,R.drawable.ic_cart));
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

    private void eventButton() {


        if (soLuongSanPham == 0) {
            buttonValues.setText("0");
            buttonMinus.setVisibility(View.INVISIBLE);
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChiTietSanPham.this);
                    dialog.setMessage("Sản phẩm đã hết hàng!");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create().show();
                }
            });
        } else {
            buttonValues.setText("1");
            int sl = Integer.parseInt(buttonValues.getText().toString());

            if (sl >= 10 || sl >= soLuongSanPham) {
                buttonPlus.setVisibility(View.INVISIBLE);
                buttonMinus.setVisibility(View.VISIBLE);
            } else if (sl <= 1) {
                buttonMinus.setVisibility(View.INVISIBLE);
                buttonPlus.setVisibility(View.VISIBLE);
            } else if (sl >= 1) {
                buttonMinus.setVisibility(View.VISIBLE);
                buttonPlus.setVisibility(View.VISIBLE);
            }

            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int soLuongMoiNhat = Integer.parseInt(buttonValues.getText().toString()) - 1;
                    if (soLuongMoiNhat < 2) {
                        buttonPlus.setVisibility(View.VISIBLE);
                        buttonMinus.setVisibility(View.INVISIBLE);
                        buttonValues.setText(String.valueOf(soLuongMoiNhat));
                    } else {
                        buttonPlus.setVisibility(View.VISIBLE);
                        buttonMinus.setVisibility(View.VISIBLE);
                        buttonValues.setText(String.valueOf(soLuongMoiNhat));
                    }

                }
            });
            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int soLuongMoiNhat = Integer.parseInt(buttonValues.getText().toString()) + 1;
                    if (soLuongMoiNhat > 9 || soLuongMoiNhat >= soLuongSanPham) {
                        buttonPlus.setVisibility(View.INVISIBLE);
                        buttonMinus.setVisibility(View.VISIBLE);
                        buttonValues.setText(String.valueOf(soLuongMoiNhat));
                    } else {
                        buttonPlus.setVisibility(View.VISIBLE);
                        buttonMinus.setVisibility(View.VISIBLE);
                        buttonValues.setText(String.valueOf(soLuongMoiNhat));
                    }
                }
            });

        }


        if (soLuongSanPham > 0) {
            buttonDatMua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MainActivity.isLogin==true){
                        int soLuong = Integer.parseInt(buttonValues.getText().toString());
                        themGioHang(id, soLuong);
                    }
                    if (MainActivity.mangGioHang.size() > 0) {
                        int sl = Integer.parseInt(buttonValues.getText().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                            if (MainActivity.mangGioHang.get(i).getIdSP() == id) {
                                MainActivity.mangGioHang.get(i).setSoLuongSP(MainActivity.mangGioHang.get(i).getSoLuongSP() + sl);
                                if (MainActivity.mangGioHang.get(i).getSoLuongSP() >= 10) {
                                    MainActivity.mangGioHang.get(i).setSoLuongSP(10);
                                }
                                MainActivity.mangGioHang.get(i).setGiaSP(giaChiTiet * MainActivity.mangGioHang.get(i).getSoLuongSP());
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            int soLuong = Integer.parseInt(buttonValues.getText().toString());
                            long giaMoi = soLuong * giaChiTiet;
                            MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                        }


                    } else {
                        int soLuong = Integer.parseInt(buttonValues.getText().toString());
                        long giaMoi = soLuong * giaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                    }
                    finish();
                    startActivity(getIntent());

                    Intent intent = new Intent(getApplicationContext(), com.example.appbanhang.activity.GioHang.class);
                    startActivity(intent);

                }
            });
            buttonThemGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MainActivity.isLogin==true){
                        int soLuong = Integer.parseInt(buttonValues.getText().toString());
                        themGioHang(id, soLuong);
                    }
                    if (MainActivity.mangGioHang.size() > 0) {
                        int sl = Integer.parseInt(buttonValues.getText().toString());
                        boolean exists = false;
                        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                            if (MainActivity.mangGioHang.get(i).getIdSP() == id) {
                                MainActivity.mangGioHang.get(i).setSoLuongSP(MainActivity.mangGioHang.get(i).getSoLuongSP() + sl);
                                if (MainActivity.mangGioHang.get(i).getSoLuongSP() >= 10) {
                                    MainActivity.mangGioHang.get(i).setSoLuongSP(10);
                                }
                                MainActivity.mangGioHang.get(i).setGiaSP(giaChiTiet * MainActivity.mangGioHang.get(i).getSoLuongSP());
                                exists = true;
                            }
                        }
                        if (exists == false) {
                            int soLuong = Integer.parseInt(buttonValues.getText().toString());
                            long giaMoi = soLuong * giaChiTiet;
                            MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                        }
                    } else {
                        int soLuong = Integer.parseInt(buttonValues.getText().toString());
                        long giaMoi = soLuong * giaChiTiet;
                        MainActivity.mangGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                    }

                    Toast.makeText(ChiTietSanPham.this, "Bạn đã thêm giỏ hàng thành công", Toast.LENGTH_LONG).show();
                    menuItem.setIcon(Converter.convertLayoutToImage(ChiTietSanPham.this,MainActivity.mangGioHang.size(),R.drawable.ic_cart));
                   
                }
            });


        } else {
            buttonDatMua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChiTietSanPham.this);
                    dialog.setMessage("Sản phẩm đã hết hàng!");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    dialog.create().show();
                }
            });
            buttonThemGioHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChiTietSanPham.this);
                    dialog.setMessage("Sản phẩm đã hết hàng!");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create().show();
                }
            });
        }
    }
    private void themGioHang(final int idsanpham, final int soLuong) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanThemGioHang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        message = jsonObject.getString("message");
                        Toast.makeText(ChiTietSanPham.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        message = jsonObject.getString("message");
                        Toast.makeText(ChiTietSanPham.this, message, Toast.LENGTH_LONG).show();
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
                param.put("soluong", String.valueOf(soLuong));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void getInformation() {

        sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        tenChiTiet = sanPham.getTenSP();
        giaChiTiet = sanPham.getGiaSP();
        hinhAnhChiTiet = sanPham.getHinhAnhSP();
        moTaChiTiet = sanPham.getMoTaSP();
        idChiTiet = sanPham.getIdSP();
        soLuongSanPham = sanPham.getSoLuongSP();
        textViewTen.setText(tenChiTiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewGia.setText("Giá: " + decimalFormat.format(giaChiTiet) + "đ");
        textViewMoTa.setText(moTaChiTiet);
        if (soLuongSanPham == 0) {
            textViewSoLuong.setText("Hết hàng");
        } else {
            textViewSoLuong.setText(String.valueOf(soLuongSanPham) + " sản phẩm sẵn có");
        }


        Picasso.with(getApplicationContext()).load(hinhAnhChiTiet)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(imageViewChiTiet);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void anhXa() {
        toolbarChiTiet = (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imageViewChiTiet = (ImageView) findViewById(R.id.imageviewchitietsanpham);
        textViewTen = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        textViewGia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        textViewMoTa = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        textViewSoLuong = (TextView) findViewById(R.id.textviewsoluongsanpham);
        buttonMinus = (Button) findViewById(R.id.buttonminusSP);
        buttonValues = (Button) findViewById(R.id.buttonvaluesSP);
        buttonPlus = (Button) findViewById(R.id.buttonplusSP);

        buttonDatMua = (Button) findViewById(R.id.buttondatmua);
        buttonThemGioHang = (Button) findViewById(R.id.buttonGioHang);

    }
}
