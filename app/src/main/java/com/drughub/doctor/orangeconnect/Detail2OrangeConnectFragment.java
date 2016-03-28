package com.drughub.doctor.orangeconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drughub.doctor.R;

import java.util.ArrayList;

public class Detail2OrangeConnectFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.orange_connect_detail2_fragment, container, false);

        getActivity().setTitle("Suriya");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.orange_connect_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<OrangeConnect> orangeConnects = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            orangeConnects.add(new OrangeConnect("Hepatitis B3", "24 Nov 1998", "Administered at 3\u00B0C"));
        }

        Detail2OrangeConnectAdapter adapter = new Detail2OrangeConnectAdapter(getActivity(), orangeConnects);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

class Detail2OrangeConnectAdapter extends RecyclerView.Adapter<Detail2OrangeConnectAdapter.DataObjectHolder> {
    Context context;
    ArrayList<OrangeConnect> orderConnects;

    public Detail2OrangeConnectAdapter(Context context, ArrayList<OrangeConnect> orderConnects) {
        this.context = context;
        this.orderConnects = orderConnects;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail2_orange_connect, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        OrangeConnect orangeConnect = orderConnects.get(position);
    }

    @Override
    public int getItemCount() {
        return orderConnects.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView report, content;
        RelativeLayout layout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            report = (TextView) itemView.findViewById(R.id.report);
            content = (TextView) itemView.findViewById(R.id.content);
            layout = (RelativeLayout) itemView.findViewById(R.id.rel);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (content.getVisibility() == View.VISIBLE)
                content.setVisibility(View.GONE);
            else
                content.setVisibility(View.VISIBLE);

        }
    }
}

