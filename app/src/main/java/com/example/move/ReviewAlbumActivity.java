package com.example.move;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAlbumActivity extends AppCompatActivity {

    private RecyclerView rv_review;
    private ImageButton btn_addReview;
    private ArrayList<ReviewItems> mReviewItems;
    private DBHelper mDBHelper;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_album_activity);
        setInit();
    }

    private void setInit(){

        mDBHelper = new DBHelper(this);
        rv_review = findViewById(R.id.rv_review);
        btn_addReview = findViewById(R.id.btn_addReview);
        mReviewItems = new ArrayList<>();

        // load recent DB
        loadRecentDB();

        btn_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MovieSearch.class);
                startActivity(intent);
//                // 팝업 창 띄우기
//                Dialog dialog = new Dialog(ReviewAlbumActivity.this, android.R.style.Theme_Material_Light_Dialog);
//                dialog.setContentView(R.layout.dialog_edit);
//                EditText edit_title = dialog.findViewById(R.id.edit_title);
//                EditText edit_rating = dialog.findViewById(R.id.edit_rating);
//                EditText edit_writeDate = dialog.findViewById(R.id.edit_writeDate);
//                Button btn_ok = dialog.findViewById(R.id.btn_okay);
//
//                btn_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        //작성하는 시점의 시간 연월일시분초 받아오기
//                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//
//                        // insert Database
//                        mDBHelper.InsertMovieReview(edit_title.getText().toString(), currentTime,
////                                Integer.parseInt(edit_rating.getText().toString()), null, null);
//
//                        // insert UI
//                        ReviewItems item = new ReviewItems();
//                        item.setTitle(edit_title.getText().toString());
//                        item.setRating(Integer.parseInt(edit_rating.getText().toString()));
//                        item.setWriteDate(currentTime);
//
//                        mAdapter.addItem(item);
//                        rv_review.smoothScrollToPosition(0);
//                        dialog.dismiss();
//                        Toast.makeText(ReviewAlbumActivity.this, "영화 리뷰 목록에 추가되었습니다", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                dialog.show();

            }
        });

    }

    private void loadRecentDB() {
        // 저장되어있던 DB를 가져온다
        mReviewItems = mDBHelper.getReviewList();
        if(mAdapter == null){
            mAdapter = new CustomAdapter(mReviewItems, this);
            rv_review.setHasFixedSize(true);
            rv_review.setAdapter(mAdapter);
        }
    }
}