package com.intern.career.ilovezappos.fargment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intern.career.ilovezappos.R;
import com.intern.career.ilovezappos.adapter.RecyclerViewAdapter;
import com.intern.career.ilovezappos.app.App;
import com.intern.career.ilovezappos.model.Product;
import com.intern.career.ilovezappos.model.ZapposProductList;
import com.intern.career.ilovezappos.restapi.ZapposApiClient;
import com.intern.career.ilovezappos.restapi.ZapposApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment implements SearchView.OnQueryTextListener, View.OnClickListener{

    private RecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView tvEmptyList;
    private FloatingActionButton mFab;
    private Animation mShoppingBadgeAppear, mShoppingBadgeDisappear, mFabRotate;
    private int countItemsAdded = 0;
    TextView mShpBagBadge;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mShoppingBadgeAppear = AnimationUtils.loadAnimation(App.getContext(),R.anim.badge_appear);
        mShoppingBadgeDisappear = AnimationUtils.loadAnimation(App.getContext(),R.anim.badge_disappear);
        mFabRotate = AnimationUtils.loadAnimation(App.getContext(),R.anim.fab_rotate);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_list,container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        tvEmptyList = (TextView) view.findViewById(R.id.tv_empty_list);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_productlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(new ArrayList<Product>());
        mRecyclerView.setAdapter(mAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnFlingListener(snapHelper);

        mFab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        mFab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search_query_hint));
        searchView.setSubmitButtonEnabled(true);

        MenuItem shoppingBag = menu.findItem(R.id.shopping_basket);
        MenuItemCompat.setActionView(shoppingBag, R.layout.action_view_shopping_bag);
        RelativeLayout rlShoppingBag = (RelativeLayout) MenuItemCompat.getActionView(shoppingBag);
        mShpBagBadge = (TextView)rlShoppingBag.findViewById(R.id.tv_shopping_basket_badge);
        mShpBagBadge.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        ZapposApiInterface client = ZapposApiClient.getApiClient().create(ZapposApiInterface.class);
        Call<ZapposProductList> call = client.getProducts(query,ZapposApiClient.API_KEY);
        call.enqueue(new Callback<ZapposProductList>() {
            @Override
            public void onResponse(Call<ZapposProductList> call, Response<ZapposProductList> response) {
                List<Product> products = response.body().getResults();
                mAdapter.updateProductList(products);
                mRecyclerView.setAdapter(mAdapter);
                tvEmptyList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ZapposProductList> call, Throwable t) {
                tvEmptyList.setText("Error loading data");
                tvEmptyList.setVisibility(View.VISIBLE);
            }
        });
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingActionButton:
                countItemsAdded++;
                updateBagBadge();
                mFab.startAnimation(mFabRotate);
                break;
            default:
                break;
        }
    }

    private void updateBagBadge(){
        mShpBagBadge.setText(""+countItemsAdded);
        mShpBagBadge.setVisibility(View.VISIBLE);
        mShpBagBadge.startAnimation(mShoppingBadgeDisappear);
        mShpBagBadge.startAnimation(mShoppingBadgeAppear);
    }

}
