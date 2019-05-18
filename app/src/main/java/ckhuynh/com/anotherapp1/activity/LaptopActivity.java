package ckhuynh.com.anotherapp1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.adapter.adapterDienthoai;
import ckhuynh.com.anotherapp1.adapter.adapterLaptop;
import ckhuynh.com.anotherapp1.model.Sanpham;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;
import ckhuynh.com.anotherapp1.ultil.Server;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView listViewlaptop;
    adapterLaptop adapterLaptop;
    ArrayList<Sanpham> manglaptop;
    int idlaptop=0;
    int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        Actiontoolbar();
        Getdata();
        OnclickActivity();
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
        listViewlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("chitietsanpham",manglaptop.get(position));
                CheckConnect.ShowToast_Short(getApplicationContext(),manglaptop.get(position).getTensp());
                startActivity(intent);
            }
        });
    }

    private void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanlaptop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null);{
                    int idlaptop= 0;
                    String tenlaptop = "";
                    int gialaptop=0;
                    String hinhanhlaptop = "";
                    String motalaptop = "" ;
                    int idloailaptop=0;
                    for(int i=0;i< response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idlaptop=jsonObject.getInt("id");
                            tenlaptop=jsonObject.getString("tensp");
                            gialaptop=jsonObject.getInt("giasp");
                            motalaptop=jsonObject.getString("motasp");
                            hinhanhlaptop=jsonObject.getString("hinhanhsp");
                            idloailaptop=jsonObject.getInt("idloai");
                            manglaptop.add(new Sanpham(idlaptop,tenlaptop,gialaptop,motalaptop,hinhanhlaptop,idloailaptop));
                            adapterLaptop.notifyDataSetChanged();
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

    private void Actiontoolbar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarlaptop= (Toolbar) findViewById(R.id.toolbarlaptop);
        listViewlaptop = (ListView) findViewById(R.id.listviewlaptop);
        manglaptop = new ArrayList<>();
        adapterLaptop= new adapterLaptop(getApplicationContext(),manglaptop);
        listViewlaptop.setAdapter(adapterLaptop);

    }
}
