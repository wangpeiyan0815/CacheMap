package com.yan.shopmapwangrr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yan.shopmapwangrr.adapter.Adapter;
import com.yan.shopmapwangrr.bean.Bean;
import com.yan.shopmapwangrr.utils.GlideCacheUtil;
import com.yan.shopmapwangrr.utils.OkHttpUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=10&gender=2&ts=1871746850&page=1";
    private RecyclerView recycler;
    private Button locad;
    private Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        OkHttpUtils okHttpUtils = new OkHttpUtils();
        okHttpUtils.getJson(path, new getData());
    }

    private void intiView() {
        recycler = (RecyclerView) findViewById(R.id.recycler);
        locad = (Button) findViewById(R.id.locad);
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        locad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locad:
                //进行跳转
                Intent intent = new Intent(MainActivity.this,DiTuActivity.class);
                startActivity(intent);
                break;
            case R.id.clear:
                //清除
                clear();
                break;
        }
    }

    //清除操作
    private void clear() {
        /*ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();*/
        GlideCacheUtil.getInstance().clearImageAllCache(MainActivity.this);
        String cacheSize = GlideCacheUtil.getInstance().getCacheSize(MainActivity.this);
        clear.setText("清除缓存\n"+cacheSize);
    }

    private class getData extends OkHttpUtils.HttpCallBack {
        @Override
        public void onError(String meg) {
            super.onError(meg);
            Toast.makeText(MainActivity.this, "数据获取失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSusscess(String data) {
            //拿到数据
            Gson gson = new Gson();
            Bean bean = gson.fromJson(data, Bean.class);
            List<Bean.DataBeanHeadline> data1 =
                    bean.getData();
            //进行适配
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recycler.setLayoutManager(layoutManager);
            Adapter adapter = new Adapter(data1, MainActivity.this);
            recycler.setAdapter(adapter);
            String cacheSize = GlideCacheUtil.getInstance().getCacheSize(MainActivity.this);
         /*   File directory = ImageLoader.getInstance().getDiskCache().getDirectory();
            String file = getFile(directory)*/;
            clear.setText("清除缓存\n"+cacheSize);
        }
    }

    private long num = 0;

    private String getFile(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                getFile(f);
            } else {
                num += f.length();
            }
        }
     return getFormatSize(num);
    }

    /**
     * 格式化单位
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
