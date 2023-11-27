package stu.edu.vn.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;

import stu.edu.vn.doan.DB.DBContext;
import stu.edu.vn.doan.utils.Helper;

public class MainActivity extends AppCompatActivity {

    public static DBContext database;

    Helper helper;

    Button btnShowContact, btnShowAdd;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DBContext(MainActivity.this, null, null, 1);
        helper = new Helper(MainActivity.this);

        btnShowAdd = findViewById(R.id.btnGetAdd);
        btnShowContact = findViewById(R.id.btnGetShow);

        LocalDate currentDate = LocalDate.now();

        Cursor cursor = database.GetData("SELECT * FROM FRIEND");
        while (cursor.moveToNext()){
            String dataBirthDay = cursor.getString(3).split("/")[1] +"/"+ cursor.getString(3).split("/")[0];
            String current = currentDate.toString().split("-")[1] + "/" + currentDate.toString().split("-")[2];
            if(dataBirthDay.equals(current)){
                helper.ToastMessage("Hôm Nay Là Sinh Nhật Của " + cursor.getString(1));
            }
        }

        btnShowAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThemLienLac.class));
        });

        btnShowContact.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DanhSachLienLac.class)));
    }
}