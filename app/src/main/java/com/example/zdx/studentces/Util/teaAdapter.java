package com.example.zdx.studentces.Util;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zdx.studentces.R;

import java.util.List;
/**
 * 自定义员工信息适配器（基本信息）
 */
public class teaAdapter extends ArrayAdapter<tea> {
    private int resourceId;

    public teaAdapter(Context context, int resource, List<tea> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tea tea = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.tea_num = (TextView) view.findViewById(R.id.tea_num);
            viewHolder.tea_name = (TextView) view.findViewById(R.id.tea_name);
            viewHolder.tea_stock = (TextView) view.findViewById(R.id.tea_stock);
            viewHolder.tea_price = (TextView) view.findViewById(R.id.tea_price);


            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tea_num.setText(tea.getTeanum());
        viewHolder.tea_name.setText(tea.getTeaname());
        viewHolder.tea_stock.setText(tea.getTeastock());
        viewHolder.tea_price.setText(tea.getTeaprice());
        return view;
    }

    class ViewHolder {
        TextView tea_num;
        TextView tea_name;
        TextView tea_stock;
        TextView tea_price;
    }

}

