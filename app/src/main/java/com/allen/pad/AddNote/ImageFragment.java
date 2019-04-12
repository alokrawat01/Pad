package com.allen.pad.AddNote;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.allen.pad.Adapter.ImagePagerAdapter;
import com.allen.pad.Model.Image;
import com.allen.pad.R;
import java.util.ArrayList;

public class ImageFragment extends Fragment implements View.OnClickListener {

    ViewPager mPager;
    ImageView iv_back;

    private static int currentPage = 0;
    ArrayList<Image> listImages;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        mPager = v.findViewById(R.id.pager);
        iv_back = v.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        listImages = new ArrayList<>(getArguments().<Image>getParcelableArrayList("IMAGES"));
        mPager.setAdapter(new ImagePagerAdapter(getContext(), listImages));

        return v;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.iv_back:
                getActivity().onBackPressed();
                break;
        }
    }

}
