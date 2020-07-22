/**
 * @Author 屠天宇
 * @CreateTime 2020/7/22
 * @UpdateTime 2020/7/22
 */

package com.sosotaxi.ui.userInformation.setting.emergencyContact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sosotaxi.R;

import java.util.List;

public class ContactRecycleViewAdapter extends RecyclerView.Adapter<ContactRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<PhoneInfo> phoneInfoList;
    private ClickListener mClickListener;

    public ContactRecycleViewAdapter(Context mContext, List<PhoneInfo> phoneInfoList) {
        this.mContext = mContext;
        this.phoneInfoList = phoneInfoList;
    }

    public void setClickListener(ClickListener clickListener){
        this.mClickListener = clickListener;
    }
    @NonNull
    @Override
    public ContactRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_contact_recycle_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecycleViewAdapter.ViewHolder holder, final int position) {

        holder.mPhoneTextView.setText(phoneInfoList.get(position).getNumber());
        holder.mNameTextView.setText(phoneInfoList.get(position).getName());
        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.click(phoneInfoList.get(position).getNumber(),
                        phoneInfoList.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneInfoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mPhoneTextView;
        private TextView mNameTextView;
        private ConstraintLayout mConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhoneTextView = itemView.findViewById(R.id.contact_recycle_view_phone);
            mNameTextView = itemView.findViewById(R.id.contact_recycle_view_name);
            mConstraintLayout = itemView.findViewById(R.id.contact_rv_constraintLayout);
        }
    }
}
