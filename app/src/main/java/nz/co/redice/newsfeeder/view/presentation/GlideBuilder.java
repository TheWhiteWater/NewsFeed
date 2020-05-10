package nz.co.redice.newsfeeder.view.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;

import nz.co.redice.newsfeeder.R;

@GlideModule
public class GlideBuilder extends AppGlideModule {


    @BindingAdapter("android:thumbnailImage")
    public static void loadThumbnailImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .thumbnail(0.25f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }


    @BindingAdapter("android:detailImage")
    public static void loadBigImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .centerCrop()
                .into(view);
    }
}
