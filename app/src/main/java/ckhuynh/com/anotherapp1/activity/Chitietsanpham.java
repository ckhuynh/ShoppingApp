package ckhuynh.com.anotherapp1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.model.Giohang;
import ckhuynh.com.anotherapp1.model.Sanpham;

public class Chitietsanpham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id=0;
    int giachitiet=0;
    String tenchitiet="";
    String motachitiet="";
    String hinhanhchitiet="";
    int idloaisp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Anhxa();
        ActionToolbar();
        Getinfomation();
        CatchEventSpinner();
        ButtonOnclick();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), ckhuynh.com.anotherapp1.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void ButtonOnclick() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size() >0){
                    boolean exist=false;
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    for(int i=0; i<MainActivity.manggiohang.size() ; i++){
                        if(MainActivity.manggiohang.get(i).idsp == id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl );
                            if(MainActivity.manggiohang.get(i).getSoluongsp()>10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exist=true;

                        }
                    }
                    if(exist==false){
                        int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                        long tongsanpham= soluong * giachitiet;
                        MainActivity.manggiohang.add(new Giohang(id,soluong,tenchitiet,hinhanhchitiet,tongsanpham));
                    }

                }else{
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long tongsanpham= soluong * giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id,soluong,tenchitiet,hinhanhchitiet,tongsanpham));
                }
                Intent intent = new Intent(getApplicationContext(), ckhuynh.com.anotherapp1.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer [] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void Getinfomation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("chitietsanpham");
        id=sanpham.getId();
        giachitiet=sanpham.getGiasp();
        tenchitiet=sanpham.getTensp();
        motachitiet=sanpham.getMotasp();
        hinhanhchitiet=sanpham.getHinhanhsp();
        idloaisp=sanpham.getIdloaisp();
        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+decimalFormat.format(giachitiet)+" Đ");
        txtmota.setText(motachitiet);
        Picasso.with(getApplicationContext()).load(hinhanhchitiet)
                .placeholder(R.drawable.camera)
                .error(R.drawable.error)
                .into(imgchitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void Anhxa() {
        toolbarchitiet= (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        txtten = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        imgchitiet= (ImageView) findViewById(R.id.imageviewchietietsanpham);
        spinner= (Spinner) findViewById(R.id.spinnerchitietsanpham);
        btndatmua = (Button) findViewById(R.id.buttonmua);

    }
}
