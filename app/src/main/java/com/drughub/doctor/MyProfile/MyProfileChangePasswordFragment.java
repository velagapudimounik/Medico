package com.drughub.doctor.MyProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileChangePasswordFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_changepassword_fragment, container, false);

        final EditText currentPassword = (EditText) view.findViewById(R.id.currentPassword);
        final EditText newPassword = (EditText) view.findViewById(R.id.newPassword);
        final EditText confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        Button submitbutton = (Button) view.findViewById(R.id.buttonSubmit);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwd = newPassword.getText().toString();
                String confirmpwd = confirmPassword.getText().toString();
                if (newPwd.equals(confirmpwd)) {
                    JSONObject object = new JSONObject();
                    try {
                        String newPass = Globals.encryptString(newPwd);
                        object.put("oldpassword", Globals.encryptString(currentPassword.getText().toString().trim()));
                        object.put("newpassword", newPass);
                        object.put("confirmpassword", newPass);
                        //object.put("email", PrefUtils.getUserName(getActivity()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Globals.POST(Urls.CHANGE_PASSWORD, null, object.toString(), new Globals.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            Log.v("response", result);
                        }

                        @Override
                        public void onFail(String result) {
                            Toast.makeText(getActivity(), "Fail===", Toast.LENGTH_SHORT).show();
                            Log.v("Result===",result);
                        }
                    });

                }
            }
        });

        return view;
    }


}
