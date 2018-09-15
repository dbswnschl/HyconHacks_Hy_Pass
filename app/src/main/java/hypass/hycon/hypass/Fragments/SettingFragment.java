package hypass.hycon.hypass.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hypass.hycon.hypass.Activities.MainActivity;
import hypass.hycon.hypass.Activities.SignInActivity;
import hypass.hycon.hypass.Activities.SignUpActivity;
import hypass.hycon.hypass.R;

public class SettingFragment extends Fragment {
    public SettingFragment() {
        super();
    }

    ImageButton btn_selectwallet;
    ImageButton btn_generatewallet;
    ImageButton btn_recoverwallet;
    ImageButton btn_donation;
    ImageButton btn_contactus;
    int index;
    JSONArray jsonArray = new JSONArray();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btn_selectwallet = (ImageButton) view.findViewById(R.id.btn_selectwallet);
        btn_generatewallet = (ImageButton) view.findViewById(R.id.btn_generatewallet);
        btn_recoverwallet = (ImageButton) view.findViewById(R.id.btn_recoverwallet);
        btn_donation = (ImageButton) view.findViewById(R.id.btn_donation);
        btn_contactus = (ImageButton) view.findViewById(R.id.btn_contactus);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        btn_selectwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언
                String mywallet = pref.getString("mywallet",null);
                try {
                    if(mywallet !=null) {
                        Gson gson = new Gson();
                        JSONObject jsonObject = (JSONObject) (new JSONObject(mywallet));
                        jsonArray =(JSONArray) jsonObject.get("values");

//                        jsonArray = gson.fromJson(mywallet,JSONArray.class);

//                        JSONArray jarr = gson.fromJson(mywallet,JSONArray.class);
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);


                        for (int i = 0; i < jsonArray.length(); i++) {
                            ;
                            arrayAdapter.add(((JSONObject)((JSONObject)jsonArray.get(i) ).get("nameValuePairs") ).getString("myNick"));
                        }

                        //                arrayAdapter = new ArrayAdapter<String>(getContext(),Address_arr);


                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
//                builderSingle.setIcon(R.drawable.ic_launcher);
                        builderSingle.setTitle("Select your NickName.");
                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                index = which;
                                String strName = arrayAdapter.getItem(which);
                                AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
                                builderInner.setMessage(strName + "지갑을 불러옵니다.");
                                builderInner.setTitle("지갑 불러오기");
                                builderInner.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        SharedPreferences.Editor editor = pref.edit();
                                        try {
                                            editor.putString("myNick", ((JSONObject) jsonArray.get(index)).getString("myNick"));
                                            editor.putString("walletId", ((JSONObject) jsonArray.get(index)).getString("walletId"));
                                            editor.commit();
                                        }catch (Exception exc){
                                            exc.printStackTrace();
                                        }
                                        Toast.makeText(getContext(), "해당 지갑을 불러왔습니다 ! ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                        dialog.dismiss();
                                    }
                                });
                                builderInner.show();
                            }
                        });
                        builderSingle.show();

                    }else{
                        Toast.makeText(getContext(),"저장된 지갑이 없습니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception exc){
                    exc.printStackTrace();
                }
            }
        });
        btn_generatewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
        btn_recoverwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);

            }
        });
        btn_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Donate Please ! ", Toast.LENGTH_SHORT).show();
            }
        });
        btn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bug Report Please ! \n ejdejd2005@naver.com ", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
