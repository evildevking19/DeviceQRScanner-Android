package com.project.android.qr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private DBManager dbManager;

    private ExpandableListView contactListView;
    private CustomExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        dbManager = new DBManager(this);
        dbManager.open();

        List<String> groups = new ArrayList<>();
        HashMap<String, ContactModel> details = new HashMap<>();
        List<ContactModel> modelList = dbManager.fetch();
        for (ContactModel model : modelList) {
            groups.add(model.getName());
            details.put(model.getName(), model);
        }

        contactListView = findViewById(R.id.contact_list_view);
        adapter = new CustomExpandableListAdapter(this, groups, details);
        contactListView.setAdapter(adapter);
    }
}