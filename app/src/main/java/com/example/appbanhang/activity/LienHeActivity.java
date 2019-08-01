package com.example.appbanhang.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.appbanhang.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarLienHe;
    Button btnQuan;
    Button btnTuan;
    Button btnThuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        toolbarLienHe = (Toolbar) findViewById(R.id.toolbarlienhe);
        anhXa();
        actionBar();
        clickButton();


    }

    private void clickButton() {
        btnQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LienHeActivity.this);
                dialog.setMessage("Địa chỉ: 97 Man Thiện, phường Hiệp Phú, quận 9                                      " +
                        "SĐT: 0373545467                            " +
                        "Mail: lehongquan@gmail.com");//
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     //   LienHeActivity.this;
                    }
                });
                dialog.create().show();


            }
        });
        btnTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LienHeActivity.this);
                dialog.setMessage("Địa chỉ: 97 Man Thiện, phường Hiệp Phú, quận 9                                      " +
                        "SĐT: 0977456756                            " +
                        "Mail: phamminhtuan@gmail.com");//
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   LienHeActivity.this;
                    }
                });
                dialog.create().show();
            }
        });
        btnThuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LienHeActivity.this);
                dialog.setMessage("Địa chỉ: 97 Man Thiện, phường Hiệp Phú, quận 9                                      " +
                        "SĐT: 0373865759                            " +
                        "Mail: phamthithuy@gmail.com");//
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   LienHeActivity.this;
                    }
                });
                dialog.create().show();
            }
        });
    }

    private void anhXa() {
        btnQuan = (Button) findViewById(R.id.btnQuan);
        btnTuan = (Button) findViewById(R.id.btnTuan);
        btnThuy = (Button) findViewById(R.id.btnThuy);
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


    private void actionBar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
