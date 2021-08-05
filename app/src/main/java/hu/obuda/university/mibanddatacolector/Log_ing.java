package hu.obuda.university.mibanddatacolector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Log_ing extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        EditText email = (EditText) findViewById(R.id.log_in_activity_email_text);
        EditText pas = (EditText) findViewById(R.id.log_ing_activity_password_text);
        Button loginbutton = (Button) findViewById(R.id.log_in_activity_log_in_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email.getText().toString(), pas.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //  Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Log_ing.this, "sikeresbe",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });
    }
}