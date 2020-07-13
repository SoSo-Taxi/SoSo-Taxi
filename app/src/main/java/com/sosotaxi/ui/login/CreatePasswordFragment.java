/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/11
 */
package com.sosotaxi.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sosotaxi.R;
import com.sosotaxi.common.Constant;
import com.sosotaxi.model.User;
import com.sosotaxi.service.net.LoginTask;
import com.sosotaxi.service.net.RegisterTask;
import com.sosotaxi.ui.main.MainActivity;
import com.sosotaxi.service.net.LoginNetService;

/**
 * 创建密码界面
 */
public class CreatePasswordFragment extends Fragment {

    private EditText mEditTextPassword;
    private EditText mEditTextPasswordConfirmed;
    private Button mButtonConfirm;

    /**
     * 密码长度标志位
     */
    private boolean mFlag1;

    /**
     * 确认密码长度标志位
     */
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

        // 获取控件
        mEditTextPassword=getActivity().findViewById(R.id.editTextCreatePassword);
        mEditTextPasswordConfirmed=getActivity().findViewById(R.id.editTextCreatePasswordConfirmed);
        mButtonConfirm=getActivity().findViewById(R.id.buttonCreatePasswordConfirm);
        mFlag1=false;
        mFlag2=false;

        // 设置输入改变监听器
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

        // 设置输入改变监听器
        mEditTextPasswordConfirmed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 检查密码位数
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
                        // 获取手机号与密码
                        String phone=getArguments().getString(Constant.EXTRA_PHONE);

                        // 创建用户对象
                        User user=new User();
                        user.setUserName(phone);
                        user.setPassword(password);
                        user.setRole("passenger");

                        // 注册用户
                        new Thread(new RegisterTask(user,handler)).start();
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

    /**
     * UI线程更新处理器
     */
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String password=mEditTextPassword.getText().toString();

            // 提示异常信息
            if(bundle.getString(Constant.EXTRA_ERROR)!=null){
                Toast.makeText(getContext(), bundle.getString(Constant.EXTRA_ERROR), Toast.LENGTH_SHORT).show();
                return false;
            }

            boolean isSuccessful = bundle.getBoolean(Constant.EXTRA_IS_SUCCESSFUL);
            boolean isAuthorized=bundle.getBoolean(Constant.EXTRA_IS_AUTHORIZED);

            if(isSuccessful){
                // 注册成功保存用户信息
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Constant.USERNAME,bundle.getString(Constant.EXTRA_PHONE));
                editor.putString(Constant.PASSWORD,bundle.getString(Constant.EXTRA_PASSWORD));
                editor.commit();

                // 获取手机号与密码
                String phone=getArguments().getString(Constant.EXTRA_PHONE);

                // 创建用户对象
                User user=new User();
                user.setUserName(phone);
                user.setPassword(password);
                user.setRememberMe(true);

                // 登陆
                new Thread(new LoginTask(user,handler)).start();

                // 跳转主界面
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if(isAuthorized==false){
                // 提示注册失败
                Toast.makeText(getContext(), R.string.error_register_failed,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
}