package pe.area51.myfragmentapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity implements ListFragment.ListFragmentInterface {

    public static final String EXTRA_NOTE_TITLE = "note_title";
    public static final String EXTRA_NOTE_CONTENT = "note_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ListFragment) getFragmentManager().findFragmentById(R.id.activity_main_fragment_list)).setListFragmentInterface(this);
    }

    @Override
    public void onNoteSelected(final Note note) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((ContentFragment) getFragmentManager().findFragmentById(R.id.activity_main_fragment_content)).showNote(note);
        } else {
            startActivity(new Intent(this, ContentActivity.class)
                            .putExtra(EXTRA_NOTE_TITLE, note.getTitle())
                            .putExtra(EXTRA_NOTE_CONTENT, note.getContent())
            );
        }
    }
}
