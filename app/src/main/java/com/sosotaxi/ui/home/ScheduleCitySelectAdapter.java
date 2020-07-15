package com.sosotaxi.ui.home;


import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.sosotaxi.R;

public class ScheduleCitySelectAdapter extends BaseAdapter implements
        SectionIndexer {
    private List<ScheduleCityGpsStruct> list = null;
    private Context mContext;

    public ScheduleCitySelectAdapter(Context mContext,
                                     List<ScheduleCityGpsStruct> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * @param list
     */
    public void updateListView(List<ScheduleCityGpsStruct> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.schedule_select_city_item, null);
            viewHolder.tvTitle = (TextView) view
                    .findViewById(R.id.city_list_item_name);
            viewHolder.tvLetter = (TextView) view
                    .findViewById(R.id.city_list_item_catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ScheduleCityGpsStruct struct = list.get(position);
        // 首字母
        String sortLetters = struct.getSortLetters();
        if (sortLetters != null && sortLetters.length() > 0) {
            if (position == getPositionForSection(position, sortLetters)) {
                viewHolder.tvLetter.setVisibility(View.VISIBLE);
                viewHolder.tvLetter.setText(sortLetters);
            }
        }else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(struct.getStrCityName());

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }

    public int getPositionForSection(String section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            if (section.equals(sortStr)) {
                return i;
            }
        }

        return -1;
    }

    public int getPositionForSection(int iPosition, String section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            if (section.equals(sortStr)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

}

