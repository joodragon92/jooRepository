package com.example.ch2_md;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


//유저에 의해 tab button이 눌린 순간의 이벤트...
//ViewPager와 연결하면 viewpager 조정시에 tab button의 활성사태는 자동으로 바뀌는데
//반대로는 직접 해주어야.. implements~~~~~Listener
public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{

    ViewPager viewPager;
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    boolean isDrawerOpened;

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //actionBar의 내용을 우리 view(Toolbar)에 적용..
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter((new MyPagerAdapter(getSupportFragmentManager())));

        //toggle....
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //매개변수의 문자열은 화면출력과는 상관이 없다..
        //웹접근성 개념처럼 상태를 표현하는 문자열로 이용..
        //이벤트 처리가 없다면.. 이벤트 핸들러 안만들어도 된다..
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpened = false;
            }
        };
        drawerLayout.addDrawerListener(toggle); // 토글적용
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 아이콘 이용

        //tabs....
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        //viewpager 연결..
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(this);

        collapsingToolbarLayout=(CollapsingToolbarLayout)
                findViewById(R.id.collapsing);
        fab=(FloatingActionButton)
                findViewById(R.id.fab);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.main_content);

        collapsingToolbarLayout.setTitle("AppBar Title");
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Snackbar.make(coordinatorLayout, "I am SnackBar..", Snackbar.LENGTH_SHORT).setAction("MoreAction", new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Log.d("kkang","snackbar click...........");
            }
        }).show();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState(); // drawer와 toggle이 동기화 되게..
    }

    //menu event 함수
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //drawer가 open된 상황에서 유저가 폰의 백버튼을 눌렀다면??
    //제어하지 않으면 activity가 사라진다..
    //back button제어..
    @Override
    public void onBackPressed() {
        if (isDrawerOpened)
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }


    //menu 초기화 적용..

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ViewPager를 위한 Adapter..
    class MyPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        //tablayout을 위한 page title문자열.. tab button 역할을 한다..
        //물론 pager에서 하지 않고 tablayout에서 직접 button문자열을 지정도 가능하고..
        String titles[]=new String[]{"tab1", "tab2", "tab3"};


        public MyPagerAdapter(FragmentManager fm) {
            super(fm); // 상위 클래스에 FragmentManager 등록하면..
            //알아서.. 지정한 Fragment를 FragmentTransaction으로 add
            //remove replace 등 제어해 준다..
            fragments.add(new OneFragment());
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        //page의 title문자열을 획득 목적으로 자동 호출..
        //tablayout의 button문자열로..
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    //추상함수 3개 alt+insert
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
