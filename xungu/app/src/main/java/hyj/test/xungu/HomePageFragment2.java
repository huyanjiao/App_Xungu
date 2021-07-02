package hyj.test.xungu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import hyj.test.xungu.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomePageFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private TextView textView;
    private RecyclerView mRecyclerView;
    private ArrayList<Item> mItemData;
    private ItemsAdapter mAdapter;
    private String result;
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



    public static HomePageFragment2 newInstance(String param1) {
        HomePageFragment2 fragment = new HomePageFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    String kind = mParam1;
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
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL);
        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(manager);

        // Initialize the ArrayList that will contain the data.
        mItemData = new ArrayList<>();
        String[] string = new String[1];

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new ItemsAdapter(this.getContext(), mItemData,mParam1);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
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



}