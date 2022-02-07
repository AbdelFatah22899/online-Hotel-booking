package com.example.a2020_android_training;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class review extends AppCompatActivity {

    Vector<String> v=new Vector<>();
    hotel_database hotel_db=new hotel_database(this);
    hotel_requests requests_db=new hotel_requests(this);
    Cursor hotel_cursor;
    String gmail;

    ImageView image;    TextView hotel_name, total_rate;    RatingBar total_rate_bar, Quality_bar, review_bar;      ListView reservations_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        gmail=getIntent().getExtras().getString("gmail");

        Cursor c= hotel_db.retrieve_all_data("name");
        for(int i=0; i<c.getCount(); i++) {
            v.add(c.getString(0));
            c.moveToNext();
        }
        final Spinner hotel_items=(Spinner) findViewById(R.id.review_hotel_items);
        ArrayAdapter<String> hotel_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        hotel_adapter.addAll(v);
        hotel_items.setAdapter(hotel_adapter);

            image=(ImageView) findViewById(R.id.review_logo);
            hotel_name=(TextView) findViewById(R.id.review_hotel_name);
            total_rate=(TextView) findViewById(R.id.review_total_rate);
            total_rate_bar=(RatingBar) findViewById(R.id.total_review_bar);
            Quality_bar=(RatingBar) findViewById(R.id.quality_bar);
            review_bar=(RatingBar)  findViewById(R.id.review_bar);
            reservations_list=(ListView) findViewById(R.id.reservations_list);


            total_rate_bar.setEnabled(false);
            Quality_bar.setEnabled(false);
        hotel_items.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hotel_cursor=hotel_db.get_hotel(hotel_items.getSelectedItem().toString());
                draw(hotel_cursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reservations_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(review_bar.getRating() >0) {
                    reservations_cursor.moveToPosition(position);
                    requests_db.update_reservation(reservations_cursor.getString(0), reservations_cursor.getString(1), reservations_cursor.getString(2), reservations_cursor.getString(3), reservations_cursor.getString(4), Integer.parseInt(reservations_cursor.getString(5)), review_bar.getRating());


                    Cursor cu=requests_db.retrieve_all_data(reservations_cursor.getString(0));
                    float review=re_review_hotel(requests_db.retrieve_all_data(reservations_cursor.getString(0)));

                    hotel_db.update_Hotel(hotel_cursor.getString(0), hotel_cursor.getString(0),Integer.parseInt(hotel_cursor.getString(1)), Integer.parseInt(hotel_cursor.getString(2)), Integer.parseInt(hotel_cursor.getString(3)), Integer.parseInt(hotel_cursor.getString(4)), Integer.parseInt(hotel_cursor.getString(5)), review, hotel_cursor.getString(7), Integer.parseInt(hotel_cursor.getString(8)));
                    Toast.makeText(review.this, "Your have added review: "+review_bar.getRating()+ "and it becomes "+String.valueOf(review), Toast.LENGTH_LONG).show();
                    draw(hotel_cursor);
                }
                else
                {
                    Toast.makeText(review.this, "click your review", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public float re_review_hotel(Cursor Requests)
    {
        float sum=0;
        float count=0;
        for(int i=0; i<Requests.getCount(); i++)
        {
            if(Float.parseFloat(Requests.getString(6))>0)
            {
                count++;
            }
            sum+=Float.parseFloat(Requests.getString(6));
            Requests.moveToNext();
        }
        if(count>0)
            return (sum/count);
        else
            return 0;
    }


   List<Map<String, String>> Adapter_list;
    Map<String, String> item;
    Cursor reservations_cursor;
    private void draw(Cursor hotel_cursor)
    {
        image.setImageResource(Integer.parseInt(hotel_cursor.getString(8)));
        hotel_name.setText(hotel_cursor.getString(0));
        total_rate.setText("Rate of "+String.valueOf(requests_db.reviews_number(hotel_cursor.getString(0)))+": ");
        total_rate_bar.setRating(Float.parseFloat(hotel_cursor.getString(6)));
        Quality_bar.setRating(Float.parseFloat(hotel_cursor.getString(5)));

        reservations_cursor=requests_db.retrieve_gmail_hotel(gmail, hotel_cursor.getString(0));
        Adapter_list=new ArrayList<>();
        for(int i=0; i<reservations_cursor.getCount(); i++)
        {
            item = new HashMap<String,String>();
            item.put( "line1", reservations_cursor.getString(2));
            item.put( "line2", reservations_cursor.getString(3));
            item.put( "line3", reservations_cursor.getString(4));
            Adapter_list.add( item );
            reservations_cursor.moveToNext();
        }
        SimpleAdapter sa = new SimpleAdapter(this, Adapter_list,
                R.layout.row,
                new String[] { "line1","line2", "line3" },
                new int[] {R.id.textView, R.id.textView2, R.id.textView3});
        reservations_list.setAdapter(sa);
    }





}
class VerticalScrollview extends ScrollView {

    public VerticalScrollview(Context context) {
        super(context);
    }

    public VerticalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.i("VerticalScrollview", "onInterceptTouchEvent: DOWN super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                return false; // redirect MotionEvents to ourself

            case MotionEvent.ACTION_CANCEL:
                Log.i("VerticalScrollview", "onInterceptTouchEvent: CANCEL super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_UP:
                Log.i("VerticalScrollview", "onInterceptTouchEvent: UP super false" );
                return false;

            default: Log.i("VerticalScrollview", "onInterceptTouchEvent: " + action ); break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        Log.i("VerticalScrollview", "onTouchEvent. action: " + ev.getAction() );
        return true;
    }
}