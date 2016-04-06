package com.drughub.doctor.Login;

import android.app.ProgressDialog;
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
import com.drughub.doctor.R;
import com.drughub.doctor.model.User;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPage extends Fragment {
    ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_layout, container, false);

        getActivity().setTitle(getResources().getString(R.string.login));

        final EditText editTextPassword = (EditText) view.findViewById(R.id.passwordTextField);
        final EditText editTextUserName = (EditText) view.findViewById(R.id.userNameTextField);
        editTextPassword.setTypeface(Typeface.DEFAULT);

        editTextUserName.setText(PrefUtils.getUserName(getActivity()));

        final TextView forgotPasswordTextView = (TextView) view.findViewById(R.id.forgotPasswordTextView);
        final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        final Button signUpButton = (Button) view.findViewById(R.id.signUpButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == loginButton) {
                    String username = editTextUserName.getText().toString();
                    String password = editTextPassword.getText().toString();
                    if (!username.isEmpty()) {
                        if (!password.isEmpty() && password.length() >= 8) {
                            signIn(username, password);
                        } else {
                            Toast.makeText(getActivity(), "Password minimum 8 characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter valid name", Toast.LENGTH_SHORT).show();
                    }
                } else if (view == signUpButton) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new SignUpFragment()).addToBackStack(null).commit();
                } else if (view == forgotPasswordTextView) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new ForgotPasswordFragment()).addToBackStack(null).commit();
                }
            }
        };

        loginButton.setOnClickListener(listener);
        signUpButton.setOnClickListener(listener);
        forgotPasswordTextView.setOnClickListener(listener);

        return view;
    }

    private void signIn(final String username, final String password) {
        progress = ProgressDialog.show(getActivity(), "SignIn", "Please wait...", true);
        User user = new User();
        user.setEmail(username);
        user.setPassword(password);
        user.SignIn(getActivity(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                if (progress != null)
                    progress.dismiss();
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
                if (progress != null)
                    progress.dismiss();
                Toast.makeText(getActivity(), "Unable to process your request, please try again.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
