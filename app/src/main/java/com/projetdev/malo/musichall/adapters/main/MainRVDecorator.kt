package com.projetdev.malo.musichall.adapters.main

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MainRVDecorator(edgePadding: Int): RecyclerView.ItemDecoration() {
    private var edgePadding: Int = 0


    init {
        this.edgePadding = edgePadding
    }
    /**
     * EdgeDecorator
     * @param edgePadding padding set on the left side of the first item and the right side of the last item
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount

        val itemPosition = parent.getChildAdapterPosition(view)

        // no position, leave it alone
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }

        // first item
        if (itemPosition == 0) {
            outRect.set(view.getPaddingLeft(), edgePadding, view.getPaddingRight(), view.getPaddingBottom())
        } else {
            outRect.set(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom())
        }// every other item
    }
}