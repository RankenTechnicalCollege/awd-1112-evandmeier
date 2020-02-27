package edu.ranken.emeier.mytutor;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.ranken.emeier.mytutor.utils.Article;
import edu.ranken.emeier.mytutor.utils.ArticleAdapter;
import edu.ranken.emeier.mytutor.utils.Utilities;

public class TabFragment extends Fragment {

    private static final String TAG = "TabFragment";

    private ArrayList<Article> mArticleList;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Successfully created TabFragment");

        mArticleList = getArguments().getParcelableArrayList(Utilities.ARTICLE_LIST_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);

        Log.d(TAG, "Inflated fragment_tab layout.");

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mAdapter = new ArticleAdapter(getActivity(), mArticleList);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.grid_column_count)));
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    public ArrayList<Article> getFragmentList() {
        return mArticleList;
    }

    public ArticleAdapter getArticleAdapter() {
        return mAdapter;
    }

    public RecyclerView getFragmentRecycler() {
        return mRecyclerView;
    }
}