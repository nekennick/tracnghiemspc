package app.pcdongthap.tracnghiemspc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.io.IOException;

import app.pcdongthap.tracnghiemspc.congthuc.CongThucFragment;
import app.pcdongthap.tracnghiemspc.monhoc.TiengAnhFragment;
import app.pcdongthap.tracnghiemspc.question.DBHelper;
import app.pcdongthap.tracnghiemspc.score.ScoreFragment;
import app.pcdongthap.tracnghiemspc.user.ChangePasswordFragment;
import app.pcdongthap.tracnghiemspc.user.MyProfileFragment;
import app.pcdongthap.tracnghiemspc.user.SignInActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int FRAGMENT_MONTOAN = 1;
    public static final int FRAGMENT_SCORE = 2;
    public static final int FRAGMENT_CONGTHUC = 3;
    public static final int FRAGMENT_MYPROFILE = 4;
    public static final int FRAGMENT_CHANGE_PASSWORD = 5;
    public static final int MY_REQUEST_CODE = 10;
    public static final int RC_APP_UPDATE = 100;






    public int mCurrentFragment = FRAGMENT_MONTOAN;
    public NavigationView mNavigationView;
    FirebaseRemoteConfig remoteConfig;

    private AppUpdateManager mAppUpdateManager;


    DrawerLayout mDrawerLayout;
    private ImageView imgAvatar;
    private TextView txtName, txtEmail;
    private AdManagerAdView mAdManagerAdView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final private MyProfileFragment myProfileFragment = new MyProfileFragment();

    final private ActivityResultLauncher<Intent> mActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) {
                            return;
                        }
                        Uri uri = intent.getData();
                        myProfileFragment.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            myProfileFragment.setBitmapImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        /*code update in-app firebase*/
        int currentVersionCode;

        currentVersionCode = getCurrentVersionCode();

        Log.d("myApp", String.valueOf(currentVersionCode));

        remoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        remoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if(task.isSuccessful()){
                    final String new_version_code = remoteConfig.getString("new_version_code");
                    if(Integer.parseInt(new_version_code) > getCurrentVersionCode()){
                        showUpdateDialog();
                    }
                }

            }
        });
        /*code update in-app firebase*/



//        //In-App update
//
//        mAppUpdateManager = AppUpdateManagerFactory.create(this);
//        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>()
//        {
//            @Override
//            public void onSuccess(AppUpdateInfo result)
//            {
//                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
//                {
//                    try
//                    {
//                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, MainActivity.this
//                                ,RC_APP_UPDATE);
//
//                    } catch (IntentSender.SendIntentException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        // mAppUpdateManager.registerListener(installStateUpdatedListener);
//        //In-App update


//
//        //qc banner
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//
//        mAdManagerAdView = findViewById(R.id.adManagerAdView);
//        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
//        mAdManagerAdView.loadAd(adRequest);
//
//        MobileAds.initialize(this);
//        mAdManagerAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdClosed() {
//                // Code to be executed when the user is about to return
//                // to the app after tapping on an ad.
//            }
//
//            @Override
//            public void onAdFailedToLoad(LoadAdError adError) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Code to be executed when an impression is recorded
//                // for an ad.
//            }
//
//            @Override
//            public void onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when an ad opens an overlay that
//                // covers the screen.
//            }
//        });
//    //qc banner


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initUi();




        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new TiengAnhFragment());
        mNavigationView.getMenu().findItem(R.id.nav_montienganh).setChecked(true);


        DBHelper db = new DBHelper(this);
//        db.deleteDataBase();
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
// hiện thông tin user đang đăng nhập
        showUserInformation();

    }
    //phía sau OnCreat


        /*code update in-app Firebase*/
    private void showUpdateDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("ĐÃ CÓ PHIÊN BẢN MỚI!")
                .setMessage("Vui lòng cập nhật lên phiên bản mới nhất từ Play Store để tiếp tục sử dụng nhé!")
                .setPositiveButton("CẬP NHẬT NGAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.pcdongthap.tracnghiemspc")));
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Đã có lỗi xãy ra!Hãy thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
        dialog.setCancelable(false);
    }



    public int getCurrentVersionCode(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        }catch (Exception e){
            Log.d("myApp", e.getMessage());
        }

        return packageInfo.versionCode;
    }
    /*code update in-app Firebase*/



