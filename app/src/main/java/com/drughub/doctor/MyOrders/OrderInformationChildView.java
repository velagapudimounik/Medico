package com.drughub.doctor.MyOrders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.drughub.doctor.R;


public class OrderInformationChildView extends RecyclerView.Adapter<OrderInformationChildView.childdata> {

    Context OrderContext = null;

    OrderInformationChildView(Context context)
    {
        OrderContext = context;
    }

    @Override
    public childdata onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_information_child_view,parent,false);
        childdata data = new childdata(view);

        return data;
    }

    @Override
    public void onBindViewHolder(childdata holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class childdata extends RecyclerView.ViewHolder

    {

        public childdata(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(),v.getId()+"",Toast.LENGTH_SHORT).show();



                }
            });
        }
    }
 }
