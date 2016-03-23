package com.drughub.doctor.patientrecords;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;


public class BookmarkedActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_fragment);
        setBackButton(true);
        setTitle(getResources().getString(R.string.bookmark));
        EditText editText = (EditText) findViewById(R.id.patientRecordSearch);
        editText.setHint(getResources().getString(R.string.bookmarkSearch));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.patient_records_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        ArrayList<PatientRecord> patientRecords = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            patientRecords.add(new PatientRecord("Amar " + i, "D.O.B : 24th Nov 1998", "16"));
        }

        PatientRecordsAdapter adapter = new PatientRecordsAdapter(this, patientRecords);
        recyclerView.setAdapter(adapter);

    }
}




