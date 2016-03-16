package com.drughub.doctor.Login;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
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
        getActivity().setTitle("Login");

        final EditText editTextName = (EditText) view.findViewById(R.id.EdittextName);
        final EditText editTextassword = (EditText) view.findViewById(R.id.Edittextpassword);
        final TextView forgotpasswordtextview = (TextView) view.findViewById(R.id.Forgotpasswordtextview);
        final Button login = (Button) view.findViewById(R.id.loginbutton);
        final Button signup = (Button) view.findViewById(R.id.signupbutton);
        final TextView orusingtextview = (TextView) view.findViewById(R.id.orusingtextview);
        final Button fbbutton = (Button) view.findViewById(R.id.facebookbutton);
        final Button gpbutton = (Button) view.findViewById(R.id.googleplusbutton);
        final Button lkbutton = (Button) view.findViewById(R.id.linkdeinbutton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view==view.findViewById(R.id.loginbutton)){
                    Intent loginintent = new Intent(getActivity(), MainActivity.class);
                    startActivity(loginintent);
                } else if (view == view.findViewById(R.id.signupbutton)) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new SignUpFragment()).addToBackStack(null).commit();

                } else if (view == view.findViewById(R.id.Forgotpasswordtextview)) {
                    getFragmentManager().beginTransaction().replace(R.id.container1, new ForgotPasswordFragment()).addToBackStack(null).commit();
                }
            }
        };
        Button btn1 = (Button) view.findViewById(R.id.loginbutton);
        btn1.setOnClickListener(listener);
        Button btn2 = (Button) view.findViewById(R.id.signupbutton);
        btn2.setOnClickListener(listener);
        TextView btn3 = (TextView) view.findViewById(R.id.Forgotpasswordtextview);
        btn3.setOnClickListener(listener);
        Button btn4 = (Button) view.findViewById(R.id.facebookbutton);
        btn4.setOnClickListener(listener);
        Button btn5 = (Button) view.findViewById(R.id.googleplusbutton);
        btn1.setOnClickListener(listener);
        Button btn6 = (Button) view.findViewById(R.id.linkdeinbutton);
        btn2.setOnClickListener(listener);
        return view;
    }
}
