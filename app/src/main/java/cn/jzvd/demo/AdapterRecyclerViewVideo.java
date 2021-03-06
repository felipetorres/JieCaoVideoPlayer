package cn.jzvd.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.demo.CustomView.JZVideoPlayerStandardGlide;

public class AdapterRecyclerViewVideo extends RecyclerView.Adapter<AdapterRecyclerViewVideo.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerViewVideo";
    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;

    public AdapterRecyclerViewVideo(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzVideoPlayer.hashCode() + "] position=" + position);

        holder.jzVideoPlayer.setUp(VideoConstant.videoUrls[0][position],
                                   JZVideoPlayer.SCREEN_WINDOW_LIST,
                                   VideoConstant.videoTitles[0][position],
                                   VideoConstant.videoThumbs[0][position]);
    }

    @Override
    public int getItemCount() {
        return videoIndexs.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandardGlide jzVideoPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzVideoPlayer = itemView.findViewById(R.id.videoplayer);
        }
    }

}
