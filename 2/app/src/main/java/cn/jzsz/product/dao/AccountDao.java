package cn.jzsz.product.dao;

import java.util.ArrayList;
import java.util.List;

import cn.jzsz.product.bean.Account;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AccountDao {
    private MyHelper helper;

    public AccountDao(Context context) {
        helper = new MyHelper(context); // 创建Dao时, 创建Helper
    }

    public void insert(Account account) {
        SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库对象
        // 用来装载要插入的数据的 Map<列名, 列的值>
        ContentValues values = new ContentValues();
        values.put("name", account.getName());
        long id = db.insert("account", null, values); // 向account表插入数据values,
        account.setId(id); // 得到id
        db.close(); // 关闭数据库

    }

    // 根据id 删除数据
    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // 按条件删除指定表中的数据, 返回受影响的行数
        int count = db.delete("account", "_id=?", new String[]{id + ""});
        db.close();
        return count;
    }

    // 更新数据
    public void update(Long ID, String newname) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update account set name='" + newname + "'where _id=" + ID;
        db.execSQL(sql);
        db.close();
    }

    // 查询所有数据倒序排列
    public List<Account> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("account", null, null, null, null, null,
                "balance DESC");
        List<Account> list = new ArrayList<Account>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex("_id")); // 可以根据列名获取索引
            String name = c.getString(c.getColumnIndex("name"));
            list.add(new Account(id, name));
        }
        c.close();
        db.close();
        return list;
    }

    public String query(long ID) {
        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("account.db",MODE_PRIVATE,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        String ss = null;
        String sql = "select name from account where _id=?";
        Cursor name = db.rawQuery(sql, new String[]{ID + ""});
        while (name.moveToNext()) {
            ss = name.getString(0);
        }
        db.close();
        return ss;
    }
}