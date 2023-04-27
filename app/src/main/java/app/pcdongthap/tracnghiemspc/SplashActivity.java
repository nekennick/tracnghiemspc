package app.pcdongthap.tracnghiemspc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import app.pcdongthap.tracnghiemspc.user.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static final String KEY_FIRT_INSTALL = "KEY_FIRT_INSTALL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);


        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mySharedPreferences.putBooleanValue(KEY_FIRT_INSTALL)){
                    startActiviy(SignInActivity.class);
                } else {
                    startActiviy(PaperOnboardingActivity.class);
                    mySharedPreferences.putBooleanValue(KEY_FIRT_INSTALL,true);
                }

            }
        },2000);
    }

    private void startActiviy(Class<?> cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
        finish();
    }

//    private void nextActivity() {
//        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
//        if(firebaseUser==null){
//            Intent intent= new Intent(SplashActivity.this, SignInActivity.class);
//            startActivity(intent);
//        }else {
//            Intent intent= new Intent(SplashActivity.this,PaperOnboardingActivity.class);
//            startActivity(intent);
//        }
//        finish();
//    }
}