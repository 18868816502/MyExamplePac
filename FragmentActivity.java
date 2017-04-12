package com.jonny.myexamplepac;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.jonny.myexamplepac.fragment.MainFragment;
import com.jonny.myexamplepac.fragment.MenuFragment;
import com.jonny.myexamplepac.fragment.ReplaceFragment;

public class FragmentActivity extends AppCompatActivity implements MenuFragment.MyMenuListener {
    private MenuFragment menuFragment;
    private MainFragment mainFragment;
    private ReplaceFragment replaceFragment;
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        menuFragment = (MenuFragment) getFragmentManager().findFragmentById(R.id.menuFragment);
        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.mainFragment);

    }

    @Override
    public void changeValue(String value) {
 //       mainFragment.getInStance(value);
        mainFragment.changeTextViewValue(value);
    }

    /**
     * 通过代码改变fragment
     */
    @Override
    public void changeFragment(){
        FragmentManager fm = getFragmentManager();
        //开启一个事务
        FragmentTransaction ft = fm.beginTransaction();
        replaceFragment=new ReplaceFragment();
        //添加fragment
        ft.add(R.id.frameLayout,replaceFragment);
        //把fragment添加到栈中
        ft.addToBackStack(null);
//        ft.remove()
        ft.commit();//提交
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if(getFragmentManager().getBackStackEntryCount()==0){
                finish();
            }else {
                getFragmentManager().popBackStack();//出栈
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
