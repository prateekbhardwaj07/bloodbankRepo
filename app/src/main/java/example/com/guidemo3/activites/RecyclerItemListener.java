package example.com.guidemo3.activites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Prateek on 3/31/2018.
 */

public class RecyclerItemListener implements RecyclerView.OnItemTouchListener {
    private RecyclerTouchListener listener;
    private GestureDetector gestureDetector;

    public interface RecyclerTouchListener{
         void onClickItem(View v, int position) ;
         void onLongClickItem(View v, int position);
    }

    public RecyclerItemListener(Context context, final RecyclerView recyclerView, final RecyclerTouchListener listener)
    {
        this.listener=listener;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                listener.onClickItem(view,recyclerView.getChildAdapterPosition(view));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                listener.onLongClickItem(view,recyclerView.getChildAdapterPosition(view));
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        return (child != null && gestureDetector.onTouchEvent(e));
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }




}
