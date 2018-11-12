package cn.jzsz.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import cn.jzsz.product.bean.Account;
import cn.jzsz.product.dao.AccountDao;

public class Main3Activity extends AppCompatActivity {
    private AccountDao dao;
    // 输入便签的EditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        dao = new AccountDao(this);
        final ImageButton bc = findViewById(R.id.back);
        final ImageButton ins = findViewById(R.id.insert);
        final EditText editText = (EditText) findViewById(R.id.show);
        bc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
        ins.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = editText.getText().toString();
                        Account a = new Account(name);
                        dao.insert(a);
                        Toast.makeText(getBaseContext(), "便签已添加", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


}
