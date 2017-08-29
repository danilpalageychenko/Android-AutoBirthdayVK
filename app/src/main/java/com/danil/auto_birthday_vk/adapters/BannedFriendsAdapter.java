package com.danil.auto_birthday_vk.adapters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.UserInfo;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BannedFriendsAdapter extends BaseAdapter{
    private ArrayList<UserInfo> m_arrData;

    public BannedFriendsAdapter(ArrayList<UserInfo> arrData){

        m_arrData = arrData;

    }

    @Override
    public int getCount() {
        return m_arrData.size();
    }

    @Override
    public Object getItem(int position) {
        return m_arrData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((UserInfo)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.banned_friends_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       // Picasso.with(parent.getContext()).load(m_arrData.get(position).getImageUrl()).transform(new CircularTransformation(48)).into(viewHolder.imgAvatar);
        viewHolder.txtFullName.setText(m_arrData.get(position).getName());

        return convertView;
    }

    public class CircularTransformation implements Transformation {

        private int mRadius = 10;

        public CircularTransformation(final int radius) {
            this.mRadius = radius;
        }

        @Override
        public Bitmap transform(Bitmap source) {
            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);

            canvas.drawARGB(0, 0, 0, 0);

            paint.setColor(Color.parseColor("#BAB399"));

            if (mRadius == 0) {
                canvas.drawCircle(source.getWidth() / 2 + 0.7f, source.getHeight() / 2 + 0.7f,
                        source.getWidth() / 2 - 1.1f, paint);
            } else {
                canvas.drawCircle(source.getWidth() / 2 + 0.7f, source.getHeight() / 2 + 0.7f,
                        mRadius, paint);
            }

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            canvas.drawBitmap(source, rect, rect, paint);

            if (source != output) {
                source.recycle();
            }
            return output;
        }

        @Override
        public String key() {
            return "circular" + String.valueOf(mRadius);
        }
    }

    class ViewHolder {
        public ImageView imgAvatar;

        public ViewHolder(View view) {

            txtFullName = (TextView) view.findViewById(R.id.bannedFriendsTextView);

        }

        TextView txtFullName;


    }

}

