package pe.area51.myfragmentapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment {

    private ListView listView;
    private ListFragmentInterface listFragmentInterface;

    final private Note[] notes = new Note[]{
            new Note("Title 1", "Content 1"),
            new Note("Title 2", "Content 2"),
            new Note("Title 3", "Content 3"),
            new Note("Title 4", "Content 4"),
            new Note("Title 5", "Content 5")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.fragment_list_listview_list);
        listView.setAdapter(new NoteListViewAdapter(this, notes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listFragmentInterface != null) {
                    listFragmentInterface.onNoteSelected((Note) parent.getItemAtPosition(position));
                }
            }
        });
    }

    public void setListFragmentInterface(final ListFragmentInterface listFragmentInterface) {
        this.listFragmentInterface = listFragmentInterface;
    }

    private class NoteListViewAdapter extends ArrayAdapter<Note> {

        private final Fragment fragment;

        public NoteListViewAdapter(final Fragment fragment, final Note[] notes) {
            super(fragment.getActivity(), 0, notes);
            this.fragment = fragment;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View listElement = fragment.getActivity().getLayoutInflater().inflate(R.layout.fragment_list_listview_element, null);
            final TextView titleTextView = (TextView) listElement.findViewById(R.id.fragment_list_listview_element_title);
            titleTextView.setText(getItem(position).getTitle());
            return listElement;
        }
    }

    public interface ListFragmentInterface {

        public void onNoteSelected(final Note note);

    }
}
