package com.jonny.myexamplepac;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class AnimActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv;
    boolean flag = true;
    private int[] res = {
            R.id.round1, R.id.round2, R.id.round3, R.id.round4, R.id.round5, R.id.round6
    };

    private ArrayList<ImageView> list= new ArrayList<>();
    private boolean isOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        iv = (ImageView) findViewById(R.id.iv);
        initRound();
    }

    /**
     * 初始化圆点控件
     */
    public void initRound() {
        for(int i=0;i<res.length;i++){
            ImageView imageView= (ImageView) findViewById(res[i]);
            imageView.setOnClickListener(this);
            list.add(imageView);
        }
    }

    /**
     * 帧动画
     *
     * @param view
     */
    public void click1(View view) {
        AnimationDrawable ad = (AnimationDrawable) iv.getDrawable();
        if (flag) {
            ad.stop();
            flag = !flag;
        } else {
            ad.start();
            flag = !flag;
        }
    }

    /**
     * 属性动画
     *
     * @param view
     */
    public void click2(View view) {
        ObjectAnimator.ofFloat(view, "rotationX", 0f, 360f).setDuration(500).start();
    }

    public void click3(View view) {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f, 1f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, p1, p2, p3).setDuration(2500).start();
    }

    public void click4(final View view) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ValueAnimator va = ValueAnimator.ofFloat(view.getY(), dm.heightPixels, view.getY()).setDuration(1500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        va.start();

//需要什么监听就加那单一的监听
//        va.addListener(new AnimatorListenerAdapter() {
//
//        });

        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //动画开始
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束
                ViewGroup viewGroup = (ViewGroup) view.getParent();//得到视图的上一级,只有父级容器才能删除子组件
                if (viewGroup != null) {
                    viewGroup.removeView(view);
                }
                Toast.makeText(AnimActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //动画取消
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //动画重复
            }
        });
    }

    public void click5(View view) {
        ObjectAnimator a1 = ObjectAnimator.ofFloat(view, "translationX", 0f, 200f);
        ObjectAnimator a2 = ObjectAnimator.ofFloat(view, "translationY", 0f, 200f);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(view, "rotation", 0f, -360f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
//      set.playTogether(a1,a2,a3);//三个动画同时执行
        set.playSequentially(a1, a2, a3);//按序列执行
        set.setStartDelay(500);//延迟执行

        //设置插值器
//        set.setInterpolator(new AccelerateDecelerateInterpolator());//插补,其变化率慢慢开始和结束,但通过中间加速
//        set.setInterpolator(new AccelerateInterpolator());//插补,其变化率开始缓慢，然后加快
//        set.setInterpolator(new AnticipateInterpolator());//内插的变化开始落后,然后向前甩
//        set.setInterpolator(new AnticipateOvershootInterpolator());//内插的变化,开始落后,甩向前冲过目标值,然后终于可以追溯到最终值
//        set.setInterpolator(new BounceInterpolator());//插补,其变化在最后反弹
//        set.setInterpolator(new CycleInterpolator(2));//内插动画重复指定的周期数
        set.setInterpolator(new DecelerateInterpolator());//插补,其变化的速度开始很快,然后减速
//        set.setInterpolator(new LinearInterpolator());//插补,其变化率是恒定的
//        set.setInterpolator(new OvershootInterpolator());//内插变化摔向前和冲过最后一个值,然后回来
//        set.setInterpolator(new TimeInterpolator() {
//            @Override
//            public float getInterpolation(float input) {
//                return 0;
//            }
//        });//一个接口,使您实现自己的插补
//      set.play(a1).with(a2);
//      set.play(a3).after(a2);
        set.start();

    }

    /**
     * 加载自定义xml动画
     *
     * @param view
     */
    public void click6(View view) {
        Animator a = AnimatorInflater.loadAnimator(this, R.animator.test1);
        a.setTarget(view);
        a.start();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.round1:
                if(isOpen){
                   closeMenu();
                    isOpen=!isOpen;
                }else{
                openMenu();
                    isOpen=!isOpen;
                }
                break;
            default:
                Toast.makeText(this,v.getTag()+"",Toast.LENGTH_SHORT).show();
                break;
        }

    }
    //打开按钮菜单
    private void openMenu(){
        for (int i =1;i<res.length;i++){
            ObjectAnimator a1=ObjectAnimator.ofFloat(list.get(i), "translationX", 0f, 140 * i).setDuration(300);
            ObjectAnimator a2=ObjectAnimator.ofFloat(list.get(i), "translationY", 0f, 140 * i).setDuration(300);
            a1.setInterpolator(new BounceInterpolator());
            a2.setInterpolator(new BounceInterpolator());
            a1.start();
            a2.start();
        }
    }
    //收起按钮菜单
    private void closeMenu(){
        for (int i =1;i<res.length;i++){
            ObjectAnimator a1=ObjectAnimator.ofFloat(list.get(i), "translationX", 140*i, 0f).setDuration(300);
            ObjectAnimator a2=ObjectAnimator.ofFloat(list.get(i), "translationY", 140*i, 0f).setDuration(300);
            a1.setInterpolator(new BounceInterpolator());
            a2.setInterpolator(new BounceInterpolator());
            AnimatorSet set =new AnimatorSet();
            set.playTogether(a1,a2);
            set.start();
        }
    }
}
