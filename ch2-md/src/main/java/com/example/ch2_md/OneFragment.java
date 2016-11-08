package com.example.ch2_md;


import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    RecyclerView recyclerView;


    public OneFragment() {
        // Required empty public constructor
    }

    //fragment의 화면을 위해 자동 호출..
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_one, container, false);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item=" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.addItemDecoration(new MyDecoration());

        return recyclerView;
    }

    //view객체를 find해서 가지고 있는 역할..
    class MyViewHolder extends RecyclerView.ViewHolder {
        //항목 하나를 구성하기 위한 find 대상이 되는 view를 나열..
        public TextView title;

        //holder 객체가 메모리에 지속만 된다면..(adapter가 알아서 지속)
        //생성자에서 find 했음으로 최초 한번만 find
        public MyViewHolder(View root) {
            super(root);
            title = (TextView) root.findViewById(android.R.id.text1);
        }
    }

    //항목 구성 adapter...
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        final List<String> list; // 항목구성 집합 데이터..fregment에서 넘겨줄거다..

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        //항목 구성을 위한 layout 초기화 및 초기화된 layout에서 view find
        //대행 holder 지정..
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new MyViewHolder(root);
        }

        //항목 하나를 어떻게 구성할건지에 대한 알고리즘이 구현 되는곳..
        //데이터를 어떻게? view 이벤트를 어떻게..
        //ListView Adapter의 getView역할..
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String text = list.get(position);
            holder.title.setText(text);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        //모든 항목이 추가된 후 한번 호출..
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            int width = parent.getWidth();
            int height = parent.getHeight();

            Drawable dr = getActivity().getResources().getDrawable(
                    R.drawable.kbo);
            int drWidth = dr.getIntrinsicWidth();
            int drHeight = dr.getIntrinsicHeight();

            int left = width / 2 - drWidth / 2;
            int top = height / 2 - drHeight / 2;

            c.drawBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.kbo), left, top, null);


        }

        //항목 하나하나를 꾸미기 위해서 호출..
        //항목의 사각형 정보가 매개변수로 전달..
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //몇번째 항목인지를 동적으로 획득..
            int index = parent.getChildAdapterPosition(view) + 1;
            if (index % 3 == 0)
                outRect.set(20, 20, 20, 60);
            else
                outRect.set(20, 20, 20, 20);

            view.setBackgroundColor(0xFFECE9E9);

            ViewCompat.setElevation(view, 20.0f);
        }
    }


}
