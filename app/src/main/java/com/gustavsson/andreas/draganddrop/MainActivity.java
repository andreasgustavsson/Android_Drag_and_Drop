package com.gustavsson.andreas.draganddrop;

import android.content.ClipData;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage1).setOnDragListener(new MyChildDragListener());
        findViewById(R.id.myimage2).setOnDragListener(new MyChildDragListener());
        findViewById(R.id.myimage3).setOnDragListener(new MyChildDragListener());
        findViewById(R.id.myimage4).setOnDragListener(new MyChildDragListener());
        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());

    }

    private static final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.performClick();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
//                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }


    // In case we drop on a CHILD of a linearlayout, then this is used
    class MyChildDragListener implements OnDragListener {
//        Drawable enterShape = getResources().getDrawable(
//                R.drawable.shape_droptarget);
//        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
//                    View view = (View) event.getLocalState();
//                    ViewGroup owner = (ViewGroup) view.getParent();
//                    owner.removeView(view);
//                    LinearLayout container = (LinearLayout) v;

                    // Find out where to place the view
//                    Rect containerRect = new Rect();
//                    container.getDrawingRect(containerRect);
//                    System.out.println("Dropped in layout rect: " + containerRect);
//                    int containerHeight = container.getHeight();


//                    System.out.println("DragEvent x,y: " + event.getX() + "," + event.getY());
//                    System.out.println("DragEvent: " + event);

//                    container.addView(view); // TODO: Change to specific drop position!
//                    view.setVisibility(View.VISIBLE);
//                    v.invalidate();
                    int viewHeight = v.getHeight();
                    float dropY = event.getY();
                    boolean insertAfter = dropY > viewHeight / 2;
                    System.out.println("Dropped at View, should insert after: " + insertAfter);
                    View viewToInsert = (View) event.getLocalState();
                    // Dropped on itself?
                    if (v == viewToInsert) {
                        v.setVisibility(View.VISIBLE);
                        return true;
                    }
                    ViewGroup viewToInsertOwner = (ViewGroup) viewToInsert.getParent();
                    ViewGroup viewParent = (LinearLayout) v.getParent();
                    boolean viewsAreOwnedByTheSameContainer = viewToInsertOwner == viewParent;
                    int indexOfView = viewParent.indexOfChild(v);
                    int insertAtIndex = indexOfView;
                    if (insertAfter) {
                        insertAtIndex++;
                    }
                    // Moved inside the same container?
                    if (viewsAreOwnedByTheSameContainer) {
                        int originalIndexOfViewToInsert = viewToInsertOwner.indexOfChild(viewToInsert);
                        if (originalIndexOfViewToInsert == insertAtIndex) {
                            return true;
                        }
                        // The view-to-insert will be removed from the container before it is inserted - we must adapt the index of where to insert it...
                        if (originalIndexOfViewToInsert < insertAtIndex) {
                            insertAtIndex--;
                        }
                    }
                    viewToInsertOwner.removeView(viewToInsert);
                    viewParent.addView(viewToInsert, insertAtIndex);
                    viewToInsert.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackground(normalShape);
                default:
                    break;
            }
            return true;
        }
    }



    // In case we drop on the linearlayout, then this is used
    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackground(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackground(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;

                    // Find out where to place the view
//                    Rect containerRect = new Rect();
//                    container.getDrawingRect(containerRect);
//                    System.out.println("Dropped in layout rect: " + containerRect);
//                    int containerHeight = container.getHeight();


//                    System.out.println("DragEvent x,y: " + event.getX() + "," + event.getY());
//                    System.out.println("DragEvent: " + event);

                    container.addView(view); // TODO: Change to specific drop position!
                    view.setVisibility(View.VISIBLE);
                    v.invalidate();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackground(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}