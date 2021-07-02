package hyj.test.xungu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import hyj.test.xungu.R;

import java.util.ArrayList;
import java.util.List;

public class MineFragment extends Fragment {

    TextView name1;
    private List<String> mData = new ArrayList<>();
    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("name","");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        name1 = rootView.findViewById(R.id.myname);
        name1.setText(userId);

        return rootView;
    }
}