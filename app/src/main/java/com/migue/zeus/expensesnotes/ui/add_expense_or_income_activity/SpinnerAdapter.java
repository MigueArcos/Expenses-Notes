package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.migue.zeus.expensesnotes.data.models.BaseEntity;

import java.util.List;

public class SpinnerAdapter<T extends BaseEntity> extends ArrayAdapter<T> {

    private List<T> items;
    private Context context;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
    }


    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    public int getItemPosition(Long id) {
        int index = 0;
        for (T i : items) {
            //noinspection ConstantConditions
            if (i.getId().equals(id)) return index;
            index++;
        }
        return index;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView text = new TextView(context);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        text.setPadding(4, 4, 4, 4);
        text.setText(items.get(position).getReadableName());
        return text;
    }

    /*@Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);
        CheckedTextView text = new CheckedTextView(context);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        text.setPadding(8, 8, 8, 8);
        text.setChecked();
        text.setText(items.get(position).getReadableName());
        return text;
    }*/

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(android.R.layout.select_dialog_singlechoice, parent, false);
        CheckedTextView text = v.findViewById(android.R.id.text1);
        text.setText(items.get(position).getReadableName());
        return v;
        //return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return items.size();
    }

}
