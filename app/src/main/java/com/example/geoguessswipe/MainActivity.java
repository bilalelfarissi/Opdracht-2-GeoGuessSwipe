package com.example.geoguessswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /**
     * Binding
     */
    @BindView(R.id.descriptionTextView)
    TextView descriptionTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    /**
     * Creating an Adapter and a List
     */
    private GeoImageAdapter geoImageAdapter;
    private ArrayList<GeoImage> geoImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind all views with the ButterKnife library which uses the annotations
        ButterKnife.bind(this);

        geoImages = new ArrayList<>();
        getDrawables();

        geoImageAdapter = new GeoImageAdapter(geoImages);
        recyclerView.setAdapter(geoImageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        createSwipe();
    }

    /**
     * This method pics every single image in te drawable folder where the name contains the word img.
     *
     */
    public void getDrawables(){
        Field[] drawablesFields = com.example.geoguessswipe.R.drawable.class.getFields();
        for (Field drawablesField : drawablesFields) {
            try {
                if (drawablesField.getName().contains("img")) {
                    if(drawablesField.getName().contains("yes")) {
                        geoImages.add(new GeoImage(getResources().getDrawable(drawablesField.getInt(null)), true));
                    } else if(drawablesField.getName().contains("no")) {
                        geoImages.add(new GeoImage(getResources().getDrawable(drawablesField.getInt(null)), false));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createSwipe() {
        /** Add a touch helper to the RecyclerView to recognize when a user swipes to delete a list entry.
         *  An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         *  and uses callbacks to signal when a user is performing these actions.
        */
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    /**
                     * Called when a user swipes left or right on a ViewHolder
                     */
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        //check if image is from Europe or not and if it's swiped to the correct side
                        if(swipeDir == ItemTouchHelper.LEFT && geoImages.get(position).isEurope()) {
                            //show notification when its true
                            Toast.makeText(getApplicationContext(),"Correct!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //show notification when its false
                            Toast.makeText(getApplicationContext(),"Too bad!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //remove image from list
                        geoImages.remove(position);
                        //notify the adapter of the removed image
                        geoImageAdapter.notifyItemRemoved(position);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
