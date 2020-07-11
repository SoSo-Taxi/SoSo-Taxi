package com.sosotaxi.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sosotaxi.R;
import com.sosotaxi.ui.main.MainActivity;

/**
 * 创建密码界面
 */
public class CreatePasswordFragment extends Fragment {

    private EditText mEditTextPassword;
    private EditText mEditTextPasswordConfirmed;
    private Button mButtonConfirm;

    private boolean mFlag1;
    private boolean mFlag2;

    public CreatePasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_create_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditTextPassword=getActivity().findViewById(R.id.editTextCreatePassword);
        mEditTextPasswordConfirmed=getActivity().findViewById(R.id.editTextCreatePasswordConfirmed);
        mButtonConfirm=getActivity().findViewById(R.id.buttonCreatePasswordConfirm);
        mFlag1=false;
        mFlag2=false;

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 检查密码位数
                mFlag1=checkPassword(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTextPasswordConfirmed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //检查密码位数
                mFlag2=checkPassword(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 设置确认按钮点击事件
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=mEditTextPassword.getText().toString();
                String passwordConfirmed=mEditTextPasswordConfirmed.getText().toString();

                if(!password.equals(passwordConfirmed)){
                    // 提示密码不一致
                    mEditTextPasswordConfirmed.setError(getString(R.string.error_password_different));

                }else{
                    // 验证密码是否符合要求
                    boolean result=password.matches(getString(R.string.regex_password));


                    if(result==true){
                        //TODO:向服务器发送密码

                        boolean isSuccessful=true;

                        if(isSuccessful){
                            //注册成功跳转主界面
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            // 提示注册失败
                            Toast.makeText(getContext(), R.string.error_register_failed,Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        // 清空密码输入框并提示密码不符合要求
                        mEditTextPassword.setText("");
                        mEditTextPasswordConfirmed.setText("");
                        mEditTextPassword.setError(getString(R.string.error_password_not_match));
                    }
                }
            }
        });
    }

    /**
     * 检查密码位数
     * @param s 密码
     * @return  flag 符合标志
     */
    private boolean checkPassword(CharSequence s){
        if(mFlag1&&mFlag2){
            // 两个密码输入框同时符合要求确认按钮可点击
            mButtonConfirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mButtonConfirm.setEnabled(true);
        }else{
            // 至少一个密码输入框不符合要求确认按钮不可点击
            mButtonConfirm.setBackgroundColor(getResources().getColor(R.color.colorDisabledButton));
            mButtonConfirm.setEnabled(false);
        }

        if(s.length()<8){
            // 密码位数小于8位不符合要求
            return false;
        }else{
            // 密码位数大于等于8位符合要求
            return true;
        }
    }
}