package com.example.skosko.ballsgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);





        SeekBar MusicVolume = (SeekBar) findViewById(R.id.MusicBar);
        SeekBar EffectVolume = (SeekBar) findViewById(R.id.EffectBar);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();



        MusicVolume.setMax(100);
        EffectVolume.setMax(100);


        EffectVolume.setProgress(SettingsValues.effectvolume);



        int effectvol = prefs.getInt("effectvol", -1);
        if (effectvol == -1) {
            EffectVolume.setProgress(SettingsValues.effectvolume);
            editor.putInt("effectvol", SettingsValues.effectvolume);
            editor.commit();
        } else {
            SettingsValues.effectvolume = effectvol;
            EffectVolume.setProgress(SettingsValues.effectvolume);
        }

        int musicvol = prefs.getInt("musicvol", -1);

        if (musicvol == -1) {
            MusicVolume.setProgress(SettingsValues.musicvolume);
            editor.putInt("musicvol", SettingsValues.musicvolume);
            editor.commit();
        } else {
            SettingsValues.musicvolume = musicvol;
            MusicVolume.setProgress(SettingsValues.musicvolume);
        }






        MusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingsValues.musicvolume = progress;
                editor.putInt("musicvol", progress);
                editor.commit();

                float log1=(float)(Math.log(101-(SettingsValues.musicvolume))/Math.log(101));
                backroundmp.setVolume(1 -log1, 1- log1);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        EffectVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingsValues.effectvolume = progress;
                editor.putInt("effectvol", progress);
                editor.commit();

                float log1=(float)(Math.log(101-(SettingsValues.effectvolume))/Math.log(101));
                effect.setVolume(1 -log1, 1- log1);
                effect.start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        backroundmp= MediaPlayer.create(this, R.raw.menu);
        effect= MediaPlayer.create(this, R.raw.jump);
        float log1=(float)(Math.log(101-(SettingsValues.musicvolume))/Math.log(101));
        backroundmp.setVolume(1 -log1, 1- log1);
        backroundmp.setLooping(true);
        backroundmp.start();




    }
    MediaPlayer effect;
    MediaPlayer backroundmp;
    @Override
    public void onBackPressed() {

        backroundmp.stop();
        Intent gameIntent = new Intent(getApplicationContext(), MainActivity.class);
        gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(gameIntent);

    }

}
