package com.kyle.healthcare.base_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

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

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isRemember = pref.getBoolean("remember_password", false);
        accountEdit.setText(pref.getString("account", ""));
        passwordEdit.setText(pref.getString("password", ""));
        Intent intent =getIntent();
        if(intent!=null){
            isRemember = intent.getBooleanExtra("log_off", isRemember);
        }
        if (isRemember) {
        login.callOnClick();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in:
                editor = pref.edit();
                if (checkValidUser()) {
                    editor.putString("account",accountEdit.getText().toString() );
                    editor.putString("password", passwordEdit.getText().toString());
                    editor.putBoolean("remember_password",true);
                    editor.apply();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    editor.clear();
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
            if ((account.equals(mUser.getPhoneNumber()) && password.equals(mUser.getPassword()))) {
                return true;
            }
        }
        Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        return false;
    }


}
