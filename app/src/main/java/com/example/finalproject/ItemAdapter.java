package com.example.finalproject;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private static final String TAG = ItemAdapter.class.getSimpleName();
    private static int viewHolderCount;
    private List<Item> volumeInfoList;

    private int mNumberItems;

    // creating interface for listitemclicklistener, only has 1 method
    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex, View itemView);
    }
    // created
    final private ListItemClickListener myOnClickListener;

    /**
     * ItemAdapter constructor. Note it also takes in the inputList which is the list of books found in FetchBook.java
     * path of booklist: Created in Fetchbook -> MainActicity -> ItemAdapter
     * @param listener
     * @param numberOfItems
     */
    public ItemAdapter(ListItemClickListener listener, int numberOfItems) {
        mNumberItems = numberOfItems;
        viewHolderCount = 0;
        myOnClickListener = listener;
        // copy input list into book list
    }

    public void setVolumeInfo(List<Item> volumeInfoList) {
        this.volumeInfoList = volumeInfoList;
        notifyDataSetChanged();
    }

    /**
     * Method to
     * @param viewGroup
     * @param viewType
     */
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    /**
     * Method to create the individual views in the recycle view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        VolumeInfo volumeInfo = null;
        if(position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        else
            holder.itemView.setBackgroundColor(Color.WHITE);
        Log.d(TAG, "#" + position);
        if(volumeInfoList != null){
            volumeInfo = this.volumeInfoList.get(position).getVolumeInfo();
        }
        holder.bind(position, volumeInfo); // TODO: THIS STATEMENT ADDS TEXT TO THE RECYCLE VIEWS
    }

    /**
     * return max items that can be displayed
     * @return mNumberItems
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Handle the text and onclick of individual items in recycle view, implements interface to do so
     */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;
        // Will display which ViewHolder is displaying this data
        TextView viewHolderIndex;

        /**
         * Constructor that gets the text elements of the items in recycle view
         * @param itemView
         */
        public ItemViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            // calling the setonclicklistener on the view
            itemView.setOnClickListener(this);
        }

        /**
         * Sets the actual texts into the recycle view element, using the booklist element and getting the title
         * @param listIndex
         */
        // TODO: THIS IS WHAT SETS THE TEXT IN RECYCLE VIEW
        void bind(int listIndex, VolumeInfo inputInfo) {
            // if the book list is empty
            if(volumeInfoList == null)listItemNumberView.setText(String.valueOf(listIndex));
            else{
                // if booklist is set
                listItemNumberView.setText(inputInfo.getTitle());
            }
        }

        /**
         * Gives position and also the current view for onclick, handled later in Main Activity
          */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            myOnClickListener.onListItemClick(clickedPosition, v);
        }
    }
}
