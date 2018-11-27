package lll.com.lvlinlin20181123;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ImageLoaderUtil {

    public static DisplayImageOptions displayImageOption() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisk(true)
                .build();
        return options;
    }
}
