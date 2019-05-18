package ckhuynh.com.anotherapp1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.adapter.adapterGiohang;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;

public class Giohang extends AppCompatActivity {
    ListView listViewgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan,btntieptuc;
    Toolbar toolbargiohang;
    adapterGiohang adapterGiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Actiontoolbar();
        CheckData();
        EventUtil();
        CatchOnClickListview();
        CatchButtonOnClick();
    }

    private void CatchButtonOnClick() {
        btntieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),Thanhtoan.class);
                    startActivity(intent);

                }else{
                    CheckConnect.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn hiện đang trống");
                }
            }
        });
    }

    private void CatchOnClickListview() {
        listViewgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Giohang.this);
                builder.setTitle("XÁC NHẬN XÓA SẢN PHẨM");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.manggiohang.remove(position);
                            adapterGiohang.notifyDataSetChanged();
                            Giohang.EventUtil();
                            if(MainActivity.manggiohang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                MainActivity.manggiohang.remove(position);
                                adapterGiohang.notifyDataSetChanged();
                                Giohang.EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterGiohang.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUtil() {
        long tongtien=0;
        for(int i=0;i < MainActivity.manggiohang.size();i++){
            tongtien += MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+" Đ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0){
            adapterGiohang.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            listViewgiohang.setVisibility(View.INVISIBLE);
        }else {
            adapterGiohang.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        listViewgiohang=(ListView) findViewById(R.id.listviewgiohang);
        txtthongbao=(TextView) findViewById(R.id.textviewthongbao);
        txttongtien= (TextView) findViewById(R.id.textviewtongtien);
        btnthanhtoan=(Button) findViewById(R.id.buttonthanhtoan);
        btntieptuc= (Button) findViewById(R.id.buttontieptuc);
        toolbargiohang=(Toolbar) findViewById(R.id.toolbargiohang);
        adapterGiohang = new adapterGiohang(Giohang.this,MainActivity.manggiohang);
        listViewgiohang.setAdapter(adapterGiohang);
    }
}
