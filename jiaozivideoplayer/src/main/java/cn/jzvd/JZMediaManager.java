package cn.jzvd;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;

/**
 * 这个类用来和jzvd互相调用，当jzvd需要调用Media的时候调用这个类，当MediaPlayer有回调的时候，通过这个类回调JZVD
 * Created by Nathen on 2017/11/18.
 */
public class JZMediaManager implements TextureView.SurfaceTextureListener {

    public static final String TAG = "JiaoZiVideoPlayer";
    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;

    public static JZResizeTextureView textureView;
    public static SurfaceTexture savedSurfaceTexture;
    public static Surface surface;
    public static JZMediaManager jzMediaManager;
    public int positionInList = -1;
    public JZMediaInterface jzMediaInterface;
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public HandlerThread mMediaHandlerThread;
    public MediaHandler mMediaHandler;
    public Handler mainThreadHandler;

    public JZMediaManager() {
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler(mMediaHandlerThread.getLooper());
        mainThreadHandler = new Handler();
        if (jzMediaInterface == null)
            jzMediaInterface = new JZMediaSystem();
    }

    public static JZMediaManager instance() {
        if (jzMediaManager == null) {
            jzMediaManager = new JZMediaManager();
        }
        return jzMediaManager;
    }

    public static JZDataSource getDataSource() {
        return instance().jzMediaInterface.dataSource;
    }

    //Extracted to avoid code replication
    public static void setDataSource(JZDataSource dataSource) {
        instance().jzMediaInterface.dataSource = dataSource;
    }

    /**
     * @return The URI, URL or AssetFileDescriptor being played
     */
    public static Object getCurrentPath() {
        return instance().jzMediaInterface.currentPath;
    }

    public static void setCurrentPath(Object currentPath) {
        instance().jzMediaInterface.currentPath = currentPath;
    }

    public static long getCurrentPosition() {
        return instance().jzMediaInterface.getCurrentPosition();
    }

    public static long getDuration() {
        return instance().jzMediaInterface.getDuration();
    }

    public static void seekTo(long time) {
        instance().jzMediaInterface.seekTo(time);
    }

    public static void pause() {
        instance().jzMediaInterface.pause();
    }

    public static void start() {
        instance().jzMediaInterface.start();
    }

    public static boolean isPlaying() {
        return instance().jzMediaInterface.isPlaying();
    }

    public void releaseMediaPlayer() {
        mMediaHandler.removeCallbacksAndMessages(null);
        Message msg = new Message();
        msg.what = HANDLER_RELEASE;
        mMediaHandler.sendMessage(msg);
    }

    public void prepare() {
        releaseMediaPlayer();
        Message msg = new Message();
        msg.what = HANDLER_PREPARE;
        mMediaHandler.sendMessage(msg);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if (savedSurfaceTexture == null) {
            savedSurfaceTexture = surfaceTexture;
            prepare();
        } else {
            if(textureView.getSurfaceTexture() != savedSurfaceTexture) {
                textureView.setSurfaceTexture(savedSurfaceTexture);
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return savedSurfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }


    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    currentVideoWidth = 0;
                    currentVideoHeight = 0;
                    jzMediaInterface.prepare();

                    if (savedSurfaceTexture != null) {
                        if (surface != null) {
                            surface.release();
                        }
                        surface = new Surface(savedSurfaceTexture);
                        jzMediaInterface.setSurface(surface);
                    }
                    break;
                case HANDLER_RELEASE:
                    jzMediaInterface.release();
                    break;
            }
        }
    }
}
