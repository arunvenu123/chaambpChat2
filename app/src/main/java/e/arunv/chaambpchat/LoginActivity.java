package e.arunv.chaambpchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {

    EditText email1, password1;
    Button btn_login, registerHere;
    FirebaseAuth auth1;
FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        android.support.v7.widget.Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Login");


        email1 = findViewById(R.id.email1);
        password1 = findViewById(R.id.password1);
        btn_login = findViewById(R.id.btn_login);
        registerHere = findViewById(R.id.register_here);

        auth1 = FirebaseAuth.getInstance();



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email = email1.getText().toString();
                String text_password = password1.getText().toString();
                if (TextUtils.isEmpty(text_email) || TextUtils.isEmpty(text_password)) {
                    Toast.makeText(LoginActivity.this, "Enter all credentials", Toast.LENGTH_SHORT).show();
                } else {
                    btn_login.setText("Please wait...");
                    auth1.signInWithEmailAndPassword(text_email, text_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent2);
                                finish();
                            } else {
                                btn_login.setText("Login");
                                Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
