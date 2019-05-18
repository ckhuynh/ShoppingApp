package ckhuynh.com.anotherapp1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.activity.MainActivity;
import ckhuynh.com.anotherapp1.model.Giohang;

public class adapterGiohang extends BaseAdapter {
    Context context;
    ArrayList<Giohang> manggiohang;

    public adapterGiohang(Context context, ArrayList<Giohang> manggiohang) {
        this.context = context;
        this.manggiohang = manggiohang;
    }

    @Override
    public int getCount() {
        return manggiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return manggiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView txttengiohang,txtgiagiohang;
        ImageView imghinhanhgiohang;
        Button btnminus,btnvalue,btnplus;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= layoutInflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengiohang = (TextView) convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imghinhanhgiohang = (ImageView) convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalue = (Button) convertView.findViewById(R.id.buttonvalue);
            viewHolder.btnplus = (Button) convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        final Giohang giohang= (Giohang) getItem(position);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" Đ");
        Picasso.with(context).load(giohang.getHinhanhsp())
                .placeholder(R.drawable.camera)
                .error(R.drawable.error)
                .into(viewHolder.imghinhanhgiohang);
        viewHolder.btnvalue.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewHolder.btnvalue.getText().toString());
        if(sl>=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if(sl<=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmn = Integer.parseInt(finalViewHolder.btnvalue.getText().toString())+1;
                int slht=MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setGiasp((giaht*slmn)/slht);
                MainActivity.manggiohang.get(position).setSoluongsp(slmn);
                DecimalFormat decimalFormat1= new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(manggiohang.get(position).getGiasp())+" Đ");
                ckhuynh.com.anotherapp1.activity.Giohang.EventUtil();
                if(slmn>9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slmn));
                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slmn));
                }

            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmn = Integer.parseInt(finalViewHolder.btnvalue.getText().toString())-1;
                int slht=MainActivity.manggiohang.get(position).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setGiasp((giaht*slmn)/slht);
                MainActivity.manggiohang.get(position).setSoluongsp(slmn);
                DecimalFormat decimalFormat1= new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(manggiohang.get(position).getGiasp())+" Đ");
                ckhuynh.com.anotherapp1.activity.Giohang.EventUtil();
                if(slmn<2){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slmn));
                }else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(slmn));
                }

            }
        });
        viewHolder=finalViewHolder;
        return convertView;
    }
}
