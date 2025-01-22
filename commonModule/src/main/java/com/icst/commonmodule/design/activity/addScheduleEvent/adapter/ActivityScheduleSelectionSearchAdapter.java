package com.icst.commonmodule.design.activity.addScheduleEvent.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.icst.commonmodule.databinding.RowSpinnerMyProfileBinding;
import com.icst.commonmodule.model.ScheduleEventModel;

import java.util.ArrayList;

public class ActivityScheduleSelectionSearchAdapter extends ArrayAdapter<ScheduleEventModel.Data> {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<ScheduleEventModel.Data> items;
    private ArrayList<ScheduleEventModel.Data> itemsAll;
    private ArrayList<ScheduleEventModel.Data> suggestions;

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((ScheduleEventModel.Data) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (ScheduleEventModel.Data product : itemsAll) {
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
            ArrayList<ScheduleEventModel.Data> filteredList =
                    (ArrayList<ScheduleEventModel.Data>) results.values;
            if (results != null && results.count > 0) {
                clear();

                for (ScheduleEventModel.Data c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            } else {

                Log.e(TAG, "No results found.....");

            }
        }
    };

    @SuppressWarnings("unchecked")
    public ActivityScheduleSelectionSearchAdapter(Context context, int viewResourceId,
                                                  ArrayList<ScheduleEventModel.Data> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<ScheduleEventModel.Data>) items.clone();
        this.suggestions = new ArrayList<>();


    }

    public View getView(int position, View convertView, ViewGroup parent) {
      /*  View v = convertView;
        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row_spinner_my_profile, null);


        }*/

        RowSpinnerMyProfileBinding rowSpinnerMyProfileBinding = RowSpinnerMyProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        ScheduleEventModel.Data product = items.get(position);
        if (product != null) {
            // TextView productLabel = v.findViewById(R.id.tvRowSpinnerItemMembership);
            if (rowSpinnerMyProfileBinding.tvRowSpinnerItemMembership != null) {
                rowSpinnerMyProfileBinding.tvRowSpinnerItemMembership.setText(product.getName());
            } else {
                rowSpinnerMyProfileBinding.tvRowSpinnerItemMembership.getText();
            }
        }
        return rowSpinnerMyProfileBinding.getRoot();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
