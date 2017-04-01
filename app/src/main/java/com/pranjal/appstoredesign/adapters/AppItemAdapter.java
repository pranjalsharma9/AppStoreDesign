package com.pranjal.appstoredesign.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranjal.appstoredesign.R;
import com.pranjal.appstoredesign.models.AppItem;

import java.util.ArrayList;

/**
 * Created by Pranjal on 01-04-2017.
 * The Adapter class for the AppItem RecyclerView.
 */

public class AppItemAdapter extends RecyclerView.Adapter <AppItemAdapter.ViewHolder> {

    //==============================================================================================
    // The ViewHolder class for AppItem RecyclerView.
    //==============================================================================================

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppName;
        TextView tvVersionString;
        TextView tvUpdateInfo;
        TextView tvWhatsNew;
        ImageView ivAppIcon;
        ProgressBar progressBar;
        View pbContainer;
        Button button;

        ViewHolder (View itemView) {
            super(itemView);
            tvAppName = (TextView) itemView.findViewById(R.id.tv_app_item_app_name);
            tvVersionString = (TextView) itemView.findViewById(R.id.tv_app_item_app_version);
            tvUpdateInfo = (TextView) itemView.findViewById(R.id.tv_app_item_update_info);
            tvWhatsNew = (TextView) itemView.findViewById(R.id.tv_app_item_whats_new);
            ivAppIcon = (ImageView) itemView.findViewById(R.id.iv_app_item_app_icon);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_app_item);
            button = (Button) itemView.findViewById(R.id.button_app_item);
            pbContainer = itemView.findViewById(R.id.container_progress_bar_app_item);
        }

    }

    //==============================================================================================
    // Adapter definition starts here.
    //==============================================================================================

    private ArrayList<AppItem> appItems;

    public AppItemAdapter (ArrayList<AppItem> appItems) {
        this.appItems = appItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating an appItem.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.app_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppItem appItem = appItems.get(position);
        holder.tvAppName.setText(appItem.getAppTitle());
        holder.tvVersionString.setText(appItem.getVersionString());
        holder.tvWhatsNew.setText("What's New");
        holder.tvUpdateInfo.setText(appItem.getUpdateInfo());
        holder.ivAppIcon.setImageResource(appItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return appItems.size();
    }

}
