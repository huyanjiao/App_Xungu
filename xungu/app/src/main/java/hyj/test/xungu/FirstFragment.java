package hyj.test.xungu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import hyj.test.xungu.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private List<String> mData = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        initData();
        Log.d("result","fff");
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mTabLayout = rootView.findViewById(R.id.first_indicator);
        mViewPager2 = rootView.findViewById(R.id.first_pager);
        FirstPageAdapter firstPageAdapter = new FirstPageAdapter(getActivity(), mData);
        mViewPager2.setAdapter(firstPageAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mData.get(position));
            }
        }).attach();
    }

    private void initData() {
        mData.add("国宝珍品");
        mData.add("博物馆导览");

    }





}
