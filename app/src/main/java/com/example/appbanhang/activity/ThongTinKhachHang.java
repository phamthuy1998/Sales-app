package com.example.appbanhang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThongTinKhachHang extends AppCompatActivity {
    private Toolbar tbdiachi;
    EditText editDiaChiGiao, editTextSDT;
    Button buttonXacNhan;
    private TextView textviewdiachimacdinh;
    private TextView textviewsodienthoaimacdinh;
    public static boolean btntrove = false;
    private LinearLayout layoutdiachigiaohang;
    private LinearLayout layoutdiachimacdinh;

    private RadioGroup radioGroupChondiachi;
    private RadioButton radioButtonMacdinh;
    private RadioButton radioButtonTuyChinh;
    private int checkedRadioId;
    private String diachi;
    private String sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        anhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            eventButton();
            actionBar();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void actionBar() {
        setSupportActionBar(tbdiachi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbdiachi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btntrove = true;

                Intent intent = new Intent(ThongTinKhachHang.this, GioHang.class);
                startActivity(intent);
            }
        });
    }

    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            editTextSDT.setError("số điện thoại không đúng!");
        } else if (number.length() == 10) {
            if (number.substring(0, 2).equals("09") || number.substring(0, 2).equals("03") || number.substring(0, 2).equals("08") || number.substring(0, 2).equals("07") || number.substring(0, 2).equals("05")) {
                return true;
            } else {
                editTextSDT.setError("số điện thoại không đúng!");
            }
        } else {
            editTextSDT.setError("số điện thoại không đúng!");
        }
        return false;
    }

    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {


        checkedRadioId = group.getCheckedRadioButtonId();

        if (checkedRadioId == R.id.radioButton_diachimacdinh) {
            layoutdiachigiaohang.setVisibility(View.GONE);
            layoutdiachimacdinh.setVisibility(View.VISIBLE);
            textviewdiachimacdinh.setText("Địa chỉ: " + MainActivity.account.getDiaChi());
            textviewsodienthoaimacdinh.setText("Số điện thoại: " + MainActivity.account.getSdt());


        } else if (checkedRadioId == R.id.radioButton_diachimoi) {
            layoutdiachigiaohang.setVisibility(View.VISIBLE);
            layoutdiachimacdinh.setVisibility(View.GONE);

        }
    }

    private void xoaGioHang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanXoaGioHang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        message = jsonObject.getString("message");
                        Toast.makeText(ThongTinKhachHang.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        message = jsonObject.getString("message");
                        Toast.makeText(ThongTinKhachHang.this, message, Toast.LENGTH_LONG).show();
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
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void eventButton() {

        radioGroupChondiachi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });

        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkedRadioId == R.id.radioButton_diachimoi) {
                    diachi = editDiaChiGiao.getText().toString().trim();
                    sdt = editTextSDT.getText().toString().trim();
                    if (diachi.length() == 0) {
                        editDiaChiGiao.setError("Bạn chưa nhập địa chỉ");
                    } else if (sdt.length() == 0) {
                        editTextSDT.setError("Bạn chưa nhập số điện thoại");
                    }

                } else {
                    diachi = MainActivity.account.getDiaChi();
                    sdt = MainActivity.account.getSdt();
                }

                Date date = new Date();
                final String ngayDat;
                String strDateFormat = "yyyy-MM-dd";    //tạo đối tượng SimpleDateFormat;
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);     //gọi hàm format để lấy chuỗi ngày tháng năm đúng theo yêu cầu
                ngayDat = sdf.format(date);
                System.out.println("ngay đặt: " + ngayDat);

                if (diachi.length() > 0 && checkNumberPhone(sdt)) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            System.out.println("ma don hang; " + madonhang);
                            if (Integer.parseInt(madonhang) > 0)
                            {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongDanChiTierDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {

                                            MainActivity.mangGioHang.clear();
                                            //neu dat hang thanh cong thi xao gio hang trong co so du lieu
                                            xoaGioHang();
                                            System.out.println("vô đây r nè");
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(ThongTinKhachHang.this);
                                            dialog.setMessage("Bạn đã dặt hàng thành công!");
                                            dialog.setPositiveButton("Tiếp tục mua hàng", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ThongTinKhachHang.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                            dialog.setNegativeButton("Xem đơn hàng", new DialogInterface.OnClickListener() {
                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(ThongTinKhachHang.this, donhangActivity.class);
                                                    startActivity(intent);
                                                }

                                            });
                                            dialog.create().show();
                                        } else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Dữ liệu giỏ hàng của bạn đã bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.mangGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masanpham", MainActivity.mangGioHang.get(i).getIdSP());
                                                jsonObject.put("tensanpham", MainActivity.mangGioHang.get(i).getTenSP());
                                                jsonObject.put("giasanpham", MainActivity.mangGioHang.get(i).getGiaSP());
                                                jsonObject.put("soluongsanpham", MainActivity.mangGioHang.get(i).getSoLuongSP());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("idkhachhang", String.valueOf(MainActivity.idLogin));
                            hashMap.put("sodienthoai", sdt);
                            System.out.println(MainActivity.account.getId());
                            System.out.println(MainActivity.idLogin);
                            hashMap.put("ngaydat", ngayDat);
                            hashMap.put("diachigiao", diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Hãy kiểm tra lại dữ liệu");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count = MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(ThongTinKhachHang.this, cart_count, R.drawable.ic_cart));
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


    private void anhXa() {
        textviewdiachimacdinh = (TextView) findViewById(R.id.textviewdiachimacdinh);
        textviewsodienthoaimacdinh = (TextView) findViewById(R.id.textviewsodienthoaimacdinh);

        editDiaChiGiao = (EditText) findViewById(R.id.editdiachigiaohang);
        editTextSDT = (EditText) findViewById(R.id.edittextsdtkhachhang);
        buttonXacNhan = (Button) findViewById(R.id.buttonxacnhan);
        tbdiachi = (Toolbar) findViewById(R.id.toolbardiachigiaohang);
        layoutdiachigiaohang = (LinearLayout) findViewById(R.id.layoutdiachigiaohang);
        layoutdiachimacdinh = (LinearLayout) findViewById(R.id.layoutdiachimacdinh);
        radioGroupChondiachi = (RadioGroup) findViewById(R.id.radioGroup_chondiachi);
        radioButtonMacdinh = (RadioButton) findViewById(R.id.radioButton_diachimacdinh);
        radioButtonTuyChinh = (RadioButton) findViewById(R.id.radioButton_diachimoi);
        layoutdiachigiaohang.setVisibility(View.INVISIBLE);
        layoutdiachimacdinh.setVisibility(View.VISIBLE);
        textviewdiachimacdinh.setText("Địa chỉ: " + MainActivity.account.getDiaChi());
        textviewsodienthoaimacdinh.setText("Số điện thoại: " + MainActivity.account.getSdt());

    }
}
