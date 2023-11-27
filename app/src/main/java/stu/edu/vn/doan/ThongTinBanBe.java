package stu.edu.vn.doan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import stu.edu.vn.doan.models.Friend;
import stu.edu.vn.doan.utils.Helper;

public class ThongTinBanBe extends AppCompatActivity {

    ImageView showAvatar;

    Helper helper;

    TextView txtInfoName, txtInfoDisplay, txtInfoBirthDay, txtInfoEmail, txtInfoSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ban_be);

        showAvatar = findViewById(R.id.showInfoAvatar);
        txtInfoName = findViewById(R.id.txtInforName);
        txtInfoDisplay = findViewById(R.id.txtInfoDisplayName);
        txtInfoEmail = findViewById(R.id.txtInfoEmail);
        txtInfoSocial = findViewById(R.id.txtInfoSocial);
        txtInfoBirthDay = findViewById(R.id.txtInfoBirthDay);
        showAvatar = findViewById(R.id.showInfoAvatar);

        helper = new Helper(this);

        Intent intent = getIntent();

        Friend f = (Friend) intent.getSerializableExtra("BanBe");

        txtInfoName.setText(f.getName().toString());
        txtInfoDisplay.setText(f.getPrimary_name().toString());
        txtInfoBirthDay.setText("Sinh Nhật: "+ f.getBirthday().toString());
        txtInfoSocial.setText("Mạng Xã Hội: "+f.getSocial().toString());
        txtInfoEmail.setText("Email: "+f.getEmail().toString());

        Bitmap bitmap = helper.ConvertImageToBitmap(f.getAvatar());
        showAvatar.setImageBitmap(bitmap);
    }
}