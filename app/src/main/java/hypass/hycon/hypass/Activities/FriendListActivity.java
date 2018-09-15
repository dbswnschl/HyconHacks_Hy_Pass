package hypass.hycon.hypass.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import hypass.hycon.hypass.ItemData;
import hypass.hycon.hypass.ListAdapter;
import hypass.hycon.hypass.R;

public class FriendListActivity extends AppCompatActivity {
    ListView mListView;
    ArrayList<String> Address_arr = null;
    ArrayList<String> Nick_arr = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        mListView = (ListView) findViewById(R.id.list_friends);
        SharedPreferences pref= getSharedPreferences("pref", MODE_PRIVATE); // 선언
        Set<String> address = pref.getStringSet("address", null);


        Set<String> nick=pref.getStringSet("nick",null);



        if(address!= null){

            Address_arr = new ArrayList<String>(address);
            Nick_arr = new ArrayList<String>(nick);
            ArrayList<ItemData> oData = new ArrayList<>();

            ItemData oItem = new ItemData();


            for (int i = 0; i < Address_arr.size(); i++) {
                oItem.strAddress = Address_arr.get(i);
                oItem.strNick = Nick_arr.get(i);
                oData.add(oItem);
            }
            ListAdapter oAdapter = new ListAdapter(oData);
            mListView.setAdapter(oAdapter);


        }





    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
