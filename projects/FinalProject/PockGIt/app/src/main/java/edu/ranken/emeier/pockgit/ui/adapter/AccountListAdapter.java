package edu.ranken.emeier.pockgit.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.entity.Account;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.activity.AccountHomeActivity;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {

    // constants
    public static final String EXTRA_USER_LOGIN = "edu.ranken.emeier.pockgit.EXTRA_USER_LOGIN";

    // fields
    private Context mContext;
    private List<Account> mAccounts;
    private final LayoutInflater mInflater;

    public AccountListAdapter(Context context, List<Account> accounts) {
        mContext = context;
        mAccounts = accounts;
        mInflater = LayoutInflater.from(context);
    }

    public void setAccounts(List<Account> accounts) {
        mAccounts = accounts;
        notifyDataSetChanged();
    }

    public Account getAccountAtPosition(int position) {
        return mAccounts.get(position);
    }

    public int getAccountIdAtPosition(int position) {
        return mAccounts.get(position).getId();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_account_card, parent, false);

        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        if (mAccounts != null) {
            Account account = mAccounts.get(position);

            // bind the account attributes to the view
            holder.mAccountName.setText(account.getName());
            holder.mAccountLogin.setText(account.getLogin());
            if (TextUtils.isEmpty(account.getAvatarUrl())) {
                holder.mAccountAvatar.setImageResource(R.drawable.placeholder);
            } else {
                Picasso.get().load(account.getAvatarUrl()).resize(100, 100).into(holder.mAccountAvatar);
            }
        } else {
            holder.mAccountName.setText("");
            holder.mAccountLogin.setText("");
            holder.mAccountAvatar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mAccounts != null ? mAccounts.size() : 0;
    }

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mAccountName, mAccountLogin;
        ImageView mAccountAvatar;
        Button mUnlinkAccount;

        AccountViewHolder(View itemView) {
            super(itemView);

            mAccountAvatar = itemView.findViewById(R.id.image_avatar);
            mAccountName = itemView.findViewById(R.id.text_name);
            mAccountLogin = itemView.findViewById(R.id.text_login);
            mUnlinkAccount = itemView.findViewById(R.id.button_unlink_account);

            mUnlinkAccount.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Account account = mAccounts.get(getAdapterPosition());
            PockGitApp app = (PockGitApp) mContext.getApplicationContext();
            PockGItRepository repository = app.getRepository();

            if (v == mUnlinkAccount) {
                AlertDialog alert = new AlertDialog.Builder(mContext)
                        .setCancelable(true)
                        .setMessage("Are you sure you want to unlink this account?")
                        .setPositiveButton("Yes", (DialogInterface dialog, int which) -> {
                            repository.unlinkAccount(account);
                        })
                        .setNegativeButton("No", (DialogInterface dialog, int which) -> {
                            // do nothing
                        })
                        .create();

                alert.show();
            } else {
                Intent intent = new Intent(mContext, AccountHomeActivity.class);
                intent.putExtra(EXTRA_USER_LOGIN, mAccountLogin.getText().toString());

                mContext.startActivity(intent);
            }
        }
    }
}
