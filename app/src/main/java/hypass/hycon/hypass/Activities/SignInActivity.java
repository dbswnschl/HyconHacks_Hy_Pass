package hypass.hycon.hypass.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import hypass.hycon.hypass.Connections.PostConnection;
import hypass.hycon.hypass.R;

public class SignInActivity extends AppCompatActivity {
    EditText text_sign_in_nickname;
    EditText text_sign_in_mnemonic;
    EditText text_sign_in_pw;
    ImageButton btn_sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        text_sign_in_nickname = (EditText) findViewById(R.id.text_sign_in_nickname);
        text_sign_in_mnemonic = (EditText) findViewById(R.id.text_sign_in_mnemonic);
        text_sign_in_pw = (EditText) findViewById(R.id.text_sign_in_pw);
        btn_sign_in = (ImageButton) findViewById(R.id.btn_sign_in);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PostConnection postConnection = new PostConnection(getResources().getString(R.string.SERVER_ADDRESS)+"wallet/recover");
                    postConnection.addParam("password",text_sign_in_pw.getText().toString());
                    postConnection.addParam("nickname",text_sign_in_nickname.getText().toString());
                    postConnection.addParam("mnemonic",text_sign_in_mnemonic.getText().toString());
                    postConnection.execute().get();
                    JSONObject resultObj = postConnection.resultObj;
                    String privateKey = resultObj.getString("privateKey");
                    String address = resultObj.getString("address");
                    final SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언);
                    SharedPreferences.Editor editor= pref.edit();
                    editor.putString("myNick", text_sign_in_nickname.getText().toString());
                    editor.putString("walletId",address);
                    editor.putString("privateKey",privateKey);
                    editor.putString("password",text_sign_in_pw.getText().toString());

                    JSONObject myWallet = new JSONObject();
                    myWallet.put("walletId", address);
                    myWallet.put("myNick", text_sign_in_nickname.getText().toString());
                    myWallet.put("password", text_sign_in_pw.getText().toString());
                    myWallet.put("privateKey", privateKey);
                    JSONArray jsonArray;
                    String prevwallet = pref.getString("mywallet", null);
                    if (prevwallet == null) {
                        jsonArray = new JSONArray();
                        jsonArray.put(myWallet);
                    } else {
                        jsonArray = new JSONArray(prevwallet);
                        jsonArray.put(myWallet);

                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(jsonArray);
                    editor.putString("mywallet", json);

                    editor.commit();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("walletId",address);
                    intent.putExtra("myNick",text_sign_in_nickname.getText().toString());
                    startActivity(intent);
                    finish();

                    Log.d("TAㅎ,",resultObj.toString());
                } catch (Exception exc) {
                    exc.printStackTrace();
                }


//임시로 통과
                Toast.makeText(SignInActivity.this, "임시로 통과", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //임시로 통과

            }
        });

    }
}
