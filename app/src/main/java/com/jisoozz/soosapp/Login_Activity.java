package com.jisoozz.soosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    EditText userETLogin,passETLogin;
    Button LoginBtn,RegisterBtn;


    //firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();  //현재 로그인한 유저 반환


        // 사용자 존재여부 확인

        if(firebaseUser != null){
            Intent i = new Intent(Login_Activity.this,MainActivity.class);
            startActivity(i);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        userETLogin = findViewById(R.id.editText);
        passETLogin = findViewById(R.id.editText3);
        LoginBtn = findViewById(R.id.buttonLogin);
        RegisterBtn = findViewById(R.id.registerBtn);


        //firebase Auth:

        auth = FirebaseAuth.getInstance();



        //회원가입 버튼

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_Activity.this,RegisterActivity.class);
                startActivity(i);
            }
        });



        //로그인 버튼 리스너

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_text = userETLogin.getText().toString();
                String pass_text = passETLogin.getText().toString();


                // 비어있지는 않은지 체크

                if(TextUtils.isEmpty(email_text)|| TextUtils.isEmpty(pass_text)){
                    Toast.makeText(Login_Activity.this,"빈칸이 없도록 채워주세요.",Toast.LENGTH_LONG).show();
                }else{
                    auth.signInWithEmailAndPassword(email_text,pass_text)  //로그인 (이메일과 비밀번호) , auth에 회원 정보가 들어감
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(Login_Activity.this,MainActivity.class);

                                        //새로운 액티비티 스택을 지우면서 새로운 액티비티 생성
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);


                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(Login_Activity.this,"아이디와 비밀번호를 다시 확인해주세요.",Toast.LENGTH_LONG).show();
                                    }


                                }
                            });
                }

            }
        });

    }
}