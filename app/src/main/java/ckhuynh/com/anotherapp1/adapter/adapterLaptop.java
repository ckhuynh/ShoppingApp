package ckhuynh.com.anotherapp1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.model.Sanpham;

public class adapterLaptop extends BaseAdapter {
    Context context;

    public adapterLaptop(Context context, ArrayList<Sanpham> manglaptop) {
        this.context = context;
        this.manglaptop = manglaptop;
    }

    ArrayList<Sanpham> manglaptop;

    @Override
    public int getCount() {
        return manglaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return manglaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imghinhanhlaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if(viewHolder == null){
            viewHolder= new adapterLaptop.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = (TextView) view.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = (TextView)  view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = (TextView)  view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imghinhanhlaptop = (ImageView) view.findViewById(R.id.imageviewlaptop);
            view.setTag(viewHolder);
        }else{
            viewHolder= (adapterLaptop.ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlaptop.setText(sanpham.tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá : "+decimalFormat.format(sanpham.giasp)+"Đ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.motasp);
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.camera)
                .error(R.drawable.error)
                .into(viewHolder.imghinhanhlaptop);
        return view;
    }
}
