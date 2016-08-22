package com.thanglastudio.meroserofero.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thanglastudio.meroserofero.R;

/**
 * Created by admin on 5/27/16.
 */
public class CustomViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mResources = {
            R.drawable.beach,R.drawable.forest,
    };
    String[]mFooterText={"हाम्रो सेरोफेरोमा भैरहेका सत्य तथ्य खबरहरुको संगालो ","सबैलाई सूचना र खबरको अधिकार बराबर "};


    public CustomViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        TextView footer = (TextView) itemView.findViewById(R.id.welcome_description);
        Picasso.with(mContext).load(mResources[position]).centerCrop().fit().into(imageView);
        footer.setText(mFooterText[position]);
        container.addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
