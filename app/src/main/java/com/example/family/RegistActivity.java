package com.example.family;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entry.User;
import com.example.tools.Tools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

@ContentView(value = R.layout.regist_activity)
public class RegistActivity extends AppCompatActivity {
    @ViewInject(R.id.regist_comfirm_bt)
    private Button regist_comfirm_bt;
    @ViewInject(R.id.regist_count_et)
    private EditText regist_count_et;
    @ViewInject(R.id.regist_pwd_et)
    private EditText regist_pwd_et;
    @ViewInject(R.id.regist_pwd_et2)
    private EditText regist_pwd_et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
    @Event(value = R.id.regist_comfirm_bt,type= View.OnClickListener.class)
    private void regist(View v){
        String count = regist_count_et.getText().toString().trim();
        String pwd = regist_pwd_et.getText().toString().trim();
        String pwd2 = regist_pwd_et2.getText().toString().trim();
        if(count.length()<3){
            Tools.showToast(RegistActivity.this,"请输入有效的帐号");
        }else if(!pwd.equals(pwd2)){
            Tools.showToast(RegistActivity.this,"您两次输入的密码不一致");

        }else {
            User user = new User();
            user.setCount(count);
            user.setPassword(pwd);
            user.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Tools.showToast(RegistActivity.this,"注册成功！");
                        startActivity(new Intent(RegistActivity.this,IndexActivity.class));
                        RegistActivity.this.finish();
                    }else{
                        Tools.showToast(RegistActivity.this,"注册失败，请换个帐号试试！");
                    }
                }
            });

        }
    }
}
