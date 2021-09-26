package hu.obuda.university.mibanddatacolector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        EditText email = (EditText) findViewById(R.id.sing_up_activity_email_text);
        EditText pas1 = (EditText) findViewById(R.id.sign_up_activity_password1_text);
        EditText pas2 = (EditText) findViewById(R.id.editTextTextPassword2);
        Button signup = (Button) findViewById(R.id.sign_activity_sign_up_button);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pas1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Signup.this, "regsik",
                                    Toast.LENGTH_SHORT).show();
                            Map<String, Object> docData = new HashMap<>();
                            long time= System.currentTimeMillis();
                            MibandData data = new MibandData(time,user.getUid(),"hr","98");
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            docData.put(user.getUid(),user.getEmail());
                            db.collection("users").add(docData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Signup.this, "sikeres failed.",
                                                Toast.LENGTH_SHORT).show();
                                        Signup.this.finish();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
            }

        });
    }
}