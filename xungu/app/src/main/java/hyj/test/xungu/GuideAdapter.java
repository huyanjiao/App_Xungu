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
class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Guide> mGuideData;
    private Guide current;
    private Context mContext;
    private int count;
    private String tab;
    private Bitmap bmp;


    GuideAdapter(Context context, ArrayList<Guide> GuideData, String tab) {
        this.mGuideData = GuideData;
        this.mContext = context;
        this.count = GuideData.size();
        this.tab = tab;
    }

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
        Guide currentGuide = mGuideData.get(position);
        current = currentGuide;
        // Populate the textviews with data.
        holder.bindTo(currentGuide);

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mGuideData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Guide currentGuide = mGuideData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailGuideActivity.class);

            detailIntent.putExtra("title", currentGuide.gettitle());
            detailIntent.putExtra("info", currentGuide.getinfo());
            Log.d("tt", currentGuide.gettitle());
            detailIntent.putExtra("image_resource",currentGuide.getimg());


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

        void bindTo(Guide currentGuide){

            Glide.with(mContext).load(currentGuide.getimg()).transition((new DrawableTransitionOptions().crossFade())).into(mSportsImage);

            Log.d("resu", String.valueOf(bmp));
            mTitleText.setText(currentGuide.gettitle());
            mNewsTitleText.setText(currentGuide.gettitle());

        }
    }


}
