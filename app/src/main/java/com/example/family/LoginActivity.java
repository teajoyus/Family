package com.example.family;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.entry.User;
import com.example.tools.RunTime;
import com.example.tools.Tools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
@ContentView(value = R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {
    public  static  final String TAG = LoginActivity.class.getName();
    @ViewInject(value = R.id.login_count_et)
    private EditText count_et;
    @ViewInject(value = R.id.login_pwd_et)
    private EditText pwd_et;
    private boolean isLoginLoad = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }


  @Event(value = R.id.login_regist_tv,type = View.OnClickListener.class)
    private  void startToRegist(View v){
      startActivity(new Intent(LoginActivity.this,RegistActivity.class));
    }

    @Event(value = R.id.login_comfirm_bt,type=View.OnClickListener.class)
   private  void login(View v){
        if(isLoginLoad){
            return;
        }
        isLoginLoad = true;
       String count = count_et.getText().toString().trim();
       String pwd = pwd_et.getText().toString().trim();
        if(count.length()==0)
        count = "258";
        if(pwd.length()==0)
        pwd = "258";
        if(count==null||count.length()<3||pwd==null||pwd.length()<3){
            Tools.showToast(LoginActivity.this,"请输入有效的帐号密码");
            return;
        }
        final User user =new User();
        user.setUsername(count);
        user.setPassword(pwd);
        user.login(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    Tools.showToast(LoginActivity.this, "登录成功！");
                    RunTime.user = user;
                    startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                    LoginActivity.this.finish();
                } else {
                    Tools.showToast(LoginActivity.this, "帐号或密码错误！");
                    Log.i(TAG, "tea>>>" + e.getMessage());
                }
            }
        });
        /*
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("count",count);
        query.addWhereEqualTo("password",pwd);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                isLoginLoad = false;
                if(e==null&&list!=null&&list.size()>0){
                    Tools.showToast(LoginActivity.this,"登录成功！");
                    RunTime.user = list.get(0);
                    startActivity(new Intent(LoginActivity.this,IndexActivity.class));
                    LoginActivity.this.finish();
                }else{
                    Tools.showToast(LoginActivity.this,"帐号或密码错误！");
                    Log.i(TAG,"tea>>>"+e.getMessage());
                }
            }
        });
        */

   }


}
