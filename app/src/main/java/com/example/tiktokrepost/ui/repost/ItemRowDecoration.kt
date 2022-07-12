package com.example.tiktokrepost.ui.repost

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemRowDecoration(
    val spanCount: Int,
    val spacing: Int,
    val includeEdge: Boolean) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        println("position = "+position)
        println("view =" + view)

        val column = position % spanCount
        println("column = "+  column)
        if(includeEdge){
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount){
                outRect.top = spacing
            }
            outRect.bottom = spacing
        }else{
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount){
                outRect.top = spacing
            }
        }
    }
}