package felixgiov.gempabumidanprakiraancuacabmkg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by felix on 10 Jan 2017.
 */

public class CustomBottomSheetDialog extends BottomSheetDialogFragment {

    String img;
    Toolbar mActionBarToolbar;

    public static CustomBottomSheetDialog getInstance() {
        return new CustomBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);

        mActionBarToolbar = (Toolbar) view.findViewById(R.id.toolbar2);
        mActionBarToolbar.setTitle("Peta BMKG");
        mActionBarToolbar.setTitleTextColor(Color.WHITE);

        img = "www.namoraltv.com.br/assets/images/page-not-found.png";
        img = getArguments().getString("img");

        ImageView imageView = (ImageView) view.findViewById(R.id.ic_image);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);

        Glide.with(this)
                .load(img)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        return view;
    }
}