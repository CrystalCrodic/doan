package stu.edu.vn.doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import stu.edu.vn.doan.adapters.ContactAdapter;
import stu.edu.vn.doan.models.Friend;

public class DanhSachLienLac extends AppCompatActivity {
    ListView lv;
    ContactAdapter adapter;
    ArrayList<Friend> danhsach;
    FloatingActionButton btnActionAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_lien_lac);

        lv = findViewById(R.id.lv);
        btnActionAdd = findViewById(R.id.btnActionAdd);
        danhsach = new ArrayList<>();
        adapter = new ContactAdapter(DanhSachLienLac.this, R.layout.contact_view, danhsach);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        GetDataView();

        btnActionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhSachLienLac.this, ThemLienLac.class));
            }
        });
    }

    public void GetDataView (){
        if(danhsach.size() > 0){
            danhsach.clear();
        }
        Cursor cursor = MainActivity.database.GetData("SELECT * FROM FRIEND");
        while (cursor.moveToNext()){
            danhsach.add(new Friend(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getBlob(6)));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.menu1){
            Intent intent = new Intent(this, ThongTinBanBe.class);
            intent.putExtra("BanBe", danhsach.get(info.position));
            startActivity(intent);
        }
        if(item.getItemId() == R.id.menu2){
            AlertDialog.Builder alert = new AlertDialog.Builder(DanhSachLienLac.this);
            alert.setTitle("Cảnh Báo Báo !!!");
            alert.setMessage("Xoá "+danhsach.get(info.position).getName() + " ?");
            alert.setIcon(R.mipmap.ic_launcher);

            alert.setPositiveButton("Yes", (dialog, which) -> {
                MainActivity.database.QueryData("DELETE FROM FRIEND WHERE uid = "+danhsach.get(info.position).getUid());
                Toast.makeText(this, "Xoá Thành Công " +danhsach.get(info.position).getName(), Toast.LENGTH_SHORT).show();
                GetDataView();
            });

            alert.setNegativeButton("No", (dialog, which) -> {

            });
            alert.show();

        }
        if(item.getItemId() == R.id.menu3){
            Intent intent = new Intent(this,SuaThongTin.class);
            intent.putExtra("BanBe", danhsach.get(info.position));
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }
}