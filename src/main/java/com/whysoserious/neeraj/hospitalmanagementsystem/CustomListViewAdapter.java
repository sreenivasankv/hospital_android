package com.whysoserious.neeraj.hospitalmanagementsystem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Neeraj on 08-Apr-16.
 */
public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tv_p_name;
        TextView tv_d_name;
        TextView tv_problem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_adapter, null);
            holder = new ViewHolder();
            holder.tv_d_name = (TextView) convertView.findViewById(R.id.d_name_adapter);
            holder.tv_p_name = (TextView) convertView.findViewById(R.id.p_name_adapter);
            holder.tv_problem = (TextView) convertView.findViewById(R.id.problem_adapter);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.tv_d_name.setText(rowItem.getDoctor());
        holder.tv_p_name.setText(rowItem.getPatient());
        holder.tv_problem.setText(rowItem.getProblem());
        return convertView;
    }
}
