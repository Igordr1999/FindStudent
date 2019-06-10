package ru.findstudent.ui;

import android.content.Intent;
import android.net.LinkAddress;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.findstudent.R;
import ru.findstudent.TechItem;
import ru.findstudent.imgloader.ImageLoader;

public class TechItemFragment extends Fragment {
    private ImageLoader mImageLoader;
    private TechItem mItem;

    public static TechItemFragment newInstance(ImageLoader imageLoader, TechItem item) {
        TechItemFragment fragment = new TechItemFragment();
        fragment.mImageLoader = imageLoader;
        fragment.mItem = item;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.pager_item_tech, container, false);

        ImageView fullImage = (ImageView) itemView.findViewById(R.id.full_image);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView info = (TextView) itemView.findViewById(R.id.info);

        int fullImageMargin = (int) getResources().getDimension(R.dimen.full_image_margin);
        int requiredSize = Math.min(container.getMeasuredWidth(),
                container.getMeasuredHeight()) - 2 * fullImageMargin;
        mImageLoader.setRequiredSize(requiredSize, requiredSize);

        mImageLoader.loadImage(mItem.getPictureUrl(), fullImage);
        String name = mItem.last_name + " " + mItem.first_name;
        title.setText(name);
        String text = "findstudent.ru/data/search/" + mItem.code + " ;";
        text += "Similarity: " + mItem.similarity + ". VK: id"+mItem.user_id;
        info.setText(text);

        info.setAutoLinkMask(Linkify.WEB_URLS);

        return itemView;
    }
}
