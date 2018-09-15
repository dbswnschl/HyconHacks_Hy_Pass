package hypass.hycon.hypass.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import hypass.hycon.hypass.R;

public class MnemonicActivity extends AppCompatActivity {
    ImageButton btn_ichecked;
    String mnemonic;
    String address;
    TextView mnemonic_1;
    TextView mnemonic_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnemonic);
        mnemonic_1= (TextView)findViewById(R.id.mnemonic_1);
        mnemonic_2= (TextView)findViewById(R.id.mnemonic_2);
        btn_ichecked = (ImageButton)findViewById(R.id.btn_ichecked);
        mnemonic = getIntent().getStringExtra("mnemonic");
        address = getIntent().getStringExtra("address");
        String [] arr_mnemonic = mnemonic.split(" ");
        String txt1 = "1. "+arr_mnemonic[0];
        String txt2 = "7. "+arr_mnemonic[6];
        for(int i = 1 ; i < 6 ; i++){
            txt1 +="\n\n"+ (i+1)+". "+arr_mnemonic[i];
        }
        for(int i = 7 ; i < arr_mnemonic.length ; i++){
            txt2 +="\n\n"+ (i+1)+". "+arr_mnemonic[i];
        }
        mnemonic_1.setText(txt1);
        mnemonic_2.setText(txt2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_ichecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MnemonicActivity.this,MainActivity.class);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });

    }
}
