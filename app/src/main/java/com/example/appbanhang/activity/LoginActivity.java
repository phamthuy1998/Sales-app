package com.example.appbanhang.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.appbanhang.model.Account;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.LoaiSP;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    private Toolbar tbLogin;
    private EditText edtEmailSDt;
    private EditText edtPassWord;
    private Button btnLogin;
    private Button btnRegister;
    private Button btnForgotPasss;
    private ProgressDialog pDialog;
    /**
     * URL : URL_LOGIN
     * param : KEY_USERNAME KEY_PASSWORD
     */

    public static final String KEY_EMAIL = "email";
    public static final String KEY_SDT = "sdt";
    public static final String KEY_PASSWORD = "password";
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        actionBar();
        addEvent();
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

    private void actionBar() {
        setSupportActionBar(tbLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.forgotpass==true){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
                MainActivity.thanhtoan = false;
                MainActivity.xemDonHang = false;

            }
        });
    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get value input
                String email = edtEmailSDt.getText().toString().trim();
                password = edtPassWord.getText().toString().trim();
                String sdt = edtEmailSDt.getText().toString().trim();
                // Call method
                loginAccount(email, sdt, password);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnForgotPasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.forgotpass=true;
                String email = edtEmailSDt.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, QuenMatKhau.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        btnForgotPasss = (Button) findViewById(R.id.btnqquenmatkhau);
        tbLogin = (Toolbar) findViewById(R.id.tbLogin);
        edtEmailSDt = (EditText) findViewById(R.id.editUsername);
        edtPassWord = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    public void loginAccount(final String email, final String sdt, final String password) {

        if (checkEditText(edtEmailSDt) && checkEditText(edtPassWord)) {
            pDialog.show();
            StringRequest requestLogin = new StringRequest(Request.Method.POST, Server.duongDanDangNhap,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            String message = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                    MainActivity.account = new Account();
                                    MainActivity.account.setUserName(jsonObject.getString("user_name"));
                                    MainActivity.account.setEmail(jsonObject.getString("email"));
                                    MainActivity.account.setSdt(jsonObject.getString("sdt"));
                                    MainActivity.account.setDiaChi(jsonObject.getString("diachi"));
                                    MainActivity.account.setId(jsonObject.getInt("user_id"));
                                    System.out.println("dia chi trong dang nhap: " + MainActivity.account.getDiaChi());
                                    MainActivity.account.setPassWord(password);


                                    MainActivity.idLogin = jsonObject.getInt("user_id");

                                    message = jsonObject.getString("message");
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();


                                    MainActivity.isLogin = true;
                                    getDuLieugioHang();
                                    if (MainActivity.xemDonHang == true) {
                                        // nếu đăng nhập thành công thì sẽ láy dữ liệu giohang trong co sở dữ liệu


                                        MainActivity.xemDonHang = false;
                                        Intent intent = new Intent(LoginActivity.this, donhangActivity.class);
                                        startActivity(intent);
                                    } else if (MainActivity.thanhtoan == true) {
                                        MainActivity.thanhtoan = false;
                                        Intent intent = new Intent(LoginActivity.this, ThongTinKhachHang.class);
                                        startActivity(intent);
                                    } else {
                                        // nếu đăng nhập thành công thì sẽ láy dữ liệu giohang trong co sở dữ liệu

                                        System.out.println("lấy du lieu");
                                        Intent intent = new Intent(LoginActivity.this, ResultLogin.class);
                                        startActivity(intent);
                                    }


                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                /**
                 * set paramater
                 */
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(KEY_EMAIL, email);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_SDT, sdt);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(requestLogin);
        }
    }

    /**
     * Check input
     */
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }
}
