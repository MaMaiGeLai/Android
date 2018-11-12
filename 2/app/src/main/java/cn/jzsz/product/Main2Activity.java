package cn.jzsz.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;


import java.net.IDN;

import cn.jzsz.product.bean.Account;
import cn.jzsz.product.dao.AccountDao;

public class Main2Activity extends AppCompatActivity {
    private AccountDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dao = new AccountDao(this);
        ImageButton bc = findViewById(R.id.back);
        final ImageButton ins = findViewById(R.id.insert);
        final EditText editText = (EditText) findViewById(R.id.show);
        Intent intent = getIntent();
        final Long ID = intent.getLongExtra("ID", 0);
        editText.setText(dao.query(ID));
        bc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        ins.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editText.getText().toString().trim();
                        dao.update(ID, name);
                        Toast.makeText(getBaseContext(), "修改成功,已保存", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

}
