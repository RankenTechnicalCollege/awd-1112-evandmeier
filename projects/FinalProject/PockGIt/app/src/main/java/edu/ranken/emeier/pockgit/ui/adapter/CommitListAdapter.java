package edu.ranken.emeier.pockgit.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.CommitViewHolder> {

    // fields
    private Context mContext;
    private List<Commit> mCommits;
    private final LayoutInflater mInflater;

    public CommitListAdapter(Context context, List<Commit> commits) {
        mContext = context;
        mCommits = commits;
        mInflater = LayoutInflater.from(context);
    }

    public void setCommits(List<Commit> commits) {
        mCommits = commits;
        notifyDataSetChanged();
    }

    public Commit getCommitAtPosition(int position) {
        return mCommits.get(position);
    }

    public String getCommitIdAtPosition(int position) {
        return mCommits.get(position).getId();
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_commit_card, parent, false);

        return new CommitViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        if (mCommits != null) {
            Commit commit = mCommits.get(position);

            holder.mTextAuthor.setText(commit.getCommitterName());
            holder.mTextMessage.setText(commit.getCommitMessage());
            holder.mTextDate.setText(commit.getCommitDate());
            Picasso.get().load(commit.getCommitAuthorAvatar()).resize(48, 48).into(holder.mImageAuthorAvatar);
        } else {
            holder.mTextAuthor.setText("");
            holder.mTextMessage.setText("");
            holder.mTextDate.setText("");
            holder.mTextAuthor.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mCommits != null ? mCommits.size() : 0;
    }


    class CommitViewHolder extends RecyclerView.ViewHolder {

        TextView mTextAuthor, mTextMessage, mTextDate;
        ImageView mImageAuthorAvatar;

        CommitViewHolder(View itemView) {
            super(itemView);

            mTextAuthor = itemView.findViewById(R.id.text_commit_author);
            mTextMessage = itemView.findViewById(R.id.text_commit_message);
            mTextDate = itemView.findViewById(R.id.text_commit_date);
            mImageAuthorAvatar = itemView.findViewById(R.id.image_author_avatar);
        }

    }
}
