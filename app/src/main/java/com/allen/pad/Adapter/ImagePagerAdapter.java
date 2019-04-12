package com.allen.pad.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.allen.pad.Model.Image;
import com.allen.pad.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImagePagerAdapter extends PagerAdapter {
    private ArrayList<Image> images;
    private LayoutInflater inflater;
    private Context context;

    public ImagePagerAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        System.out.println("Images: size: " + images.size());
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.item_view_pager, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
        System.out.println("Image: " + images.get(position).getTitle() + " position: " + position);

        Glide
                .with(context)
                .load(images.get(position).getTitle())
                .into(myImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
