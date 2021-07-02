package hyj.test.xungu;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import hyj.test.xungu.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    private FragmentManager mFragmentManager;

    private Fragment[] fragments;
    private int lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationView = findViewById(R.id.main_navigation_bar);
        initFragment();
        initListener();
    }

    private void initFragment() {
        FirstFragment mFirstFragment = new FirstFragment();
        HomeFragment mHomeFragment = new HomeFragment();
        MiddleFragment mMiddleFragment = new MiddleFragment();
        MineFragment mMineFragment = new MineFragment();
        fragments = new Fragment[]{mFirstFragment,mHomeFragment,  mMiddleFragment, mMineFragment};
        mFragmentManager = getSupportFragmentManager();
        //默认显示HomeFragment
        mFragmentManager.beginTransaction()
                .replace(R.id.main_page_controller, mFirstFragment)
                .show(mFirstFragment)
                .commit();
    }

    private void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.first:
                        if (lastFragment != 0) {
                            MainActivity.this.switchFragment(lastFragment, 0);
                            lastFragment = 0;
                        }
                        return true;
                    case R.id.home:
                        if (lastFragment != 1) {
                            MainActivity.this.switchFragment(lastFragment, 1);
                            lastFragment = 1;
                        }
                        return true;
                    case R.id.plan:
                        if (lastFragment != 2) {
                            MainActivity.this.switchFragment(lastFragment, 2);
                            lastFragment = 2;
                        }
                        return true;

                    case R.id.mine:
                        if (lastFragment != 3) {
                            MainActivity.this.switchFragment(lastFragment, 3);
                            lastFragment = 3;
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.hide(fragments[lastFragment]);
        if (!fragments[index].isAdded()){
            transaction.add(R.id.main_page_controller,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
