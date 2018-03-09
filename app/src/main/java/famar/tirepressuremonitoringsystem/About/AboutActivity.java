package famar.tirepressuremonitoringsystem.About;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import famar.tirepressuremonitoringsystem.Help.HelpActivity;
import famar.tirepressuremonitoringsystem.R;
import famar.tirepressuremonitoringsystem.Settings.SettingsActivity;

public class AboutActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.mSettings:
                intent = new Intent(AboutActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.mHelp:
                intent = new Intent(AboutActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.mAbout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.menu_about));

        View aboutBackground = (View)findViewById(R.id.aboutBackground);
        AnimationDrawable animationDrawable = (AnimationDrawable) aboutBackground.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }
}
