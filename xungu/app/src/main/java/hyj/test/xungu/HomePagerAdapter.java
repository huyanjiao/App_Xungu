package hyj.test.xungu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentStateAdapter {


    private List<String> mData = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<String> data) {
        super(fragmentActivity);
        mData = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return HomePageFragment2.newInstance(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
