package ru.findstudent.ui;

public interface DataLoadingListener {
    void onDataLoaded();
    void onDataLoadingFailed(int errorResId);
}