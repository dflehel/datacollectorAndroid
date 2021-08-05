package hu.obuda.university.mibanddatacolector;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public interface DatabeseSave {
    public FirebaseAuth mauth = FirebaseAuth.getInstance();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public  void saveData(MibandData data);

}
