package hyj.test.xungu;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hyj.test.xungu.R;

import java.util.ArrayList;


public class FirstPageFragment2 extends Fragment {


    private TextView textView;
    private RecyclerView mRecyclerView;
    private ArrayList<Guide> mGuideData;
    private GuideAdapter mAdapter;
    private String result;
    private String mParam1 ="博物馆";
    public static final int HEADER_RECYCLER_VIEW_ITEM = 0;
    // 当前的条目是普通recyclerView的条目
    public static final int NORMAL_RECYCLER_VIEW_ITEM = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_page2, container, false);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
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
        mGuideData = new ArrayList<>();
        String[] string = new String[1];
        initializeData();
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new GuideAdapter(this.getContext(), mGuideData,mParam1);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
    private void initializeData() {
        // Get the resources from the XML file.
        String[] guideList = getResources()
                .getStringArray(R.array.guide_titles);
        String[] guideInfo = getResources()
                .getStringArray(R.array.guide_info);
        TypedArray guideImageResources =
                getResources().obtainTypedArray(R.array.guide_images);
        // Clear the existing data (to avoid duplication).
        mGuideData.clear();

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.

        for (int i = 0; i < guideList.length; i++) {
            mGuideData.add(new Guide(guideList[i], guideInfo[i],
                    guideImageResources.getResourceId(i, 0)));
        }
        guideImageResources.recycle();
        // Notify the adapter of the change.

    }

}