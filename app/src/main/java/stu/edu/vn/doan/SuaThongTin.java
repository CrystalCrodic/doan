package stu.edu.vn.doan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import stu.edu.vn.doan.models.Friend;
import stu.edu.vn.doan.utils.FormatDate;
import stu.edu.vn.doan.utils.Helper;

public class SuaThongTin extends AppCompatActivity {
    EditText edtName, edtDisplayName, edtEmail;
    Button btnAdd, btnCancel;
    ImageView avatar;
    ImageButton btnCamera, btnEditBirthDay;
    ArrayList<String> socials;
    ArrayAdapter<String> adapter;
    Spinner select;
    TextView txtEditBirthDay;
    String[] list = {"Vui Lòng Chọn MXH", "Facebook", "Zalo", "Telegram", "Yahoo","Zingme"};
    String choiceSocial = "";
    int position = -1;
    Calendar calendar;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thong_tin);
        helper = new Helper(this);
        addControl();
        SocialData();

        Intent intent = getIntent();
        Friend f = (Friend) intent.getSerializableExtra("BanBe");
        calendar = Calendar.getInstance();

        edtName.setText(f.getName());
        edtDisplayName.setText(f.getPrimary_name());
        txtEditBirthDay.setText(f.getBirthday());
        choiceSocial = f.getSocial();
        edtEmail.setText(f.getEmail());

        Bitmap image = helper.ConvertImageToBitmap(f.getAvatar());
        avatar.setImageBitmap(image);

        for (String data: list) {
            position++;
            if(data.equals(choiceSocial)){
                break;
            }
        }

        adapter = new ArrayAdapter<>(SuaThongTin.this, android.R.layout.simple_spinner_item, socials);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        select.setSelection(position);

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
                if(ActivityCompat.checkSelfPermission(SuaThongTin.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(SuaThongTin.this, new String[]{android.Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(myIntent, 200);
            }
        });

        btnEditBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DATE, dayOfMonth);
                                txtEditBirthDay.setText(FormatDate.formatDate(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog = new DatePickerDialog(SuaThongTin.this, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(v -> {
            if (edtName.getText().toString().isEmpty() || edtDisplayName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() || choiceSocial.equals("Vui Lòng Chọn MXH")) {
                Toast.makeText(SuaThongTin.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                return;
            }
            byte[] image1 = helper.ConvertImageToByteArray(avatar);

            String birthday = txtEditBirthDay.getText().toString();
            AlertDialog.Builder alert = new AlertDialog.Builder(SuaThongTin.this);
            alert.setTitle("Thông Báo !!!");
            alert.setMessage("Bạn Có Chắc Về Hành Động Này ?");
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.database.UpdateFriend(f.getUid(),edtName.getText().toString(), edtDisplayName.getText().toString(), birthday, choiceSocial, edtEmail.getText().toString(), image1);
                    helper.ToastMessage("Cập Nhật Thông Tin Thành Công");
                    startActivity(new Intent(SuaThongTin.this, MainActivity.class));
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        });

        btnCancel.setOnClickListener(v -> {
            Intent intent1 = new Intent(SuaThongTin.this, MainActivity.class);
            startActivity(intent1);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK){
            Bitmap img = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(img);
        }
    }

    private void addControl() {
        edtName = findViewById(R.id.edtNameEdit);
        edtDisplayName = findViewById(R.id.edtPrimaryNameEdit);
        edtEmail = findViewById(R.id.edtEmailEdit);
        btnAdd = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnExit);
        btnCamera = findViewById(R.id.btnCameraEdit);
        avatar = findViewById(R.id.EditAvatarView);
        select = findViewById(R.id.spinnerSocialEdit);
        btnEditBirthDay = findViewById(R.id.btnEditBirthDay);
        txtEditBirthDay = findViewById(R.id.txtBirthDayEdit);
    }

    private void SocialData() {
        socials = new ArrayList<>();
        for (String data: list) {
            socials.add(data);
        }
    }
}