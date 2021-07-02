package hyj.test.xungu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirstPageAdapter extends FragmentStateAdapter {


    private List<String> mData = new ArrayList<>();

    public FirstPageAdapter(@NonNull FragmentActivity fragmentActivity, List<String> data) {
        super(fragmentActivity);
        mData = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:return new FirstPageFragment();
            case 1:return new FirstPageFragment2();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}
