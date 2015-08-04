package pe.area51.mysharedpreferencesapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView textView;

    public static final String SHARED_PREFERENCES_NAME = "pe.area51.mysharedpreferencesapp.VISITORS";
    public static final String SHARED_PREFERENCES_KEY_LAST_VISITOR = "last_visitor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.activity_main_textview_message);
        final String lastVisitor = loadLastVisitor();
        if (lastVisitor != null) {
            textView.setText(getString(R.string.last_user, lastVisitor));
        } else {
            textView.setText(getString(R.string.first_user));
        }
        findViewById(R.id.activity_main_button_register_user)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String name = ((EditText) findViewById(R.id.activity_main_edittext_name)).getText().toString();
                        textView.setText(getString(R.string.message, name));
                        saveLastVisitor(name);
                    }
                });
    }

    private String loadLastVisitor() {
        return getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                .getString(SHARED_PREFERENCES_KEY_LAST_VISITOR, null);
    }

    private boolean saveLastVisitor(final String name) {
        return getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
                .edit()
                .putString(SHARED_PREFERENCES_KEY_LAST_VISITOR, name)
                .commit();
    }

}
