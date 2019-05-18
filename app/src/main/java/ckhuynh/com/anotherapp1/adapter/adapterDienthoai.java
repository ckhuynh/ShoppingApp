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

public class adapterDienthoai extends BaseAdapter {
    Context context;

    public adapterDienthoai(Context context, ArrayList<Sanpham> mangdienthoai) {
        this.context = context;
        this.mangdienthoai = mangdienthoai;
    }

    ArrayList<Sanpham> mangdienthoai;
    @Override
    public int getCount() {
        return mangdienthoai.size();
    }

    @Override
    public Object getItem(int position) {
        return mangdienthoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtendienthoai,txtgiadienthoai,txtmotadienthoai;
        public ImageView imghinhanhdienthoai;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder= null;
        if(viewHolder == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txtendienthoai = (TextView) view.findViewById(R.id.textviewdienthoai);
            viewHolder.txtgiadienthoai = (TextView)  view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadienthoai = (TextView)  view.findViewById(R.id.textviewmotadienthoai);
            viewHolder.imghinhanhdienthoai = (ImageView) view.findViewById(R.id.imageviewdienthoai);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtendienthoai.setText(sanpham.tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiadienthoai.setText("Giá : "+decimalFormat.format(sanpham.giasp)+"Đ");
        viewHolder.txtmotadienthoai.setMaxLines(2);
        viewHolder.txtmotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadienthoai.setText(sanpham.motasp);
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.camera)
                .error(R.drawable.error)
                .into(viewHolder.imghinhanhdienthoai);
        return view;
    }
}
