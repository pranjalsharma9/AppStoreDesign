package com.pranjal.appstoredesign.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pranjal.appstoredesign.App;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // The integer constants that represent the state of the itemView of this ViewHolder.
        static final int UPDATE_COMPLETED = 0;
        static final int UPDATE_ONGOING = 1;
        static final int NOT_UPDATED = 2;

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

        // Function to set the progressBar progress and other views on progress update.
        public void setProgress (int progress) {
            // If the update is complete, set the views accordingly.
            if (progress == 100) {
                setButtonAndProgressBarVisibility(UPDATE_COMPLETED);
            } else {
                progressBar.setProgress(progress); // Else, only set the progress.
            }
        }

        // Setting up the views based on the itemView's state.
        void setButtonAndProgressBarVisibility (int state) {
            switch (state) {
                case UPDATE_COMPLETED : // When the update has completed.
                    pbContainer.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText(BUTTON_OPEN);
                    button.setOnClickListener(null);
                    break;
                case UPDATE_ONGOING : // When the update is in progress.
                    button.setVisibility(View.GONE);
                    pbContainer.setVisibility(View.VISIBLE);
                    break;
                case NOT_UPDATED : // When the update is not done, or was cancelled.
                    pbContainer.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText(BUTTON_UPDATE);
                    break;
                default : // Default : When the update has completed.
                    pbContainer.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    button.setText(BUTTON_OPEN);
                    break;
            }
        }

    }

    //==============================================================================================
    // Adapter definition starts here.
    //==============================================================================================

    // The strings to be used in various components.
    private static final String BUTTON_UPDATE =
            App.getContext().getString(R.string.app_item_button_update);
    private static final String BUTTON_OPEN =
            App.getContext().getString(R.string.app_item_button_open);
    private static final String WHATS_NEW =
            App.getContext().getString(R.string.app_item_whats_new);

    // The normal collapsed height of the app_item.
    private static final int COLLAPSED_HEIGHT =
            (int) App.getContext().getResources().getDimension(R.dimen.app_item_collapsed_height);

    // The data set.
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int positionCopy = position;
        final AppItem appItem = appItems.get(position);
        holder.tvAppName.setText(appItem.getAppTitle());
        holder.tvVersionString.setText(appItem.getVersionString());
        holder.tvWhatsNew.setText(WHATS_NEW);
        holder.tvUpdateInfo.setText(appItem.getUpdateInfo());
        holder.ivAppIcon.setImageResource(appItem.getImageResId());

        // Set the visibilities of the button and progressBar container based on the progress value
        // and the update status of the app.
        // If the app is updated make the button visible with "OPEN" as text.
        if (appItem.isUpdated()) {
            holder.setButtonAndProgressBarVisibility(ViewHolder.UPDATE_COMPLETED);
        } else if (appItem.getProgress() >= 0) { // If the update is in progress, display progress.
            holder.setButtonAndProgressBarVisibility(ViewHolder.UPDATE_ONGOING);
            holder.progressBar.setProgress(appItem.getProgress());
        } else { // Or if the progress hasn't been started yet, display the "UPDATE" text on button.
            holder.setButtonAndProgressBarVisibility(ViewHolder.NOT_UPDATED);
        }

        // Setting the onClickListeners.
        // If the item is not updated, start the update or else do not respond to the button click.
        if (!appItem.isUpdated()) {
            holder.button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.setButtonAndProgressBarVisibility(ViewHolder.UPDATE_ONGOING);
                            appItem.startUpdate(positionCopy);
                        }
                    }
            );
        }
        // If the progressBarContainer is visible, an update is in progress, stop it!
        holder.pbContainer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.setButtonAndProgressBarVisibility(ViewHolder.NOT_UPDATED);
                        holder.setProgress(0);
                        appItem.stopUpdate();
                    }
                }
        );

        // Setting up the updateInfo TextView and the "what's new" button.
        if (appItem.isActive()) {
            holder.tvWhatsNew.setVisibility(View.INVISIBLE);
            holder.tvWhatsNew.setOnClickListener(null);
            holder.itemView.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        } else {
            holder.tvWhatsNew.setVisibility(View.VISIBLE);
            holder.itemView.getLayoutParams().height = COLLAPSED_HEIGHT;

            holder.tvWhatsNew.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appItem.setActive(true);
                            holder.tvWhatsNew.setVisibility(View.INVISIBLE);
                            holder.tvWhatsNew.setOnClickListener(null);

                            // The animation object to make a smooth transition.
                            Animation animation = new Animation() {
                                // The height difference to animate.
                                private final float DELTA_HEIGHT =
                                        appItem.getUpdateInfoHeight();

                                @Override
                                protected void applyTransformation(float interpolatedTime,
                                                                   Transformation t) {
                                    // Set the height to the final value, or the intermediate value.
                                    holder.itemView.getLayoutParams().height =
                                            COLLAPSED_HEIGHT +
                                                    (int) ((interpolatedTime == 1) ?
                                                    DELTA_HEIGHT :
                                                    (DELTA_HEIGHT * interpolatedTime));
                                    holder.itemView.requestLayout();
                                }

                                @Override
                                public boolean willChangeBounds() {
                                    return true;
                                }
                            };

                            // Setting the duration to 200ms.
                            animation.setDuration(200);
                            // Starting the animation onClick.
                            holder.itemView.startAnimation(animation);
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return appItems.size();
    }

}
