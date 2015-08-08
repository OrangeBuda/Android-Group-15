package pe.area51.mythreadsapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.progress_title);
        progressDialog.setMessage(getString(R.string.progress_message));
        progressDialog.setCancelable(false);
        findViewById(R.id.activity_main_button_main_thread)
                .setOnClickListener(this);
        findViewById(R.id.activity_main_button_worker_thread)
                .setOnClickListener(this);
        findViewById(R.id.activity_main_button_async_task)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_button_main_thread:
                executeOnMainThread();
                break;
            case R.id.activity_main_button_worker_thread:
                executeOnWorkerThread();
                break;
            case R.id.activity_main_button_async_task:
                executeAsyncTask();
                break;
        }
    }

    private void longTask() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeOnMainThread() {
        progressDialog.show();
        longTask();
        progressDialog.dismiss();
    }

    private void executeOnWorkerThread() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                longTask();
                progressDialog.dismiss();
            }
        }).start();
    }

    private void executeAsyncTask() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                longTask();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
            }
        }.execute();
    }

}
