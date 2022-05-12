package com.example.move;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<ReviewItems> mReviewItems;
    private Context mContext;
    private DBHelper mDBHelper;
    private DatePickerDialog.OnDateSetListener callbackMethod;

    public CustomAdapter(ArrayList<ReviewItems> mReviewItems, Context mContext) {
        this.mReviewItems = mReviewItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        System.out.println("뷰 홀더 생성됨");
        return new ViewHolder(holder);
    }

    @Override // 실제 리스트들이 로드될 떄
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        String url ="https://image.tmdb.org/t/p/w500"+mReviewItems.get(position).getPosterPath();
        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.imv_poster);
        holder.tv_title.setText(mReviewItems.get(position).getTitle());
        System.out.println("실제 리스트들 중 title 로드");
        holder.tv_rating.setText(String.valueOf(mReviewItems.get(position).getRating()));
        System.out.println("실제 리스트들 중 rating 로드");
        holder.tv_watchDate.setText(mReviewItems.get(position).getWatchDate());
        System.out.println("실제 리스트들 중 writeDate 로드");

    }

    @Override
    public int getItemCount() {
        return mReviewItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        private TextView tv_rating;
        private TextView tv_watchDate;
        private ImageView imv_poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_watchDate = itemView.findViewById(R.id.tv_watchDate);
            imv_poster = itemView.findViewById(R.id.imv_poster);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int curPos = getAdapterPosition(); // 현재 리스트 클릭한 아이템 위치
                    ReviewItems reviewItem = mReviewItems.get(curPos);

                    String[] strChooseItems = {"수정하기", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택해주세요");
                    builder.setItems(strChooseItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position) {
                            if(position == 0) { // 수정하기 : 0
                                // 팝업 창 띄우기
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.dialog_edit);
                                TextView edit_title = dialog.findViewById(R.id.tv_real_title);
                                EditText edit_watchDate = dialog.findViewById(R.id.tv_real_date);
                                EditText edit_shortReview = dialog.findViewById(R.id.edit_shortReview);
                                EditText edit_textReview = dialog.findViewById(R.id.edit_textReview);
                                ImageView imv_des_poster = dialog.findViewById(R.id.imv_des_poster);

                                RatingBar btn_ratingbar = dialog.findViewById(R.id.btn_ratingbar);

                                Button btn_ok = dialog.findViewById(R.id.btn_okay);

                                // 저장된 리뷰 정보 미리보기
                                edit_title.setText(reviewItem.getTitle());
                                edit_watchDate.setText(reviewItem.getWatchDate());
                                edit_shortReview.setText(reviewItem.getShortReview());
                                edit_textReview.setText(reviewItem.getReview());
                                btn_ratingbar.setRating(reviewItem.getRating());

                                edit_watchDate.setSelection(edit_watchDate.getText().length());
                                edit_shortReview.setSelection(edit_shortReview.getText().length());
                                edit_textReview.setSelection(edit_textReview.getText().length());

                                String url ="https://image.tmdb.org/t/p/w500"+reviewItem.getPosterPath();
                                Glide.with(mContext)
                                        .load(url)
                                        .centerCrop()
                                        .crossFade()
                                        .into(imv_des_poster);

                                btn_ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                    @Override
                                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromuser) {
                                        reviewItem.setRating((int) rating);
                                    }
                                });

                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        // update table
                                        String watchDate = edit_watchDate.getText().toString();
                                        String shortReview = edit_shortReview.getText().toString();
                                        String textReview = edit_textReview.getText().toString();

                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                        String beforeTime = reviewItem.getWriteDate();
                                        String posterPath = reviewItem.getPosterPath();
                                        String title = reviewItem.getTitle();
                                        int rating = reviewItem.getRating();

                                        mDBHelper.UpdateMovieReview(title, currentTime, rating , watchDate, posterPath, shortReview, textReview, beforeTime);

                                        // update UI
                                        reviewItem.setWatchDate(watchDate);
                                        reviewItem.setRating(rating);
                                        reviewItem.setShortReview(shortReview);
                                        reviewItem.setReview(textReview);
                                        notifyItemChanged(curPos, reviewItem);
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "리뷰 수정이 완료 되었습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                dialog.show();

                            }
                            else if(position == 1){ //삭제하기 : 1

                                // delete table
                                String beforeTime = reviewItem.getWriteDate();
                                mDBHelper.DeleteMovieReview(beforeTime);

                                //delete UI
                                mReviewItems.remove(curPos);
                                notifyItemRemoved(curPos);
                                Toast.makeText(mContext, "목록이 제거되었습니다", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.show();
                }
            });
        }
    }

    //액티비티에서 호출되는 함수이며, 현재 어뎁터에 새로운 게시글 아이템을 전달받아 추가하는 목적
    public void addItem(ReviewItems _item){
        mReviewItems.add(0, _item); // 역순으로 add됨 -> 최신으로 작성한 게 위로 가도록
        notifyItemInserted(0);
    }
}
