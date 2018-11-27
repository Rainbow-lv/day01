package lll.com.lvlinlin20181123;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;

import lll.com.lvlinlin20181123.fragment.Frag_01;
import lll.com.lvlinlin20181123.fragment.Frag_02;
import lll.com.lvlinlin20181123.fragment.Frag_03;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fragment;
    private RadioGroup radioGroup;
    private DrawerLayout drawer;
    private ListView listView;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Frag_01 frag_01;
    private Frag_02 frag_02;
    private Frag_03 frag_03;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        fragment = findViewById(R.id.fragment);
        radioGroup = findViewById(R.id.radioGroup);
        drawer = findViewById(R.id.drawer);
        listView = findViewById(R.id.lv);
        init();
        //获取管理者
        manager = getSupportFragmentManager();
        //开启事务
        transaction = manager.beginTransaction();
        //添加Fragment
        frag_01 = new Frag_01();
        frag_02 = new Frag_02();
        frag_03 = new Frag_03();
        transaction.add(R.id.fragment,frag_01).show(frag_01);
        transaction.add(R.id.fragment,frag_02).hide(frag_02);
        transaction.add(R.id.fragment,frag_03).hide(frag_03);
        //提交
        transaction.commit();
        //默认第一个选中
        radioGroup.check(radioGroup.getChildAt(0).getId());
        //切换按钮改变页面状态
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction1 = manager.beginTransaction();
                switch (checkedId){
                    case R.id.radio01:
                        transaction1.show(frag_01).hide(frag_02).hide(frag_03);
                        break;
                    case R.id.radio02:
                        transaction1.show(frag_02).hide(frag_01).hide(frag_03);
                        break;
                    case R.id.radio03:
                        transaction1.show(frag_03).hide(frag_02).hide(frag_01);
                        break;
                }
                transaction1.commit();
            }
        });
        //设置点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction2 = manager.beginTransaction();
                switch (position){
                        case 0:
                            transaction2.show(frag_01).hide(frag_02).hide(frag_03);
                            radioGroup.check(radioGroup.getChildAt(position).getId());
                            drawer.closeDrawers();
                            break;
                        case 1:
                            transaction2.show(frag_02).hide(frag_01).hide(frag_03);
                            radioGroup.check(radioGroup.getChildAt(position).getId());
                            drawer.closeDrawers();
                            break;
                        case 2:
                            transaction2.show(frag_03).hide(frag_02).hide(frag_01);
                            radioGroup.check(radioGroup.getChildAt(position).getId());
                            drawer.closeDrawers();
                            break;
                    }
                transaction2.commit();
            }
        });
    }

    private void init() {
        for (int i= 1;i<4;i++){
            list.add("页面"+i);
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(mAdapter);
    }
}
