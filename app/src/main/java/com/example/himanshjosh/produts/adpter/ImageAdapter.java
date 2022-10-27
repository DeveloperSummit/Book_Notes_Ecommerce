package com.example.himanshjosh.produts.adpter;

import static com.example.himanshjosh.utlity.ConstantField.IMAGE_BASE_URL;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.himanshjosh.R;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> photolist;

    public ImageAdapter(Context context, ArrayList<String> photolist) {
        this.mContext = context;
        this.photolist = photolist;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.getCropToPadding();
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        //  imageView.setImageURI(Uri.parse(IMAGE_BASE_URL+photolist.get(position)));

        Glide.with(mContext)
                .load(IMAGE_BASE_URL + photolist.get(position))
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(imageView);

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return photolist.size();
    }
}