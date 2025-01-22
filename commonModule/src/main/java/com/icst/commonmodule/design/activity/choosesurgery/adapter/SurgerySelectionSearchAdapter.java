package com.icst.commonmodule.design.activity.choosesurgery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.icst.commonmodule.databinding.RowSpinnerMyProfileBinding;
import com.icst.commonmodule.model.RegisterSurgeryList;


import java.util.ArrayList;

public class SurgerySelectionSearchAdapter extends ArrayAdapter<RegisterSurgeryList.Data> {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<RegisterSurgeryList.Data> items;
    private ArrayList<RegisterSurgeryList.Data> itemsAll;
    private ArrayList<RegisterSurgeryList.Data> suggestions;

    Filter nameFilter = new Filter() {

        public String convertResultToString(Object resultValue) {
            String str = ((RegisterSurgeryList.Data) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (RegisterSurgeryList.Data product : itemsAll) {
                    if (product.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<RegisterSurgeryList.Data> filteredList =
                    (ArrayList<RegisterSurgeryList.Data>) results.values;
            if (results != null && results.count > 0) {
                clear();

                for (RegisterSurgeryList.Data c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            } else {
                Log.e(TAG, "No results found.....");
            }
        }
    };

    @SuppressWarnings("unchecked")
    public SurgerySelectionSearchAdapter(Context context, int viewResourceId, ArrayList<RegisterSurgeryList.Data> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<RegisterSurgeryList.Data>) items.clone();
        this.suggestions = new ArrayList<RegisterSurgeryList.Data>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        RowSpinnerMyProfileBinding rowSpinnerMyProfileBinding = RowSpinnerMyProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        RegisterSurgeryList.Data product = items.get(position);

        if (product != null) {
            if (rowSpinnerMyProfileBinding.tvRowSpinnerItemMembership != null) {
                rowSpinnerMyProfileBinding.tvRowSpinnerItemMembership.setText(product.getName());
            }
        }
        return rowSpinnerMyProfileBinding.getRoot();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}