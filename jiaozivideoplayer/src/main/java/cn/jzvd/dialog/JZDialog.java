package cn.jzvd.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.jzvd.JZVideoPlayerStandard;
import cn.jzvd.R;

import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_PAUSE;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_PLAYING;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_PREPARING;

abstract class JZDialog {

    private JZVideoPlayerStandard player;

    JZDialog(JZVideoPlayerStandard player) {
        this.player = player;
    }

    Context getContext() {
        return player.getContext();
    }

    JZVideoPlayerStandard getPlayer() {
        return player;
    }

    public abstract void show();

    public abstract void dismiss();

    Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(getContext(), R.style.jz_style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(Window.FEATURE_ACTION_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.setLayout(-2, -2);
        WindowManager.LayoutParams localLayoutParams = window.getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(localLayoutParams);
        return dialog;
    }

    void onClickUiToggleToClear() {
        if (player.currentState == CURRENT_STATE_PREPARING) {
            if (player.bottomContainer.getVisibility() == View.VISIBLE) {
                player.changeUiToPreparing();
            } else {
            }
        } else if (player.currentState == CURRENT_STATE_PLAYING) {
            if (player.bottomContainer.getVisibility() == View.VISIBLE) {
                player.changeUiToPlayingClear();
            } else {
            }
        } else if (player.currentState == CURRENT_STATE_PAUSE) {
            if (player.bottomContainer.getVisibility() == View.VISIBLE) {
                player.changeUiToPauseClear();
            } else {
            }
        } else if (player.currentState == CURRENT_STATE_AUTO_COMPLETE) {
            if (player.bottomContainer.getVisibility() == View.VISIBLE) {
                player.changeUiToComplete();
            } else {
            }
        }
    }
}
