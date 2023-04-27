package app.pcdongthap.tracnghiemspc.slide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import app.pcdongthap.tracnghiemspc.R;
import app.pcdongthap.tracnghiemspc.question.Question;
import app.pcdongthap.tracnghiemspc.score.ScoreController;

public class TestDoneActivity extends AppCompatActivity {


    TextView tvTrue, tvFasle, tvNotAns, tvTotalPoint, txtTestDone;
    Button btnSave, btnAgain, btnExit;
    ImageView imvTestDone;
    ArrayList<Question> arr_QuesBegin = new ArrayList<Question>();
    int num_NotAns = 0;
    int num_True_Ans = 0;
    int num_Fasle_Ans = 0;
    int num_TotalPoint = 0;


    ScoreController scoreController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_done);


        scoreController = new ScoreController(TestDoneActivity.this);

        Intent intent = getIntent();
        arr_QuesBegin = (ArrayList<Question>) intent.getExtras().getSerializable("arr_Ques");
        begin();
        checkResult();
        showResult();
        Exit();
        saveScore();
//        again();
    }

//    private void again() {
//        btnAgain.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                refresh();
//                finish();
//                Intent intent2 = new Intent(TestDoneActivity.this, ScreenSlidePagerActivity.class);
//                intent2.putExtra("arr_Ques", arr_QuesBegin);
//                intent2.putExtra("test", "no");
//                startActivity(intent2);
//            }
//
//            private void refresh() {
//                for (int i = 0; i < arr_QuesBegin.size(); i++) {
//                    arr_QuesBegin.get(i).setTraloi("");
//                }
//            }
//        });
//    }

    private void saveScore() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                LayoutInflater inflater = TestDoneActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.allert_dialog_save_score, null);
                builder.setView(view);

                EditText edtName = view.findViewById(R.id.edtName);
                TextView txtResultNumTrue = view.findViewById(R.id.txtResultNumTrue);
                TextView txtResultScore = view.findViewById(R.id.txtResultScore);
// cham diem
                int numResultTotal = num_True_Ans * 2;
                String numResultTrue = String.valueOf(num_True_Ans);

                edtName.setText(user.getDisplayName());
                txtResultNumTrue.setText(numResultTrue + "/" + (num_True_Ans + num_NotAns + num_Fasle_Ans) );

                txtResultScore.setText(numResultTotal + " Điểm");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = edtName.getText().toString();
                        String numResultTrue = txtResultNumTrue.getText().toString();

                        scoreController.insertScore(name, (int) numResultTotal, numResultTrue);
                        Toast.makeText(TestDoneActivity.this, "Lưu điểm thành công!", Toast.LENGTH_LONG).show();
                        finish();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog b = builder.create();
                b.show();
            }
        });
    }

    private void Exit() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TestDoneActivity.this);
                builder.setIcon(R.drawable.exit);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát hay không ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }
//cham diem bang ket qua
    private void showResult() {
        num_TotalPoint = num_True_Ans * 2 ;
        tvNotAns.setText("" + num_NotAns);
        tvFasle.setText("" + num_Fasle_Ans);
        tvTrue.setText("" + num_True_Ans);
        tvTotalPoint.setText("" + num_TotalPoint);
        if (num_TotalPoint <= 68) {
            txtTestDone.setText("Không Đạt");
            imvTestDone.setImageResource(R.drawable.khongdat);
        } else if (num_TotalPoint <= 90 ) {
            txtTestDone.setText("Đạt");
            imvTestDone.setImageResource(R.drawable.dat);
        } else {
            txtTestDone.setText("Xuất Sắc");
            imvTestDone.setImageResource(R.drawable.xuatsac);
        }

    }

    public void begin() {
        tvFasle = findViewById(R.id.tvFalse);
        tvTrue = findViewById(R.id.tvTrue);
        tvNotAns = findViewById(R.id.tvNotAns);
        tvTotalPoint = findViewById(R.id.tvTotalPoint);
//        btnAgain = findViewById(R.id.btnAgain);
        btnSave = findViewById(R.id.btnSaveScore);
        btnExit = findViewById(R.id.btnExit);
        txtTestDone = findViewById(R.id.txtTestDone);
        imvTestDone = findViewById(R.id.imvTestDone);
    }

    public void checkResult() {
        for (int i = 0; i < arr_QuesBegin.size(); i++) {
            if (arr_QuesBegin.get(i).getTraloi().equals("") == true) {
                num_NotAns++;
            } else if (arr_QuesBegin.get(i).getResult().equals(arr_QuesBegin.get(i).getTraloi()) == true) {
                num_True_Ans++;
            } else num_Fasle_Ans++;
        }
    }



}

