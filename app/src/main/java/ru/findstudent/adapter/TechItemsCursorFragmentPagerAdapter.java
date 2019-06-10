package ru.findstudent.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.findstudent.TechItem;
import ru.findstudent.imgloader.ImageLoader;
import ru.findstudent.imgloader.MultipleImageLoader;
import ru.findstudent.ui.TechItemFragment;

public class TechItemsCursorFragmentPagerAdapter extends FragmentPagerAdapter {
    private ImageLoader mImageLoader;
    private Cursor mCursor;

    public TechItemsCursorFragmentPagerAdapter(Context context, FragmentManager fm, Cursor cursor) {
        super(fm);
        mImageLoader = new MultipleImageLoader(context, 0);
        mCursor = cursor;
    }

    @Override
    public Fragment getItem(int position) {
        mCursor.moveToPosition(position);
        return TechItemFragment.newInstance(mImageLoader, new TechItem(mCursor));
    }

    @Override
    public int getCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }
}
