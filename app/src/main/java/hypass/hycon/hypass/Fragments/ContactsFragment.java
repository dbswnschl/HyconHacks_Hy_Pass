package hypass.hycon.hypass.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hypass.hycon.hypass.Activities.FriendListActivity;
import hypass.hycon.hypass.R;


public class ContactsFragment extends Fragment {
    public ContactsFragment() {
        super();
    }

    ImageButton btn_friendlist;
    ImageButton btn_addfriend;
    ImageButton btn_deletefriend;
    int index;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        btn_friendlist = (ImageButton) view.findViewById(R.id.btn_friendlist);
        btn_addfriend = (ImageButton) view.findViewById(R.id.btn_addfriend);
        btn_deletefriend = (ImageButton) view.findViewById(R.id.btn_deletefriend);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        btn_friendlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendListActivity.class);
                startActivity(intent);
            }
        });
        btn_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String POPUP_TITLE = "ADD NEW FRIEND";
                final String POPUP_TEXT = "Please fill in your friend's credentials";
                String Nick_HINT = "--User Id--";
                String Address_HINT = "--Wallet Address--";

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle(POPUP_TITLE);
                alert.setMessage(POPUP_TEXT);

                // Set an EditText view to get user input
                final EditText nickName = new EditText(getContext());
                nickName.setHint(Nick_HINT);
                final EditText address = new EditText(getContext());
                address.setHint(Address_HINT);
                LinearLayout layout = new LinearLayout(getContext().getApplicationContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(nickName);
                layout.addView(address);
                alert.setView(layout);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (nickName.getText().toString().length() <= 0 || address.getText().toString().length() <= 0) {
                            Toast.makeText(getContext(), "Please fill the Blanks !", Toast.LENGTH_SHORT).show();

                        } else {


                            SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언
                            Set<String> addressSet = pref.getStringSet("address", null);


                            Set<String> nick = pref.getStringSet("nick", null);
                            if (addressSet == null) {
                                addressSet = new HashSet<String>();
                                nick = new HashSet<String>();
                            }
                            addressSet.add(address.getText().toString());
                            nick.add(nickName.getText().toString());


                            SharedPreferences.Editor editor = pref.edit();// editor에 put 하기
                            editor.putStringSet("address", addressSet);
                            editor.putStringSet("nick", nick);


                            editor.commit(); //완료한다.

                            Toast.makeText(getContext(), "Added Successful !", Toast.LENGTH_SHORT).show();


                            // Do something with value!
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });
        btn_deletefriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);

                final SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE); // 선언);
                final Set<String> nick = pref.getStringSet("nick", null);
                Set<String> address = pref.getStringSet("address", null);

                if (nick == null) {
                    Toast.makeText(getContext(), "There is no Friend in List !", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ArrayList<String> nick_arr = new ArrayList<>(nick);
                final ArrayList<String> address_arr = new ArrayList<>(address);
                for (int i = 0; i < nick_arr.size(); i++) {
                    arrayAdapter.add(nick_arr.get(i));
                }

                //                arrayAdapter = new ArrayAdapter<String>(getContext(),Address_arr);


                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
//                builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Select who you want to Delete.");
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
                        builderInner.setMessage(strName + "\n(" + address_arr.get(which) + ")");
                        builderInner.setTitle("Delete your Firend ! ");
                        builderInner.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nick_arr.remove(index);
                                address_arr.remove(index);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putStringSet("address", new HashSet<String>(address_arr));
                                editor.putStringSet("nick", new HashSet<String>(nick_arr));
                                editor.commit();
                                Toast.makeText(getContext(), "DELETE SUCCESS ! ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
            }
        });
    }
}
