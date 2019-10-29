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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText email, username, password;
    Button btn_register;


    FirebaseAuth auth;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_username = username.getText().toString();
                String text_email = email.getText().toString();
                String text_password = password.getText().toString();
                if(TextUtils.isEmpty(text_username) || TextUtils.isEmpty(text_email)|| TextUtils.isEmpty(text_password)){
                    Toast.makeText(RegisterActivity.this, "Enter all credentials", Toast.LENGTH_SHORT).show();
                }else if(text_password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password should contain more than 6 characters", Toast.LENGTH_SHORT).show();

                }else{
                    btn_register.setText("Please wait...");
                    register(text_username, text_email,text_password);
                }

            }
        });
    }

    private void register(final String username, String email, String password){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     FirebaseUser firebaseUser = auth.getCurrentUser();
                     String userid = firebaseUser.getUid();

                     reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                     HashMap<String, String> hashMap = new HashMap<>();
                     hashMap.put("id", userid);
                     hashMap.put("username", username);
                     hashMap.put("imageURL", "default");

                     reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful()){
                                 Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(intent);
                                 Toast.makeText(RegisterActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                 finish();
                             }
                         }
                     });
                 }else
                     btn_register.setText("Register");
                     Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
            }
        });

    }
}