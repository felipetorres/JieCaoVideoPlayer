package cn.jzvd.demo.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.Glide;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZVideoPlayerStandard;
import cn.jzvd.plugin.JZUiControlPlugin;
import cn.jzvd.plugin.ThumbPlugin;

public class JZVideoPlayerStandardGlide extends JZVideoPlayerStandard {

    public JZVideoPlayerStandardGlide(Context context) {
        super(context);
    }

    public JZVideoPlayerStandardGlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.loader.register(this, new CustomThumbPlugin());
        super.init(context);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        for (JZUiControlPlugin plugin : super.loader.getRegisteredControlPlugins()) {
            plugin.onAutoCompletion();
        }
    }
}

class CustomThumbPlugin extends ThumbPlugin {

    @Override
    public void setUp(JZDataSource dataSource, int defaultUrlMapIndex, int screen, Object... objects) {
        super.setUp(dataSource, defaultUrlMapIndex, screen, objects);
        if(objects.length >= 2) {
            Glide.with(context).load(objects[1]).into(thumbImageView);
        }
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        thumbImageView.setVisibility(View.GONE);
    }
}