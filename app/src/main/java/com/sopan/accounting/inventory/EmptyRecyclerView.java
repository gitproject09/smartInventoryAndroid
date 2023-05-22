package com.sopan.accounting.inventory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

/**
 * EmptyRecyclerView is RecyclerView subclass that provides empty view support for RecyclerView
 * to show or hide an empty view based on whether the adapter provided to the RecyclerView has
 * data or not.
 */

public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;

    /**
     * The AdapterDataObserver calls checkIfEmpty() method every time, and it observes
     * an event that changes the content of the adapter
     */
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Checks if both mEmptyView and adapter are not null.
     * Hide or show mEmptyView depending on the size of the data(item count) in the adapter.
     */
    private void checkIfEmpty() {
        // If the item count provided by the adapter is equal to zero, make the empty View visible
        // and hide the EmptyRecyclerView.
        // Otherwise, hide the empty View and make the EmptyRecyclerView visible.
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    /**
     * Set an empty layout on the EmptyRecyclerView
     * @param relativeLayout refers to the empty state of the relative layout
     */
    public void setEmptyLayout(RelativeLayout relativeLayout) {
        mEmptyView = relativeLayout;
        checkIfEmpty();
    }
}
