package ckhuynh.com.anotherapp1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ckhuynh.com.anotherapp1.R;
import ckhuynh.com.anotherapp1.ultil.CheckConnect;
import ckhuynh.com.anotherapp1.ultil.Server;

public class Thanhtoan extends AppCompatActivity {

    EditText edttenkh,edtsdt,edtmail;
    Button btnxacnhan,btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(CheckConnect.haveNetworkConnection(getApplicationContext())){
            EvenntButton();
        }else{
            CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng kiểm tra lại kết nối");
        }
    }

    private boolean checknumber( String str) {
        for(int i=0;i<str.length();i++){
           if(str.charAt(i)<48||str.charAt(i)>57) return false;
        }
        return true;
    }
    private boolean checkmail(String str){
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==64) return true;
        }
        return false;
    }

    private void EvenntButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edttenkh.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String mail = edtmail.getText().toString().trim();
                if(ten.length() > 0 && sdt.length() > 0 && mail.length() > 0&&checknumber(sdt)&&checkmail(mail)){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdangiohang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn đã thanh toán thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnect.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else{
                                            CheckConnect.ShowToast_Short(getApplicationContext(),"dữ liệu giỏ hàng bị lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0; i < MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("name",ten);
                            hashMap.put("phone",sdt);
                            hashMap.put("mail",mail);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnect.ShowToast_Short(getApplicationContext(),"Vui lòng nhập đúng và đủ dữ liệu");
                }
            }
        });
    }

    private void Anhxa() {
        edttenkh = (EditText) findViewById(R.id.edittexttenkhachhang);
        edtsdt = (EditText) findViewById(R.id.edittextsodienthoai);
        edtmail = (EditText) findViewById(R.id.edittextemail);
        btnxacnhan = (Button) findViewById(R.id.buttonxacnhan);
        btntrove = (Button) findViewById(R.id.buttontrove);
    }

}
