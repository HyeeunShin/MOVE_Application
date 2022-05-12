package com.example.move;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ReviewUpload extends AppCompatActivity{
    private Button txtBtn, tagBtn, saveBtn;
    private FrameLayout container;
    private TextView textView_Date, tv, textmain, viewtaglist;
    private EditText addTitleBtn, reviewText;
    private ViewGroup rootView;
    private OnClickListener click;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private int id;
    private String title, genre, textReview, shortReviewtxt, reviewTxt,textrv;
    private String watchDate;
    private int rating;
    private FragmentManager fm;
    private FragmentTransaction tran;
    private Fragment fragTag, fragTxt; //2개의 Fragment 참조변수
    private boolean clicked, tag, txt;
    private String shortReview;
    private ToggleButton good, nojam, sgsg, soso, killtime, one, with, gamdong;
    private Button tagOkBtn;
    private DBHelper mDBHelper;
    private ArrayList<ReviewItems> mReviewItems;
    private CustomAdapter mAdapter;
    private RecyclerView rv_review;

    Calendar cal = Calendar.getInstance();
    int tyear = cal.get(Calendar.YEAR);
    int tmonth = cal.get(Calendar.MONTH) + 1;
    int tday = cal.get(Calendar.DAY_OF_MONTH);
    ArrayList<String> tagList = new ArrayList<String>();

    int intrating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_upload);

        mDBHelper = new DBHelper(this);
        mReviewItems = new ArrayList<>();
        rv_review = findViewById(R.id.rv_review);

        this.InitializeView();
        this.InitializeListener();

        Intent intent = getIntent();
        String gettitle = intent.getStringExtra("title");
        String poster_path = intent.getStringExtra("poster_path");

        final TextView addTitleBtn = (TextView) findViewById(R.id.add_title);
        final Button txtBtn = (Button) findViewById(R.id.textBtn);
        final Button tagBtn = (Button) findViewById(R.id.tagBtn);
        final Button saveBtn = (Button) findViewById(R.id.saveBtn);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.add_stars);
        //     final TextView textmain = (TextView) findViewById(R.id.textviewmain);
        final FrameLayout container = (FrameLayout) findViewById(R.id.container);
        final TextView dateView = (TextView) findViewById(R.id.add_date);
        final ImageView imageView_poster = (ImageView) findViewById(R.id.add_image);

        addTitleBtn.setText(gettitle);
        Glide.with(this)
                .load(poster_path)
                .centerCrop()
                .crossFade()
                .into(imageView_poster);

        dateView.setText(tyear + "." + tmonth + "." + tday);

        fragTag = new TagFragment();
        fragTxt = new TextFragment();
//        setFrag(0);

        tag = false;
        txt = false;
        ReviewItems item = new ReviewItems();
        tagList = new ArrayList<String>();
        int i = 0;

        loadRecentDB();

        //간편리뷰 버튼 누르면,
        tagBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(0);
                tag = true;
                txt = false;
            }
        });
        //clicked==true : 태그버튼이 눌린상태

        txtBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrag(1);
                tag = false;
                txt = true;
            }
        });

        //ratingBar 값 reviewitem 속에  저장>_<!
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromuser) {
                intrating = (int) rating;
                item.setRating(intrating);
            }
        });

        //저장 버튼 누르면,
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String shortrv = null;
                String rv = null;

                // TextView  shortText = (TextView) container.findViewById(R.id.viewtag);

                if (tag == true && txt == false) {
                    TextView shortText = (TextView) container.findViewById(R.id.viewtag);
                    if (shortText.getText().toString() == "null") {
                        item.setShortReview(shortrv);
                    } else {
                        item.setShortReview(shortText.getText().toString());
                    }
                    item.setReview(rv);
                }
                else if(tag==false && txt==true) {
                    EditText reviewText = (EditText) container.findViewById(R.id.add_textReview);
                    TextView viewtextlist = (TextView) container.findViewById(R.id.viewtext);

                    item.setReview(reviewText.getText().toString());
                    item.setShortReview(shortrv);
                }
                //입력한 제목 불러오고, 변수에 할당
                title = addTitleBtn.getText().toString();
                watchDate = textView_Date.getText().toString();

                // 작성하는 시점의 시간 연월일시분초 받아오기
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                // insert UI
                item.setTitle(title); // 제목
                item.setWatchDate(watchDate); // 감상 날짜
                item.setWriteDate(currentTime); // 작성 날짜
                item.setPosterPath(poster_path);

                // insert Database
                mDBHelper.InsertMovieReview(item.getTitle(), currentTime,
                        item.getRating(), item.getWatchDate(), item.getPosterPath(),
                        item.getShortReview(), item.getReview());

                mAdapter.addItem(item);
//                rv_review.smoothScrollToPosition(0);

                //reviewitem 확인용 textView
//                textmain.setText("\n제목: " + reviewitem.getTitle() +  "\n날짜: " + reviewitem.getWatchDate() + "\n별점: " + reviewitem.getRating() + "/5\n" + reviewitem.getShortReview() + "\n" + reviewitem.getReview() + "\npath: " + reviewitem.getPosterPath());

                //저장버튼 누르면 다음 testActivity로 인텐트 전송
                Intent aintent = new Intent(ReviewUpload.this, ReviewAlbumActivity.class);
                startActivity(aintent);
                finish();
                Toast.makeText(ReviewUpload.this, "영화 리뷰 목록에 추가되었습니다", Toast.LENGTH_SHORT).show();

            } // end onClick
        }); // end setOnClickListener

    }

    //관람일 이벤트
    public void InitializeView() {
        textView_Date = (TextView) findViewById(R.id.add_date);
    }

    public void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear +1;
                textView_Date.setText(year + "." + monthOfYear + "." + dayOfMonth);
            }
        };
    }

    public void OnClickHandler(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, tyear, tmonth-1, tday);
        dialog.show();
    }

    public void setFrag(int n) {    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.container, fragTag);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();

                break;
            case 1:
                tran.replace(R.id.container, fragTxt);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                tran.commit();
                break;
        }
    }

    private void loadRecentDB() {
        // 저장되어있던 DB를 가져온다
        mReviewItems = mDBHelper.getReviewList();
        System.out.println("저장되어있는 DB 불러오기 성공");
        if(mAdapter == null){
            System.out.println("어뎁터 null");
            mAdapter = new CustomAdapter(mReviewItems, this);
            System.out.println("새로운 어뎁터 생성");
//            rv_review.setHasFixedSize(true);
//            rv_review.setAdapter(mAdapter);
//            System.out.println("어뎁터 set");
        }
    }

}
