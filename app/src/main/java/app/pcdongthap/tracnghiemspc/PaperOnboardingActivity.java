package app.pcdongthap.tracnghiemspc;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import app.pcdongthap.tracnghiemspc.user.SignInActivity;
import app.pcdongthap.tracnghiemspc.user.SignUpActivity;

public class PaperOnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
                // viet su kien khi lướt onboarding ở đây
            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                Intent intent= new Intent(PaperOnboardingActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Không cần máy tính", "Tiện dụng, di động, dù ở nhà hay ở cơ quan, học được ở mọi nơi, bất cứ khi nào rảnh rỗi",
                Color.parseColor("#FFFFFF"), R.drawable.artboard1, R.drawable.key);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Xem đáp án trong khi làm bài", "Một tính năng rất hữu ích là kiểm tra đáp án vừa chọn, giúp anh em ghi nhớ nhanh câu hỏi và đáp án",
                Color.parseColor("#FFFFFF"), R.drawable.artboard2, R.drawable.wallet);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Trực quan", "Xáo trộn câu hỏi như thi thật",
                Color.parseColor("#FFFFFF"), R.drawable.artboard3, R.drawable.shopping_cart);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}