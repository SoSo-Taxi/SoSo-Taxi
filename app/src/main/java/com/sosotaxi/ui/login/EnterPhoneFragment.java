package com.sosotaxi.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sosotaxi.R;
import com.sosotaxi.common.Constant;

import static android.app.Activity.RESULT_OK;

/**
 * 输入手机号界面
 */
public class EnterPhoneFragment extends Fragment {

    private TextView mTextViewAreaCode;
    private EditText mEditTextPhone;
    private Button mButtonContinue;


    public EnterPhoneFragment() {
        // 所需空构造器
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 填充布局
        return inflater.inflate(R.layout.fragment_enter_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //绑定控件
        mEditTextPhone=getView().findViewById(R.id.editTextEnterPhone);
        mButtonContinue=getView().findViewById(R.id.buttonContinue);
        mTextViewAreaCode=getView().findViewById(R.id.textViewAreaCode);

        //设置继续按钮点击事件
        mButtonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String areaCodeString=mTextViewAreaCode.getText().toString();
                String areaCode=areaCodeString.substring(0,areaCodeString.length()-1);
                String phone=mEditTextPhone.getText().toString();

                //TODO: 查询手机号是否已注册
                boolean isRegistered=phone.equals("1");

                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                Fragment currentFragment=fragmentManager.findFragmentById(R.id.fragmentLogin);

                if(isRegistered){
                    //已注册跳转到输入密码界面
                    EnterPasswordFragment enterPasswordFragment=new EnterPasswordFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString(Constant.EXTRA_PHONE,areaCode+phone);
                    enterPasswordFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .setCustomAnimations(
                                    R.animator.fragment_slide_left_enter,
                                    R.animator.fragment_slide_left_exit,
                                    R.animator.fragment_slide_right_enter,
                                    R.animator.fragment_slide_right_exit)
                            .add(R.id.fragmentLogin,enterPasswordFragment,null)
                            .addToBackStack(null)
                            .commit();

                    LoginActivity loginActivity=(LoginActivity)getActivity();
                    loginActivity.setBackUpButtonOn();
                }else{
                    //未注册跳转到输入验证码界面
                    EnterVerificationCodeFragment enterVerificationCodeFragment=new EnterVerificationCodeFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString(Constant.EXTRA_PHONE,areaCode+phone);
                    enterVerificationCodeFragment.setArguments(bundle);

                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .setCustomAnimations(
                                    R.animator.fragment_slide_left_enter,
                                    R.animator.fragment_slide_left_exit,
                                    R.animator.fragment_slide_right_enter,
                                    R.animator.fragment_slide_right_exit)
                            .add(R.id.fragmentLogin,enterVerificationCodeFragment,null)
                            .addToBackStack(null)
                            .commit();

                    LoginActivity loginActivity=(LoginActivity)getActivity();
                    loginActivity.setBackUpButtonOn();
                }

            }
        });

        mTextViewAreaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectAreaCodeActivity.class);
                startActivityForResult(intent, Constant.SELECT_AREA_CODE_REQUEST);
            }
        });

        //手机号输入框字数监听
        mEditTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    //未输入时继续按钮不可用
                    mButtonContinue.setBackgroundColor(getResources().getColor(R.color.colorDisabledButton));
                    mButtonContinue.setEnabled(false);
                }else{
                    //输入后才可用
                    mButtonContinue.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mButtonContinue.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK) {
            switch (requestCode){
                case Constant.SELECT_AREA_CODE_REQUEST:
                    int areaCode=data.getIntExtra(Constant.EXTRA_AREA_CODE,86);
                    String areaCodeString="+"+areaCode+" ▼";
                    mTextViewAreaCode.setText(areaCodeString);
                    break;
            }
        }
    }
}