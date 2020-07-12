/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/11
 */


package com.sosotaxi.ui.userInformation.personData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sosotaxi.R;

public class EditPersonalDataActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private TextView mGenderTextView;
    private TextView mAgeTextView;
    private TextView mIndustryTextView;
    private EditText mCorporationEditText;
    private EditText mJobEditText;
    private EditText mIntroEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_data);
        Toolbar toolbar = findViewById(R.id.edit_person_data_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        mUsernameEditText = findViewById(R.id.edit_username_EditText);

        mUsernameEditText.setText(intent.getCharSequenceExtra("phone"));
//        mUsernameEditText.setEnabled(false);

        mGenderTextView = findViewById(R.id.gender_textView);
        mGenderTextView.setText("男");

        mAgeTextView = findViewById(R.id.edit_age_textView);


        mAgeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AgeChoiceFragment fragment = new AgeChoiceFragment(getSupportFragmentManager(),mAgeTextView);
                getSupportFragmentManager().beginTransaction().add(R.id.fr,fragment).commitAllowingStateLoss();
            }
        });

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
                Intent intent = new Intent(getApplicationContext(),IndustrySelectActivity.class);
                startActivityForResult(intent,200);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 200){
                mIndustryTextView.setText(data.getCharSequenceExtra("industry"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_done_menu,menu);
        return true;
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
            intent.putExtra("username",mUsernameEditText.getText());
            if(!mIndustryTextView.getText().equals("添加您的行业")){
                intent.putExtra("industry_changed",true);
                intent.putExtra("industry",mIndustryTextView.getText());
            }
            if (mCorporationEditText.getText().length() != 0){
                intent.putExtra("corporation_changed",true);
                intent.putExtra("corporation",mCorporationEditText.getText());
            }else {
                intent.putExtra("corporation_changed",false);
                intent.putExtra("corporation","公司");
            }
            if (mJobEditText.getText().length() != 0){
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
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return false;
    }


}