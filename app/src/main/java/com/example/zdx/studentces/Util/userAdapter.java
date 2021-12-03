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
public class userAdapter extends ArrayAdapter<user> {
    private int resourceId;

    public userAdapter(Context context, int resource, List<user> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        user user = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.user_name = (TextView) view.findViewById(R.id.user_name);
            viewHolder.user_id = (TextView) view.findViewById(R.id.user_id);
            viewHolder.user_passwd = (TextView) view.findViewById(R.id.user_passwd);


            view.setTag(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.user_name.setText(user.getUsername());
        viewHolder.user_id.setText(user.getUserid());
        viewHolder.user_passwd.setText(user.getUserpasswd());
        return view;
    }

    class ViewHolder {
        TextView user_name;
        TextView user_id;
        TextView user_passwd;
    }

}

