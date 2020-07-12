/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/11
 */


package com.sosotaxi.ui.userInformation.personData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sosotaxi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgeChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgeChoiceFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ConstraintLayout mConstraintLayout;
    private TextView mCancelTextView;
    private TextView mAgeTextView;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;


    private FragmentManager fragmentManager;
    public AgeChoiceFragment() {
        // Required empty public constructor
    }

    public AgeChoiceFragment(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }
    public AgeChoiceFragment(FragmentManager fragmentManager,TextView ageTextView){
        this.fragmentManager = fragmentManager;
        this.mAgeTextView = ageTextView;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgeChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgeChoiceFragment newInstance(String param1, String param2) {
        AgeChoiceFragment fragment = new AgeChoiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConstraintLayout = view.findViewById(R.id.fragment_clayout);
        mCancelTextView = view.findViewById(R.id.cancel_textView);
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });

        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);
        tv5 = view.findViewById(R.id.tv5);
        tv6 = view.findViewById(R.id.tv6);


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv1.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv2.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv3.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv4.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv5.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeTextView.setText(tv6.getText());
                BlankFragment blankFragment = new BlankFragment();
                fragmentManager.beginTransaction().replace(R.id.fr,blankFragment).commitAllowingStateLoss();
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_age_choice,container,false);
        return view;
    }
}