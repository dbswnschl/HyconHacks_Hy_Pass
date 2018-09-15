package hypass.hycon.hypass.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONObject;

import hypass.hycon.hypass.Connections.PostConnection;
import hypass.hycon.hypass.R;

public class SignUpActivity extends AppCompatActivity {

    EditText text_sign_up_nickname;
    EditText text_sign_up_pw;
    EditText text_sign_up_pw2;
    ImageButton btn_sign_up;
    String ServerAddress;
    String mnemonic;
    String walletId;
    String privateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        text_sign_up_nickname = (EditText) findViewById(R.id.text_sign_up_nickname);
        text_sign_up_pw = (EditText) findViewById(R.id.text_sign_up_pw);
        text_sign_up_pw2 = (EditText) findViewById(R.id.text_sign_up_pw2);
        btn_sign_up = (ImageButton) findViewById(R.id.btn_sign_up);
        ServerAddress = getResources().getString(R.string.SERVER_ADDRESS);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  APP_SERVER 로 지갑생성 실행 (pw 첨부), 니모닉, 키파일을 받아옴
                // 받아온 키파일에서의 지갑주소 + 닉네임 을 APP_SERVER로 보냄, 응답수신
                // 닉네임, 니모닉, 비밀번호 내부SQL로 저장
                // 다이얼로그에 니모닉 표시, 확인시 메인페이지로
                if (text_sign_up_nickname.getText().toString().length() <= 5) {
                    Toast.makeText(SignUpActivity.this, "Confirm your ID !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (text_sign_up_pw.getText().toString().length() <= 4 || !(text_sign_up_pw.getText().toString().equals(text_sign_up_pw2.getText().toString()))) {
                    Toast.makeText(SignUpActivity.this, "Confirm your password !", Toast.LENGTH_SHORT).show();
                    return;
                }

//                //임시로 통과
//                Toast.makeText(SignUpActivity.this,"임시로 통과",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SignUpActivity.this, WarnningActivity.class);
//                startActivity(intent);
//                finish();
                //임시로 통과


                PostConnection post = new PostConnection(ServerAddress + "wallet/");
                post.addParam("password", text_sign_up_pw.getText().toString());
                post.addParam("nickname", text_sign_up_nickname.getText().toString());
                try {
                    post.execute().get();
                    JSONObject resultObj = post.resultObj; // 응답데이터 조회

                    mnemonic = resultObj.getString("mnemonic").toString();
                    walletId = resultObj.getString("address").toString();
                    privateKey = resultObj.getString("privateKey").toString();
                    if (mnemonic.length() > 0) {
                        Toast.makeText(SignUpActivity.this, "Your Wallet was generated Success !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, WarnningActivity.class);
                        intent.putExtra("mnemonic", mnemonic);
                        intent.putExtra("walletId", walletId);

                        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언


                        SharedPreferences.Editor editor = pref.edit();// editor에 put 하기
                        editor.putString("walletId", walletId);
                        editor.putString("myNick", text_sign_up_nickname.getText().toString());
                        editor.putString("password", text_sign_up_pw.getText().toString());
                        editor.putString("privateKey", privateKey);



                        editor.commit(); //완료한다.
                        startActivity(intent);
                    }

                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });

    }
}
