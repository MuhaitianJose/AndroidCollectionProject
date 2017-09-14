package com.muhaitian.mvplibrary.recycler.helper;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


/**
 * Created by muhaitian on 2017/9/14.
 */

public class RecyclerViewHelper {
    public RecyclerViewHelper() {
        throw new AssertionError();
    }

    public static void initRecyclerViewV() {

    }

    public static void initRecyclerView(Context context, RecyclerView recyclerView, boolean isDivided,
                                        RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (isDivided) {
            recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        }
        recyclerView.setAdapter(adapter);
    }
}
