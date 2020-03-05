package edu.ranken.emeier.gittogo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ranken.emeier.gittogo.R;
import edu.ranken.emeier.gittogo.models.Repo;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    // fields
    private final Context mContext;
    private final List<Repo> mRepoList;
    private final LayoutInflater mInflater;

    public RepoAdapter(Context context, List<Repo> repoList) {
        mContext = context;
        mRepoList = repoList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.repo_item, parent, false);

        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        // get properties from current Repo
        String repoName = mRepoList.get(position).getName();
        String repoOwner = mRepoList.get(position).getOwner();
        String repoDesc = mRepoList.get(position).getDesc();

        // set the text of the widgets
        holder.mRepoName.setText(repoName);
        holder.mRepoOwner.setText(repoOwner);
        holder.mRepoDesc.setText(repoDesc);
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // widgets
        private TextView mRepoName, mRepoOwner, mRepoDesc;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);

            //bind widgets
            mRepoName = itemView.findViewById(R.id.text_repo_name);
            mRepoOwner = itemView.findViewById(R.id.text_repo_owner);
            mRepoDesc = itemView.findViewById(R.id.text_repo_desc);

            // bind listeners
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // do something
        }
    }
}
