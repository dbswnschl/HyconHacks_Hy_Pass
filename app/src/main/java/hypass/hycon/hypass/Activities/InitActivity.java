package hypass.hycon.hypass.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

import hypass.hycon.hypass.R;

public class InitActivity extends AppCompatActivity {
    Button btn_no_wallet;
    Button btn_yes_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        btn_no_wallet = (Button) findViewById(R.id.btn_no_wallet);
        btn_yes_wallet = (Button) findViewById(R.id.btn_yes_wallet);

    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언
        String walletId = pref.getString("walletId", null);
        boolean isinit = getIntent().getBooleanExtra("init",false);
        if (walletId != null && isinit == false) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("walletId",walletId);
            startActivity(intent);
            finish();

        } else {

            btn_no_wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_no_wallet = new Intent(InitActivity.this, SignUpActivity.class);
                    startActivity(intent_no_wallet);
                }
            });
            btn_yes_wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_yes_wallet = new Intent(InitActivity.this, SignInActivity.class);
                    startActivity(intent_yes_wallet);
                }
            });
        }
    }

}
