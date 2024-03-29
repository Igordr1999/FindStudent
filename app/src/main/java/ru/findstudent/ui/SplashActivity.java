package ru.findstudent.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ru.findstudent.Api;
import ru.findstudent.MainApplication;
import ru.findstudent.R;
import ru.findstudent.StreamUtils;
import ru.findstudent.TechItem;
import ru.findstudent.TechParser;
import ru.findstudent.db.DbHelper;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements DataLoadingListener {
    private static final long DEFAULT_SPLASH_LENGTH = 2000L;
    private LoadDataTask mLoadDataTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mLoadDataTask == null) {
            mLoadDataTask = new LoadDataTask(this, this);
            mLoadDataTask.execute();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelDataLoading();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelDataLoading();
    }

    private void cancelDataLoading() {
        if (mLoadDataTask != null) {
            mLoadDataTask.cancel(true);
            mLoadDataTask = null;
        }
    }

    @Override
    public void onDataLoaded() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataLoadingFailed(int errorResId) {
        Toast.makeText(getApplicationContext(), errorResId, Toast.LENGTH_LONG).show();
    }


    private static class LoadDataTask extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<Context> mContextWeakRef;
        private WeakReference<DataLoadingListener> mListenerWeakRef;
        private int mErrorResId = R.string.error_load;

        LoadDataTask(Context context, DataLoadingListener listener) {
            mContextWeakRef = new WeakReference<>(context);
            mListenerWeakRef = new WeakReference<>(listener);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            DbHelper dbHelper = MainApplication.getDbHelper();

            Context context = mContextWeakRef.get();
            if (context != null && !isNetworkAvailable(context)) {
                return attemptToLoadDataFromDb(dbHelper);
            }

            HttpURLConnection conn = null;
            try {
                URL url = new URL(Api.DATA_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Token cddadb257c5cc89851ddb91c71d37ee7ff9db0ca");

                InputStream in = new BufferedInputStream(conn.getInputStream());
                String data = StreamUtils.readStream(in);
                in.close();

                List<TechItem> items = TechParser.parse(data);
                dbHelper.deleteTechnologies();
                dbHelper.insertTechnologies(items);

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                if (attemptToLoadDataFromDb(dbHelper)) {
                    return true;
                }
                mErrorResId = R.string.error_read;
            } catch (JSONException e) {
                e.printStackTrace();
                if (attemptToLoadDataFromDb(dbHelper)) {
                    return true;
                }
                mErrorResId = R.string.error_parse;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return false;
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        private boolean attemptToLoadDataFromDb(DbHelper dbHelper) {
            if (dbHelper.hasTechnologies()) {
                try {
                    Thread.sleep(DEFAULT_SPLASH_LENGTH);
                } catch (InterruptedException e) {
                    // Ignore
                }
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!isCancelled()) {
                DataLoadingListener listener = mListenerWeakRef.get();
                if (listener != null) {
                    if (result) {
                        listener.onDataLoaded();
                    } else {
                        listener.onDataLoadingFailed(mErrorResId);
                    }
                }
            }
        }
    }
}
