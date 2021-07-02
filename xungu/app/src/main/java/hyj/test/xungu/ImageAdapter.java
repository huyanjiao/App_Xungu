package hyj.test.xungu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import hyj.test.xungu.R;

import java.util.List;


class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    List<Integer> mImgs;
    ImageAdapter(Context context, List<Integer> imgs) {
        this.mImgs = imgs;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new ImageViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        Glide.with(imageViewHolder.image)
                .load(mImgs.get(position))
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(10)))
                .into(imageViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return mImgs.size();
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder {

   ImageView image;

    ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.itemImage);
    }

}