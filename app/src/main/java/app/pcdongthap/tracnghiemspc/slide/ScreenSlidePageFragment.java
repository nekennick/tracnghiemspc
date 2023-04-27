package app.pcdongthap.tracnghiemspc.slide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import app.pcdongthap.tracnghiemspc.R;
import app.pcdongthap.tracnghiemspc.question.Question;


public class ScreenSlidePageFragment extends Fragment {
    ArrayList<Question> arr_Ques;
    public static final String ARG_PAGE = "page";
    public static final String ARG_CHECKANSWER = "checkAnswer";
    private int mPageNumber;
    public int checkAns;


    Activity content;
    TextView txtNum, txtQuesTion;
    ImageView imgIcon;
    RadioGroup radioGroup;
    RadioButton radA, radB, radC, radD;
    Button btnClick, btn;



    public ScreenSlidePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);



        
        txtNum = rootView.findViewById(R.id.tvNum);
        txtQuesTion = rootView.findViewById(R.id.tvQuestion);
        imgIcon = rootView.findViewById(R.id.ivIcon);
        radA = rootView.findViewById(R.id.radA);
        radB = rootView.findViewById(R.id.radB);
        radC = rootView.findViewById(R.id.radC);
        radD = rootView.findViewById(R.id.radD);
        radioGroup = rootView.findViewById(R.id.radGroup);
//        Ánh xạ xem trước đáp án
        btnClick = (Button) rootView.findViewById(R.id.btnClick);


//        xem trước đáp án

        return rootView;


    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//        MobileAds.initialize(content);
//        final AdLoader adLoader = new AdLoader.Builder(content, "ca-app-pub-3940256099942544/2247696110")
//                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
//                    @Override
//                    public void onNativeAdLoaded(NativeAd nativeAd) {
//                        NativeTemplateStyle styles = new
//                                NativeTemplateStyle.Builder().build();
//                        TemplateView template = content.findViewById(R.id.my_template);
//                        template.setStyles(styles);
//                        template.setNativeAd(nativeAd);
//                    }
//                })
//                .build();
//
//        adLoader.loadAd(new AdRequest.Builder().build());


        arr_Ques = new ArrayList<Question>();
        ScreenSlidePagerActivity slidePagerActivity = (ScreenSlidePagerActivity) getActivity();
        arr_Ques = slidePagerActivity.getData();
        mPageNumber = getArguments().getInt(ARG_PAGE);
        checkAns = getArguments().getInt(ARG_CHECKANSWER);







    }




    public static ScreenSlidePageFragment create(int pageNumber, int checkAnswer) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_CHECKANSWER, checkAnswer);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtNum.setText("Câu " + (mPageNumber + 1));
//        txtNum.setText("Câu" + (getItem(mPageNumber).getImage()));
        txtQuesTion.setText(arr_Ques.get(mPageNumber).getQuestion());
        if (getItem(mPageNumber).getImage() == null) {
            imgIcon.setVisibility(View.GONE);
        } else {
            imgIcon.setImageResource(getResources().getIdentifier(getItem(mPageNumber).getImage() + "",
                    "drawable", "app.pcdongthap.tracnghiemspc"));
        }
        radA.setText(getItem(mPageNumber).getAns_a());
        radB.setText(getItem(mPageNumber).getAns_b());
        radC.setText(getItem(mPageNumber).getAns_c());
        radD.setText(getItem(mPageNumber).getAns_d());

        //Code xem trước đáp án
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckAns(getItem(mPageNumber).getResult(), getItem(mPageNumber).getTraloi());
            }
        });
        //xem trước đáp án









        if (checkAns != 0) {
            radA.setClickable(false);
            radB.setClickable(false);
            radC.setClickable(false);
            radD.setClickable(false);
            btnClick.setVisibility(View.GONE);
            getCheckAns(getItem(mPageNumber).getResult(), getItem(mPageNumber).getTraloi());
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

//                getItem(mPageNumber).choiceID = checkedId;
                getItem(mPageNumber).setTraloi(getChoiceFromId(checkedId));
//                Toast.makeText(getActivity(), "Đây là đáp án"+checkedId, Toast.LENGTH_SHORT).show();
            }
        });
    }







    public Question getItem(int position) {
        return arr_Ques.get(position);
    }

    private String getChoiceFromId(int ID) {
        if (ID == R.id.radA) {
            return "1";
        } else if (ID == R.id.radB) {
            return "2";
        } else if (ID == R.id.radC) {
            return "3";
        } else if (ID == R.id.radD) {
            return "4";
        } else return "";
    }


//Đổi màu đáp án
    private void getCheckAns(String result, String ans) {
        if (result.equals(ans)) {
            if (result.equals("1")) {
                radA.setBackgroundColor(Color.GREEN);
            } else if (result.equals("2")) {
                radB.setBackgroundColor(Color.GREEN);
            } else if (result.equals("3")) {
                radC.setBackgroundColor(Color.GREEN);
            } else if (result.equals("4")) {
                radD.setBackgroundColor(Color.GREEN);
            } else ;
        } else if (result != ans) {
            if (result.equals("1")) {
                radA.setBackgroundColor(Color.GREEN);
            } else if (result.equals("2")) {
                radB.setBackgroundColor(Color.GREEN);
            } else if (result.equals("3")) {
                radC.setBackgroundColor(Color.GREEN);
            } else if (result.equals("4")) {
                radD.setBackgroundColor(Color.GREEN);
            } else ;

        }
    }






}
