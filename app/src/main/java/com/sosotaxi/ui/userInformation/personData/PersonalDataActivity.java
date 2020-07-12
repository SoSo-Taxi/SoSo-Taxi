/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/11
 */


package com.sosotaxi.ui.userInformation.personData;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sosotaxi.R;

public class PersonalDataActivity extends AppCompatActivity {

    private TextView mUserNameTextView;
    private TextView mIndustryTextView;
    private TextView mCorporationTextView;
    private TextView mJobTextView;
    private TextView mIntroTextView;

    private boolean mHasIndustryTextView = false;
    private boolean mHasCorporationTextView = false;
    private boolean mHasJobTextView = false;
    private boolean mHasIntroTextView = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        Toolbar pdToolbar = findViewById(R.id.personal_data_toolbar);
        pdToolbar.setTitle("");
        setSupportActionBar(pdToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView personalDataImageView = findViewById(R.id.personal_data_imageView);
        Intent intent = getIntent();
        if (intent  != null &&  intent.getParcelableExtra("image") != null) {
            Bitmap bitmap = (Bitmap)getIntent().getParcelableExtra("image");
            personalDataImageView.setImageBitmap(bitmap);
        }
        mUserNameTextView = findViewById(R.id.personal_data_username_textView);
        mUserNameTextView.setText(intent.getCharSequenceExtra("phone"));

        mIndustryTextView = findViewById(R.id.industry_textView);
        mCorporationTextView = findViewById(R.id.corporation_textView);
        mJobTextView = findViewById(R.id.job_textView);
        mIntroTextView = findViewById(R.id.intro_textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_personal_data_menu,menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == 201){
                mUserNameTextView.setText(data.getCharSequenceExtra("username"));

                mHasIndustryTextView = data.getBooleanExtra("industry_changed",false);
                mIndustryTextView.setText(data.getCharSequenceExtra("industry"));
                    mHasCorporationTextView = data.getBooleanExtra("corporation_changed",false);
                    mCorporationTextView.setText(data.getCharSequenceExtra("corporation"));

                mHasJobTextView = data.getBooleanExtra("job_changed",false);
                    mJobTextView.setText(data.getCharSequenceExtra("job"));

                mHasIntroTextView = data.getBooleanExtra("intro_changed",false);
                    mIntroTextView.setText(data.getCharSequenceExtra("intro"));

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent();
            intent.putExtra("phone",mUserNameTextView.getText());
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        if (item.getItemId() == R.id.edit_personal_data){
            Intent intent = new Intent(getApplicationContext(),EditPersonalDataActivity.class);
            intent.putExtra("phone",mUserNameTextView.getText());
            if(mHasIndustryTextView){
                intent.putExtra("industry",mIndustryTextView.getText());
            }
            if(mHasCorporationTextView){
                intent.putExtra("corporation",mCorporationTextView.getText());
            }
            else {
                intent.putExtra("corporation","");
            }
            if(mHasJobTextView){
                intent.putExtra("job",mJobTextView.getText());
            }else {
                intent.putExtra("job","");
            }
            if(mHasJobTextView){
                intent.putExtra("intro",mIntroTextView.getText());
            }
            else {
                intent.putExtra("intro","");
            }
            startActivityForResult(intent,201);
            return true;
        }
        return false;
    }
}