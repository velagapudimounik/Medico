package com.drughub.doctor.Login;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drughub.doctor.MainActivity;
import com.drughub.doctor.R;

public class LoginPage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_layout, container, false);

        getActivity().setTitle(getResources().getString(R.string.login));

        EditText editTextPassword = (EditText) view.findViewById(R.id.passwordTextField);
        editTextPassword.setTypeface(Typeface.DEFAULT);

        final TextView forgotPasswordTextView = (TextView) view.findViewById(R.id.forgotPasswordTextView);
        final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        final Button signUpButton = (Button) view.findViewById(R.id.signUpButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == loginButton){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
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
}
