package com.example.todolist.utils

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

 class SwipeGesture(val context:Context, swipeDirs: Int = 0,
                    dragDirs: Int = ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT)
     : ItemTouchHelper.SimpleCallback(swipeDirs, dragDirs) {

     lateinit var leftSwipeListener: (position: Int) -> Unit?
     lateinit var rightSwipeListener : (position: Int) -> Unit?
     lateinit var isCompleteListener : (position: Int) -> Boolean


     override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
     }

     override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position  = viewHolder.absoluteAdapterPosition
         Log.d("Item_text", position.toString())
         when (direction) {
             ItemTouchHelper.RIGHT ->
                 rightSwipeListener(position)
             ItemTouchHelper.LEFT ->
                 leftSwipeListener(position)
             }

     }

     override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(ContextCompat.getColor(context, R.color.delete_swipe_color))
            .addSwipeLeftActionIcon(R.drawable.delete_swipe_icon)
            .addSwipeRightBackgroundColor(ContextCompat.getColor(context, R.color.done_swipe_color))
            .addSwipeRightActionIcon(R.drawable.done_icon)
            .create()
            .decorate();

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


}