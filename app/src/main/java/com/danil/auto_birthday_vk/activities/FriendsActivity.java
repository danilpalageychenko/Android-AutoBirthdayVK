package com.danil.auto_birthday_vk.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danil.auto_birthday_vk.BirthComparator;
import com.danil.auto_birthday_vk.R;
import com.danil.auto_birthday_vk.UserInfo;
import com.danil.auto_birthday_vk.db.DBHelper;
import com.danil.auto_birthday_vk.tools_and_constants.DBConstants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class FriendsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private RelativeLayout mRelativeLayout;

    private ListView friendsList;
    private static ArrayList<UserInfo> data;


    SimpleDateFormat year = new SimpleDateFormat("yyyy");
    String currentYear = year.format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        getSupportActionBar().setTitle("Мои друзья");



        initView();

        Snackbar.make(mRelativeLayout, "Authorized successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        VKRequest request = VKApi.friends().get(VKParameters.from(
                VKApiConst.FIELDS, "photo_100,online,bdate",
                VKApiConst.NAME_CASE, "nom",
                "order", "hints"));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    parseJson(response.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BirthComparator birthComparator = new BirthComparator();

                Collections.sort(data,birthComparator);

                FriendsAdapter adapter = new FriendsAdapter(getApplicationContext());
                friendsList.setAdapter(adapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                VKSdk.logout();
                startLoginActivity();
                break;
            case R.id.action_banned:
                Intent intent1 = new Intent(this, BannedFriends.class);
                startActivity(intent1);

        }

        return super.onOptionsItemSelected(item);
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginInActivity.class));
        finish();
    }

    private void initView() {
        mRelativeLayout = (RelativeLayout) findViewById(R.id.coordinatorLayout);

       friendsList = (ListView) findViewById(R.id.friendsList);
        friendsList.setOnItemClickListener(FriendsActivity.this);


        data = new ArrayList<>();
    }

    private void parseJson(JSONObject json) throws JSONException {
        try {
            JSONArray allFriends = json.getJSONObject("response").getJSONArray("items");
            for (int i = 0; i < allFriends.length(); i++) {
                JSONObject friend;
                friend = allFriends.getJSONObject(i);
                int id = friend.getInt("id");
                String name = friend.getString("first_name");
                String lastname = friend.getString("last_name");
                String photoUrl = friend.getString("photo_100");
                String bdate = friend.optString("bdate", null);


                if (bdate == null) {
                    continue;
                }
                boolean online = friend.getInt("online") == 1;

                String fullName = name + " " + lastname;

                SimpleDateFormat curFormater = new SimpleDateFormat("dd.MM", Locale.ENGLISH);
                Date dateObj = curFormater.parse(bdate);
                SimpleDateFormat postFormater = new SimpleDateFormat("d MMMM");

                String newDateStr = postFormater.format(dateObj);

                bdate = newDateStr;

                UserInfo newFriend = new UserInfo(id, fullName, photoUrl, online, bdate);

                data.add(newFriend);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить друга в список исключений?");
        builder.setMessage("Друг больше не будет поздравляться!");

        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ContentValues values = new ContentValues();
                DBHelper dbHelper = new DBHelper(FriendsActivity.this);

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_USER_ID, data.get(position).getId());
                values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_YEAR, currentYear);
                values.put(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY_FIELD_NAME, data.get(position).getName());


                db.insert(DBConstants.TABLE_FRIENDS_WHO_HAD_BIRTHDAY, null, values);

                db.close();

                Toast.makeText(FriendsActivity.this, data.get(position).getName() + " больше не будет поздравляться.", Toast.LENGTH_LONG).show();


            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

   public class FriendsAdapter extends BaseAdapter {


        private Context mContext;


        public FriendsAdapter(Context context) {
            mContext = context;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return data.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.friends_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (data.get(position).isOnline()) {
                viewHolder.isOnline.setImageResource(R.drawable.isonline);
            } else {
                viewHolder.isOnline.setImageResource(R.drawable.isoffline);
            }

            String date = data.get(position).getBdate();

            BirthComparator bc = new BirthComparator();

            viewHolder.txtFullName.setText(data.get(position).getName());

            DateFormat format = new SimpleDateFormat("d MMMM");

            Date date1 = null;
            try {
                date1 = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dayMonth = new SimpleDateFormat("d MMMM");
            String currentDayAndMonth = dayMonth.format(new Date());
            Date current = null;
            try {
                current = format.parse(currentDayAndMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar diff1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            diff1.setTimeInMillis(date1.getTime() - current.getTime());
            String daysTillBday1 = bc.getCoolTime(diff1);


            viewHolder.daysTillBDay.setText("Дней до ДР: " + daysTillBday1);
            viewHolder.bDate.setText(date);
            Picasso.with(parent.getContext()).load(data.get(position).getImageUrl()).transform(new CircularTransformation(48)).into(viewHolder.imgAvatar);


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

    }

    class ViewHolder {
        public ImageView imgAvatar;

        public ViewHolder(View view) {
            imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            txtFullName = (TextView) view.findViewById(R.id.txtFullName);
            bDate = (TextView) view.findViewById(R.id.bDate);
            isOnline = (ImageView) view.findViewById(R.id.isonline);
            cake = (ImageView) view.findViewById(R.id.cake);
            daysTillBDay = (TextView) view.findViewById(R.id.daysTillBday);

        }

        TextView txtFullName;
        TextView bDate;
        ImageView isOnline;
        ImageView cake;
        TextView daysTillBDay;

    }
}