package edu.ranken.emeier.roomwordsamplelive.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.emeier.roomwordsamplelive.R;
import edu.ranken.emeier.roomwordsamplelive.data.entitiy.Word;
import edu.ranken.emeier.roomwordsamplelive.ui.activity.NewWordActivity;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mItems;
    private Context mContext;

    private final LayoutInflater mInflater;

    public WordListAdapter(Context context, List<Word> items) {
        mItems = items;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setItems(List<Word> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_word, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        if (mItems != null) {
            Word item = mItems.get(position);
            holder.textView.setText(item.getWord());
        } else {
            holder.textView.setText("");
        }
    }

    public Word getWordAtPosition(int position) {
        return mItems.get(position);
    }

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public WordViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Word word = mItems.get(getAdapterPosition());
            Intent intent = new Intent(mContext, NewWordActivity.class);
            intent.putExtra("wordId", word.getId());
            intent.putExtra("word", word.getWord());
            mContext.startActivity(intent);
        }
    }
}
