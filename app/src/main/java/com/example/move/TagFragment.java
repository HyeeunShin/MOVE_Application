package com.example.move;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;


public class TagFragment extends Fragment {
    private String shortReview;
    private ToggleButton good, nojam, sgsg, soso, killtime, one, with, gamdong;
    private Button comBtn;
    private TextView viewtaglist;
    //Fragment의 lifecycle 메소드 중에서 Fragment가 보여줄 View를 설정하는 메소드
    //MainActivity.java 에서 onCreate() 메소드 안에 setContentView()하듯이
    //Fragment에서는 이 메소드에서 Fragment가 보여줄 View 객체를 생성해서 return 시켜줘야 함.
    int i = 0;
    ArrayList<String> tagList = new ArrayList<String>();
    Fragment textFragment = new TextFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View tagView = null;//Fragment가 보여줄 View 객체를 참조할 참조변수
        //매개변수로 전달된 LayoutInflater객체를 통해 fragment_digital.xml 레이아웃 파일을
        //View 객체로 생성
        tagView = inflater.inflate(R.layout.tag_review, null);
        //생성된 View 객체를 리턴
        tagList.add(null);
        good = (ToggleButton) tagView.findViewById(R.id.tag_good);
        one = (ToggleButton) tagView.findViewById(R.id.tag_one);
        gamdong = (ToggleButton) tagView.findViewById(R.id.tag_gamdong);
        sgsg = (ToggleButton) tagView.findViewById(R.id.tag_sgsg);
        killtime = (ToggleButton) tagView.findViewById(R.id.tag_killtime);
        with = (ToggleButton) tagView.findViewById(R.id.tag_with);
        nojam = (ToggleButton) tagView.findViewById(R.id.tag_nojam);
        soso = (ToggleButton) tagView.findViewById(R.id.tag_soso);
        viewtaglist = (TextView) tagView.findViewById(R.id.viewtag);
        comBtn = (Button) tagView.findViewById(R.id.completeBtn);

        comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String viewShort = "";
                if(tagList.size() == 1){
                    viewtaglist.setText(tagList.get(0));
                }
                else{
                    for(int i=1; i <= tagList.size()-1; i++){
                        viewShort += tagList.get(i);
                    }
                    viewtaglist.setText(viewShort);
                }
//                Bundle bundle = new Bundle(1);
//                bundle.putString("간편리뷰", viewShort);
//                textFragment.setArguments(bundle);

                Toast.makeText(getActivity(), "간편 리뷰 선택 완료", Toast.LENGTH_SHORT).show();

            }
        });
        good.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#웃김");
                } else {
                    tagList.remove("#웃김");
                }
            }
        });
        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#또 볼래");
                } else {
                    tagList.remove("#또 볼래");
                }
            }
        });
        with.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#연인과 함께");

                } else {
                    tagList.remove("#연인과 함께");
                }
            }
        });
        soso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#그럭저럭");

                } else {
                    tagList.remove("#그럭저럭");
                }
            }
        });
        sgsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#시간순삭");

                } else {
                    tagList.remove("#시간순삭");
                }
            }
        });
        killtime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#킬링타임");

                } else {
                    tagList.remove("#킬링타임");
                }
            }
        });
        nojam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#노잼");

                } else {
                    tagList.remove("#노잼");
                }
            }
        });
        gamdong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tagList.add("#감동");


                } else {
                    tagList.remove("#감동");
                }
            }
        });



//      viewtaglist.setText(shortReviewtxt);

        return tagView;
    }
//public View onPause(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//
//    return tagView;
//}
}




