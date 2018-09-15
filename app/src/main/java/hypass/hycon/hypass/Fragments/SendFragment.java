package hypass.hycon.hypass.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

import hypass.hycon.hypass.Connections.PostConnection;
import hypass.hycon.hypass.R;

import static android.content.Context.MODE_PRIVATE;


public class SendFragment extends Fragment {
    public SendFragment() {
        super();
    }

    Spinner spinner;
    ArrayList<String> spinner_arr;
    Context context;
    String currentSelectedAddress;
    EditText txt_send_hyc;
    EditText txt_send_fee;
    ImageButton btn_send;
    SharedPreferences pref;
    ArrayList<String> address_list;
    ArrayList<String> nick_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        context = view.getContext();
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner_arr = new ArrayList<String>();
        spinner_arr.add("수신자를 선택합니다.");
        btn_send = (ImageButton) view.findViewById(R.id.btn_send);
        txt_send_hyc = (EditText) view.findViewById(R.id.txt_send_hyc);
        txt_send_fee = (EditText) view.findViewById(R.id.txt_send_fee);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, spinner_arr);
        spinner.setAdapter(adapter);

        pref = getContext().getSharedPreferences("pref", MODE_PRIVATE); // 선언

        Set<String> address = pref.getStringSet("address", null);


        Set<String> nick = pref.getStringSet("nick", null);

        if (address != null) {

            nick_list = new ArrayList<String>(nick);
            address_list = new ArrayList<String>(address);

            for (int i = 0; i < nick_list.size(); i++) {
                spinner_arr.add(nick_list.get(i));
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (address_list.size() > 0 && i != 0)
                        currentSelectedAddress = address_list.get(i - 1);
                    else {
                        currentSelectedAddress = null;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {

        }
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentSelectedAddress == null){
                    Toast.makeText(context,"Please Select Receiver !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if( txt_send_hyc.getText().toString().length() == 0 || txt_send_fee.getText().toString().length() == 0 ||Double.parseDouble(txt_send_hyc.getText().toString()) <= 0  || Double.parseDouble(txt_send_fee.getText().toString()) <= 0){
                    Toast.makeText(context,"Fill the blanks ! ",Toast.LENGTH_SHORT).show();
                    return;
                }

                String walletId = pref.getString("walletId", null);
                String password = pref.getString("password", null);
                String privateKey = pref.getString("privateKey", null);

                if (currentSelectedAddress != null && walletId != null && password != null) {
                    PostConnection postConnection = new PostConnection(getResources().getString(R.string.SERVER_ADDRESS) + "tx");
//                    postConnection.addParam("name",walletId);
                    postConnection.addParam("password", password);
                    postConnection.addParam("to", currentSelectedAddress);
                    postConnection.addParam("amount", txt_send_hyc.getText().toString());
                    postConnection.addParam("privateKey", privateKey);
                    postConnection.addParam("fee", txt_send_fee.getText().toString());
                    try {
                        postConnection.execute().get();
                        JSONObject resultObj = postConnection.resultObj;
                        Log.d("TAGG",resultObj.toString());
                        if(resultObj.toString().contains("txHash") == true){
                            Toast.makeText(context,"Your Send is Successful",Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

    }
}
