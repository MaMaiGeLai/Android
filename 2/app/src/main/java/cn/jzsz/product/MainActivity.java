package cn.jzsz.product;

import java.util.List;

import cn.jzsz.product.bean.Account;
import cn.jzsz.product.dao.AccountDao;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    // 需要适配的数据集合
    private List<Account> list;
    // 数据库增删改查操作类
    private AccountDao dao;
    // ListView
    private ListView accountLV;
    // 适配器
    private MyAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initView();
        dao = new AccountDao(this);
        // 从数据库查询出所有数据
        list = dao.queryAll();
        Log.v("len", list.size() + "");
        adapter = new MyAdapter();
        accountLV.setAdapter(adapter);

    }

    // 初始化控件
    private void initView() {
        accountLV = (ListView) findViewById(R.id.accountLV);
        accountLV.setOnItemClickListener(new MyOnItemClickListener());
    }


    public void add(View v) {
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
    }

    // 自定义一个适配器(把数据装到ListView的工具)
    private class MyAdapter extends BaseAdapter {
        public int getCount() { // 获取条目总数
            return list.size();
        }

        public Object getItem(int position) { // 根据位置获取对象
            return list.get(position);
        }

        public long getItemId(int position) { // 根据位置获取id
            return position;
        }

        // 获取一个条目视图
        public View getView(int position, View convertView, ViewGroup parent) {
            // 重用convertView
            View item = null;
            if (convertView != null) {
                item = convertView;
            } else {
                item = View.inflate(getApplicationContext(), R.layout.item,
                        null);
            }
            // 获取该视图中的TextView
            TextView nameTV = (TextView) item.findViewById(R.id.nameTV);
            // 根据当前位置获取Account对象
            final Account a = list.get(position);
            // 把Account对象中的数据放到TextView中
            nameTV.setText(a.getName());
            ImageView deleteIV = (ImageView) item.findViewById(R.id.deleteIV);
            // 删除图片的点击事件触发的方法
            deleteIV.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // 删除数据之前首先弹出一个对话框
                    android.content.DialogInterface.OnClickListener listener = new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            list.remove(a); // 从集合中删除
                            dao.delete(a.getId()); // 从数据库中删除
                            notifyDataSetChanged();// 刷新界面
                        }
                    };
                    Builder builder = new Builder(MainActivity.this); // 创建对话框
                    builder.setTitle("确定要删除吗?"); // 设置标题
                    // 设置确定按钮的文本以及监听器
                    builder.setPositiveButton("确定", listener);
                    builder.setNegativeButton("取消", null); // 设置取消按钮
                    builder.show(); // 显示对话框
                }
            });
            return item;
        }
    }

    // ListView的Item点击事件
    private class MyOnItemClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // 获取点击位置上的数据
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            Account a = (Account) parent.getItemAtPosition(position);
            Long ID = a.getId();
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
    }

}
