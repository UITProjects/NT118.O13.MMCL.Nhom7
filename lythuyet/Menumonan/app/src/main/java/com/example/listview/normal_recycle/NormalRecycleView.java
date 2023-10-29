package com.example.listview.normal_recycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listview.R;

import java.util.ArrayList;
import java.util.Objects;

public class NormalRecycleView extends AppCompatActivity {
    RecyclerView normal_RecycleView;
    private MenuAdapter adapter;
    String type;
    ImageView back_arrow;
    private ArrayList<MonAn> planet_ArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("type");

        if (Objects.equals(type, "mon_man")|| Objects.equals(type,"mon_canh")||Objects.equals(type,"mon_xao")){
            setContentView(R.layout.thongtinchitiet);
        }
        else
            setContentView(R.layout.category_menu);
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        normal_RecycleView = findViewById(R.id.normal_recycleview);
        normal_RecycleView.setLayoutManager(new LinearLayoutManager(this));
        planet_ArrayList = new ArrayList<>();
        adapter = new MenuAdapter(this,planet_ArrayList,this);
        normal_RecycleView.setAdapter(adapter);
        normal_RecycleView.addItemDecoration(new MyItemDecoration(this));
        create_listdata();

    }
    public class MyItemDecoration extends RecyclerView.ItemDecoration {

        private final int decorationHeight;
        private Context context;

        public MyItemDecoration(Context context) {
            this.context = context;
            decorationHeight = 100;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent != null && view != null) {

                int itemPosition = parent.getChildAdapterPosition(view);
                int totalCount = parent.getAdapter().getItemCount();

                if (itemPosition >= 0 && itemPosition < totalCount - 1) {
                    outRect.bottom = decorationHeight;
                }

            }

        }
    }

    private void create_listdata() {
        adapter.notifyDataSetChanged();
        if (Objects.equals(type, "mon_man")){
            MonAn planet = new MonAn("Sườn nướng",12000,15000,R.drawable.suonnuong,1);
            planet_ArrayList.add(planet);
            planet = new MonAn("Gà kho",15000,15000,R.drawable.gakho,5);
            planet_ArrayList.add(planet);
            planet = new MonAn("Thịt kho trứng",12000,12000,R.drawable.thitkhotrung,(float)1.4);
            planet_ArrayList.add(planet);
        } else if(Objects.equals(type,"mon_canh")){
            MonAn planet = new MonAn("canh bầu",7000,10000,R.drawable.canhbau,(float)3.5);
            planet_ArrayList.add(planet);
            planet = new MonAn("Canh măng",8000,9000,R.drawable.canhmang,5);
            planet_ArrayList.add(planet);
            planet = new MonAn("Canh cải",9000,10000,R.drawable.canhcai,(float)1.9);
            planet_ArrayList.add(planet);
            planet = new MonAn("Canh rau muống",10000,11000,R.drawable.canhraumuong,(float)5);
            planet_ArrayList.add(planet);
        }
        else if(Objects.equals(type,"mon_xao")){
            MonAn planet = new MonAn("Mì xào bò",7000,10000,R.drawable.mixaobo,(float)3.5);
            planet_ArrayList.add(planet);
            planet = new MonAn("Hủ tíu xào bò",8000,9000,R.drawable.hutiuxao,5);
            planet_ArrayList.add(planet);
            planet = new MonAn("Nuôi xào bò",9000,10000,R.drawable.nuoixaobo,(float)1.9);
            planet_ArrayList.add(planet);
            planet = new MonAn("Thịt bò xào giá đỡ",30000,31000,R.drawable.thitboxaogiado,(float)5);
            planet_ArrayList.add(planet);
        }
        else{
            MonAn planet = new MonAn("Món mặn",5,5,R.drawable.monman_image);
            planet_ArrayList.add(planet);
            planet = new MonAn("Món canh",10,5,R.drawable.moncanh);
            planet_ArrayList.add(planet);
            planet = new MonAn("Món Xào",10,5,R.drawable.monxao);
            planet_ArrayList.add(planet);
        }



    }
}