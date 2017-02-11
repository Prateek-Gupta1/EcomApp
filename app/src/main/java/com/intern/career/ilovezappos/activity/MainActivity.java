package com.intern.career.ilovezappos.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.intern.career.ilovezappos.R;
import com.intern.career.ilovezappos.fargment.ProductListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return ProductListFragment.newInstance(null,null);
    }
}
