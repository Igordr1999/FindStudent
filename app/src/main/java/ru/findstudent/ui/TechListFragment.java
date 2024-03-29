package ru.findstudent.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.findstudent.MainApplication;
import ru.findstudent.R;
import ru.findstudent.adapter.TechItemsCursorAdapter;
import ru.findstudent.db.DbHelper;

public class TechListFragment extends ListFragment {
    private TechItemsCursorAdapter mTechItemsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tech_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, TechPagerFragment.newInstance(position), TechPagerFragment.tag());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mTechItemsAdapter == null) {
            DbHelper dbHelper = MainApplication.getDbHelper();
            Cursor cursor = dbHelper.getTechnologies();
            mTechItemsAdapter = new TechItemsCursorAdapter(getActivity(), cursor);
        }
        setListAdapter(mTechItemsAdapter);
    }

    public static String tag() {
        return TechListFragment.class.getSimpleName();
    }
}
