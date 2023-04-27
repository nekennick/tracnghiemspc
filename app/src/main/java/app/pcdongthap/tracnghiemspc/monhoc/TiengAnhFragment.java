package app.pcdongthap.tracnghiemspc.monhoc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

import app.pcdongthap.tracnghiemspc.R;
import app.pcdongthap.tracnghiemspc.slide.ScreenSlidePagerActivity;


public class TiengAnhFragment extends Fragment {

    Activity content;
    String monhoc;
    ExamAdapter examAdapter;
    GridView gvExam;
    Button btnXemDiem,btnXemCongThuc;
    ArrayList<Exam> arr_exam = new ArrayList<Exam>();
    ArrayList<NumExam> numExam = new ArrayList<NumExam>();
    private AdManagerInterstitialAd mAdManagerInterstitialAd;
    private final static String TAG = "AbMob";
    private AdLoader adLoader;
//    AppCompatActivity activity;
//    Context context;
    public TiengAnhFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_tienganh, container, false);


        content = getActivity();
        return root;


    }



    //    private void linkView(){
//        btnDethi= btnDethi.findViewById(R.id.btn_dethi);
//    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gvExam = content.findViewById(R.id.gvExam);


        //ad
        MobileAds.initialize(content, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

                onStart();
            }
        });
        //ad




        arr_exam.add(new Exam("Tay Nghề KT", R.drawable.b7));
        arr_exam.add(new Exam("Tay Nghề KD", R.drawable.b2));
//        arr_exam.add(new Exam("QL Sửa chữa", R.drawable.b3));
//        arr_exam.add(new Exam("Lái Xe", R.drawable.b4));
//        arr_exam.add(new Exam("Vật Lý", R.drawable.physics));
//        arr_exam.add(new Exam("Lịch Sử", R.drawable.history_book));
//        arr_exam.add(new Exam("Địa lý", R.drawable.geography));
//        arr_exam.add(new Exam("GDCD", R.drawable.people));
//        arr_exam.add(new Exam("Môn 8", R.drawable.geography));
//        arr_exam.add(new Exam("Môn 9", R.drawable.people));

        numExam.add(new NumExam("Bậc 2"));
//        numExam.add(new NumExam("Bậc 3"));
//        numExam.add(new NumExam("Bậc 4"));
//        numExam.add(new NumExam("Bậc 5"));
//        numExam.add(new NumExam("Bậc 6"));
//        numExam.add(new NumExam("Bậc 7"));

        examAdapter = new ExamAdapter(content, arr_exam);
        gvExam.setAdapter(examAdapter);
        gvExam.setVerticalSpacing(16);
        gvExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_num_exam);
                dialog.setTitle("Danh sách đề");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setAttributes(lp);

                dialog.show();
                if(position==0){
                    monhoc="taynghekt";
                }else if(position==1){
                    monhoc="taynghekd";
                }else if(position==2){
                    monhoc="";
                }else {
                    monhoc="";
                }
                ChooseNumExam answerAdapter = new ChooseNumExam(numExam, getContext());
                GridView gvLsNumExam= dialog.findViewById(R.id.gvLsNumExam);
                gvLsNumExam.setAdapter(answerAdapter);


                gvLsNumExam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (monhoc == "") {
                            Toast.makeText(getActivity(), "Chưa mở!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(content, ScreenSlidePagerActivity.class);
                            intent.putExtra("num_exam", position + 1);
                            intent.putExtra("subject", monhoc);
                            intent.putExtra("test", "yes");
                            startActivity(intent);
                            //ad
                            if (mAdManagerInterstitialAd != null) {
                                mAdManagerInterstitialAd.show(content);
                            } else {
                                Log.d("TAG", "The interstitial ad wasn't ready yet.");
                            }
                            //ad
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        context = view.getContext();
//        activity = (AppCompatActivity) view.getContext();
//        btn = view.findViewById(R.id.fragbtn);
//
//
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showads();
//            }
//        });
//
//
//
//
//    }






    //ad
    @Override
    public void onStart() {
        super.onStart();


        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();

        AdManagerInterstitialAd.load(content,
                "/6499/example/interstitial", adRequest,
                //idqc  ca-app-pub-9808019056055820/8741298634   /6499/example/interstitial
                new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                        // The mAdManagerInterstitialAd reference will be null until
                        // an ad is loaded.
                        mAdManagerInterstitialAd = interstitialAd;

                        mAdManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                mAdManagerInterstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                // Called when ad fails to show.
                                mAdManagerInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mAdManagerInterstitialAd = null;
                    }
                });
    }
//ad




}