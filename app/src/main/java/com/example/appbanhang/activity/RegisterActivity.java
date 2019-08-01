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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = RegisterActivity.class.getSimpleName();

    private Toolbar tbRegister;
    private EditText edtUserName;
    private EditText edtPassWord;
    private EditText edtEmail;
    private EditText edtSdt;
    private EditText edtDiaChi;
    private Button btnRegister;
    private Button btnLogin;
    private ProgressDialog pDialog;

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_SDT = "sdt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        actionBar();
        addEvents();
    }

    private void actionBar() {
        setSupportActionBar(tbRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String chuanHoaTen(String str) {
        str = str.trim();
        str = str.toLowerCase();
        str = str.replaceAll("\\s+", " ");
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
            if (i < temp.length - 1) {
                str += " ";
            }
        }
        return str;
    }

    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data input
                String username = edtUserName.getText().toString().trim();
                if (username.length() != 0) {
                    username = chuanHoaTen(username);
                }
                String password = edtPassWord.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                String sdt = edtSdt.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                System.out.println("dia chi: " + diaChi);

                registerUser(username, password, email, sdt, diaChi);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {

        tbRegister = (Toolbar) findViewById(R.id.tbRegister);
        edtUserName = (EditText) findViewById(R.id.editUsername);
        edtPassWord = (EditText) findViewById(R.id.editPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.editEmail);
        edtDiaChi = (EditText) findViewById(R.id.editDiachi);
        edtSdt = (EditText) findViewById(R.id.editSdt);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
            return true;
        } else {
            edtEmail.setError("Email sai định dạng!");
        }
        return false;
    }

    public boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            edtSdt.setError("số điện thoại không hợp lệ!");
        } else if (number.length() == 10||number.length()==11) {
            if (number.substring(0, 2).equals("09") || number.substring(0, 2).equals("03") || number.substring(0, 2).equals("08") || number.substring(0, 2).equals("07") || number.substring(0, 2).equals("05")) {
                return true;
            } else {
                edtSdt.setError("số điện thoại không hợp lệ!");
            }
        } else {
            edtSdt.setError("số điện thoại không hợp lệ!");
        }
        return false;
    }

    private void registerUser(final String username, final String password, final String email, final String sdt, final String diachi) {
        PasswordValidator validator = new PasswordValidator();
        UsernameValidator validatorName = new UsernameValidator();
        if (checkEditText(edtUserName) && validatorName.validate(username) && checkEditText(edtSdt) && checkNumberPhone(sdt) && isValidEmail(email) && checkEditText(edtUserName) &&checkEditText(edtDiaChi)&& checkEditText(edtPassWord) && validator.validate(password)) {
            pDialog.show();
            StringRequest registerRequest = new StringRequest(Request.Method.POST, Server.duongDanDangKy,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                            String message = "";
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getInt("success") == 1) {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                    //Start LoginActivity
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    message = jsonObject.getString("message");
                                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
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
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_EMAIL, email);
                    params.put(KEY_SDT, sdt);
                    params.put("diachi", diachi);
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(registerRequest);
        }

    }

    /**
     * Check Input
     */
    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }

    public class PasswordValidator {

        private Pattern pattern;
        private Matcher matcher;

        private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

        public PasswordValidator() {
            pattern = Pattern.compile(PASSWORD_PATTERN);
        }

        public boolean validate(final String password) {
            boolean check;
            matcher = pattern.matcher(password);
            check = matcher.matches();
            if (check == false) {
                edtPassWord.setError("Password phải dài từ 6-14 ký tự, bao gồm chữ hoa, chữ thường và ký tự đặc biệt");
            }
            return check;

        }
    }

    public class UsernameValidator {
        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^[A-Za-z0-9._\\s]{3,15}$";

        public UsernameValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            boolean check;

            check = pattern.matcher(username).matches();
            if (check == false) {
                edtUserName.setError("Username  phải dài từ 3-15 ký tự, bao gồm A-Z, a-z, 0-9, _, - ");

            }
            return check;
        }
    }
}
