package stu.edu.vn.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

import stu.edu.vn.doan.utils.FormatDate;
import stu.edu.vn.doan.utils.Helper;

public class ThemLienLac extends AppCompatActivity {

    EditText edtName, edtDisplayName, edtNgay, edtThang, edtNam, edtEmail;
    Button btnAdd, btnCancel;
    ImageView avatar;
    ImageButton btnCamera, btnCalendar;
    ArrayList<String> socials;
    ArrayAdapter<String> adapter;
    Spinner select;
    String choiceSocial = "No Select";
    Helper helper;
    TextView txtBirthDayTime;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lien_lac);
        helper = new Helper(this);
        btnCalendar = findViewById(R.id.btnCalendar);
        txtBirthDayTime = findViewById(R.id.txtBirthDayTime);
        calendar = Calendar.getInstance();
        addControl();
        SocialData();

        adapter = new ArrayAdapter<>(ThemLienLac.this, android.R.layout.simple_spinner_item, socials);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceSocial = socials.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(ActivityCompat.checkSelfPermission(ThemLienLac.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ThemLienLac.this, new String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(myIntent, 200);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DATE, dayOfMonth);
                                txtBirthDayTime.setText(FormatDate.formatDate(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog = new DatePickerDialog(ThemLienLac.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().isEmpty()
                        || edtDisplayName.getText().toString().isEmpty()
                        || edtEmail.getText().toString().isEmpty()
                        || choiceSocial == "No Select"){
                    Toast.makeText(ThemLienLac.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                byte[] image = helper.ConvertImageToByteArray(avatar);

                String birthday = txtBirthDayTime.getText().toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(ThemLienLac.this);
                alert.setTitle("Thông Báo !!!");
                alert.setMessage("Thêm Dữ Liệu Vào Danh Sách ?");
                alert.setIcon(R.mipmap.ic_launcher);

                alert.setPositiveButton("Yes", (dialog, which) -> {
                    MainActivity.database.AddFriend(edtName.getText().toString(), edtDisplayName.getText().toString(), birthday, choiceSocial, edtEmail.getText().toString(),image);
                    helper.ToastMessage("Thêm Thành Công");
                    startActivity(new Intent(ThemLienLac.this, MainActivity.class));
                });

                alert.setNegativeButton("No", (dialog, which) -> {

                });
                alert.show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(ThemLienLac.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK){
            Bitmap img = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(img);
        }
    }

    private void addControl() {
        edtName = findViewById(R.id.edtName);
        edtDisplayName = findViewById(R.id.edtPrimaryName);
        edtEmail = findViewById(R.id.edtEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);
        btnCamera = findViewById(R.id.btnCamera);
        avatar = findViewById(R.id.avatarView);
        select = findViewById(R.id.spinnerSocial);
    }

    private void SocialData() {
        socials = new ArrayList<>();
        socials.add("Vui Lòng Chọn MXH");
        socials.add("Facebook");
        socials.add("Zalo");
        socials.add("Telegram");
        socials.add("Yahoo");
        socials.add("Zingme");
    }
}