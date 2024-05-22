package com.example.samrthome0;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity {
    SignInButton signInButton;
    FirebaseAuth auth;

    GoogleSignInClient client;
    FirebaseDatabase  fireDatabase;
    DatabaseReference reference;
    EditText username, pass;
    Button login;
    Button forgotPassBtn;
    //tempreture t =new tempreture();
    //Password p = new Password();
    //Led l = new Led();
    //FanMotor f = new FanMotor();
    //Attack a = new Attack();
    //Message m = new Message();
    int RC_SIGN_IN =20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Google Account
        GoogleSignInOptions option = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail().build();
        client = GoogleSignIn.getClient(this,option);
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                googleSignIn();
//            }
//        });

        //sensors
        //t.save_temperture(50);
        //p.save_password("123");
        //l.turnOnLed();
        //f.turnOnFanMotor();
        //a.setAlert(false);
        //m.saveMessage("Welcome 2024 ");
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            fireDatabase = FirebaseDatabase.getInstance();
            auth = FirebaseAuth.getInstance();
            client = GoogleSignIn.getClient(this,option);
            reference = fireDatabase.getReference("users");
            signInButton = findViewById(R.id.Sign_in);

            forgotPassBtn =findViewById(R.id.forgotPass);
            username=findViewById(R.id.Usernamelog);
            pass=findViewById(R.id.passwordlog);


            login=findViewById(R.id.btnLog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validatePassword() | !validateUsername()){
                    Toast.makeText(Login.this, "email or password not correct", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkUser();
                    Toast.makeText(Login.this, "correct", Toast.LENGTH_SHORT).show();
                }
//                String Name =username.getText().toString();
//                String password=pass.getText().toString();
//                if (Name.equals("admin") && password.equals("admin")) {
//                    startActivity(new Intent(Login.this,MainActivity.class));
//
//                }
//                else
//                    Toast.makeText(Login.this, "email or password not correct", Toast.LENGTH_SHORT).show();

            }


        });

            SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
            boolean remembero = chrem.getBoolean("remember", false);
            if (remembero == true) {
                Intent mainPageHHome = new Intent(Login.this, MainActivity.class);
                startActivity(mainPageHHome);
            } else if (remembero == false) {
                Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
            }

            //rememberme
            CheckBox rememberMe=findViewById(R.id.checkBox2);
            rememberMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rememberMe.isChecked()) {
                        SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                        SharedPreferences.Editor editor = chrem.edit();
                        editor.putBoolean("remember", true);
                        editor.apply();
                    } else {
                        SharedPreferences chrem = getApplicationContext().getSharedPreferences("checkedrem", 0);
                        SharedPreferences.Editor editor = chrem.edit();
                        editor.putBoolean("remember", false);
                        editor.apply();
                    }

                }
            });
            rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                }
            });


        TextView register_view=findViewById(R.id.textView4);
        register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));

            }
        });
           /* TextView forget_pass=findViewById(R.id.textView3);
            forget_pass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,forgetpassword.class));

                }
            });*/
            ////////////////////forget password
            forgotPassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText resetMail = new EditText(view.getContext());
                    AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                    passwordResetDialog.setTitle("Reset Password");
                    passwordResetDialog.setMessage("Enter Your E-Mail");
                    passwordResetDialog.setView(resetMail);
                    passwordResetDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String mailres = resetMail.getText().toString().trim();
                            auth.sendPasswordResetEmail(mailres).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Login.this, "Reset Link is Sent to Your E-mail", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Link is not sent ! Try again", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


                    passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Close Dialog
                        }
                    });
                    passwordResetDialog.create().show();
                }
            });



            return insets;
        });
    }

    public Boolean validateUsername(){
        String val = username.getText().toString();
        if(val.isEmpty()){
            username.setError("Username can not be Empty");
            return false;
        }
        else{

            username.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = pass.getText().toString();
        if(val.isEmpty()){
            pass.setError("Password can not be Empty");
            return false;
        }
        else{

            pass.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = username.getText().toString().trim();
        String userpass = pass.getText().toString().trim();

        reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkuserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username.setError(null);
                    String passwrdFromDB = snapshot.child(userUsername).child("pass").getValue(String.class);

                    if (!Objects.equals(passwrdFromDB, userpass)) {
                        username.setError(null);
                        Intent I = new Intent(Login.this, MainActivity.class);
                        // Create the Intent instance and then call putExtra on it

                        I.putExtra("User Name", userUsername);
                        startActivity(I);
                    } else {
                        pass.setError("Invalid Credentials");
                        pass.requestFocus();
                    }
                } else {
                    username.setError("User does not exist");
                    username.requestFocus();
                }
              //  return null;
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //////////////////////////////Google Sign In by gmail
    public void googleSignIn(){

        Intent i = client.getSignInIntent();
        startActivityForResult(i,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                firebaseAuth(acc.getIdToken());

            }
            catch (Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }

    }
    public void firebaseAuth(String idToken){

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String,Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name",user.getDisplayName());
                            map.put("profile",user.getPhotoUrl().toString());

                            fireDatabase.getReference().child("users").child(user.getUid()).setValue(map);
                            Intent i = new Intent(Login.this,MainActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}








































