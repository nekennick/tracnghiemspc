package app.pcdongthap.tracnghiemspc.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import app.pcdongthap.tracnghiemspc.BuildConfig;
import app.pcdongthap.tracnghiemspc.MainActivity;
import app.pcdongthap.tracnghiemspc.R;

public class SignInActivity extends AppCompatActivity {
//    Button layoutSignUp;
//    private EditText edtEmail,edtPassword;
//    Button btn_SignIn;
//    private CheckBox cbRemember;
//    ProgressDialog progressDialog;
//    SharedPreferences sharedPreferences;
    TextView tvVerNumber;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount gsa;
    ImageView googleBtn;

    public static final String signinCredentials = "login_credentials";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_2);

        tvVerNumber = findViewById(R.id.textVerNumber);
        //Lấy version name
        tvVerNumber.setText("Phiên bản " + BuildConfig.VERSION_NAME);


        //        đăng nhập bằng google
        final ImageView googleBtn = findViewById(R.id.google_signin);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });

    }

    private void signIn() {
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                task.getResult(ApiException.class);
                MainActivity();
            } catch (ApiException e) {
                Toast.makeText(this ,"Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void MainActivity(){
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }





//    //        đăng nhập bằng google
    

   

   
    //        đăng nhập bằng google



    //đang ký



//    private void iitUi() {
//
//        btn_SignIn=findViewById(R.id.btn_SignIn);
//        progressDialog=new ProgressDialog(this);
//    }
//    private void initListener() {
//        layoutSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(SignInActivity.this,SignUpActivity.class);
//                startActivity(intent);
//            }
//        });
//        btn_SignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickSignIn();
//            }
//        });
//    }
    //dang nhập
//    private void onClickSignIn() {
//        String email= edtEmail.getText().toString().trim();
//        String password= edtPassword.getText().toString().trim();
//        FirebaseAuth auth=FirebaseAuth.getInstance();
//        progressDialog.show();
//
//        if(email.isEmpty()){
//            edtEmail.setError("Mail không được để trống");
//            edtEmail.requestFocus();
//            return;
//        }
//
//        if(password.isEmpty()){
//            edtPassword.setError("Password không được để trống");
//            edtPassword.requestFocus();
//            return;
//        }
//
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            edtEmail.setError("Mail không đúng cú pháp");
//            edtEmail.requestFocus();
//            return;
//        }
//
//
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            progressDialog.dismiss();
//                            // Sign in success, update UI with the signed-in user's information
//                            Intent intent= new Intent(SignInActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//
//                            if (cbRemember.isChecked()) {
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.putString("mailKey", edtEmail.getText().toString());
//                                editor.putString("passwordKey", edtPassword.getText().toString());
//                                editor.putBoolean("checked", true);
//                                editor.apply();
//                            }
//
//                            else {
//                                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                editor.remove("mailKey");
//                                editor.remove("passwordKey");
//                                editor.remove("checked");
//                                editor.apply();
//                            }
//
//                        } else {
//                            progressDialog.dismiss();
//                            Toast.makeText(SignInActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//    }




}