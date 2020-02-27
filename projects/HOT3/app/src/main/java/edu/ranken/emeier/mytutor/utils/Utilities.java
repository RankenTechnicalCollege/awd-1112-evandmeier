package edu.ranken.emeier.mytutor.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.ranken.emeier.mytutor.R;
import edu.ranken.emeier.mytutor.TabFragment;

public class Utilities {

    public static String ARTICLE_LIST_EXTRA = "edu.ranken.emeier.mytutor.ArticleListExtra";

    public static ArrayList<Article> initializeArticleList(Context context) {
        ArrayList<Article> list = new ArrayList<>();

        // retrieve all data from the strings.xml file
        String[] titleArray = context.getResources().getStringArray(R.array.article_titles);
        String[] authorArray = context.getResources().getStringArray(R.array.article_authors);
        String[] topicArray = context.getResources().getStringArray(R.array.article_topics);
        String[] linkArray = context.getResources().getStringArray(R.array.article_links);
        String[] dateStringArray = context.getResources().getStringArray(R.array.article_dates);

        // create array of Calendar objects (to match Article constructor)
        Calendar[] dateArray = new Calendar[dateStringArray.length];
        for (int i = 0; i < dateStringArray.length; i++) {
            // convert date string to Calendar object
            dateArray[i] = parseDateString(dateStringArray[i]);

            // add new article to ArrayList
            list.add(new Article(
                    titleArray[i],
                    authorArray[i],
                    topicArray[i],
                    linkArray[i],
                    dateArray[i]
            ));
        }

        return list;
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

    public static TabFragment createTabFragment(ArrayList<Article> articles) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARTICLE_LIST_EXTRA, articles);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static void addArticleToFragment(TabFragment fragment, Article article) {
        ArrayList<Article> list = fragment.getFragmentList();

        list.add(article);
        fragment.getArticleAdapter().notifyItemInserted(list.size());
        fragment.getFragmentRecycler().smoothScrollToPosition(list.size());
    }

    public static Calendar parseDateString(String date) {
        // convert date string to calendar object
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date dateObject;

        try {
            dateObject = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            dateObject = new Date();
        }
        calendar.setTime(dateObject);

        return calendar;
    }

    public static void showAlert(Context context, String title, String message) {
        // create alert dialog
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        // set OnClickListener
        alertDialog.setPositiveButton("Ok", (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });

        alertDialog.show();
    }

    public static void displaySnackbar(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
