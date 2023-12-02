package com.example.mobileproject.graph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobileproject.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

public class Graph extends AppCompatActivity {
    Calendar date;
    public static long last_time ;
    public static int mode;
    Paint paint;

    Button show_btn;
    TextView realtime_txtview,history_txtview;
    String token;
    Map<Calendar,Float> data;
    GraphView graphView;
    LineGraphSeries<DataPoint> series;
    Handler ui_handler = new Handler();

    void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mode_fragment,fragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        show_btn = findViewById(R.id.btn_show);






        graphView = findViewById(R.id.idGraphView);
        graphView.setCursorMode(true);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        // on below line we are adding data to our graph view.

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.yellow);

        // on below line we are setting
        // our title text size.
        graphView.setScrollBarSize(30);


        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.yellow);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(18);

        // on below line we are adding
        // data series to our graph view.



















        token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDE1ODY4NjMsImlhdCI6MTcwMTUwMDQ2MywianRpIjoiMzAxOWU0ZWQtMmRlNS00NzdlLTljMTUtODIwMGVmOGZkNWI3IiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6WyJzdHJpbmctcmVhbG0iLCJtYXN0ZXItcmVhbG0iLCJhY2NvdW50Il0sInN1YiI6ImM2YzY0MzlkLWFiOWEtNDhiZC05ZTNhLTRmNDk5MDhkMTZkZCIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiMmMxMzkzYTktOTk2Zi00M2NhLThkZGEtMWY4MDUyNTdkN2MyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3RyaW5nLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJvcGVucmVtb3RlIjp7InJvbGVzIjpbIndyaXRlOmxvZ3MiLCJyZWFkIiwid3JpdGU6YXNzZXRzIiwid3JpdGU6YWRtaW4iLCJyZWFkOmxvZ3MiLCJyZWFkOm1hcCIsInJlYWQ6YXNzZXRzIiwid3JpdGU6dXNlciIsInJlYWQ6dXNlcnMiLCJ3cml0ZTpydWxlcyIsInJlYWQ6cnVsZXMiLCJyZWFkOmluc2lnaHRzIiwid3JpdGU6YXR0cmlidXRlcyIsIndyaXRlIiwid3JpdGU6aW5zaWdodHMiLCJyZWFkOmFkbWluIl19LCJtYXN0ZXItcmVhbG0iOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwicXVlcnktcmVhbG1zIiwidmlldy1hdXRob3JpemF0aW9uIiwicXVlcnktY2xpZW50cyIsInF1ZXJ5LXVzZXJzIiwibWFuYWdlLWV2ZW50cyIsIm1hbmFnZS1yZWFsbSIsInZpZXctZXZlbnRzIiwidmlldy11c2VycyIsInZpZXctY2xpZW50cyIsIm1hbmFnZS1hdXRob3JpemF0aW9uIiwibWFuYWdlLWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6IjJjMTM5M2E5LTk5NmYtNDNjYS04ZGRhLTFmODA1MjU3ZDdjMiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlN5c3RlbSBBZG1pbmlzdHJhdG9yIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJnaXZlbl9uYW1lIjoiU3lzdGVtIiwiZmFtaWx5X25hbWUiOiJBZG1pbmlzdHJhdG9yIn0.ax4GEOqBOUcabqdUgblF8sv6SWbIBlp7iUUz_WGIfgMgnZOfefwC_Sln6xo7LutYQj7_YIC13NToYLyZ7qFpEAwDRYJO06988PbAhDv6N7yVMvuRsxtu11Nzh1tIPqHT8xIUqnchTrYrby5JpIjKGRG_igP0I_0z8cp_b11hLIIJwp6SjQVNCWyHd5aJzGFzoTGFn7COXfuTnEzJoSOQ18BU_w1viDlmY-6xyeFssCf21YvkEucCJGacllW4xKM9MG9GFePKGDcBQIXuCGsGFu0A6Qijc2djrU6KtFLdhdwIy4kpZhTUBsaFJJmIZjLxsgwykhrzHu0RjVbBLSdl4w";
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Show", String.valueOf(Graph.last_time));
                Toast.makeText(getApplicationContext(),String.valueOf(Graph.last_time),Toast.LENGTH_SHORT).show();
                if (Graph.mode == 0){
                    Map<String,String> query = new HashMap<>();
                    query.put("attributeRefs","[{\"id\":\"5zI6XqkQVSfdgOrZ1MyWEf\",\"name\":\"temperature\"}]\n");
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
                    long to_timestamp =  calendar.getTimeInMillis();
                    long from_timestamp = to_timestamp-Graph.last_time;
                    query.put("fromTimestamp",String.valueOf(from_timestamp));
                    query.put("toTimestamp",String.valueOf(to_timestamp));
                    Thread data_thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                data = new ExportdataAPI("https://uiot.ixxc.dev/api/master/asset/datapoint/export","GET",query,token).GetData();
                                int stop  = 0;
                                SortedSet<Calendar> keys = new TreeSet<>(data.keySet());
                                DataPoint[] temp = new DataPoint[keys.size()];
                                int count = 0 ;
                                for (Calendar key :keys){
                                    temp[count] = new DataPoint(key.get(11),data.get(key));
                                    count++;

                                }
                                if (Graph.mode == 0)
                                    graphView.setTitle("Temperature");
                                series = new LineGraphSeries<>(temp);
                                ui_handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        graphView.removeAllSeries();
                                        series.setCustomPaint(paint);
                                        series.setThickness(20);
                                        graphView.setTitleTextSize(50);
                                        series.setDrawDataPoints(true);
                                        series.setDataPointsRadius(10);
                                        graphView.getViewport().setYAxisBoundsManual(true);
                                        graphView.getViewport().setMinY(20);
                                        graphView.getViewport().setMaxY(40);
                                        graphView.getGridLabelRenderer().;
                                        graphView.addSeries(series);


                                    }
                                });


                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    });
                    data_thread.start();


                }





            }
        });


        realtime_txtview = findViewById(R.id.txtview_realtime);
        realtime_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Realtime());
            }
        });


        history_txtview = findViewById(R.id.txtview_history);
        history_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new History());
            }
        });


















        Spinner attribute_spinner = findViewById(R.id.spinner_attribute);
        attribute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextSize(25);

                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> attribute_ArrayList = new ArrayList<>();
        attribute_ArrayList.add("Temperature");
        attribute_ArrayList.add("Humidity");
        attribute_ArrayList.add("Rainfall");
        ArrayAdapter<String> attribute_adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,attribute_ArrayList);
        attribute_adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        attribute_spinner.setAdapter(attribute_adapter);





        String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDEzMDg0MjcsImlhdCI6MTcwMTIyMjAyNywianRpIjoiYzQ5MDVhZjMtOTExMC00YWU2LWJjOGQtNWZiZTAwYWY4YWNiIiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6WyJzdHJpbmctcmVhbG0iLCJtYXN0ZXItcmVhbG0iLCJhY2NvdW50Il0sInN1YiI6ImM2YzY0MzlkLWFiOWEtNDhiZC05ZTNhLTRmNDk5MDhkMTZkZCIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiZGZmODg0OWMtN2M1Ni00NmZhLWE5ZTMtMmU2ZTA3M2FjNWZmIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3RyaW5nLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJvcGVucmVtb3RlIjp7InJvbGVzIjpbIndyaXRlOmxvZ3MiLCJyZWFkIiwid3JpdGU6YXNzZXRzIiwid3JpdGU6YWRtaW4iLCJyZWFkOmxvZ3MiLCJyZWFkOm1hcCIsInJlYWQ6YXNzZXRzIiwid3JpdGU6dXNlciIsInJlYWQ6dXNlcnMiLCJ3cml0ZTpydWxlcyIsInJlYWQ6cnVsZXMiLCJyZWFkOmluc2lnaHRzIiwid3JpdGU6YXR0cmlidXRlcyIsIndyaXRlIiwid3JpdGU6aW5zaWdodHMiLCJyZWFkOmFkbWluIl19LCJtYXN0ZXItcmVhbG0iOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwicXVlcnktcmVhbG1zIiwidmlldy1hdXRob3JpemF0aW9uIiwicXVlcnktY2xpZW50cyIsInF1ZXJ5LXVzZXJzIiwibWFuYWdlLWV2ZW50cyIsIm1hbmFnZS1yZWFsbSIsInZpZXctZXZlbnRzIiwidmlldy11c2VycyIsInZpZXctY2xpZW50cyIsIm1hbmFnZS1hdXRob3JpemF0aW9uIiwibWFuYWdlLWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImRmZjg4NDljLTdjNTYtNDZmYS1hOWUzLTJlNmUwNzNhYzVmZiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlN5c3RlbSBBZG1pbmlzdHJhdG9yIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJnaXZlbl9uYW1lIjoiU3lzdGVtIiwiZmFtaWx5X25hbWUiOiJBZG1pbmlzdHJhdG9yIn0.pH_v-QGC7P4n6u1F2_v9iCz08yYTZ1byegLvT8GPV0LbxXtD72Lf3eiwoUa72FCHMoy99GdpkJhYgOfMNyO2dDXnOWHWyV4qfUYmG9BgXbmeT9kP4W7-MaXWNW-uYAZdJOtKOiVxc28z_RGUIPLt9xtmEF7viotv4gBBdm1czsaY49w40FWRj1INc9oK5r2siNqzPsqx55Mwk8_AoLfvjlN3qqqmEEcxF9FoC94qgKvki3KVsSgy7S9cJ2ZBKkTLBilEBgEIj5WGbojtGFZxkaJZtZ5iE_8p4dDzv6JvBC6WYkPuGU2qmZiE7tZ19afko_SI7paAXRZDADJ1V8x3Rg";
        Map<String,String> query = new HashMap<>();
        query.put("fromTimestamp","1696166340000");
        query.put("toTimestamp","1701088289000");
        query.put("attributeRefs","[{\"id\":\"5zI6XqkQVSfdgOrZ1MyWEf\",\"name\":\"temperature\"}]");


        Map<Timestamp,Float> datapoint;
            Thread export_Thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                       ExportdataAPI export = new ExportdataAPI("https://uiot.ixxc.dev/api/master/asset/datapoint/export",
                                "GET",
                                query,
                                TOKEN
                        );
                        Map<Calendar,Float> datapoint = export.GetData();
                        String a = "3";

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
          //  export_Thread.start();




    }
}