package com.project.android.qr;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> names;
    private HashMap<String, ContactModel> details;

    public CustomExpandableListAdapter(Context context, List<String> names,
                                       HashMap<String, ContactModel> details) {
        this.context = context;
        this.names = names;
        this.details = details;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.details.get(this.names.get(listPosition));
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ContactModel model = (ContactModel) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView nameView = convertView.findViewById(R.id.item_name);
        TextView emailView = convertView.findViewById(R.id.item_email);
        TextView dobView = convertView.findViewById(R.id.item_dob);
        TextView streetView = convertView.findViewById(R.id.item_street);
        TextView cityView = convertView.findViewById(R.id.item_city);
        TextView stateView = convertView.findViewById(R.id.item_state);
        TextView countryView = convertView.findViewById(R.id.item_country);
        TextView postcodeView = convertView.findViewById(R.id.item_postcode);
        TextView phoneView = convertView.findViewById(R.id.item_phone);
        TextView cellView = convertView.findViewById(R.id.item_cell);
        TextView natView = convertView.findViewById(R.id.item_nat);

        nameView.setText("Name: " + model.getName());
        emailView.setText("Email: " + model.getEmail());
        dobView.setText("Birthdate: " + model.getDob());
        streetView.setText("Street: " + model.getStreet());
        cityView.setText("City: " + model.getCity());
        stateView.setText("State: " + model.getState());
        countryView.setText("Country: " + model.getCountry());
        postcodeView.setText("Postcode: " + model.getPostcode());
        phoneView.setText("Phone: " + model.getPhone());
        cellView.setText("Cell: " + model.getCell());
        natView.setText("Native: " + model.getNat());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.names.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.names.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String name = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView nameView = convertView.findViewById(R.id.list_name);
        nameView.setTypeface(null, Typeface.BOLD);
        nameView.setText(name);

        String img = details.get(name).getImg_sm();
        Picasso.get().load(img).into((ImageView) convertView.findViewById(R.id.list_img));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
