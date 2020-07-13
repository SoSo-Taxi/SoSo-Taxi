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
import com.sosotaxi.ui.main.MainActivity;
import com.sosotaxi.service.net.LoginNetService;

/**
 * 输入密码界面
 */
public class EnterPasswordFragment extends Fragment {

    private EditText mEditTextEnterPassword;
    private Button mButtonConfirm;

    public EnterPasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_enter_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取控件
        mEditTextEnterPassword=getActivity().findViewById(R.id.editTextEnterPassword);
        mButtonConfirm=getActivity().findViewById(R.id.buttonEnterPasswordConfirm);

        // 设置密码输入框监听
        mEditTextEnterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<8){
                    // 密码小于8位时确认按钮不可用
                    mButtonConfirm.setBackgroundColor(getResources().getColor(R.color.colorDisabledButton));
                    mButtonConfirm.setEnabled(false);
                }else{
                    // 密码大于等于8位时确认按钮才可用
                    mButtonConfirm.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    mButtonConfirm.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //设置确认按钮点击事件
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取手机号与密码
                String phone=getArguments().getString(Constant.EXTRA_PHONE);
                String password=mEditTextEnterPassword.getText().toString();

                // 创建用户对象
                User user=new User();
                user.setUserName(phone);
                user.setPassword(password);

                // 用户登陆
                new Thread(new LoginTask(user,handler)).start();
            }
        });
    }

    /**
     * UI线程更新处理器
     */
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String password=mEditTextEnterPassword.getText().toString();

            // 提示异常信息
            if(bundle.getString(Constant.EXTRA_ERROR)!=null){
                Toast.makeText(getContext(), bundle.getString(Constant.EXTRA_ERROR), Toast.LENGTH_SHORT).show();
                return false;
            }

            boolean isAuthorized = bundle.getBoolean(Constant.EXTRA_IS_AUTHORIZED);

            if(isAuthorized){
                // 验证通过保存用户信息
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Constant.SHARE_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString(Constant.USERNAME,bundle.getString(Constant.EXTRA_PHONE));
                editor.putString(Constant.PASSWORD,bundle.getString(Constant.EXTRA_PASSWORD));
                editor.commit();

                // 跳转主界面
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                // 验证失败清空密码并提示密码错误
                mEditTextEnterPassword.setText("");
                mEditTextEnterPassword.setError(getString(R.string.error_password_incorret));
            }
            return true;
        }
    });
}