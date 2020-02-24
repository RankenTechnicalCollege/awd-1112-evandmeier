package edu.ranken.emeier.mytutor;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TabFragment extends Fragment {

    private ArrayList<Article> mArticleList;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Article> articles = getArguments().getParcelableArrayList("articles");
        String topic = getArguments().getString("topic");

        mArticleList = returnListByTopic(articles, topic);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mAdapter = new ArticleAdapter(getActivity(), mArticleList);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_column_count)));
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public static ArrayList<Article> returnListByTopic(ArrayList<Article> list, String topic) {
        ArrayList<Article> newList = new ArrayList<>();

        for (Article article : list) {
            if (topic.equalsIgnoreCase(article.getTopic())) {
                newList.add(article);
            }
        }

        return newList;
    }
}
