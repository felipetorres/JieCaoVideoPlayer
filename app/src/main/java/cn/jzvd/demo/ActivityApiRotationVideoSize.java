package cn.jzvd.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cn.jzvd.JZResizeTextureView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import cn.jzvd.demo.CustomView.JZVideoPlayerStandardGlide;

/**
 * Created by Nathen on 2017/11/2.
 */

public class ActivityApiRotationVideoSize extends AppCompatActivity implements View.OnClickListener {

    JZVideoPlayerStandardGlide myJZVideoPlayerStandard;
    Button mBtnRotation, mBtnFillParent, mBtnFillCrop, mBtnOriginal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setTitle("RotationAndVideoSize");
        setContentView(R.layout.activity_api_rotation_videosize);

        myJZVideoPlayerStandard = findViewById(R.id.jz_video);
        myJZVideoPlayerStandard.setUp(VideoConstant.videoUrls[0][7],
                                      JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                                      VideoConstant.videoTitles[0][7],
                                      VideoConstant.videoThumbs[0][7]);
        // The Point IS
        myJZVideoPlayerStandard.rotateTo(180);

        mBtnRotation = findViewById(R.id.rotation_to_90);
        mBtnFillParent = findViewById(R.id.video_image_display_fill_parent);
        mBtnFillCrop = findViewById(R.id.video_image_display_fill_crop);
        mBtnOriginal = findViewById(R.id.video_image_diaplay_original);
        mBtnRotation.setOnClickListener(this);
        mBtnFillParent.setOnClickListener(this);
        mBtnFillCrop.setOnClickListener(this);
        mBtnOriginal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rotation_to_90:
                myJZVideoPlayerStandard.rotateTo(90);

                break;
            case R.id.video_image_display_fill_parent:
                myJZVideoPlayerStandard.setVideoImageDisplayType(JZResizeTextureView.TYPE_FILL_PARENT);

                break;
            case R.id.video_image_display_fill_crop:
                myJZVideoPlayerStandard.setVideoImageDisplayType(JZResizeTextureView.TYPE_FILL_CROP);

                break;
            case R.id.video_image_diaplay_original:
                myJZVideoPlayerStandard.setVideoImageDisplayType(JZResizeTextureView.TYPE_ORIGINAL);

                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
        myJZVideoPlayerStandard.setVideoImageDisplayType(JZResizeTextureView.TYPE_NONE);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
