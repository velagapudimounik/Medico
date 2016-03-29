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

        final EditText editTextName = (EditText) view.findViewById(R.id.userNameTextField);
        final EditText editTextassword = (EditText) view.findViewById(R.id.passwordTextField);
        editTextassword.setTypeface(Typeface.DEFAULT);

        final TextView forgotpasswordtextview = (TextView) view.findViewById(R.id.forgotPasswordTextView);
        final Button login = (Button) view.findViewById(R.id.loginButton);
        final Button signup = (Button) view.findViewById(R.id.signUpButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view==view.findViewById(R.id.loginButton)){
                    Intent loginintent = new Intent(getActivity(), MainActivity.class);
                    startActivity(loginintent);
                } else if (view == view.findViewById(R.id.signUpButton)) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new SignUpFragment()).addToBackStack(null).commit();

                } else if (view == view.findViewById(R.id.forgotPasswordTextView)) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new ForgotPasswordFragment()).addToBackStack(null).commit();
                }
            }
        };
        Button btn1 = (Button) view.findViewById(R.id.loginButton);
        btn1.setOnClickListener(listener);
        Button btn2 = (Button) view.findViewById(R.id.signUpButton);
        btn2.setOnClickListener(listener);
        TextView btn3 = (TextView) view.findViewById(R.id.forgotPasswordTextView);
        btn3.setOnClickListener(listener);
        Button btn4 = (Button) view.findViewById(R.id.facebookButton);
        btn4.setOnClickListener(listener);
        return view;
    }
}
