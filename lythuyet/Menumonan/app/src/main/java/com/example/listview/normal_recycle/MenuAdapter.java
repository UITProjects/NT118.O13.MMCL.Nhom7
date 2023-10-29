package com.example.listview.normal_recycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listview.R;

import java.util.ArrayList;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.PlanetHolder>  {
    private Context context;
    private ArrayList<MonAn> monans;
    public Activity current_activity;

    public MenuAdapter(Context context, ArrayList<MonAn> planets, Activity current_activity) {
        this.context = context;
        this.monans = planets;
        this.current_activity = current_activity;
    }

    @NonNull
    @Override
    public MenuAdapter.PlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.monan_layout,parent,false
        );
        return new PlanetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.PlanetHolder holder, int position) {
        MonAn monan = monans.get(position);
        holder.SetDetails(monan);
        if (!monan.thongtinchitiet)
            holder.set_button_click(position,current_activity);

    }

    @Override
    public int getItemCount() {
        return monans.size();
    }
    static class PlanetHolder extends RecyclerView.ViewHolder{
        private TextView tenmonan, soluong, soluonggiamgia,giahientai,giachuagiam;
        private ImageView forward_click;
        private ImageView monan_ImageView;
        private RatingBar ratingBar;
        private ImageView salesicon_ImageView;
        private ImageView muong_icon_ImageView;
        private ImageView back_arrow;
        View itemView;
        public PlanetHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tenmonan = itemView.findViewById(R.id.planet_Txt);
            soluong = itemView.findViewById(R.id.distance_Txt);
            soluonggiamgia = itemView.findViewById(R.id.dimater_Txt);
            forward_click = itemView.findViewById(R.id.forward_clickk);
            monan_ImageView = itemView.findViewById(R.id.monan_ImageView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            giachuagiam = itemView.findViewById(R.id.giachuagiam_TextView);
            giahientai = itemView.findViewById(R.id.giahientai_TextView);
            salesicon_ImageView = itemView.findViewById(R.id.sales_icon);
            muong_icon_ImageView = itemView.findViewById(R.id.muong_icon);
            back_arrow = itemView.findViewById(R.id.back_arrow);

        }
        void set_button_click(int position, Activity current_activity){
            forward_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mon_ans_Intent = new Intent(forward_click.getContext(), NormalRecycleView.class);
                    if (position==0)
                        mon_ans_Intent.putExtra("type","mon_man");
                    else if(position==1)
                        mon_ans_Intent.putExtra("type","mon_canh");
                    else if (position==2)
                        mon_ans_Intent.putExtra("type","mon_xao");
                    forward_click.getContext().startActivity(mon_ans_Intent);              }
            });
        }
        void SetDetails(MonAn monan){
            tenmonan.setText(monan.getTenmonan());
            soluong.setText(String.format
                            (Locale.UK,
                        "%d sản phẩm",monan.getSoluong()
                            )
            );
            soluonggiamgia.setText(String.format(
                    Locale.UK, "%d đang giảm giá",monan.getSoluonggiamgia()
            ));
            monan_ImageView.setImageResource(monan.image_resid);
            if (monan.thongtinchitiet) {
                ratingBar.setVisibility(View.VISIBLE);
                giahientai.setText(String.valueOf( monan.getGiahientai()) +" đ");
                giachuagiam.setText(String.valueOf(monan.getGiachuagiam()) +" đ");
                giachuagiam.setPaintFlags(giachuagiam.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                giahientai.setVisibility(View.VISIBLE);
                giachuagiam.setVisibility(View.VISIBLE);
                ratingBar.setRating(monan.rating);
            }
            else {
                soluong.setVisibility(View.VISIBLE);
                soluonggiamgia.setVisibility(View.VISIBLE);
                salesicon_ImageView.setVisibility(View.VISIBLE);
                muong_icon_ImageView.setVisibility(View.VISIBLE);


            }



        }
    }
}
