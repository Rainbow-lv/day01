package lll.com.lvlinlin20181123;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class MApp extends Application {
    //自定义缓存目录
    File file = new File(Environment.getExternalStorageState()+"/"+"img");
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(build);
    }
}

