package com.drughub.doctor.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.MainActivity;
import com.drughub.doctor.MyApplication;
import com.drughub.doctor.R;
import com.drughub.doctor.model.User;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends Fragment {

    public static final int MIN_PASSWORD_LENGTH = 8;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_layout, container, false);

        getActivity().setTitle(getResources().getString(R.string.login));

        final EditText editTextPassword = (EditText) view.findViewById(R.id.passwordTextField);
        final EditText editTextUserName = (EditText) view.findViewById(R.id.userNameTextField);
        editTextPassword.setTypeface(Typeface.DEFAULT);

        editTextUserName.setText(PrefUtils.getUserName(getActivity()));
        editTextPassword.setText(PrefUtils.getPassword(getActivity()));

        final TextView forgotPasswordTextView = (TextView) view.findViewById(R.id.forgotPasswordTextView);
        final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        final Button signUpButton = (Button) view.findViewById(R.id.signUpButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == loginButton) {
                    String username = editTextUserName.getText().toString();
                    String password = editTextPassword.getText().toString();

                    if (username.isEmpty())
                        Toast.makeText(getActivity(), "Please enter your Email ID", Toast.LENGTH_SHORT).show();
                    else if (!Globals.isValidEmail(username))
                        Toast.makeText(getActivity(), "Please enter valid Email ID", Toast.LENGTH_SHORT).show();
                    else if (password.isEmpty())
                        Toast.makeText(getActivity(), "Please enter your password", Toast.LENGTH_SHORT).show();
                    //else if (password.length() < MIN_PASSWORD_LENGTH)
                    //    Toast.makeText(getActivity(), "Password should be minimum "+MIN_PASSWORD_LENGTH+" characters", Toast.LENGTH_SHORT).show();
                    else
                        signIn(username, password);

                } else if (view == signUpButton) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new SignUpFragment()).addToBackStack(null).commit();
                } else if (view == forgotPasswordTextView) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new ForgotPasswordFragment()).addToBackStack(null).commit();

                    //startActivity(new Intent(getActivity(), ForgotPasswordFragment.class));
                    //getActivity().finish();
                }
            }
        };

        loginButton.setOnClickListener(listener);
        signUpButton.setOnClickListener(listener);
        forgotPasswordTextView.setOnClickListener(listener);

        return view;
    }

    private void signIn(final String username, final String password) {
        User user = new User();
        user.setEmail(username);
        user.setPassword(Globals.encryptString(password));
        user.SignIn(getActivity(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("result")) {
                        PrefUtils.saveToLoginPrefs(getActivity(), username, password);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
            }
        });
    }
}
