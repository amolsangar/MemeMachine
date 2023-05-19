package edu.indiana.asangar.mememachine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/* HomeRVAdapter.java
 *
 * Java code for Home Fragment Recycle View Adapter
 * This adapter converts the data modal according to recycle view items
 *
 * Created by: Amol Sangar
 * Created on: 2/20/23
 * Last Modified by: Amol Sangar
 * Last Modified on: 2/27/23
 * Assignment/Project: A590 Android Development Final Project - Meme Machine
 * Part of: Meme Machine, refers to home_recycleview_item.xml layout file
 **/

public class HomeRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private ArrayList<IMemeModal> memeModalArrayList;
    private Context context;

    public HomeRVAdapter(ArrayList<IMemeModal> instaModalArrayList, Context context) {
        this.memeModalArrayList = instaModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item
        View view;
        if(viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycleview_item, parent, false);
            return new ItemViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return memeModalArrayList == null ? 0 : memeModalArrayList.size();
    }

    public class ItemViewHolder  extends RecyclerView.ViewHolder {

        public View showMoreBtn;
        CircleImageView iconIV;
        private TextView authorTV;
        private PhotoView PostPhotoView;
        private TextView likesCountTV, captionTV;
        private ImageButton sharePostBtn;

        public ItemViewHolder (@NonNull View itemView) {
            super(itemView);
            // initializing item in recycler view
            showMoreBtn = itemView.findViewById(R.id.btnShowMore);
            iconIV = itemView.findViewById(R.id.idCVIcon);
            authorTV = itemView.findViewById(R.id.idTVAuthor);
            PostPhotoView = itemView.findViewById(R.id.PostPhotoView);

            likesCountTV = itemView.findViewById(R.id.idTVLikesCount);
            captionTV = itemView.findViewById(R.id.idTVCaption);
            sharePostBtn = itemView.findViewById(R.id.sharePostBtn);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return memeModalArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        IMemeModal modal = memeModalArrayList.get(position);

        String forumHREF = String.format("<a href=\"%s\">%s</a>", modal.getForumURL(), modal.getForumName());
        viewHolder.authorTV.setText(Html.fromHtml(forumHREF + " / " + modal.getAuthor()));
        viewHolder.authorTV.setMovementMethod(LinkMovementMethod.getInstance());

        Picasso.get().load(modal.getImageURL()).into(viewHolder.PostPhotoView);
        viewHolder.captionTV.setText(modal.getCaption());
        viewHolder.likesCountTV.setText("" + modal.getLikesCount() + " upvotes");

        // ICON - Can be set using URL or drawables
        // Its set to drawable using XML at the moment
        // Picasso.get().load(R.drawable.reddit_logo)
        //        .error(R.drawable.reddit_logo)
        //        .into(holder.iconIV);

        viewHolder.sharePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.PostPhotoView.setDrawingCacheEnabled(true);
                viewHolder.PostPhotoView.buildDrawingCache();
                Bitmap bitmap = Bitmap.createBitmap(viewHolder.PostPhotoView.getDrawingCache());
                Utils.shareImage(view.getContext(), bitmap);
            }
        });
    }

}