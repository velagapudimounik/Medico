package com.drughub.doctor.Cart;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.widget.Button;
import android.widget.Toast;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;


public class CartViewAdapter extends RecyclerView.Adapter<CartViewAdapter.childinfo> {

    Fragment fragment = null;
    Context cart_context;

    CartViewAdapter(Context context)
    {
        this.cart_context = context;

    }


    @Override
    public childinfo onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_child_view , parent , false);
        childinfo info = new childinfo(view);

        com.drughub.doctor.utils.DrughubIcon delete = (com.drughub.doctor.utils.DrughubIcon) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity)view.getContext(), "Are You Sure ?\nYou want to remove vaccine from cart.", "No", "Yes");

                Button dialog_yes = (Button) dialog.findViewById(R.id.dialogYesBtn);
                Button dialog_no = (Button) dialog.findViewById(R.id.dialogNoBtn);

                dialog_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(view.getContext(),"deleted",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });
            }
        });


         return info;
    }

    @Override
    public void onBindViewHolder(childinfo holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class childinfo extends RecyclerView.ViewHolder
    {


        public childinfo(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
