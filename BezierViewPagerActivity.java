package com.jonny.myexamplepac;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jonny.myexamplepac.Adapter.BezierViewPager;
import com.jonny.myexamplepac.Adapter.CardPagerAdapter;
import com.jonny.myexamplepac.R;
import com.jonny.myexamplepac.view.BezierRoundView;

import java.util.ArrayList;


public class BezierViewPagerActivity extends AppCompatActivity {
    private ArrayList imgList = new ArrayList<>();
    private Runnable runnable;
    private BezierViewPager viewPager;
    private CardPagerAdapter cardAdapter;
    private int CHANGETIME = 3000;// 图片轮播时间
    private int currentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_viewpager);
        init();

    }

    private void init() {
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984457722&di=6d7d3e20e07fc833fc606089d01132e6&imgtype=0&src=http%3A%2F%2Fimgst.izhangheng.com%2F2016%2F08%2Fnight-beauty-girl-3.jpg");
        imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2946550071,381041431&fm=11&gp=0.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");

        int mWidth = getWindowManager().getDefaultDisplay().getWidth();
        float heightRatio = 0.565f;  //高是宽的 0.565 ,根据图片比例
        cardAdapter = new CardPagerAdapter(getApplicationContext());
        cardAdapter.addImgUrlList(imgList);  //放置图片url的list

        //设置阴影大小，即vPage  左右两个图片相距边框  maxFactor + 0.3*CornerRadius   *2
        //设置阴影大小，即vPage 上下图片相距边框  maxFactor*1.5f + 0.3*CornerRadius
        int maxFactor = mWidth / 25;
        cardAdapter.setMaxElevationFactor(maxFactor);

        int mWidthPading = mWidth / 8;

        //因为我们adapter里的cardView CornerRadius已经写死为10dp，所以0.3*CornerRadius=3
        //设置Elevation之后，控件宽度要减去 (maxFactor + dp2px(3)) * heightRatio
        //heightMore 设置Elevation之后，控件高度 比  控件宽度* heightRatio  多出的部分
        float heightMore = (1.5f * maxFactor + dp2px(3)) - (maxFactor + dp2px(3)) * heightRatio;
        int mHeightPading = (int) (mWidthPading * heightRatio - heightMore);

        viewPager = (BezierViewPager) findViewById(R.id.view_pager);
        viewPager.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) (mWidth * heightRatio)));
        viewPager.setPadding(mWidthPading, mHeightPading, mWidthPading, mHeightPading);
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(cardAdapter);
        viewPager.setOnPageChangeListener(myOnPageChangeListener);
        autoRoll();
        viewPager.showTransformer(0.2f);
        cardAdapter.setOnCardItemClickListener(new CardPagerAdapter.OnCardItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getApplicationContext(),"点击了第"+(position+1)+"个",Toast.LENGTH_SHORT).show();
            }
        });

        BezierRoundView bezRound = (BezierRoundView) findViewById(R.id.bezRound);
        bezRound.attach2ViewPage(viewPager);
        bezRound.setRadius(40);//设置圆球半径
        ImageView iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_bg.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, (int) ((mWidth * heightRatio) + dp2px(60))));
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void autoRoll(){
        runnable = new Runnable() {
            @Override
            public void run() {
                int next = viewPager.getCurrentItem() + 1;
                if (next >= cardAdapter.getCount()) {
                    next = 0;
                }
                viewHandler.sendEmptyMessage(next);
            }
        };
        viewHandler.postDelayed(runnable, CHANGETIME);
    }
    /**
     * 每隔固定时间切换广告栏图片
     */
    @SuppressLint("HandlerLeak")
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setCurView(msg.what);
        }

    };
    /**
     * 设置当前的引导页
     */
    public void setCurView(int position) {
        if (position < 0 || position > cardAdapter.getCount()) {
            return;
        }
        viewPager.setCurrentItem(position);

    }
    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
            // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
//            if (arg0 != ViewPager.SCROLL_STATE_IDLE) return;
//
//            // 当视图在第一个时，将页面号设置为图片的最后一张。
//            if (currentPosition == 0) {
//                viewPager.setCurrentItem(imgList.size() - 1, false);
//
//            } else if (currentPosition == imgList.size() - 1) {
//                // 当视图在最后一个是,将页面号设置为图片的第一张。
//                viewPager.setCurrentItem(0, false);
//            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            arg0 = arg0 % imgList.size();
            viewHandler.removeCallbacks(runnable);
            viewHandler.postDelayed(runnable, CHANGETIME);

            currentPosition=arg0;

        }

    };


}
