package com.frankfancode.recyclerviewentrance;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntranceActivity extends AppCompatActivity {

    private RecyclerView gridRV;
    private RecyclerView linearRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        gridRV = (RecyclerView) findViewById(R.id.rv);
        linearRv = (RecyclerView) findViewById(R.id.linearRv);

        List<IconEntity> iconList = new ArrayList<>();
        iconList.addAll(getIconList());
        iconList.addAll(getIconList());
        iconList.addAll(getIconList());

        initGridRV(iconList);
        initLinearRv(iconList);

    }

    private void initLinearRv(List<IconEntity> iconList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        linearRv.setLayoutManager(linearLayoutManager);

        Mydapter adapter = new Mydapter(iconList,linearRv);
        linearRv.setAdapter(adapter);

    }

    private void initGridRV(List<IconEntity> iconList) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        gridRV.setLayoutManager(gridLayoutManager);
        gridRV.addItemDecoration(new SpacesItemDecoration(0));

        Mydapter adapter = new Mydapter(iconList,gridRV);
        gridRV.setAdapter(adapter);
    }


    private List<IconEntity> getIconList() {

        List<IconEntity> iconList = new ArrayList<>();
        try {
            String[] list = getAssets().list("");

            if (list != null) {
                for (String path : list) {

                    if (path.endsWith("png")) {
                        IconEntity iconEntity = new IconEntity();
                        iconEntity.imagePath = path;
                        iconEntity.imageName = path.split("\\.")[0];
                        iconList.add(iconEntity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iconList;
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.top = space;
            } else {
                outRect.top = 10 + space;
            }
        }
    }
}

