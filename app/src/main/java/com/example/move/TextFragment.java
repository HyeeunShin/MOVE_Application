package com.example.move;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TextFragment extends Fragment {
    private Button textOkBtn;
    private String review;
    // Intent textIntent;

    //Fragment의 lifecycle 메소드 중에서 Fragment가 보여줄 View를 설정하는 메소드
    //MainActivity.java 에서 onCreate() 메소드 안에 setContentView()하듯이
    //Fragment에서는 이 메소드에서 Fragment가 보여줄 View 객체를 생성해서 return 시켜줘야 함.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO Auto-generated method stub

        View textView = null;//Fragment가 보여줄 View 객체를 참조할 참조변수
        //매개변수로 전달된 LayoutInflater객체를 통해 fragment_analog.xml 레이아웃 파일을
        //View 객체로 생성
        textView = inflater.inflate(R.layout.text_review, null);
        //생성된 View 객체를 리턴
        //    final EditText reviewText = (EditText) textView.findViewById(R.id.add_textReview); //텍스트리뷰

        return textView;
    }
}


