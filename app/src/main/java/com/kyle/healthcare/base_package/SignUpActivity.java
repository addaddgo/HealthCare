package com.kyle.healthcare.base_package;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyle.healthcare.R;
import com.kyle.healthcare.database.user_info.User;

import org.litepal.LitePal;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView man_ic,woman_ic;
    private TextView man_tv,woman_tv;
    private Button sign_up;

    boolean gender;
    boolean isChecked;

    private String name;
    private String phoneNumber;
    private String password;
    private String carNumber;
    private String idNumber;

    private EditText et_name;
    private EditText et_phone;
    private EditText et_pass;
    private EditText et_car;
    private EditText et_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        man_ic = findViewById(R.id.man_ic);
        man_tv = findViewById(R.id.man_tv);
        woman_ic = findViewById(R.id.woman_ic);
        woman_tv = findViewById(R.id.woman_tv);
        sign_up = findViewById(R.id.bt_sign_up);

        et_name = findViewById(R.id.user_name);
        et_phone = findViewById(R.id.user_phone_number);
        et_pass = findViewById(R.id.user_password);
        et_car = findViewById(R.id.user_car_number);
        et_id = findViewById(R.id.user_id_number);

        man_ic.setOnClickListener(this);
        woman_ic.setOnClickListener(this);
        sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.man_ic:
                isChecked = true;
                gender = true;
                man_ic.setImageResource(R.drawable.ic_man_c);
                man_tv.setTextColor(Color.rgb(68,68,68));
                woman_ic.setImageResource(R.drawable.ic_woman);
                woman_tv.setTextColor(Color.rgb(195,195,195));
                break;
            case R.id.woman_ic:
                isChecked = true;
                gender = false;
                man_ic.setImageResource(R.drawable.ic_man);
                man_tv.setTextColor(Color.rgb(195,195,195));
                woman_ic.setImageResource(R.drawable.ic_woman_c);
                woman_tv.setTextColor(Color.rgb(68,68,68));
                break;
            case R.id.bt_sign_up:
                if(checkValid()){
                    addUser();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private boolean checkValid() {
        name = et_name.getText().toString();
        phoneNumber = et_phone.getText().toString();
        password = et_pass.getText().toString();
        carNumber = et_car.getText().toString();
        idNumber = et_id.getText().toString();
        if(!isChecked){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
            return false;
        }else if(name.isEmpty()){
            Toast.makeText(this, "请填写姓名", Toast.LENGTH_SHORT).show();
            return false;
        }else if(phoneNumber.isEmpty()){
            Toast.makeText(this, "请填写电话号码", Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.isEmpty()){
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
            return false;
        }else if(carNumber.isEmpty()){
            Toast.makeText(this, "请填写车牌号", Toast.LENGTH_SHORT).show();
            return false;
        }else if(idNumber.isEmpty()){
            Toast.makeText(this, "请填写身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addUser() {
        LitePal.getDatabase();
        User user = new User(gender,name,phoneNumber,password,carNumber,idNumber);
        user.save();
    }
}
