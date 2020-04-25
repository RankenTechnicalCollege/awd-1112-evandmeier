package edu.ranken.emeier.hot5.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.emeier.hot5.R;
import edu.ranken.emeier.hot5.data.entity.Note;
import edu.ranken.emeier.hot5.ui.activity.EditNoteActivity;
import edu.ranken.emeier.hot5.ui.activity.MainActivity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    // constants
    public static final String EXTRA_NOTE_ID = "edu.ranken.emeier.hot5.EXTRA_NOTE_ID";

    // fields
    private Context mContext;
    private List<Note> mNotes;
    private final LayoutInflater mInflater;

    public NoteListAdapter(Context context, List<Note> notes) {
        mContext = context;
        mNotes = notes;
        mInflater = LayoutInflater.from(context);
    }

    public void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAtPosition(int position) {
        return mNotes.get(position);
    }

    public int getNoteIdAtPosition(int position) {
        return mNotes.get(position).getId();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_note_card, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (mNotes != null) {
            // get the current note
            Note note = mNotes.get(position);
            Log.i("ADAPTER", note.getTitle());
            Log.i("ADAPTER", note.getDate());
            Log.i("ADAPTER", note.getBody());

            // bind the note attributes to the views
            holder.mNoteTitle.setText(note.getTitle());
            holder.mNoteDate.setText(note.getDate());
            holder.mNoteBody.setText(note.getBody());
        } else {
            holder.mNoteTitle.setText("");
            holder.mNoteDate.setText("");
            holder.mNoteBody.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mNotes != null ? mNotes.size() : 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNoteTitle, mNoteDate, mNoteBody;

        NoteViewHolder(View itemView) {
            super(itemView);

            mNoteTitle = itemView.findViewById(R.id.text_note_title);
            mNoteDate = itemView.findViewById(R.id.text_note_date);
            mNoteBody = itemView.findViewById(R.id.text_note_body);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int noteId = mNotes.get(getAdapterPosition()).getId();
            Intent intent = new Intent(mContext, EditNoteActivity.class);
            intent.putExtra(EXTRA_NOTE_ID, noteId);

            ((Activity) mContext).startActivityForResult(intent, MainActivity.REQUEST_EDIT_NOTE);
        }
    }
}
