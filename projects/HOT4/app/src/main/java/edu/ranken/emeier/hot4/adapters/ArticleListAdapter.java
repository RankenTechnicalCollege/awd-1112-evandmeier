package edu.ranken.emeier.hot4.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.ranken.emeier.hot4.MainActivity;
import edu.ranken.emeier.hot4.R;
import edu.ranken.emeier.hot4.models.Article;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    // constants
    private final String TAG = MainActivity.class.getSimpleName();

    // fields
    private final Context mContext;
    private List<Article> mArticleList;
    private final LayoutInflater mInflater;

    public ArticleListAdapter(Context context) {
        this(context, new ArrayList<>());
    }

    public ArticleListAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mArticleList = articleList;
        mInflater = LayoutInflater.from(context);
    }

    public void setArticles(List<Article> articles) {
        mArticleList = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_article, parent, false);

        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // get properties from current Article
        Article article = mArticleList.get(position);
        String articleInfo = String.format("%s\t\t%s", article.getDatePublished().substring(0, 10), article.getAuthorName());
        String summary;

        int intendedLength = mContext.getResources().getInteger(R.integer.summary_length);
        // setup the summary
        if (article.getSummary().length() < intendedLength) {
            summary = article.getSummary();
        } else {
            summary = article.getSummary().substring(0, intendedLength) + "...";
        }

        // set the values of the widgets
        holder.mArticleInfo.setText(articleInfo);
        holder.mArticleTitle.setText(article.getTitle());
        holder.mArticleSummary.setText(summary);
        Picasso.get().load(article.getImageUrl()).placeholder(R.drawable.ic_loading).resize(100, 100).into(holder.mArticleImage);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mArticleImage;
        private TextView mArticleInfo, mArticleTitle, mArticleSummary;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            mArticleImage = itemView.findViewById(R.id.article_image);
            mArticleInfo = itemView.findViewById(R.id.article_info);
            mArticleTitle = itemView.findViewById(R.id.article_title);
            mArticleSummary = itemView.findViewById(R.id.article_summary);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, String.format("Clicked on '%s' article.", mArticleTitle.getText().toString()));

            int index = getLayoutPosition();

            String url = mArticleList.get(index).getArticleUrl();
            Uri webpage = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            } else {
                Log.d(TAG, "Failed to open specified web page!");
            }
        }
    }
}
