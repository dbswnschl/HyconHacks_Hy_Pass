package hypass.hycon.hypass.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
