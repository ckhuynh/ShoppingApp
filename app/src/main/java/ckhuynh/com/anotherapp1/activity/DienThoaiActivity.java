package ckhuynh.com.anotherapp1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.adapter.adapterDienthoai;
import ckhuynh.com.anotherapp1.model.Sanpham;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;
import ckhuynh.com.anotherapp1.ultil.Server;


public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    ListView listViewdt;
    adapterDienthoai adapterDienthoai;
    ArrayList<Sanpham> mangdt;
    int iddt=0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        Anhxa();
        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
            Getdata();
            OnclickActivity();
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
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


    private void OnclickActivity() {
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("chitietsanpham",mangdt.get(position));
                CheckConnect.ShowToast_Short(getApplicationContext(),mangdt.get(position).getTensp());
                startActivity(intent);
            }
        });
    }

    private void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdandiethoai, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null);{
                    int iddt= 0;
                    String tendt = "";
                    int giadt=0;
                    String hinhanhdt = "";
                    String motadt = "" ;
                    int idloaidt=0;
                    for(int i=0;i< response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            iddt=jsonObject.getInt("id");
                            tendt=jsonObject.getString("tensp");
                            giadt=jsonObject.getInt("giasp");
                            motadt=jsonObject.getString("motasp");
                            hinhanhdt=jsonObject.getString("hinhanhsp");
                            idloaidt=jsonObject.getInt("idloai");
                            mangdt.add(new Sanpham(iddt,tendt,giadt,motadt,hinhanhdt,idloaidt));
                            adapterDienthoai.notifyDataSetChanged();
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

    private void ActionToolbar() {
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbardt= (Toolbar) findViewById(R.id.toolbardienthoai);
        listViewdt = (ListView) findViewById(R.id.listviewdienthoai);
        mangdt= new ArrayList<>();
        adapterDienthoai = new adapterDienthoai(getApplicationContext(),mangdt);
        listViewdt.setAdapter(adapterDienthoai);
    }
}
