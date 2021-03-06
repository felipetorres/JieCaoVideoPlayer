package cn.jzvd.plugin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUserActionStandard;
import cn.jzvd.JZUtils;
import cn.jzvd.R;
import cn.jzvd.ui.ContainerLocation;
import cn.jzvd.ui.PluginLocation;

import static cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_TINY;

public class ThumbPlugin extends JZUiControlPlugin {

    protected ImageView thumbImageView;

    public ThumbPlugin() {
        super.container = ContainerLocation.NONE;
        super.location = PluginLocation.CENTER;
    }

    @Override
    public String getName() {
        return ThumbPlugin.class.getSimpleName();
    }

    @Override
    public void init(ViewGroup parent) {
        super.init(parent);
        thumbImageView = parent.findViewById(R.id.thumb);
        thumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThumbPlugin.this.onClick();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.plugin_thumb;
    }

    @Override
    public void setUp(JZDataSource dataSource, int defaultUrlMapIndex, int screen, Object... objects) {
        if(player.currentScreen == SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void onClick() {
        if (player.dataSource == null || player.dataSource.getCurrentPath(player.currentUrlMapIndex) == null) {
            Toast.makeText(context, context.getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (player.getStateMachine().currentStateNormal()) {
            if (withWifiDialog()
                    && !player.dataSource.getCurrentPath(player.currentUrlMapIndex).toString().startsWith("file")
                    && !player.dataSource.getCurrentPath(player.currentUrlMapIndex).toString().startsWith("/")
                    && !JZUtils.isWifiConnected(context)
                    && !wifiDialog.showed()) {
                wifiDialog.show();
                return;
            }
            player.onEvent(JZUserActionStandard.ON_CLICK_START_THUMB);
            player.startVideo();
        } else if (player.getStateMachine().currentStateAutoComplete()) {
            player.onClickUiToggle();
        }
    }

    @Override
    public void onNormal(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPreparing(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPlayingShow(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPlayingClear(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPauseShow(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPauseClear(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onComplete(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onError(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            thumbImageView.setVisibility(View.INVISIBLE);
        }
    }
}
