package cn.jzvd.component;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.R;

import static cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN;
import static cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_LIST;
import static cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_NORMAL;
import static cn.jzvd.JZVideoPlayer.SCREEN_WINDOW_TINY;

public class StartButtonComponent extends JZUIControlComponent {

    private static final String TAG = "StartButtonComponent";

    private ImageView startButton;

    public StartButtonComponent(JZVideoPlayer player) {
        super(player);
    }

    @Override
    protected void init(FrameLayout frameLayout) {
        startButton = frameLayout.findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartButtonComponent.this.onClick();
            }
        });
    }

    private void onClick() {
        Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
        if (player.dataSource == null || player.dataSource.getCurrentPath(player.currentUrlMapIndex) == null) {
            Toast.makeText(context, player.getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
            return;
        }
        if (player.getStateMachine().currentStateNormal()) {
            if (!player.dataSource.getCurrentPath(player.currentUrlMapIndex).toString().startsWith("file") && !
                    player.dataSource.getCurrentPath(player.currentUrlMapIndex).toString().startsWith("/") &&
                    !JZUtils.isWifiConnected(context) && !wifiDialog.showed()) {
                wifiDialog.show();
                return;
            }
            player.startVideo();
            player.onEvent(JZUserAction.ON_CLICK_START_ICON);
        } else if (player.getStateMachine().currentStatePlaying()) {
            player.onEvent(JZUserAction.ON_CLICK_PAUSE);
            Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
            player.getStateMachine().setPause();
        } else if (player.getStateMachine().currentStatePause()) {
            player.onEvent(JZUserAction.ON_CLICK_RESUME);
            player.getStateMachine().setPlaying();
        } else if (player.getStateMachine().currentStateAutoComplete()) {
            player.onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
            player.startVideo();
        }
    }

    public void performClick() {
        onClick();
    }

    @Override
    public String getName() {
        return StartButtonComponent.class.getSimpleName();
    }

    @Override
    public void setUp(JZDataSource dataSource, int defaultUrlMapIndex, int screen, Object... objects) {
        if (player.currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            changeStartButtonSize((int) player.getResources().getDimension(R.dimen.jz_start_button_w_h_fullscreen));
        } else if (player.currentScreen == SCREEN_WINDOW_NORMAL
                || player.currentScreen == SCREEN_WINDOW_LIST) {
            changeStartButtonSize((int) player.getResources().getDimension(R.dimen.jz_start_button_w_h_normal));
        } else if (player.currentScreen == SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    private void changeStartButtonSize(int size) {
        ViewGroup.LayoutParams lp = startButton.getLayoutParams();
        lp.height = size;
        lp.width = size;
    }

    @Override
    public void onNormal(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.VISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onPreparing(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.INVISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onPlayingShow(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.VISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onPlayingClear(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPauseShow(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.VISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onPauseClear(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onComplete(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.VISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onError(int currentScreen) {
        if (currentScreen != SCREEN_WINDOW_TINY) {
            startButton.setVisibility(View.VISIBLE);
            updateStartImage();
        }
    }

    @Override
    public void onPreparingChangingUrl() {
        startButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDismissControlView() {
        startButton.setVisibility(View.INVISIBLE);
    }

    private void updateStartImage() {

        if (player.getStateMachine().currentStatePlaying()) {
            startButton.setVisibility(View.VISIBLE);
            startButton.setImageResource(R.drawable.jz_click_pause_selector);
        } else if (player.getStateMachine().currentStateError()) {
            startButton.setVisibility(View.INVISIBLE);
        } else if (player.getStateMachine().currentStateAutoComplete()) {
            startButton.setVisibility(View.VISIBLE);
            startButton.setImageResource(R.drawable.jz_click_replay_selector);
        } else {
            startButton.setImageResource(R.drawable.jz_click_play_selector);
        }
    }
}
