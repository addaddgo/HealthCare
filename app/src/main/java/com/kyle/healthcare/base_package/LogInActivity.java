package com.kyle.healthcare.base_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kyle.healthcare.MainActivity;
import com.kyle.healthcare.R;
import com.kyle.healthcare.database.user_info.User;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountEdit;
    private EditText passwordEdit;

    private TextView signUp;
    private TextView problemLogin;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        accountEdit = findViewById(R.id.user_id);
        passwordEdit = findViewById(R.id.user_password);
        login = findViewById(R.id.log_in);
        signUp = findViewById(R.id.tv_sign_up);
        problemLogin = findViewById(R.id.tv_problem_login);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        problemLogin.setOnClickListener(this);
//        {
//            @Override
//            public void onClick(View v) {
//                String account = accountEdit.getText().toString();
//                String password = passwordEdit.getText().toString();
//                if (account.equals("Kyle") && password.equals("123456")) {
//                    editor = pref.edit();
//                    if(checkBox.isChecked()){
//                        editor.putString("account",account );
//                        editor.putString("password", password);
//                        editor.putBoolean("remember_password",true);
//                    }else{
//                        editor.clear();
//                    }
//                    editor.apply();

//                } else {
//                    Toast.makeText(LogInActivity.this, "invalid account or password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in:
                if (checkValidUser()) {
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_problem_login:
                Toast.makeText(this, "该功能正在下线测试", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private boolean checkValidUser() {
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (account.isEmpty()) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<User> users = DataSupport.select("phoneNumber", "password", "idNumber").find(User.class);
        for (User mUser : users) {
            if ((account.equals(mUser.getPhoneNumber()) || account.equals(mUser.getIdNumber())) && password.equals(mUser.getPassword())) {
                return true;
            }
        }
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        return false;
    }
}
