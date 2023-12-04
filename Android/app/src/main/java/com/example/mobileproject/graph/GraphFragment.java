package com.example.mobileproject.graph;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.R;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;


public class GraphFragment extends Fragment {

    View graph_View;
    public static long last_time ;
    public static int axis_x_format;
    public static int mode;
    Paint paint;

    Button show_btn;
    TextView realtime_txtview,history_txtview;
    String token;
    Map<Date,Float> data;
    GraphView graphView;
    LineGraphSeries<DataPoint> series;
    Handler ui_handler = new Handler();
    TextView mode_edt ;
    Fragment realtime_fragment,history_fragment;
    Spinner attribute_spinner;
    void replaceFragment(Fragment fragment){
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layout_fragment,fragment);
        transaction.commit();
    }

    void hideFragment(Fragment fragment){
        if(fragment.isHidden())
            return;
        FragmentManager manager  = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();

    }

    @Nullable
    @Override
    public View getView() {
        return graphView;
    }

    @Override
    public void onResume() {
        show_btn.setVisibility(View.VISIBLE);
        graphView.setVisibility(View.VISIBLE);
        mode_edt.setVisibility(View.VISIBLE);
        attribute_spinner.setVisibility(View.VISIBLE);

        super.onResume();

    }

    @Override
    public void onStart() {
        show_btn.setVisibility(View.GONE);
        graphView.setVisibility(View.GONE);
        mode_edt.setVisibility(View.GONE);
        attribute_spinner.setVisibility(View.GONE);
        super.onStart();
    }

    @Override
    public void onPause() {
        show_btn.setVisibility(View.GONE);
        graphView.setVisibility(View.GONE);
        mode_edt.setVisibility(View.GONE);
        attribute_spinner.setVisibility(View.GONE);
        super.onPause();
    }

    void showFragment(Fragment fragment){
        if(!fragment.isHidden())
            return;
        FragmentManager manager  = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        graph_View =  inflater.inflate(R.layout.fragment_graph_fragment, container, false);
        show_btn = graph_View.findViewById(R.id.btn_show);
        show_btn.setVisibility(View.GONE);
        mode_edt= graph_View.findViewById(R.id.edt_mode);

        GraphFragment.mode = 0;
        realtime_fragment = new Realtime();
        history_fragment = new History();

        mode_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realtime_txtview.setVisibility(View.VISIBLE);
                history_txtview.setVisibility(View.VISIBLE);
                show_btn.setVisibility(View.VISIBLE);
                replaceFragment( realtime_fragment);
                showFragment(realtime_fragment);
            }
        });






        graphView = graph_View.findViewById(R.id.idGraphView);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));

        graphView.setTitleColor(R.color.yellow);


        graphView.setCursorMode(true);


        graphView.setTitleColor(R.color.yellow);


        graphView.setTitleTextSize(30);

        DefaultLabelFormatter custom_formatter = new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis((long)value);
                    if (GraphFragment.axis_x_format==0)
                        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)).concat(":00");
                    else if(GraphFragment.axis_x_format==1)
                        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)).concat("/"+String.valueOf(calendar.get(Calendar.MONTH))).concat(" "+String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))+":00");
                }
                DecimalFormat df = new DecimalFormat("0.00");
                return df.format(value);
            }
        };


        DateAsXAxisLabelFormatter test = new DateAsXAxisLabelFormatter(graph_View.getContext());




















        token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoREkwZ2hyVlJvaE5zVy1wSXpZeDBpT2lHMzNlWjJxV21sRk4wWGE1dWkwIn0.eyJleHAiOjE3MDE3MDY1NjAsImlhdCI6MTcwMTYyMDE2MCwianRpIjoiNjg3MTYwNzItMDNmMy00MTNlLWI3ODktNTAxOTdmMWYzNjg0IiwiaXNzIjoiaHR0cHM6Ly91aW90Lml4eGMuZGV2L2F1dGgvcmVhbG1zL21hc3RlciIsImF1ZCI6WyJzdHJpbmctcmVhbG0iLCJtYXN0ZXItcmVhbG0iLCJhY2NvdW50Il0sInN1YiI6ImM2YzY0MzlkLWFiOWEtNDhiZC05ZTNhLTRmNDk5MDhkMTZkZCIsInR5cCI6IkJlYXJlciIsImF6cCI6Im9wZW5yZW1vdGUiLCJzZXNzaW9uX3N0YXRlIjoiY2Q5MjExNWEtMDFkOC00OTVmLTg2OGUtNzJhM2JjYzc5MmMyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwczovL3Vpb3QuaXh4Yy5kZXYiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsic3RyaW5nLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInF1ZXJ5LXJlYWxtcyIsInZpZXctYXV0aG9yaXphdGlvbiIsInF1ZXJ5LWNsaWVudHMiLCJxdWVyeS11c2VycyIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIiwicXVlcnktZ3JvdXBzIl19LCJvcGVucmVtb3RlIjp7InJvbGVzIjpbIndyaXRlOmxvZ3MiLCJyZWFkIiwid3JpdGU6YXNzZXRzIiwid3JpdGU6YWRtaW4iLCJyZWFkOmxvZ3MiLCJyZWFkOm1hcCIsInJlYWQ6YXNzZXRzIiwid3JpdGU6dXNlciIsInJlYWQ6dXNlcnMiLCJ3cml0ZTpydWxlcyIsInJlYWQ6cnVsZXMiLCJyZWFkOmluc2lnaHRzIiwid3JpdGU6YXR0cmlidXRlcyIsIndyaXRlIiwid3JpdGU6aW5zaWdodHMiLCJyZWFkOmFkbWluIl19LCJtYXN0ZXItcmVhbG0iOnsicm9sZXMiOlsidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJ2aWV3LXJlYWxtIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJjcmVhdGUtY2xpZW50IiwibWFuYWdlLXVzZXJzIiwicXVlcnktcmVhbG1zIiwidmlldy1hdXRob3JpemF0aW9uIiwicXVlcnktY2xpZW50cyIsInF1ZXJ5LXVzZXJzIiwibWFuYWdlLWV2ZW50cyIsIm1hbmFnZS1yZWFsbSIsInZpZXctZXZlbnRzIiwidmlldy11c2VycyIsInZpZXctY2xpZW50cyIsIm1hbmFnZS1hdXRob3JpemF0aW9uIiwibWFuYWdlLWNsaWVudHMiLCJxdWVyeS1ncm91cHMiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsInNpZCI6ImNkOTIxMTVhLTAxZDgtNDk1Zi04NjhlLTcyYTNiY2M3OTJjMiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlN5c3RlbSBBZG1pbmlzdHJhdG9yIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJnaXZlbl9uYW1lIjoiU3lzdGVtIiwiZmFtaWx5X25hbWUiOiJBZG1pbmlzdHJhdG9yIn0.mCuhqGHBsXBuukw2H3wg1ilJD-wKm1x3FnUkxUqnDHidtEszEXGxdisBf4DlAYps3HHCfPr6DSQbGTrtKGOgEyv9Y7ubyLuKtv2QF_qPReSVjtm7Yol6IMLEb6exs-p29jiDULL1S2v2rQhQZJDSwmQRacgK5DfohZApTRrU-EfrKeL3JKLGNSuXBHkexOY3m8OaQPgRdAauZu5KRuvO6N4ZBf9zNVL1XRoiC5Gz_0a_ZGN1QFYZrmiy-6_vkgKpqV6K-aYuw1Z1OGZ6PaS7YNzNxL3FOUdlB1Sz2XlcsYhHH9ITw7NXQWtMZh51Gk4n5qX1LKyFLAU2-Y6uK8NX4g";
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Show", String.valueOf(GraphFragment.last_time));
                if (GraphFragment.mode == 0){
                    Map<String,String> query = new HashMap<>();
                    query.put("attributeRefs","[{\"id\":\"5zI6XqkQVSfdgOrZ1MyWEf\",\"name\":\"temperature\"}]\n");
                    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
                    long to_timestamp =  calendar.getTimeInMillis();
                    long from_timestamp = to_timestamp- GraphFragment.last_time;
                    query.put("fromTimestamp",String.valueOf(from_timestamp));
                    query.put("toTimestamp",String.valueOf(to_timestamp));
                    Thread data_thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ExportdataAPI export_data = new ExportdataAPI("https://uiot.ixxc.dev/api/master/asset/datapoint/export","GET",query,token);
                                data = export_data.GetData();
                                int stop  = 0;
                                SortedSet<Date> keys = new TreeSet<>(data.keySet());
                                DataPoint[] temp = new DataPoint[keys.size()];
                                int count = 0 ;
                                for (Date key :keys){
                                    temp[count] = new DataPoint(key,data.get(key));
                                    count++;
                                }
                                if (GraphFragment.mode == 0)
                                    graphView.setTitle("Temperature");
                                series = new LineGraphSeries<>(temp);
                                ui_handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        graphView.removeAllSeries();
                                        series.setCustomPaint(paint);
                                        series.setDrawDataPoints(true);
                                        series.setDataPointsRadius(10);
                                        graphView.getViewport().setYAxisBoundsManual(true);
                                        graphView.getViewport().setMinY(0);
                                        graphView.getViewport().setMaxY(35);
                                        graphView.addSeries(series);
                                        graphView.setTitleTextSize(50);
                                        graphView.getViewport().setXAxisBoundsManual(true);
                                        graphView.getViewport().setMinX(temp[0].getX());
                                        graphView.getViewport().setMaxX(temp[temp.length-1].getX()+(double) 2*3600*1000);
                                        graphView.getViewport().setScalable(true);
                                        graphView.getGridLabelRenderer().setLabelFormatter(custom_formatter);


                                    }
                                });


                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    });
                    data_thread.start();
                    show_btn.setVisibility(View.GONE);
                    realtime_txtview.setVisibility(View.GONE);
                    history_txtview.setVisibility(View.GONE);
                    hideFragment(realtime_fragment);



                }





            }
        });


        realtime_txtview = graph_View.findViewById(R.id.txtview_realtime);
        realtime_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(realtime_fragment);
            }
        });


        history_txtview = graph_View.findViewById(R.id.txtview_history);
        history_txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(history_fragment);
                showFragment(history_fragment);
            }
        });


















        attribute_spinner = graph_View.findViewById(R.id.spinner_attribute);
        attribute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                ((TextView) parent.getChildAt(0)).setTextSize(20);

                Toast.makeText(view.getContext(), item, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> attribute_ArrayList = new ArrayList<>();
        attribute_ArrayList.add("Temperature");
        attribute_ArrayList.add("Humidity");
        attribute_ArrayList.add("Rainfall");
        ArrayAdapter<String> attribute_adapter = new ArrayAdapter<>(graph_View.getContext(), android.R.layout.simple_spinner_item,attribute_ArrayList);
        attribute_adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        attribute_spinner.setAdapter(attribute_adapter);


        return graph_View;
    }


}