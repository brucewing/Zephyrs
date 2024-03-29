package activity_login.onefriend;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Barry on 15/8/17.
 */

public class MusicService extends Service {

    private MediaPlayer player;
    private Timer timer;


//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageRef = storage.getReference();
//
//    // Create a reference with an initial file path and name
//    StorageReference pathReference = storageRef.child("images/stars.jpg");
//
//    // Create a reference to a file from a Google Cloud Storage URI
//    StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");
//
//    // Create a reference from an HTTPS URL
//// Note that in the URL, characters are URL escaped!
//    StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/onefriend-e6a9e.appspot.com/o/h.mp3?alt=media&token=e6f1a974-6d57-44c5-9951-dbf68fc094ad");
//
//    StorageReference islandRef = storageRef.child("images/island.jpg");




    //绑定服务时,调用此方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MusicControl();
    }

    //创建播放音乐的服务
    @Override
    public void onCreate() {
        super.onCreate();

        //创建音乐播放器对象
        player = new MediaPlayer();
    }

    //销毁播放音乐服务
    @Override
    public void onDestroy() {
        super.onDestroy();

        //停止播放音乐
        player.stop();

        //释放占用的资源
        player.release();

        //将player置为空
        player = null;
    }

    //播放音乐
    public void play() {

        try {

            if(player == null)
            {
                player = new MediaPlayer();
            }

            //重置
            player.reset();

            //加载多媒体文件
            String path = "https://firebasestorage.googleapis.com/v0/b/onefriend-e6a9e.appspot.com/o/h.mp3?alt=media&token=e6f1a974-6d57-44c5-9951-dbf68fc094ad";
            player.setDataSource(MusicService.this, Uri.parse(path));

            //准备播放音乐
            player.prepare();

            //播放音乐
            player.start();

            //添加计时器
            addTimer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //暂停播放音乐
    public void pausePlay() {

        player.pause();
    }

    //继续播放音乐
    public void continuePlay() {

        player.start();
    }

    //创建一个实现音乐接口的音乐控制类
    class MusicControl extends Binder implements MusicInterface {

        @Override
        public void play() {

            MusicService.this.play();
        }

        @Override
        public void pausePlay() {

            MusicService.this.pausePlay();
        }

        @Override
        public void continuePlay() {

            MusicService.this.continuePlay();
        }

        @Override
        public void seekTo(int progress) {

            MusicService.this.seekTo(progress);
        }
    }

    //设置音乐的播放位置
    public void seekTo(int progress) {

        player.seekTo(progress);
    }

    //添加计时器用于设置音乐播放器中的播放进度
    public void addTimer() {

        //如果没有创建计时器对象
        if(timer == null) {

            //创建计时器对象
            timer = new Timer();

            timer.schedule(new TimerTask() {

                               //执行计时任务
                               @Override
                               public void run() {

                                   //获得歌曲总时长
                                   int duration = player.getDuration();

                                   //获得歌曲的当前播放进度
                                   int currentPosition = player.getCurrentPosition();

                                   //创建消息对象
                                   Message msg = TabMusic.handler.obtainMessage();

                                   //将音乐的播放进度封装至消息对象中
                                   Bundle bundle = new Bundle();
                                   bundle.putInt("duration", duration);
                                   bundle.putInt("currentPosition", currentPosition);
                                   msg.setData(bundle);

                                   //将消息发送到主线程的消息队列
                                   TabMusic.handler.sendMessage(msg);
                               }
                           },

                    //开始计时任务后的5毫秒，第一次执行run方法，以后每500毫秒执行一次
                    5, 500);
        }
    }
}