//    //In-App Update
//
//    private InstallStateUpdatedListener installStateUpdatedListener =new InstallStateUpdatedListener()
//    {
//        @Override
//        public void onStateUpdate(InstallState state)
//        {
//            if(state.installStatus() == InstallStatus.DOWNLOADED)
//            {
//                showCompletedUpdate();
//            }
//        }
//    };
//
//    @Override
//    protected void onStop()
//    {
////         if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
//        super.onStop();
//    }
//
//    private void showCompletedUpdate()
//    {
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready!",
//                Snackbar.LENGTH_INDEFINITE);
//        snackbar.setAction("Install", new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                mAppUpdateManager.completeUpdate();
//            }
//        });
//        snackbar.show();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == RC_APP_UPDATE && resultCode != RESULT_OK)
//        {
//            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>()
//        {
//            @Override
//            public void onSuccess(AppUpdateInfo result)
//            {
//                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
//                {
//                    try
//                    {
//                        mAppUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this
//                                ,RC_APP_UPDATE);
//
//                    } catch (IntentSender.SendIntentException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//    // In-App Update



    private void initUi() {
        mNavigationView = findViewById(R.id.navigation_view);
        imgAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.imvAvatar);
        txtName = mNavigationView.getHeaderView(0).findViewById(R.id.txtName);
        txtEmail = mNavigationView.getHeaderView(0).findViewById(R.id.txtEmail);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_montienganh) {
            if (mCurrentFragment != FRAGMENT_MONTOAN) {
                replaceFragment(new TiengAnhFragment());
                mCurrentFragment = FRAGMENT_MONTOAN;
                mNavigationView.getMenu().findItem(R.id.nav_montienganh).setChecked(true);
            }
        } else if (id == R.id.nav_xemdiem) {
            if (mCurrentFragment != FRAGMENT_SCORE) {
                replaceFragment(new ScoreFragment());
                mCurrentFragment = FRAGMENT_SCORE;
                mNavigationView.getMenu().findItem(R.id.nav_xemdiem).setChecked(true);
            }
        } else if (id == R.id.nav_congthuc) {
            if (mCurrentFragment != FRAGMENT_CONGTHUC) {
                replaceFragment(new CongThucFragment());
                mCurrentFragment = FRAGMENT_CONGTHUC;
                mNavigationView.getMenu().findItem(R.id.nav_congthuc).setChecked(true);
            }
        } else if (id == R.id.nav_sign_out) {

            gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                }
            });
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(this, SignInActivity.class);
//            startActivity(intent);
//            finish();
        } else if (id == R.id.nav_my_profile) {
            if (mCurrentFragment != FRAGMENT_MYPROFILE) {
                replaceFragment(myProfileFragment);
                mCurrentFragment = FRAGMENT_MYPROFILE;
                mNavigationView.getMenu().findItem(R.id.nav_my_profile).setChecked(true);
                mNavigationView.getMenu().findItem(R.id.nav_my_profile).setCheckable(true);
            }
        }else if (id == R.id.nav_change_password) {
            if (mCurrentFragment != FRAGMENT_CHANGE_PASSWORD) {
                replaceFragment(new ChangePasswordFragment());
                mCurrentFragment = FRAGMENT_CHANGE_PASSWORD;
                mNavigationView.getMenu().findItem(R.id.nav_change_password).setChecked(true);
                mNavigationView.getMenu().findItem(R.id.nav_change_password).setCheckable(true);

            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    public void showUserInformation() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            return;
        } else {
            String Name = account.getDisplayName();
            String Mail = account.getEmail();
            Uri avatarUri = account.getPhotoUrl();
            if (Name == null) {
                txtName.setVisibility(View.GONE);
            } else {
                txtName.setVisibility(View.VISIBLE);
                txtName.setText(Name);

            }
            txtEmail.setText(Mail);
            Glide.with(this).load(avatarUri).error(R.drawable.ic_user_default).into(imgAvatar);
        }
    }
//Hàm lấy ảnh từ thư viện
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallary();
            }
        }
    }

    public void openGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

}