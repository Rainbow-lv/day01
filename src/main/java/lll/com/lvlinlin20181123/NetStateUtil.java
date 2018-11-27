package lll.com.lvlinlin20181123;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import lll.com.lvlinlin20181123.fragment.Frag_01;

public class NetStateUtil {
    public static boolean isConn(Frag_01 frag_01) {
        boolean Flag = false;
        //上下文获取 网络状态
        ConnectivityManager connmanager = (ConnectivityManager) frag_01.getActivity().getSystemService(frag_01.getActivity().CONNECTIVITY_SERVICE);
        //权限  获取网络
        NetworkInfo network = connmanager.getActiveNetworkInfo();
        if (network != null){
            Flag = connmanager.getActiveNetworkInfo().isAvailable();
        }
        return Flag;
    }
}
