package com.tct.perfstress.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tct.perfstress.PerApplication;
import com.tct.perfstress.R;
import com.tct.perfstress.mode.ListItem;
import com.tct.perfstress.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lqchen on 17-8-23.
 */

public class ListviewAdapter extends BaseAdapter {
    private List<ListItem> mListItems = new ArrayList<>();
    private LayoutInflater inflater;
    private HashMap<Integer, Boolean> isSelected;
    private boolean firstLaunch = true;


    public ListviewAdapter(Context context, HashMap<Integer, Boolean> isSelected) {
//        this.mListItems = mListItems;
        this.inflater = LayoutInflater.from(context);
        this.isSelected = isSelected;

    }

    public void updateList(List<ListItem> listItems) {
        firstLaunch = false;
        this.mListItems = listItems;
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < mListItems.size(); i++) {
            getIsSelected().put(i, false);
        }
    }


    @Override
    public int getCount() {

        return mListItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mListItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        View mView = null;

        if (convertView == null || convertView.getTag() == null) {
            mView = inflater.inflate(R.layout.list_common_item, null);
            mViewHolder = new ViewHolder(mView);
            mView.setTag(mViewHolder);
        } else {
            mView = convertView;
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        ListItem listItem = (ListItem) getItem(i);
        mViewHolder.appIcon.setImageDrawable(listItem.getAppIcon());
        mViewHolder.tv_appName.setText(listItem.getAppName());
        mViewHolder.tv_appPkg.setText(listItem.getAppPkgName());
//        mViewHolder.checkBox.setChecked(listItem.isselected());

        mViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(MainActivity.TAG, "点击 " + i);
                if (isSelected.get(i)) {
                    PerApplication.appSelected --;
                    isSelected.put(i, false);
                    setIsSelected(isSelected);
                } else {
                    PerApplication.appSelected ++;
                    isSelected.put(i, true);
                    setIsSelected(isSelected);
                }
                notifyDataSetChanged();
            }
        });
        if (firstLaunch) {
            mViewHolder.checkBox.setChecked(false);
        } else {
            mViewHolder.checkBox.setChecked(getIsSelected().get(i));
        }
        return mView;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        this.isSelected = isSelected;
    }


    private class ViewHolder {
        ImageView appIcon;
        TextView tv_appName;
        TextView tv_appPkg;
        CheckBox checkBox;

        private ViewHolder(View view) {
            this.appIcon = view.findViewById(R.id.appIcon);
            this.tv_appName = view.findViewById(R.id.tv_appName);
            this.tv_appPkg = view.findViewById(R.id.tv_appPkg);
            this.checkBox = view.findViewById(R.id.checkBox);
        }
    }
}
