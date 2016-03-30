package com.example.administrator.my;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity {
    private Button bt_start1=null;
    private Button bt_stop1=null;
    private  Button bt_start2=null;
    private Button bt_stop2=null;
    private SeekBar seek1=null;
    private SeekBar seek2=null;
    private SurfaceView sf=null;
    private boolean isChanging=false;
    public MediaPlayer m = null;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer);
    m=new MediaPlayer();
        m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "stop", Toast.LENGTH_SHORT).show();
                m.release();
            }
        });

mTimer=new Timer();
        mTimerTask=new TimerTask() {
            @Override
            public void run() {
                if(isChanging==true)
                                   return;
                if(m.isPlaying()){
              if(m.getVideoHeight()==0)
                                     seek1.setProgress(m.getCurrentPosition());
                             else
                                   seek2.setProgress(m.getCurrentPosition());

            }}
        };
        mTimer.schedule(mTimerTask, 0, 10);

             bt_start1= (Button) this.findViewById(R.id.button1);
          bt_stop1 = (Button) this.findViewById(R.id.button2);
            bt_start1.setOnClickListener(new ClickEvent());
                bt_stop1.setOnClickListener(new ClickEvent());
               seek1=(SeekBar)this.findViewById(R.id.seekBar1);
                seek1.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        bt_start2 = (Button) this.findViewById(R.id.button3);
              bt_stop2 = (Button) this.findViewById(R.id.button4);
        bt_start2.setOnClickListener(new ClickEvent());
               bt_stop2.setOnClickListener(new ClickEvent());
                seek2=(SeekBar)this.findViewById(R.id.seekBar2);
               seek2.setOnSeekBarChangeListener(new SeekBarChangeEvent());
              sf = (SurfaceView) findViewById(R.id.surfaceView);
               surfaceHolder = sf.getHolder();
               surfaceHolder.setFixedSize(100, 100);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }
class ClickEvent implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        if (v == bt_start1) {
            m.reset();//恢复到未初始化的状态
            m = MediaPlayer.create(MainActivity.this, R.raw.music);
            seek1.setMax(m.getDuration());//设置SeekBar的长度
            try {
                m.prepare();        //准备
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            m.start();        //播放
        } else if (v == bt_stop1 || v == bt_stop2) {
            m.stop();
        } else if (v == bt_start2) {
            m.reset();//恢复到未初始化的状态
            m = MediaPlayer.create(MainActivity.this, R.raw.video);
            seek2.setMax(m.getDuration());//设置SeekBar的长度
            m.setAudioStreamType(AudioManager.STREAM_MUSIC);
            m.setDisplay(surfaceHolder);//设置屏幕
            try {
                m.prepare();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            m.start();
        }
    }
}
         /*
142.   * SeekBar进度改变事件
143.   */
        class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener{
                   @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // TODO Auto-generated method stub
                }
                 @Override
         public void onStartTrackingTouch(SeekBar seekBar) {
                  isChanging=true;
              }
                   @Override
         public void onStopTrackingTouch(SeekBar seekBar) {
                     m.seekTo(seekBar.getProgress());
                isChanging=false;
               }
              }
}

