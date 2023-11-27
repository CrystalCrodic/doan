package stu.edu.vn.doan.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class Helper {

    Activity activity;

    public Helper(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void ToastMessage (String text){
        Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show();
    }

    public byte[] ConvertImageToByteArray (ImageView avatar){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) avatar.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10,byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        return image;
    }

    public Bitmap ConvertImageToBitmap (byte[] image){
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bitmap;
    }
}
