package edu.ranken.emeier.mytutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private final Context mContext;
    private final List<Article> mArticleList;
    private final LayoutInflater mInflater;

    public ArticleAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mArticleList = articleList;
        mInflater = LayoutInflater.from(context);
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
        String articleTitle = mArticleList.get(position).getTitle();
        String articleAuthor = mArticleList.get(position).getAuthor();

        // convert the calendar object to a formatted string
        Calendar articleDate = mArticleList.get(position).getPublishedDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateString = dateFormat.format(articleDate.getTime());

        // set the text of the widgets
        holder.mArticleTitle.setText(articleTitle);
        holder.mArticleAuthor.setText(articleAuthor);
        holder.mArticleDate.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mArticleTitle, mArticleAuthor, mArticleDate;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);

            mArticleTitle = itemView.findViewById(R.id.text_title);
            mArticleAuthor = itemView.findViewById(R.id.text_author);
            mArticleDate = itemView.findViewById(R.id.text_date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // implicit intent to open browser
        }
    }
}
