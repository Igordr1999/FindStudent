package ru.findstudent.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.findstudent.R;
import ru.findstudent.TechItem;
import ru.findstudent.imgloader.ImageLoader;
import ru.findstudent.imgloader.MultipleImageLoader;

@Deprecated
public class TechItemsCursorPagerAdapter extends PagerAdapter {
    private ImageLoader mImageLoader;
    private Cursor mCursor;
    private LayoutInflater mInflater;
    private int mFullImageMargin;

    public TechItemsCursorPagerAdapter(Context context, Cursor cursor) {
        mInflater = LayoutInflater.from(context);
        mImageLoader = new MultipleImageLoader(context, 0);
        mFullImageMargin = (int) context.getResources().getDimension(R.dimen.full_image_margin);
        mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mInflater.inflate(R.layout.pager_item_tech, container, false);
        ImageView fullImage = (ImageView) itemView.findViewById(R.id.full_image);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView info = (TextView) itemView.findViewById(R.id.info);

        mCursor.moveToPosition(position);
        TechItem item = new TechItem(mCursor);

        int requiredSize = container.getMeasuredWidth() - 2 * mFullImageMargin;
        mImageLoader.setRequiredSize(requiredSize, requiredSize);

        mImageLoader.loadImage(item.getPictureUrl(), fullImage);
        title.setText(item.code);
        info.setText(item.record_create_date);

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}