package com.kyle.healthcare.base_package;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kyle.healthcare.R;
import com.kyle.healthcare.database.user_info.User;

import org.litepal.LitePal;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private int viewIndex = 1;

    private ImageView man_ic, woman_ic;
    private TextView man_tv, woman_tv;
    boolean gender;
    private EditText et_name;

    private EditText et_phone;
    private EditText et_pass;

    private EditText et_car;
    private EditText et_id;

    private Button sign_up;
    private Button forward;
    private Button backward;

    boolean isChecked;

    private String name;
    private String phoneNumber;
    private String password;
    private String carNumber;
    private String idNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeView(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_forward_1:
            case R.id.sign_up_forward_2:
                if (checkValid()) {
                    changeView(1);
                }
                break;
            case R.id.bt_sign_up:
                if (checkValid()) {
                    addUser();
                    finish();
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sign_up_backward_1:
                finish();
            case R.id.sign_up_backward_2:
                changeView(-1);
                if (gender) {
                    man_ic.callOnClick();
                } else {
                    woman_ic.callOnClick();
                }
                et_name.setText(name);
                break;
            case R.id.sign_up_backward_3:
                changeView(-1);
                et_phone.setText(phoneNumber);
                et_pass.setText(password);
                break;
            case R.id.man_ic:
                isChecked = true;
                gender = true;
                man_ic.setImageResource(R.drawable.ic_man_c);
                man_tv.setTextColor(Color.rgb(68, 68, 68));
                woman_ic.setImageResource(R.drawable.ic_woman);
                woman_tv.setTextColor(Color.rgb(195, 195, 195));
                break;
            case R.id.woman_ic:
                isChecked = true;
                gender = false;
                man_ic.setImageResource(R.drawable.ic_man);
                man_tv.setTextColor(Color.rgb(195, 195, 195));
                woman_ic.setImageResource(R.drawable.ic_woman_c);
                woman_tv.setTextColor(Color.rgb(68, 68, 68));
                break;

            default:
                break;
        }
    }

    private void changeView(int offset) {
        viewIndex += offset;
        if (viewIndex <= 0) {
            viewIndex = 1;
        }
        if (viewIndex >= 4) {
            viewIndex = 3;
        }
        switch (viewIndex) {
            case 1:
                setContentView(R.layout.activity_sign_up_1);
                man_ic = findViewById(R.id.man_ic);
                man_tv = findViewById(R.id.man_tv);
                woman_ic = findViewById(R.id.woman_ic);
                woman_tv = findViewById(R.id.woman_tv);
                man_ic.setOnClickListener(this);
                woman_ic.setOnClickListener(this);
                et_name = findViewById(R.id.user_name);
                forward = findViewById(R.id.sign_up_forward_1);
                backward = findViewById(R.id.sign_up_backward_1);
                forward.setOnClickListener(this);
                backward.setOnClickListener(this);
                break;
            case 2:
                setContentView(R.layout.activity_sign_up_2);
                et_phone = findViewById(R.id.user_phone_number);
                et_pass = findViewById(R.id.user_password);
                forward = findViewById(R.id.sign_up_forward_2);
                backward = findViewById(R.id.sign_up_backward_2);
                forward.setOnClickListener(this);
                backward.setOnClickListener(this);
                break;
            case 3:
                setContentView(R.layout.activity_sign_up_3);
                et_car = findViewById(R.id.user_car_number);
                et_id = findViewById(R.id.user_id_number);
                backward = findViewById(R.id.sign_up_backward_3);
                sign_up = findViewById(R.id.bt_sign_up);
                backward.setOnClickListener(this);
                sign_up.setOnClickListener(this);
                break;
            default:
                break;
        }
    }

    private boolean checkValid() {
        switch (viewIndex) {
            case 1:
                name = et_name.getText().toString();
                if (!isChecked) {
                    Toast.makeText(this, "请选择性别", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (name.isEmpty()) {
                    Toast.makeText(this, "请填写姓名", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case 2:
                phoneNumber = et_phone.getText().toString();
                password = et_pass.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(this, "请填写电话号码", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (password.isEmpty()) {
                    Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            case 3:
                carNumber = et_car.getText().toString();
                idNumber = et_id.getText().toString();
                if (carNumber.isEmpty()) {
                    Toast.makeText(this, "请填写车牌号", Toast.LENGTH_SHORT).show();
                    return false;
                } else if (idNumber.isEmpty()) {
                    Toast.makeText(this, "请填写身份证号", Toast.LENGTH_SHORT).show();
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void addUser() {
        LitePal.getDatabase();
        User user = new User(gender, name, phoneNumber, password, carNumber, idNumber);
        user.save();
    }
}
