package hypass.hycon.hypass.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import hypass.hycon.hypass.Activities.InitActivity;
import hypass.hycon.hypass.Activities.SignInActivity;
import hypass.hycon.hypass.Activities.SignUpActivity;
import hypass.hycon.hypass.Connections.GetConnection;
import hypass.hycon.hypass.Connections.PostConnection;
import hypass.hycon.hypass.R;

public class MyPageFragment extends Fragment {
    public MyPageFragment() {
        super();
    }
    String walletId;
    TextView txt_current_price;
    TextView txt_amount_hycon;
    TextView txt_amount_krw;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page,container,false);
        txt_current_price = (TextView)view.findViewById(R.id.txt_current_price);
        txt_amount_hycon = (TextView)view.findViewById(R.id.txt_amount_hycon);
        txt_amount_krw = (TextView)view.findViewById(R.id.txt_amount_krw);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        walletId = getActivity().getIntent().getStringExtra("walletId");
        GetConnection getConnection = new GetConnection(getString(R.string.SERVER_ADDRESS)+"wallet/"+walletId);
        try {
            getConnection.execute().get();
            JSONObject resultObj = getConnection.resultObj;
            Log.d("TAG,",resultObj.toString());
            txt_amount_hycon.setText(resultObj.getString("balance"));
            if (resultObj.getString("hyconPrice").contains("status")){
                txt_current_price.setText("0.00");
                txt_amount_krw.setText("0.00");
            }else {
                txt_current_price.setText(resultObj.getString("hyconPrice"));
                txt_amount_krw.setText(resultObj.getString("krw"));
            }
        }catch (Exception exc){
            exc.printStackTrace();
            boolean init = true;

            Intent intent = new Intent(getActivity(), InitActivity.class);
            Toast.makeText(getContext(),"지갑정보를 불러오는데 실패했습니다.\n서버 상태를 확인하거나 앱을 재실행 해주세요 !",Toast.LENGTH_SHORT).show();
            intent.putExtra("init",init);
            startActivity(intent);
            getActivity().finish();

        }

    }
}
