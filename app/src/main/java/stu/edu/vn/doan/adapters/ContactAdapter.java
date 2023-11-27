package stu.edu.vn.doan.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import stu.edu.vn.doan.R;
import stu.edu.vn.doan.models.Friend;
import stu.edu.vn.doan.utils.Helper;

public class ContactAdapter extends ArrayAdapter<Friend> {
    private Activity context;
    private int layout;
    private ArrayList<Friend> danhsach;

    public ContactAdapter(Activity context, int layout, ArrayList<Friend> danhsach) {
        super(context, layout, danhsach);
        this.context = context;
        this.layout = layout;
        this.danhsach = danhsach;
    }

    @NonNull
    @Override
    public Activity getContext() {
        return context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layout, null);

        Helper helper = new Helper(context);
        Friend f = danhsach.get(position);

        ImageView avatar;
        TextView txtName, txtDisplayName;

        avatar = convertView.findViewById(R.id.avatar);
        txtName = convertView.findViewById(R.id.txtName);
        txtDisplayName = convertView.findViewById(R.id.txtDisplayName);

        txtName.setText(f.getName());
        txtDisplayName.setText(f.getPrimary_name());

        byte[] img = danhsach.get(position).getAvatar();

        Bitmap bitmap = helper.ConvertImageToBitmap(img);
        avatar.setImageBitmap(bitmap);

        return convertView;
    }
}
