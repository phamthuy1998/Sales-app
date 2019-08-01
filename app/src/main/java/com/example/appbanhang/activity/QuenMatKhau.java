package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuenMatKhau extends AppCompatActivity {
    private Toolbar tbquenmatkhau;
    private EditText txtMail;
    private Button buttonlayPass;
    private Button buttonDangNhap;

    private LinearLayout layoutNhap;
    private LinearLayout layoutThongbao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Intent intent = getIntent();
        tbquenmatkhau = (Toolbar) findViewById(R.id.tbquenmatkhau);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            anhXa();
            actionBar();
            check();

        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }

    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (email.length() == 0) {
            txtMail.setError("Vui lòng nhập vào email hợp lệ!");
            return false;
        }
        if (matcher.find()) {
            return true;
        } else {
            txtMail.setError("Email sai định dạng!");
        }
        return false;
    }

    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setText("");
            editText.setError("Vui lòng nhập vào email hợp lệ!");
        }
        return false;
    }

    private void check() {

        String email = (String) getIntent().getSerializableExtra("email");
        if (email.length() > 0) {
            txtMail.setText(email);
        }
        buttonlayPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = txtMail.getText().toString().trim();
                if (checkEditText(txtMail) && isValidEmail(mail)) {
                    sendMail(mail);
                } else {

                }

            }


        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuenMatKhau.this, LoginActivity.class);
                startActivity(intent);

            }


        });

    }

    private void sendMail(final String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongDan = Server.duongDanLayPassWord;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongDan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(QuenMatKhau.class.getSimpleName(), response);
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("success") == 1) {
                        message = jsonObject.getString("message");
                        Toast.makeText(QuenMatKhau.this, message, Toast.LENGTH_LONG).show();
                        layoutNhap.setVisibility(View.GONE);
                        buttonlayPass.setVisibility(View.GONE);
                        layoutThongbao.setVisibility(View.VISIBLE);
                        buttonDangNhap.setVisibility(View.VISIBLE);
                    } else {
                        message = jsonObject.getString("message");
                        Toast.makeText(QuenMatKhau.this, message, Toast.LENGTH_LONG).show();
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
                param.put("email", email);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionBar() {
        setSupportActionBar(tbquenmatkhau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbquenmatkhau.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void anhXa() {
        buttonlayPass = (Button) findViewById(R.id.buttonlaymatkhau);
        buttonDangNhap = (Button) findViewById(R.id.btnLogin);
        layoutNhap = (LinearLayout) findViewById(R.id.layoutnhappass);
        layoutThongbao = (LinearLayout) findViewById(R.id.layoutthongbao);
        layoutThongbao.setVisibility(View.GONE);
        buttonDangNhap.setVisibility(View.GONE);
        txtMail = (EditText) findViewById(R.id.editmaillaypass);
    }

}
