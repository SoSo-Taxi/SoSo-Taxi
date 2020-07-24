/**
 * @Author 屠天宇
 * @CreateTime 2020/7/12
 * @UpdateTime 2020/7/24
 */

package com.sosotaxi.ui.userInformation.personData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sosotaxi.R;

public class IndustryChosenRecycleViewAdapter extends
        RecyclerView.Adapter<IndustryChosenRecycleViewAdapter.LinearViewHolder> {

    private Context mContext;

    private GetChoice mGetChoice;

    private String[] mChoices = {"互联网-软件","通信-硬件","房地产-建筑","文化传媒"
            , "金融类","消费品","汽车-机械","教育-翻译","贸易-物流"
            ,"生物-医疗","能源-化工","政府机构","服务业","其他行业"
    };

    public void setGetChoice(GetChoice getChoice){
        this.mGetChoice = getChoice;
    }

    public IndustryChosenRecycleViewAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public IndustryChosenRecycleViewAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new LinearViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.adapter_industry_chosen_recycleview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  IndustryChosenRecycleViewAdapter.LinearViewHolder holder, final int position) {

        holder.mTextView.setText(mChoices[position]);

        holder.mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mGetChoice.onItemClick(mChoices[position],(short)position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mChoices.length;
    }




    class  LinearViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout mConstraintLayout;
        private TextView mTextView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            mConstraintLayout = itemView.findViewById(R.id.rv_cl);
            mTextView = itemView.findViewById(R.id.rv_tv);
        }
    }
}
