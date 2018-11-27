package lll.com.lvlinlin20181123.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {

    private final SQLiteDatabase db;

    public Dao(Context context) {
        //创建对象
        MySqlite mySqlite = new MySqlite(context);
        db = mySqlite.getWritableDatabase();
    }
    //写方法
    //插入
    public void insert(String url,String title){
        //先删除 再插入
        db.delete("test","url=?",new String[]{title});
        //插入
        ContentValues values = new ContentValues();
        values.put("url",url);
        values.put("json",title);
        long insert = db.insert("test", null, values);
    }
    //查询
    public String query(String title){
        String json = "";
        Cursor query = db.query("test", null, "title=?", new String[]{title}, null, null, null);
        while (query.moveToNext()){
            json = query.getString(query.getColumnIndex("title"));
        }
        return json;
    }
}
