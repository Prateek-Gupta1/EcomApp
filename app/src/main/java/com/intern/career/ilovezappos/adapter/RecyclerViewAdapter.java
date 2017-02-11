package com.intern.career.ilovezappos.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.intern.career.ilovezappos.BR;
import com.intern.career.ilovezappos.R;
import com.intern.career.ilovezappos.model.Product;

import java.util.List;

/**
 * Created by Prateek on 2/9/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Product> mProducts;

    public RecyclerViewAdapter(List<Product> products){
        mProducts = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Product product = mProducts.get(position);
        ProductViewHolder prdHolder = (ProductViewHolder)holder;
        if(product.getPrice() == product.getOriginalPrice()){
            prdHolder.isDiscounted = false;
        }
        prdHolder.getBinding().setVariable(BR.product,product);
        prdHolder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        private boolean isDiscounted = false;

        public ProductViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            TextView tvOriginalPrice = (TextView) itemView.findViewById(R.id.tv_original_price);
            if(isDiscounted)
                tvOriginalPrice.setPaintFlags(tvOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            else
                tvOriginalPrice.setVisibility(View.INVISIBLE);
        }

        public ViewDataBinding getBinding(){
            return binding;
        }

    }

    public void updateProductList(List<Product> products){
        this.mProducts.clear();
        this.mProducts.addAll(products);
        notifyDataSetChanged();
    }

}
