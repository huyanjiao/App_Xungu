/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hyj.test.xungu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import hyj.test.xungu.R;

import java.util.ArrayList;

/***
 * The adapter class for the RecyclerView, contains the sports data.
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Item> mItemData;
    private Item current;
    private Context mContext;
    private int count;
    private String tab;
    private Bitmap bmp;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param sportsData ArrayList containing the sports data.
     * @param context Context of the application.
     */
    ItemsAdapter(Context context, ArrayList<Item> sportsData, String tab) {
        this.mItemData = sportsData;
        this.mContext = context;
        this.count = sportsData.size();
        this.tab = tab;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {

                return new ViewHolder(LayoutInflater.from(mContext).
                        inflate(R.layout.list_ciqi, parent, false));


    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder,
                                 final int position) {
        // Get current sport.
        Item currentItem = mItemData.get(position);
        current = currentItem;
        // Populate the textviews with data.
        holder.bindTo(currentItem);

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mItemData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
                              implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Item currentItem = mItemData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            String img = "http://82.156.19.110:8080/ForAndroid/img/"+currentItem.getimg()+".jpg";
            detailIntent.putExtra("title", currentItem.gettitle());
            detailIntent.putExtra("info", currentItem.getinfo());
            Log.d("tt", currentItem.gettitle());
            detailIntent.putExtra("image_resource",img);
            Log.d("tt", currentItem.getimg());

            mContext.startActivity(detailIntent);
        }

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mNewsTitleText;
        private ImageView mSportsImage;
        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mNewsTitleText = itemView.findViewById(R.id.newsTitle);

            mSportsImage = itemView.findViewById(R.id.sportsImage);
            itemView.setOnClickListener(this);
        }

        void bindTo(Item currentItem){
            // Populate the textviews with data.
            String path = "http://82.156.19.110:8080/ForAndroid/img/"+currentItem.getimg()+".jpg";
            //getBitmapFromServer(currentItem.getimg());
            Glide.with(mContext).load(path).transition((new DrawableTransitionOptions().crossFade())).into(mSportsImage);

            Log.d("resu", String.valueOf(bmp));
            mTitleText.setText(currentItem.gettitle());
            mNewsTitleText.setText(currentItem.getdynasty());
            Log.d("resu", (String) mTitleText.getText());


            //Log.d("lll",bitmap.getWidth()+" - "+ bitmap.getHeight());
        }
    }


}
