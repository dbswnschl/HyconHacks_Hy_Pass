package hypass.hycon.hypass.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import hypass.hycon.hypass.R;

public class WarnningActivity extends AppCompatActivity {
    ImageButton btn_igotit;
    String mnemonic;
    String address;
    String myNick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warnning);
        btn_igotit = (ImageButton)findViewById(R.id.btn_igotit);
        mnemonic = getIntent().getStringExtra("mnemonic");
        address = getIntent().getStringExtra("address");
        myNick = getIntent().getStringExtra("myNick");
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_igotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //임시
                Intent intent = new Intent(WarnningActivity.this,MnemonicActivity.class);
                intent.putExtra("mnemonic",mnemonic);
                intent.putExtra("address",address);
                intent.putExtra("myNick",myNick);
                startActivity(intent);
                finish();
            }
        });
    }
}
