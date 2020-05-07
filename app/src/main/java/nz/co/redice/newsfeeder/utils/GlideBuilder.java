package nz.co.redice.newsfeeder.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class GlideBuilder extends AppGlideModule {

//    public static CircularProgressDrawable getProgress(Context context) {
//        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
//        cpd.setStrokeWidth(10f);
//        cpd.setCenterRadius(50f);
//        cpd.start();
//        return cpd;
//    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .fitCenter()
                .into(view);
    }
}
