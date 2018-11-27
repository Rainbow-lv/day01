package lll.com.lvlinlin20181123.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xlistviewlibrary.utils.NetWordUtils;
import com.bwie.xlistviewlibrary.view.XListView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import lll.com.lvlinlin20181123.ImageLoaderUtil;
import lll.com.lvlinlin20181123.NetStateUtil;
import lll.com.lvlinlin20181123.R;
import lll.com.lvlinlin20181123.bean.Product;
import lll.com.lvlinlin20181123.sql.Dao;

public class Frag_01 extends Fragment {

    String baseurl = "http://www.xieast.com/api/news/news.php?page=";
    int page=1;
    private XListView xlistView;
    ArrayList<Product.DataBean> lists = new ArrayList<>();
    private String mUrl;
    private ImageLoader imageLoader;
    private MyAdapter myAdapter;
    private Dao dao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_01,container,false);
        //找控件
        xlistView = view.findViewById(R.id.xlv);
        //创建Dao层
        dao = new Dao(getActivity());
        init(page);
        //设置xlistView可以加载更多
        xlistView.setPullLoadEnable(true);
        //设置监听
        xlistView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                lists.clear();
                init(0);
                page = 1;
            }

            @Override
            public void onLoadMore() {
                page++;
                init(page);
            }
        });
        //设置适配器
        myAdapter = new MyAdapter();
        xlistView.setAdapter(myAdapter);
        return view;
    }

    private void init(int page) {
        mUrl = baseurl + page;
        if (NetStateUtil.isConn(this)){
            new AnsyTask().execute(mUrl);
        }else {
            Toast.makeText(getActivity(),"当前没有网络，请稍后重试",Toast.LENGTH_SHORT).show();
            //查询数据库
            String query = dao.query(mUrl);
            if (!query.isEmpty()){
                //开始解析
                Gson gson = new Gson();
                Product product = gson.fromJson(query, Product.class);
                List<Product.DataBean> data = product.getData();
                lists.addAll(data);
               myAdapter.notifyDataSetChanged();
              //设置刷新时间
               xlistView.setRefreshTime("刚刚");
               //让刷新头和尾消失
               xlistView.stopRefresh();
               xlistView.stopLoadMore();
            }
        }

    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int viewType = getItemViewType(position);
            switch (viewType){
                case 0:
                    ViewHolder01 vh01 = new ViewHolder01();
                    if (convertView == null){
                        convertView = View.inflate(getActivity(),R.layout.item01,null);
                        vh01.tv01 = convertView.findViewById(R.id.tv01);
                        vh01.iv = convertView.findViewById(R.id.iv);
                        convertView.setTag(vh01);
                    }else {
                        vh01 = (ViewHolder01) convertView.getTag();
                    }
                    //绑定数据
                    Product.DataBean dataBean = lists.get(position);
                    vh01.tv01.setText(dataBean.getTitle());
                    imageLoader = ImageLoader.getInstance();
                    imageLoader.displayImage(dataBean.getThumbnail_pic_s(),vh01.iv);
                    DisplayImageOptions options = ImageLoaderUtil.displayImageOption();
                    break;
                case 1:
                    ViewHolder02 vh02 = new ViewHolder02();
                    if (convertView == null){
                        convertView = View.inflate(getActivity(),R.layout.item02,null);
                        vh02.iv01 = convertView.findViewById(R.id.iv01);
                        vh02.iv02 = convertView.findViewById(R.id.iv02);
                        vh02.iv03 = convertView.findViewById(R.id.iv03);
                        vh02.tv02 = convertView.findViewById(R.id.tv02);
                        convertView.setTag(vh02);
                    }else {
                        vh02 = (ViewHolder02) convertView.getTag();
                    }
                    //绑定数据
                    Product.DataBean dataBean1 = lists.get(position);
                    imageLoader.displayImage(dataBean1.getThumbnail_pic_s(),vh02.iv01);
                    imageLoader.displayImage(dataBean1.getThumbnail_pic_s02(),vh02.iv02);
                    imageLoader.displayImage(dataBean1.getThumbnail_pic_s03(),vh02.iv03);
                    vh02.tv02.setText(dataBean1.getTitle());
                    break;
            }
            return convertView;
        }
        class ViewHolder01{
            public TextView tv01;
            public ImageView iv;
        }
        class ViewHolder02{
            public ImageView iv01;
            public ImageView iv02;
            public ImageView iv03;
            public TextView tv02;
        }
    }

    private class AnsyTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String jsonString = NetWordUtils.getNetjson(strings[0]);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //调用插入的方法
           dao.insert(mUrl,s);
            //开始解析
            Gson gson = new Gson();
            Product product = gson.fromJson(s, Product.class);
            List<Product.DataBean> data = product.getData();
            lists.addAll(data);
            myAdapter.notifyDataSetChanged();
            //设置刷新时间
            xlistView.setRefreshTime("刚刚");
            //让刷新头和尾消失
            xlistView.stopRefresh();
            xlistView.stopLoadMore();
        }
    }
}
