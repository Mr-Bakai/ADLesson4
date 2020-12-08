package com.hfad.adlesson4;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class ActivityTwo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "tag";
    public static final String KEY3 = "key3";
    private EditText etText;
    private Button btnResult;
    private TextView dateView;
    private ImageButton dateButton;
    public static final String KEY2 = "key2";
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitytwo);
        init();

        Intent intent = getIntent();
        if (intent != null){
            Title title = (Title) intent.getSerializableExtra(com.hfad.adlesson4.MainActivity.KEY);
            if (title != null){
                etText.setText(title.name);}
        }
    }

    private void init(){
        etText = findViewById(R.id.etText);
        btnResult = findViewById(R.id.btnResult);
        dateButton = findViewById(R.id.dateButton);
        dateView = findViewById(R.id.dateView);

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Title title = new Title(etText.getText().toString(), date);
                intent.putExtra(KEY2,title);
                setResult(RESULT_OK, intent);
                Log.d(TAG, "onClick: " + date);
                finish();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
      //  date = year + " " + month + " " + dayOfMonth;
        dateView.setText(date);
    }
}