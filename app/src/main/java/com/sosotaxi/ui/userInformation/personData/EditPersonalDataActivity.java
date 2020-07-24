/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/24
 */


package com.sosotaxi.ui.userInformation.personData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;
import com.sosotaxi.model.Passenger;
import com.sosotaxi.service.net.EditPersonalDataTask;
import com.sosotaxi.service.net.GetPersonalDataTask;

public class EditPersonalDataActivity extends AppCompatActivity {

    private EditText mNicknameEditText;
    private String mUsername;
    private EditText mGenderEditText;
    private TextView mAgeTextView;
    private TextView mIndustryTextView;
    private EditText mCorporationEditText;
    private EditText mJobEditText;
    private EditText mIntroEditText;
    private short mIndustryIndex = -1;
    private Handler mFillTextHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_data);
        Toolbar toolbar = findViewById(R.id.edit_person_data_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mNicknameEditText = findViewById(R.id.edit_username_EditText);
        mNicknameEditText.setText(intent.getCharSequenceExtra("nickname"));
        mUsername = intent.getStringExtra("phone");
        mGenderEditText = findViewById(R.id.edit_gender_EditText);
        mGenderEditText.setText("男");

        mAgeTextView = findViewById(R.id.edit_age_textView);

        mAgeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //隐藏键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                AgeChoiceFragment fragment = new AgeChoiceFragment(getSupportFragmentManager(),mAgeTextView);
                getSupportFragmentManager().beginTransaction().add(R.id.fr,fragment).commitAllowingStateLoss();
            }
        });

        mFillTextHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                mNicknameEditText.setText(bundle.getString("nickname"));
               mAgeTextView.setText(convertBirthYearToText(bundle.getShort("birthYear",(short)-1)));
               mGenderEditText.setText(bundle.getString("gender","男"));
                return true;
            }
        });




//        mUsernameEditText.setEnabled(false);
        new Thread(new GetPersonalDataTask(mUsername,mFillTextHandler)).start();



        mIndustryTextView = findViewById(R.id.industry_select_textView);
        mIndustryTextView.setText(intent.getCharSequenceExtra("industry"));

        mCorporationEditText = findViewById(R.id.corporation_EditText);
        mCorporationEditText.setText(intent.getCharSequenceExtra("corporation"));

        mJobEditText = findViewById(R.id.job_EditText);
        mJobEditText.setText(intent.getCharSequenceExtra("job"));

        mIntroEditText = findViewById(R.id.Intro_EditView);
        mIntroEditText.setText(intent.getCharSequenceExtra("intro"));

        ConstraintLayout industry_constraintLayout = findViewById(R.id.industry_constraintLayout);
        industry_constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IndustryChosenActivity.class);
                startActivityForResult(intent,200);
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.edit_person_data_cl);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.edit_person_data_cl:
                        InputMethodManager imm = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        break;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 200){
                mIndustryTextView.setText(data.getCharSequenceExtra("industry"));
                mIndustryIndex = data.getShortExtra("industryIndex",(short)-1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_done_menu,menu);
        return true;
    }

    private short convertBirthYearToShort(){
        String birth = mAgeTextView.getText().toString();
        if (birth.equals("50后")){
            return 1950;
        }else if (birth.equals("60后")){
            return 1960;
        }else if (birth.equals("70后")){
            return 1970;
        }else if (birth.equals("80后")){
            return 1980;
        }else if (birth.equals("90后")){
            return 1990;
        }else if (birth.equals("00后")){
            return 2000;
        }else {
            return -1;
        }
    }
    private String convertBirthYearToText(short birthYear){
        System.out.println(birthYear);
        if (birthYear >= (short) 2000){
            return "00后";
        }else if (birthYear >= (short)1990){
            return "90后";
        }else if (birthYear >= (short)1980){
            return "80后";
        }else if (birthYear >= (short)1970){
            return "70后";
        }else if (birthYear >= (short)1960){
            return "60后";
        }else if (birthYear >= (short)1950){
            return "50后";
        }else {
            return "几零后";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        if (item.getItemId() == R.id.edit_done){
            Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getApplicationContext(),PersonalDataActivity.class));
            Intent intent = new Intent();
            Passenger passenger = new Passenger();
            intent.putExtra("username", mNicknameEditText.getText());
            passenger.setUsername(mUsername);
            if(!mIndustryTextView.getText().equals("添加您的行业")){
                if (mIndustryIndex != (short)-1){
                    passenger.setIndustry(mIndustryIndex);
                }
                intent.putExtra("industry_changed",true);
                intent.putExtra("industry",mIndustryTextView.getText());
            }else {
                intent.putExtra("industry_changed",false);
                intent.putExtra("industry","行业");
            }
            if (mCorporationEditText.getText().length() != 0){
                passenger.setCompany(mCorporationEditText.getText().toString());
                intent.putExtra("corporation_changed",true);
                intent.putExtra("corporation",mCorporationEditText.getText());
            }else {
                intent.putExtra("corporation_changed",false);
                intent.putExtra("corporation","公司");
            }
            if (mJobEditText.getText().length() != 0){
                passenger.setOccupation(mJobEditText.getText().toString());
                intent.putExtra("job_changed",true);
                intent.putExtra("job",mJobEditText.getText());
            }else {
                intent.putExtra("job_changed",false);
                intent.putExtra("job","职业");
            }
            if (mIntroEditText.getText().length() != 0){
                intent.putExtra("intro_changed",true);
                intent.putExtra("intro",mIntroEditText.getText());
            }else {
                intent.putExtra("intro_changed",false);
                intent.putExtra("intro","还未填写个性签名，介绍一下自己吧");
            }
            passenger.setNickname(mNicknameEditText.getText().toString());
            passenger.setBirthYear(convertBirthYearToShort());
            passenger.setGender(mGenderEditText.getText().toString());
            setResult(RESULT_OK,intent);
            new Thread(new EditPersonalDataTask(passenger)).start();
            finish();
            return true;
        }
        return false;
    }


}