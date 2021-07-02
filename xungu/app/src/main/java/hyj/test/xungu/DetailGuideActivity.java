package hyj.test.xungu;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import hyj.test.xungu.R;

public class DetailGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        TextView sportsTitle = findViewById(R.id.titleDetail);
        TextView itemTitle = findViewById(R.id.newsTitleDetail);
        TextView iteminfo = findViewById(R.id.subTitleDetail);
        ImageView itemImage = findViewById(R.id.sportsImageDetail);

        sportsTitle.setText(getIntent().getStringExtra("title"));
        itemTitle.setText(getIntent().getStringExtra("title"));
        iteminfo.setText(getIntent().getStringExtra("info"));

        Glide.with(this).load((getIntent().getIntExtra("image_resource",0))).transition((new DrawableTransitionOptions().crossFade())).into(itemImage);
        //Bitmap bitmap=getIntent().getParcelableExtra("image_resource");
        Log.d("detial",getIntent().getStringExtra("title"));

        // itemImage.setImageBitmap(bitmap);
    }
}