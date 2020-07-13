/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/11
 */
package com.sosotaxi.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;
import com.sosotaxi.common.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入验证码界面
 */
public class EnterVerificationCodeFragment extends Fragment {

    private TextView mTextViewEnterVerificationCodePhone;
    private TextView mTextViewEnterVerificationCodeResend;
    private List<EditText> mEditTextEnterVerificationCodes;

    /**
     * 验证码计时器
     */
    private CountDownTimer mTimer;

    public EnterVerificationCodeFragment() {
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
        return inflater.inflate(R.layout.fragment_enter_verification_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditTextEnterVerificationCodes=new ArrayList<>();

        // 获取控件
        mTextViewEnterVerificationCodePhone=getActivity().findViewById(R.id.textViewEnterVerificationCodePhone);
        mTextViewEnterVerificationCodeResend=getActivity().findViewById(R.id.textViewVerificationCodeResend);
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode0));
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode1));
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode2));
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode3));
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode4));
        mEditTextEnterVerificationCodes.add((EditText) getActivity().findViewById(R.id.editTextVerificationCode5));

        // 首位验证码输入框获取控制焦点
        final EditText firstEditText=mEditTextEnterVerificationCodes.get(0);
        final EditText secondEditText=mEditTextEnterVerificationCodes.get(1);

        firstEditText.requestFocus();

        firstEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1){
                    // 输入完毕后控制焦点移动到下一个输入框
                    firstEditText.clearFocus();
                    secondEditText.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        for(int i=1;i<mEditTextEnterVerificationCodes.size()-1;i++){
            final EditText lastEditText=mEditTextEnterVerificationCodes.get(i-1);
            final EditText currentEditText=mEditTextEnterVerificationCodes.get(i);
            final EditText nextEditText=mEditTextEnterVerificationCodes.get(i+1);
            currentEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()==1){
                        // 输入完毕后控制焦点移动到下一个输入框
                        currentEditText.clearFocus();
                        nextEditText.requestFocus();
                    }
                    if(count==0){
                        // 删除时控制焦点移动到上一个输入框
                        currentEditText.clearFocus();
                        lastEditText.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            if(i==4){
                nextEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length()==1){
                            StringBuffer stringBuffer=new StringBuffer();
                            for(int i=0;i<mEditTextEnterVerificationCodes.size();i++){
                                stringBuffer.append(mEditTextEnterVerificationCodes.get(i).getText());
                            }

                            String verificationCode=stringBuffer.toString();
                            //TODO:发送验证码至服务器进行验证

                            boolean isCorrect =verificationCode.equals("123456");

                            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                            Fragment currentFragment=fragmentManager.findFragmentById(R.id.fragmentLogin);

                            if(isCorrect){
                                // 验证码正确跳转创建密码界面
                                CreatePasswordFragment createPasswordFragment=new CreatePasswordFragment();
                                createPasswordFragment.setArguments(getArguments());

                                // 设置转场
                                fragmentManager.beginTransaction()
                                        .hide(currentFragment)
                                        .setCustomAnimations(
                                                R.animator.fragment_slide_left_enter,
                                                R.animator.fragment_slide_left_exit,
                                                R.animator.fragment_slide_right_enter,
                                                R.animator.fragment_slide_right_exit)
                                        .add(R.id.fragmentLogin,createPasswordFragment,null)
                                        .addToBackStack(null)
                                        .commit();

                                mTimer.cancel();

                                LoginActivity loginActivity=(LoginActivity)getActivity();
                                loginActivity.setBackUpButtonOn();
                            }else{
                                // 验证码错误则清空验证码
                                for(int i=0;i<mEditTextEnterVerificationCodes.size();i++){
                                    mEditTextEnterVerificationCodes.get(i).setText("");
                                    mEditTextEnterVerificationCodes.get(i).clearFocus();
                                }

                                // 控制焦点移动到首位输入框
                                mEditTextEnterVerificationCodes.get(0).requestFocus();

                                // 提示验证码错误
                                Toast.makeText(getContext(), R.string.error_verification_code,Toast.LENGTH_SHORT).show();

                            }
                        }

                        if(count==0){
                            // 删除时控制焦点移动到上一个输入框
                            nextEditText.clearFocus();
                            currentEditText.requestFocus();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        }

        // 显示当前手机号
        String phone=getArguments().getString(Constant.EXTRA_PHONE);
        mTextViewEnterVerificationCodePhone.setText(phone);

        // 设置重新发送点击事件
        mTextViewEnterVerificationCodeResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:向服务器请求再次发送验证码

                // 重启验证码计时器
                mTimer.start();

                // 提示验证码已发送
                Toast.makeText(getContext(), R.string.hint_verification_code_sent, Toast.LENGTH_SHORT).show();
            }
        });

        // TODO：向服务器请求发送验证码

        // 启动验证码计时器
        mTimer=getTimer();
        mTimer.start();

        // 提示验证码已发送
        Toast.makeText(getContext(), R.string.hint_verification_code_sent, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取验证码发送计时器
     * @return 计时器
     */
    private CountDownTimer getTimer(){
        CountDownTimer timer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 显示剩余时间
                mTextViewEnterVerificationCodeResend.setText(millisUntilFinished/1000L+" s");
                mTextViewEnterVerificationCodeResend.setClickable(false);
            }

            @Override
            public void onFinish() {
                // 设置重新发送为可用
                mTextViewEnterVerificationCodeResend.setText(R.string.btn_verification_code_resend);
                mTextViewEnterVerificationCodeResend.setClickable(true);
            }
        };
        return timer;
    }
}