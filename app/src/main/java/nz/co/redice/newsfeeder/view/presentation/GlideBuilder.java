package nz.co.redice.newsfeeder.view.presentation;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import nz.co.redice.newsfeeder.R;

@GlideModule
public class GlideBuilder extends AppGlideModule {


    @BindingAdapter("android:thumbnailImage")
    public static void loadThumbnailImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .override(600, 400)
                .into(view);
    }


    @BindingAdapter("android:detailImage")
    public static void loadBigImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .centerCrop()
                .into(view);
    }
}
