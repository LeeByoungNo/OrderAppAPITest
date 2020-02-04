package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdListActivity extends AppCompatActivity {

    private List<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter ;

    @Override
    protected void onResume() {
        super.onResume();

        //adapter.addAll(list);

//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        //adapter.addAll(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.advertise_list);

        ListView listview = findViewById(R.id.listview) ;

        //데이터를 저장하게 되는 리스트


        // data GET =================================================================
        new Thread(){
            public void run(){

                try{

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/totalAdList.json","");

                    Log.d("API","RESULT: "+result);


                    JSONObject jsonObjTotalAdList =  new JSONObject(result);
                    if(jsonObjTotalAdList.get("status").equals("1")) {

                        JSONArray data = jsonObjTotalAdList.getJSONArray("data");

                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {

                                JSONObject adInfo = data.getJSONObject(j);


                                Log.d("debug",adInfo.get("subject")+""+adInfo.get("regDate"));

                                list.add((String)adInfo.get("subject"));

                                Map<String,String> dataInfo = new HashMap<>();
                                dataInfo.put("html_url", (String)adInfo.get("htmlUrl"));
                                dataInfo.put("link_type", (String)adInfo.get("linkType"));
                                dataInfo.put("banner_url", (String)adInfo.get("bannerUrl"));

                            }
                        }


                    }

                    // 일반광고
                    result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/publicAdList.json", "mode=A");

                    JSONObject jsonObj =  new JSONObject(result);
                    if(jsonObj.get("status").equals("1")) {

                        JSONArray data = jsonObj.getJSONArray("data");

                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {

                                JSONObject adInfo = data.getJSONObject(j);

                                /*TableItem tableItem = new TableItem(tablePublicAdList, SWT.NONE);
                                tableItem.setText(new String[]{adInfo.get("subject")+"",adInfo.get("regDate")+""});*/

                                list.add((String)adInfo.get("subject"));

                                Map<String,String> dataInfo = new HashMap<String,String>();



                                // null check
                                if(false == adInfo.isNull("htmlUrl")) {
                                    dataInfo.put("html_url", (String)adInfo.get("htmlUrl"));
                                }
                                dataInfo.put("link_type", (String)adInfo.get("linkType"));
                                dataInfo.put("banner_url", (String)adInfo.get("bannerUrl"));
                                dataInfo.put("adv_type", (String)adInfo.get("advType"));
                                dataInfo.put("spCode", (String)adInfo.get("spCode"));


                            }
                        }
                    }
                    // 일반광고

                    adapter.notifyDataSetChanged();

                }catch(Exception e){
                    Log.d("error",""+e);
                }
            }
        }.start();
        // data GET =================================================================

        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        //리스트뷰의 어댑터를 지정해준다.
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //클릭한 아이템의 문자열을 가져옴
                String selected_item = (String)parent.getItemAtPosition(position);
            }
        });

        //리스트뷰에 보여질 아이템을 추가
       /* list.add("사과");
        list.add("배");
        list.add("귤");
        list.add("바나나");*/
    }
}
