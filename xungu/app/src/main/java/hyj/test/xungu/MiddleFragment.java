package hyj.test.xungu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import hyj.test.xungu.R;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

public class MiddleFragment extends Fragment {

    private ImageView img;
    private int RESULT_LOAD_IMG = 1;
    private String encodedString="1";
    private Bitmap bitmap;
    private String imgPath;
    private Button submit;

    private EditText Picture_GrowExpre;
    private EditText Picture_DiseaseExpre;
    private EditText Picture_AbnormalExpre;


    //下面是拍照
    private int SELECT_PIC_BY_TACK_PHOTO = 99;
    private int numimg = 1000;
    private Uri photoUri;


    EditText editkind;
    EditText edittitle;
    EditText editdynasty;
    EditText editinfo;
    private ProgressDialog prgDialog;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private String kind;
    private List<String> mData = new ArrayList<>();
    String albumPath = "";
    //用来转换相机路径用的

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_middle, container, false);
        prgDialog= new ProgressDialog(getActivity());
        prgDialog.setCancelable(false);
        edittitle=rootView.findViewById(R.id.title);
        editdynasty=rootView.findViewById(R.id.dynasty);
        editinfo=rootView.findViewById(R.id.info);
        submit=rootView.findViewById(R.id.submit);
        editkind=rootView.findViewById(R.id.kind);
        editkind.setInputType(InputType.TYPE_NULL);
        img= rootView.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicSelectDialog();
            }
        });
        editkind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();

            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String title = edittitle.getText().toString().trim();
                final String dynasty = editdynasty.getText().toString().trim();
                final String kind = editkind.getText().toString().trim();
                final String info = editinfo.getText().toString().trim();
                uploadimg(getContext(),imgPath);
                if(check()) {
                    //调用Java后台登录接口
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                String path = "http://82.156.19.110:8080/ForAndroid/InsertItemServlet";
                                uploadimg(getContext(),imgPath);
                                URL url = new URL(path);
                                Log.d("iii", String.valueOf(url));
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);//设置这个连接是否可以写入数据
                                connection.setDoOutput(true);
                                connection.setRequestMethod("POST");
                                connection.setRequestProperty("Connection", "Keep-Alive");
                                connection.setRequestProperty("Charset", "UTF-8");
                                //text/plain能上传纯文本文件的编码格式
                                connection.setRequestProperty("Content-Type", "text/plain");
                                connection.connect();
                                String out = title + ","+ dynasty +","+ kind + ","+ info;
                                Log.d("iii",out);
                                DataOutputStream outs = new DataOutputStream(connection.getOutputStream());//输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
                               //创建字符流对象并用高效缓冲流包装它，便获得最高的效率,发送的是字符串推荐用字符流，其它数据就用字节流
                                outs.write(out.getBytes("utf-8"));//把json字符串写入缓冲区中
                                outs.flush();//刷新缓冲区，把数据发送出去，这步很重要
                                outs.close();

                                int responseCode = connection.getResponseCode();
                                Log.d("iii", String.valueOf(responseCode));
                                if (responseCode == 200) {
                                    InputStream is = connection.getInputStream();
                                    Log.d("re", String.valueOf(is));
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[1024];
                                    int len = -1;
                                    while ((len = is.read(buffer)) != -1) {
                                        baos.write(buffer, 0, len);
                                    }
                                    final String result = baos.toString();
                                    Log.d("re", result);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (result.equals("1")) {
                                                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                                                edittitle.setText(null);
                                                editdynasty.setText(null);
                                                editkind.setText(null);
                                                editinfo.setText(null);
                                                img.setImageResource(R.drawable.addimg);
                                            } else {
                                                Toast.makeText(getActivity(), "发布失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

            }


        });

        return rootView;
    }
    private void showPickerView() {
//      要展示的数据
        OptionPicker picker = new OptionPicker(getActivity(), new String[]{
                "国宝","瓷器", "玉器", "书画", "金器", "青铜", "饰品","其他"
        });
        picker.setOffset(2);
        picker.setSelectedIndex(1);
        picker.setTextSize(15);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(String item) {
                editkind.setText(item);

            }
        });
        picker.show();

    }
    private void showPicSelectDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(getActivity());
        //2、设置布局
        View view = View.inflate(getActivity(), R.layout.picdialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                takephoto();
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= 23) {
                    int REQUEST_CODE_CONTACT = 101;
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    //验证是否许可权限
                    for (String str : permissions) {
                        if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限
                            getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);

                        }else{
                            loadImage();
                        }
                    }
                }

            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }



    public Boolean check(){
        boolean isok=false;
        String title1=edittitle.getText().toString();
        if(title1.isEmpty()){
            Toast.makeText(getActivity(),"名称不能为空",Toast.LENGTH_LONG).show();
            return isok;
        }

        String dynasty=editdynasty.getText().toString();
        if(dynasty.isEmpty()){
            Toast.makeText(getActivity(),"朝代不能为空",Toast.LENGTH_LONG).show();
            return isok;
        }

        String kind1=editkind.getText().toString();
        if(kind1.isEmpty()){
            Toast.makeText(getActivity(),"种类不能为空",Toast.LENGTH_LONG).show();
            return isok;
        }
        String info1=editinfo.getText().toString();
        if(info1.isEmpty()){
            Toast.makeText(getActivity(),"详细信息不能为空",Toast.LENGTH_LONG).show();
            return isok;
        }
        return true;
    }
    public void takephoto() {

        //TODO 设置读写权限
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED))
        {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            } else {
                //权限已经被授予，在这里直接写要执行的相应方法即可
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"

                ContentValues values = new ContentValues();
                photoUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Log.d("iii", String.valueOf(photoUri));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                Log.d("iii", String.valueOf(intent));
                startActivityForResult(intent,SELECT_PIC_BY_TACK_PHOTO);
            }
        }else{
            Toast.makeText(getActivity(),"内存卡不存在", Toast.LENGTH_LONG).show();
        }

    }

    public void loadImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Log.d("iii", String.valueOf(galleryIntent));
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    //当图片被选中的返回结果
    @SuppressLint("RestrictedApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("iii", String.valueOf(resultCode));
        Log.d("iii", String.valueOf(requestCode));
        try {
            //这是从相机选择
            if (requestCode == RESULT_LOAD_IMG &&  null != data ) {
                Uri selectedImage = data.getData();
                Log.d("iii", String.valueOf(data.getData()));
                img.setImageURI(selectedImage);
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // 获取游标
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                Log.d("iii", String.valueOf(imgPath));
                String[] it = imgPath.split("/");

                //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                if(Integer.parseInt(Build.VERSION.SDK) < 14)
                {
                    //只有4.0以下才需要手动关闭
                    cursor.close();
                }
                //Glide.with(getActivity()).load(imgPath).transition((new DrawableTransitionOptions().crossFade())).into(img);
            }
            //这是用相机拍照
            if (requestCode == SELECT_PIC_BY_TACK_PHOTO ) {
                Log.d("iii", String.valueOf(MediaStore.Images.Media.DATA));
                String[] pojo = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().managedQuery(photoUri, pojo, null, null,null);
                cursor.moveToFirst();
                if(cursor != null )
                {
                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                    String picPath="";
                    imgPath = cursor.getString(columnIndex);
                    Log.d("iii", String.valueOf(imgPath));
                    //4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
                    if(Integer.parseInt(Build.VERSION.SDK) < 14)
                    {
                        //只有4.0以下才需要手动关闭
                        cursor.close();
                    }

                    Glide.with(getActivity()).load(imgPath).transition((new DrawableTransitionOptions().crossFade())).into(img);
                } else{
                    Toast.makeText(getActivity(), "选择图片文件不正确", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    //开始上传图片


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (prgDialog != null) {
            prgDialog .dismiss();
        }
    }
    public void uploadimg(Context context, String oldFilePath){

        try {
            URL url = new URL("http://82.156.19.110:8080/ForAndroid/SaveimgServlet");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            // 允许Input、Output，不使用Cache
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            con.setConnectTimeout(50000);
            con.setReadTimeout(50000);
            // 设置传送的method=POST
            con.setRequestMethod("POST");
            //在一次TCP连接中可以持续发送多份数据而不会断开连接
            con.setRequestProperty("Connection", "Keep-Alive");
            //设置编码
            con.setRequestProperty("Charset", "UTF-8");
            //text/plain能上传纯文本文件的编码格式
            con.setRequestProperty("Content-Type", "image/jpeg");

            // 设置DataOutputStream
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());

            // 取得文件的FileInputStream
            FileInputStream fStream = new FileInputStream(oldFilePath);
            // 设置每次写入1024bytes
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int length = -1;
            // 从文件读取数据至缓冲区
            while ((length = fStream.read(buffer)) != -1) {
                // 将资料写入DataOutputStream中
                ds.write(buffer, 0, length);
            }
            ds.flush();
            fStream.close();
            ds.close();
            if(con.getResponseCode() == 200){
                Log.d("文件上传成功！上传文件为：" ,oldFilePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("报错信息toString：" ,e.toString());
        }
    }

}


