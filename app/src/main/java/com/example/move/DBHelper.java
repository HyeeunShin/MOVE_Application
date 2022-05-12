package com.example.move;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "movieReview.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // DB 생성되었을 때 호출
        // database -> table -> column -> value
        db.execSQL("CREATE TABLE IF NOT EXISTS ReviewList (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, writeDate TEXT NOT NULL DEFAULT(datetime('now', 'localtime')), " +
                "rating INTEGER NOT NULL, watchDate TEXT NOT NULL, posterPath TEXT NOT NULL, " +
                "shortReview TEXT, review TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    // SELECT 문 (쓴 리뷰들을 조회)
    public ArrayList<ReviewItems> getReviewList(){
        ArrayList<ReviewItems> reviewItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ReviewList ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){ // 반드시 데이터가 있음(조회해온 데이터가 있을 때 수행)
            while(cursor.moveToNext()){
                System.out.println("불러오는 게 실행 안됨");
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));
                int rating = cursor.getInt(cursor.getColumnIndex("rating"));
                String watchDate = cursor.getString(cursor.getColumnIndex("watchDate"));
                String posterPath = cursor.getString(cursor.getColumnIndex("posterPath"));
                String shortReview = cursor.getString(cursor.getColumnIndex("shortReview"));
                String review = cursor.getString(cursor.getColumnIndex("review"));

                ReviewItems reviewItem = new ReviewItems();
                reviewItem.setId(id);
                reviewItem.setTitle(title);
                reviewItem.setWriteDate(writeDate);
                reviewItem.setRating(rating);
                reviewItem.setWatchDate(watchDate);
                reviewItem.setPosterPath(posterPath);
                reviewItem.setShortReview(shortReview);
                reviewItem.setReview(review);
                reviewItems.add(reviewItem);
            }
        }
        cursor.close();
        return  reviewItems;
    }

    // INSERT 문 (리뷰를 작성하여 DB에 넣는다)
    public void InsertMovieReview(String _title, String _writeDate, int _rating, String _watchDate, String _posterPath, String _shortReview, String _review){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO ReviewList (title, writeDate, rating, watchDate, posterPath, shortReview, review) VALUES('" + _title + "', '" + _writeDate + "', '" + _rating + "', '" + _watchDate + "', '" + _posterPath + "', '" + _shortReview + "', '" + _review + "');");
    }

    // UPDATE 문 (리뷰 목록을 수정한다)
    public void UpdateMovieReview(String _title, String _writeDate, int _rating, String _watchDate, String _posterPath, String _shortReview, String _review, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE ReviewList SET title='" + _title + "', writeDate='" + _writeDate + "', rating='" + _rating + "', shortReview='" + _shortReview + "', review='" + _review + "' WHERE writeDate='" + _beforeDate + "'");
    }

    // DELETE 문 (리뷰 목록을 삭제한다)
    public void DeleteMovieReview(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM ReviewList WHERE writeDate='" + _beforeDate + "'");
    }
}
