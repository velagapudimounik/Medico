package com.drughub.doctor.MyOrders;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.drughub.doctor.R;


public class MyOrderActivityAdapter extends RecyclerView.Adapter<MyOrderActivityAdapter.infodata> {

    FragmentActivity order_context = null;
    Context order_activity = null;
    Fragment fragment = null;


    MyOrderActivityAdapter(FragmentActivity context )
    {
        this.order_context = context;
    }


    @Override
    public infodata onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders_child_view,parent,false);
        return new infodata(view);
    }

    @Override
    public void onBindViewHolder(infodata holder, int position) {



    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class infodata extends RecyclerView.ViewHolder
    {

        public infodata(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      fragment = new OrderInformationList();
                    FragmentManager fragmentManager = order_context.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.order_container,fragment).addToBackStack(null).commit();

                    Toast.makeText(v.getContext(), v.getId() + "", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
