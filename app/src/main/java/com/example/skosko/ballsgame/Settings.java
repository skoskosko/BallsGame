package com.example.skosko.ballsgame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);




        SeekBar MainVolume  = (SeekBar) findViewById(R.id.MainBar);
        SeekBar MusicVolume = (SeekBar) findViewById(R.id.MusicBar);
        SeekBar EffectVolume = (SeekBar) findViewById(R.id.EffectBar);


        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();



        MainVolume.setMax(100);
        MusicVolume.setMax(100);
        EffectVolume.setMax(100);


        EffectVolume.setProgress(SettingsValues.effectvolume);

        int mainvol = prefs.getInt("mainvol", -1);
        if (mainvol == -1) {
            MainVolume.setProgress(SettingsValues.volume);
            editor.putInt("mainvol", SettingsValues.volume);
            editor.commit();
        } else {
            SettingsValues.volume = mainvol;
            MainVolume.setProgress(SettingsValues.volume);
        }


        int effectvol = prefs.getInt("effectvol", -1);
        if (effectvol == -1) {
            EffectVolume.setProgress(SettingsValues.effectvolume);
            editor.putInt("effectvol", SettingsValues.effectvolume);
            editor.commit();
        } else {
            SettingsValues.effectvolume = effectvol;
            MusicVolume.setProgress(SettingsValues.effectvolume);
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





        MainVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingsValues.volume = progress;
                editor.putInt("mainvol", progress);
                editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        MusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SettingsValues.musicvolume = progress;
                editor.putInt("musicvol", progress);
                editor.commit();
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
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
