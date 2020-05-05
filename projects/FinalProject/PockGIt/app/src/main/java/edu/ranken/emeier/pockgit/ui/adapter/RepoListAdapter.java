package edu.ranken.emeier.pockgit.ui.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.activity.RepositoryDetailsActivity;
import edu.ranken.emeier.pockgit.data.entity.Repo;
import edu.ranken.emeier.pockgit.utils.Utilities;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    // constants
    public static final String EXTRA_REPO_ID = "edu.ranken.emeier.pockgit.EXTRA_REPO_ID";

    // fields
    private Context mContext;
    private List<Repo> mRepos;
    private final LayoutInflater mInflater;
    private PockGItRepository mRepository;

    public RepoListAdapter(Context context, List<Repo> repos) {
        mContext = context;
        mRepos = repos;
        mInflater = LayoutInflater.from(context);
    }

    public void setRepos(List<Repo> repos) {
        mRepos = repos;
        notifyDataSetChanged();
    }

    public Repo getRepoAtPosition(int position) {
        return mRepos.get(position);
    }

    public int getRepoIdAtPosition(int position) {
        return mRepos.get(position).getId();
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_repo_card, parent, false);

        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        if (mRepos != null) {
            Repo repo = mRepos.get(position);

            holder.mTextFullName.setText(repo.getFullName());
            holder.mSwitchCommitNotifications.setChecked(repo.isCommitNotificationsEnabled());
//            holder.mSwitchPullNotifications.setChecked(repo.isPullRequestNotificationsEnabled());
            if (TextUtils.isEmpty(repo.getOwnerAvatar())) {
                holder.mImageAvatar.setImageResource(R.drawable.placeholder);
            } else {
                Picasso.get().load(repo.getOwnerAvatar()).resize(100, 100).into(holder.mImageAvatar);
            }
        } else {
            holder.mTextFullName.setText("");
            holder.mImageAvatar.setVisibility(View.INVISIBLE);
            holder.mSwitchCommitNotifications.setChecked(false);
//            holder.mSwitchPullNotifications.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return mRepos != null ? mRepos.size() : 0;
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextFullName;
        ImageView mImageAvatar;
        Switch mSwitchCommitNotifications; // mSwitchPullNotifications;

        RepoViewHolder(View itemView) {
            super(itemView);

            mTextFullName = itemView.findViewById(R.id.text_full_name);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
            mSwitchCommitNotifications = itemView.findViewById(R.id.switch_commit_notifications);
//            mSwitchPullNotifications = itemView.findViewById(R.id.switch_pull_notifications);

            itemView.setOnClickListener(this);
            mSwitchCommitNotifications.setOnClickListener(this);
//            mSwitchPullNotifications.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Repo repo = mRepos.get(getAdapterPosition());
            PockGitApp app = (PockGitApp) mContext.getApplicationContext();
            PockGItRepository repository = app.getRepository();

            if (v == mSwitchCommitNotifications) {
                repo.setCommitNotificationsEnabled(mSwitchCommitNotifications.isChecked());
                repository.updateRepo(repo);
                //Utilities.showToast(mContext, "Commit switch pressed!");
//            } else if (v == mSwitchPullNotifications) {
//                repo.setPullRequestNotificationsEnabled(mSwitchPullNotifications.isChecked());
//                repository.updateRepo(repo);
//                //Utilities.showToast(mContext, "Pull Req switch pressed!");
            } else {
                int repoId = mRepos.get(getAdapterPosition()).getId();
                Intent intent = new Intent(mContext, RepositoryDetailsActivity.class);
                intent.putExtra(EXTRA_REPO_ID, repoId);

                mContext.startActivity(intent);
            }
        }
    }
}