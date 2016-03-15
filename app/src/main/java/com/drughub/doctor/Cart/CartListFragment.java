package com.drughub.doctor.Cart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.app.Dialog;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.R;


public class CartListFragment extends Fragment{




    Fragment fragment = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.cart_container);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.cart_item_list,container,false);

       /* TextView toolbar_text = (TextView) view.findViewById(R.id.toolbar_title);
        toolbar_text.setText("Cart");
*/



        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        CartViewAdapter adapter = new CartViewAdapter(this.getActivity());
        recyclerView.setAdapter(adapter);


        Button place_order = (Button) view.findViewById(R.id.place);
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        fragment = new OrderSummary();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

                    }

        });

      return view;
    }

}
