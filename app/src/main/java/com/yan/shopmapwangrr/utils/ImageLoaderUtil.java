package com.yan.shopmapwangrr.utils;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;

/**
 * Created by dell on 2017/2/25.
 */

public class ImageLoaderUtil {
    /**
     * 初始化方法
     */
    public static void init(Context context) {

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(80 * 1024 * 1024); // 80 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    public static DisplayImageOptions getOption(int onLoadingImageResId, int onEmptyImageResId, int onFailedImageResId, BitmapDisplayer bitmapDisplayer) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(onLoadingImageResId)
                .showImageForEmptyUri(onEmptyImageResId)
                .showImageOnFail(onFailedImageResId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(bitmapDisplayer)
                .build();
    }
}
