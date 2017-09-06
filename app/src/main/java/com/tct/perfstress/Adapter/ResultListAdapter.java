package com.tct.perfstress.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;
import com.tct.perfstress.mode.ListItem;

/**
 * Created by lqchen on 17-9-6.
 */

public class ResultListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public ResultListAdapter(Context mContext) {
        this.mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return PerApplication.appSelected;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        View mView;

        if (convertView == null || convertView.getTag() == null) {
            mView = mInflater.inflate(R.layout.listview_common_item, null);
            holder = new ViewHolder(mView);
            mView.setTag(holder);
        } else {
            mView = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem listItem = PerApplication.chosenList.get(i);
        holder.imageView.setImageDrawable(listItem.getAppIcon());
        holder.title.setText(listItem.getAppName());
        holder.launchTime.setText(PerApplication.latencies.get(i) + " ms");

        return mView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView sub_title;
        TextView launchTime;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.appIcon);
            title = view.findViewById(R.id.title);
            sub_title = view.findViewById(R.id.sub_title);
            launchTime = view.findViewById(R.id.launchTime);
        }
    }

}
