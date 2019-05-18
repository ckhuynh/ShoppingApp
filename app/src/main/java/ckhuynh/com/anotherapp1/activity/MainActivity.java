package ckhuynh.com.anotherapp1.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.android.volley.TimeoutError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.adapter.adapterGiohang;
import ckhuynh.com.anotherapp1.adapter.adapterLoaisanpham;
import ckhuynh.com.anotherapp1.adapter.adaptersanpham;
import ckhuynh.com.anotherapp1.model.Giohang;
import ckhuynh.com.anotherapp1.model.Loaisp;
import ckhuynh.com.anotherapp1.model.Sanpham;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;
import ckhuynh.com.anotherapp1.ultil.Server;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listviewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    adapterLoaisanpham adapterLoaisanpham;
    int id = 0;
    String tenloaisp= "";
    String hinhanhloaisp= "";
    ArrayList<Sanpham> mangsanpham;
    adaptersanpham adaptersanpham;
    public static ArrayList<Giohang> manggiohang;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
            Actionbar();
            ActionViewFlipper();
            Getdulieuloaisp();
            GetdulieuSPMoiNhat();
            CatchlistviewOnClick();


        }else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
            finish();
        }

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

    private void CatchlistviewOnClick() {
        listviewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            startActivity(intent);
                            intent.putExtra("idloai",mangloaisp.get(2).getId());
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            startActivity(intent);
                            intent.putExtra("idloai",mangloaisp.get(1).getId());
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetdulieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null);{
                    int ID= 0;
                    String Tensanpham = "";
                    int Giasanpham=0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "" ;
                    int Idloai=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensanpham=jsonObject.getString("tensp");
                            Giasanpham=jsonObject.getInt("giasp");
                            Hinhanhsanpham=jsonObject.getString("hinhanhsp");
                            Motasanpham=jsonObject.getString("motasp");
                            Idloai=jsonObject.getInt("idloai");
                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Motasanpham,Hinhanhsanpham,Idloai));
                            adaptersanpham.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp,  new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               if(response != null){
                    for(int i=0;i<response.length() ; i++){
                        try {
                            JSONObject jsonObject= response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add( new Loaisp(id, tenloaisp, hinhanhloaisp));
                            adapterLoaisanpham.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                   mangloaisp.add(new Loaisp(0,"Liên Hệ","https://miatosf.com/wp-content/uploads/2017/08/phone-call-icon-2-blue-300x300.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/qcao/11_04_2019_14_09_56_A9Giasock-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/12_04_2019_22_08_25_huawei-P30-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/09_04_2019_14_06_36_Vivo-V15-Ver3-800-300.png");
        mangquangcao.add("https://cdn.tgdd.vn/qcao/11_04_2019_15_28_26_Oppo-f11-800-300.png");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imangview= new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imangview);
            imangview.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imangview);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);

    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewfilpper);
        recyclerViewmanhinhchinh= (RecyclerView) findViewById(R.id.recyclerview);
        navigationView= (NavigationView) findViewById(R.id.navigationview);
        listviewmanhinhchinh=(ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang Chính","https://cdn.iconscout.com/icon/free/png-256/home-house-main-page-building-address-casa-3-3105.png"));
        adapterLoaisanpham= new adapterLoaisanpham(mangloaisp, getApplicationContext());
        listviewmanhinhchinh.setAdapter(adapterLoaisanpham);
        mangsanpham = new ArrayList<>();
        adaptersanpham = new adaptersanpham(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(adaptersanpham);
        if(manggiohang != null){

        }else {
            manggiohang = new ArrayList<>();
        }
    }


}
