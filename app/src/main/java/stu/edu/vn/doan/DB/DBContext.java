package stu.edu.vn.doan.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DBContext extends SQLiteOpenHelper {
    private static final String FriendSQL = "CREATE TABLE IF NOT EXISTS " +
            "FRIEND(uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name VARCHAR(255), primary_name VARCHAR(255), " +
            "birthday VARCHAR(255), social VARCHAR(255), " +
            "email VARCHAR(255), avatar BLOB)";

    public DBContext(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "QuanLyLienLac.sqlite", factory, version);
    }

    public void QueryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData (String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    public void AddFriend (String name, String displayName, String birthday, String social, String email, byte[] img){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FRIEND VALUES(null, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement pstm = database.compileStatement(sql);
        pstm.clearBindings();

        pstm.bindString(1, name);
        pstm.bindString(2, displayName);
        pstm.bindString(3, birthday);
        pstm.bindString(4, social);
        pstm.bindString(5, email);
        pstm.bindBlob(6, img);
        pstm.executeInsert();
    }

    public void UpdateFriend (int uid, String name, String displayName, String birthday, String social, String email, byte[] img){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE FRIEND SET name = ?, primary_name = ?, birthday = ?, social = ?, email = ?, avatar = ? WHERE uid = ?";
        SQLiteStatement pstm = database.compileStatement(sql);
        pstm.clearBindings();

        pstm.bindString(1, name);
        pstm.bindString(2, displayName);
        pstm.bindString(3, birthday);
        pstm.bindString(4, social);
        pstm.bindString(5, email);
        pstm.bindBlob(6, img);
        pstm.bindLong(7, uid);
        pstm.executeInsert();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FriendSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FRIEND");
    }
}