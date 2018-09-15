package hypass.hycon.hypass.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import hypass.hycon.hypass.Fragments.ContactsFragment;
import hypass.hycon.hypass.Fragments.ExchangeFragment;
import hypass.hycon.hypass.Fragments.MyPageFragment;
import hypass.hycon.hypass.Fragments.SendFragment;
import hypass.hycon.hypass.Fragments.SettingFragment;
import hypass.hycon.hypass.R;

public class MainActivity extends FragmentActivity {
    BottomBar bottomBar;
    private Fragment fragment_home;
    private Fragment fragment_send;
    private Fragment fragment_exchange;
    private Fragment fragment_contacts;
    private Fragment fragment_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_home = new MyPageFragment();
        fragment_send = new SendFragment();
        fragment_exchange = new ExchangeFragment();
        fragment_contacts = new ContactsFragment();
        fragment_setting = new SettingFragment();
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (tabId == R.id.tab_home) {
                    transaction.replace(R.id.layout_main_container, fragment_home).commit();
                } else if (tabId == R.id.tab_send) {
                    transaction.replace(R.id.layout_main_container, fragment_send).commit();
                } else if (tabId == R.id.tab_setting) {
                    transaction.replace(R.id.layout_main_container, fragment_setting).commit();
                } else if (tabId == R.id.tab_exchange){
                    transaction.replace(R.id.layout_main_container, fragment_exchange).commit();
                }else if (tabId == R.id.tab_contacts){
                    transaction.replace(R.id.layout_main_container, fragment_contacts).commit();
                }

            }
        });


    }
}
