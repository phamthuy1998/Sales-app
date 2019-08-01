package com.example.appbanhang.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.model.Account;
import com.example.appbanhang.util.CheckConnection;
import com.example.appbanhang.util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultLogin extends AppCompatActivity {
private Toolbar resultLogin;
    private EditText txtUserName;
    private EditText txtEmail;
    private EditText txtsdt;
    private EditText txtdiachi;
    private EditText txtpassword;
    private EditText txtnewPass;
    private EditText txtconfirmPass;
    private CheckBox checkEditPassword;
    private LinearLayout layoutcheckEditPassword;
    private Button btnsaveChanged;
    private Button btnsignOut;
    private ProgressDialog pDialog;

    private String password;
    private String diaChi;
    private String name;
    private String email;
    private String phone;

    public static final String TAG = ResultLogin.class.getSimpleName();
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_login);
        Intent intent = getIntent();
        resultLogin = (Toolbar) findViewById(R.id.toolbarUser);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getInfor();
            addControl();
            addInf();
            actionBar();
            addEvent();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }

    }

    private void addInf() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang lưu...");
        pDialog.setCanceledOnTouchOutside(false);
    }

    private void getInfor() {
        name = MainActivity.account.getUserName();
        phone = MainActivity.account.getSdt();
        email = MainActivity.account.getEmail();
        password = MainActivity.account.getPassWord();
        diaChi = MainActivity.account.getDiaChi();

    }

    public boolean checkNumberPhone(String number) {
        number = number.toString().trim();
        if(number.length()==0){
            txtsdt.setText("");
            txtsdt.setError("số điện thoại không được bỏ trống");
            return  false;
        }
        else {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(number);
            if (!matcher.matches()) {
                txtsdt.setError("số điện thoại không hợp lệ!");
            } else if (number.length() == 10) {
                if (number.substring(0, 2).equals("09") || number.substring(0, 2).equals("03") || number.substring(0, 2).equals("08") || number.substring(0, 2).equals("07") || number.substring(0, 2).equals("05")) {
                    return true;
                } else {
                    txtsdt.setError("số điện thoại không hợp lệ!");
                }
            } else {
                txtsdt.setError("số điện thoại không hợp lệ!");
            }
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        if (email.toString().trim().length() == 0) {
            txtEmail.setText("");
            txtEmail.setError("Email không được bỏ trống");
            return false;
        } else {
            String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            Pattern regex = Pattern.compile(emailPattern);
            Matcher matcher = regex.matcher(email);
            if (matcher.find()) {
                return true;
            } else {
                txtEmail.setError("Email sai định dạng!");
            }
            return false;
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int cart_count= MainActivity.mangGioHang.size();
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menugiohang);
        menuItem.setIcon(Converter.convertLayoutToImage(ResultLogin.this,cart_count,R.drawable.ic_cart));
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



    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
            editText.setText("");
        }
        return false;
    }


    private void updateUser(final int userID, final String username, final String password, final String email, final String sdt, final String diachi) {
        pDialog.show();
        StringRequest registerRequest = new StringRequest(Request.Method.POST, Server.duongDanChinhSuaThongTinUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        String message = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {

                                MainActivity.account = new Account();
                                MainActivity.account.setPassWord(password);
                                MainActivity.account.setUserName(username);
                                MainActivity.account.setEmail(email);
                                MainActivity.account.setSdt(sdt);
                                MainActivity.account.setDiaChi(diachi);

                                message = jsonObject.getString("message");
                                Toast.makeText(ResultLogin.this, message, Toast.LENGTH_LONG).show();

                                //Start LoginActivity
                                Toast.makeText(ResultLogin.this, "Đã lưu thay đổi", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ResultLogin.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                message = jsonObject.getString("message");
                                Toast.makeText(ResultLogin.this, message, Toast.LENGTH_LONG).show();
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
                params.put("user_id", String.valueOf(userID));
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("sdt", sdt);
                params.put("diachi", diachi);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(registerRequest);

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
                txtnewPass.setError("Password phải dài từ 6-14 ký tự, bao gồm chữ hoa, chữ thường và ký tự đặc biệt");
            }
            return check;

        }
    }


    private void addEvent() {
        // kiểm tra xem checkbox đổi mật khẩu đã được tích hay chưa
        // nếu đã check thì hiện thanh nhập mật khẩu cũ và mật khẩu mới
        checkEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEditPassword.isChecked()) {
                    layoutcheckEditPassword.setVisibility(View.VISIBLE);
                } else {
                    layoutcheckEditPassword.setVisibility(View.INVISIBLE);
                }

            }
        });

        //Bắt sự kiện khi click button đăng xuất
        btnsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.isLogin = false;
                MainActivity.xemDonHang=false;
                MainActivity.mangGioHang.clear();
                MainActivity.account = new Account();
                Intent intent = new Intent(ResultLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // bắt sự kiện khi click button Lưu thay đổi
        btnsaveChanged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = txtUserName.getText().toString().trim();
                String mail = txtEmail.getText().toString().trim();
                String diachi = txtdiachi.getText().toString().trim();
                String sdt = txtsdt.getText().toString().trim();
                String pass = txtpassword.getText().toString().trim();
                String newpass = txtnewPass.getText().toString().trim();
                String confPass = txtconfirmPass.getText().toString().trim();
                String thongBao = "";
                if (userName.equals(name) && mail.equals(email) && sdt.equals(phone)&&diachi.equals(diaChi) && checkEditPassword.isChecked() == false) {
                    thongBao = "Đã lưu thay đổi";
                    Toast.makeText(ResultLogin.this, "Đã lưu thay đổi", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResultLogin.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    PasswordValidator validator = new PasswordValidator();
                    if (checkEditPassword.isChecked()) {
                        if (userName.length() == 0) {
                            txtUserName.setText("");
                            txtUserName.setError("Tên không được bỏ trống");
                        } else if (checkNumberPhone(sdt)==false) {

                        } else if (isValidEmail(mail)==false) {

                        }
                        else if(diachi.length()==0){
                            txtdiachi.setText("");
                            txtdiachi.setError("Bạn chưa nhập địa chỉ");
                        }
                        else if (pass.length() ==0) {
                            txtpassword.setError("Bạn chưa nhập mật khẩu cũ");
                        } else if (password.equals(pass) == false) {
                            txtpassword.setError("Mật khẩu cũ không đúng");
                        } else if (newpass.length() == 0) {
                            txtnewPass.setError("Bạn chưa nhập mật khẩu mới");
                        } else if (confPass.length() == 0) {
                            txtconfirmPass.setError("Bạn chưa nhập lại mật khẩu mới");
                        } else if (newpass.equals(confPass) == false) {
                            txtconfirmPass.setError("Mật khẩu mới không khớp");
                            System.out.println("mật khẩu không khớp");
                        } else if(validator.validate(txtnewPass.getText().toString().trim())){
                            updateUser(MainActivity.idLogin, userName, newpass, mail, sdt,diachi);

                        }
                    } else {
                        if (userName.length() == 0) {
                            txtUserName.setText("");
                            txtUserName.setError("Tên không được bỏ trống");
                        }
                        else if(diachi.length()==0){
                            txtdiachi.setText("");
                            txtdiachi.setError("Bạn chưa nhập địa chỉ");
                        } else if(checkNumberPhone(sdt)&&isValidEmail(mail)) {
                            System.out.println("mã id user :" + MainActivity.idLogin);
                            updateUser(MainActivity.idLogin, userName, MainActivity.account.getPassWord(), mail, sdt,diachi);


                        }

                    }
                }


            }

        });
    }

    private void actionBar() {
        setSupportActionBar(resultLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resultLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        //resultLogin = (Toolbar) findViewById(R.id.tbLogin);
        txtdiachi=(EditText) findViewById(R.id.edittextdiachi);
        txtEmail = (EditText) findViewById(R.id.edittexemailUser);
        txtUserName = (EditText) findViewById(R.id.edittenuser);
        txtsdt = (EditText) findViewById(R.id.edittextsdtUser);
        checkEditPassword = (CheckBox) findViewById(R.id.checkboxDoimatkhau);

        txtpassword = (EditText) findViewById(R.id.edittexmatkhauhientai);
        txtnewPass = (EditText) findViewById(R.id.edittexmatkhaumoi);
        txtconfirmPass = (EditText) findViewById(R.id.edittextnhaplaimatkhaumoi);

        layoutcheckEditPassword = (LinearLayout) findViewById(R.id.layoutpassword);
        btnsaveChanged = (Button) findViewById(R.id.buttonluuthaydoiuser);
        btnsignOut = (Button) findViewById(R.id.buttondangxuat);

        txtUserName.setText(MainActivity.account.getUserName());
        txtEmail.setText(MainActivity.account.getEmail());
        txtsdt.setText(MainActivity.account.getSdt());
        txtdiachi.setText(MainActivity.account.getDiaChi());

        layoutcheckEditPassword.setVisibility(View.INVISIBLE);

    }

}
