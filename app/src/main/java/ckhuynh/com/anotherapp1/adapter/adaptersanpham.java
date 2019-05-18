package ckhuynh.com.anotherapp1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.activity.Chitietsanpham;
import ckhuynh.com.anotherapp1.model.Sanpham;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;

public class adaptersanpham extends RecyclerView.Adapter<adaptersanpham.ItemHolder> {
    Context context;

    public adaptersanpham(Context context, ArrayList<Sanpham> arraysanpham) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    ArrayList<Sanpham> arraysanpham;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder= new ItemHolder(view);
        return itemHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraysanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá : "+decimalFormat.format(sanpham.giasp)+"Đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.camera)
                .error(R.drawable.error)
                .into(holder.imghinhanhsanpham);

    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsanpham;
        public TextView txttensanpham, txtgiasanpham;;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsanpham= (ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham = (TextView) itemView.findViewById(R.id.textviewteansanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Chitietsanpham.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("chitietsanpham",arraysanpham.get(getPosition()));
                    CheckConnect.ShowToast_Short(context,arraysanpham.get(getPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
