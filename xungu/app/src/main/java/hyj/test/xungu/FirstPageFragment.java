package hyj.test.xungu;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import hyj.test.xungu.R;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;
import com.to.aboomy.pager2banner.ScaleInTransformer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FirstPageFragment extends Fragment {


    private TextView textView;
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mItemData;
    private ArrayList<Integer> mImg = new ArrayList<Integer>();
    private ItemsAdapter mAdapter;
    private String result;
    private String mParam1 ="国宝";
    public static final int HEADER_RECYCLER_VIEW_ITEM = 0;
    // 当前的条目是普通recyclerView的条目
    public static final int NORMAL_RECYCLER_VIEW_ITEM = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:getData(result);

                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        new Thread() {
            @Override
            public void run() {
                try {
                    String kind = "国宝";
                    Log.d("result",kind);
                    String path = "http://82.156.19.110:8080/ForAndroid/ShowItemServlet?kind=" + kind ;
                    URL url = new URL(path);
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        InputStream is = connection.getInputStream();
                        Log.d("re", String.valueOf(is));
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        while ((len = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, len);
                        }
                        result = baos.toString();
                        handler.sendEmptyMessage(0);
                        Log.d("result",baos.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("result", String.valueOf(e));
                }
            }
        }.start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_page, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        Banner banner = rootView.findViewById(R.id.banner);

        //使用内置Indicator
        IndicatorView indicator = new IndicatorView(getActivity())
                .setIndicatorColor(Color.DKGRAY)
                .setIndicatorSelectorColor(Color.WHITE)
                .setIndicatorRatio(1f) //ratio，默认值是1 ，也就是说默认是圆点，根据这个值，值越大，拉伸越长，就成了矩形，小于1，就变扁了呗
                .setIndicatorRadius(2f) // radius 点的大小
                .setIndicatorSelectedRatio(3)
                .setIndicatorSelectedRadius(2f)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_BIG_CIRCLE);

        //创建adapter
        ImageAdapter adapter = new ImageAdapter(this.getContext(),mImg);
        initializeData();
        //传入RecyclerView.Adapter 即可实现无限轮播
        banner.setAutoPlay(true)
                .setIndicator(indicator)
                .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
                .setPagerScrollDuration(800)
                .setPageMargin(10, 0)
                .addPageTransformer(new ScaleInTransformer())
                .setOuterPageChangeListener(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        Log.e("aa", "onPageSelected position " + position);
                    }
                })
                .setAdapter(adapter);


        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 2);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(manager);
        // 设置布局管理一条数据占用几行，如果是头布局则头布局自己占用一行
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int postion) {
                return 2;
            }
        });
        // Initialize the ArrayList that will contain the data.
        mItemData = new ArrayList<>();
        String[] string = new String[1];

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ItemsAdapter(this.getContext(), mItemData,mParam1);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    public void getData(String result ) {
        String[] results = result.split(";");

        for(int i = 0; i < results.length; ++i) {
            String[] item = results[i].split(",");
            String title = item[0];
            String dynasty = item[1];
            String kind = item[2];
            String info = item[3];
            String img = item[4];
            String tag = "item";
            Log.d(tag, item[0]);
            mItemData.add(new Item(title,dynasty,kind,info,img));
            Log.d("result", String.valueOf(item[1]));
        }

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
    public void initializeData() {

        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.item_image);
        for (int i = 0; i <7; i++)
            mImg.add(sportsImageResources.getResourceId(i, 0));

    }





}

