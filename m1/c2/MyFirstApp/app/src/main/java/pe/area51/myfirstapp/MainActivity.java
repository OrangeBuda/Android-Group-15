package pe.area51.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static  String USERNAME_EXTRA = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity_main_button_sendname)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String username = ((EditText)findViewById(R.id.activity_main_edittext_username)).getText().toString();
                        final Intent intent = new Intent(MainActivity.this, HelloActivity.class);
                        intent.putExtra(USERNAME_EXTRA, username);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_main_testbutton1:
                Toast.makeText(this, R.string.menu_button_1_pressed, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_main_testbutton2:
                Toast.makeText(this, R.string.menu_button_2_pressed, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
