package famar.tirepressuremonitoringsystem.Help;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import famar.tirepressuremonitoringsystem.MainApplication.MainActivity;
import famar.tirepressuremonitoringsystem.R;

public class HelpActivity extends AppCompatActivity
{
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int num_screens;
    private Button btnSkip, btnNext;
    //private PreferenceManager prefManager;

    TypedArray images;
    String[] titles;
    String[] descriptions;
    int[] backgroundColors;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        //prefManager = new PreferenceManager(this);
        //if (!prefManager.isFirstTimeLaunch())
        {
            //launchHomeScreen();
            //finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 23)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        getSupportActionBar().hide();


        setContentView(R.layout.activity_help);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        backgroundColors = getResources().getIntArray(R.array.array_background_color);
        images = getResources().obtainTypedArray(R.array.array_images);
        titles = getResources().getStringArray(R.array.array_titles);
        descriptions = getResources().getStringArray(R.array.array_descriptions);
        num_screens = getResources().getInteger(R.integer.num_help_screens);

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < num_screens)
                {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                }
                else
                {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage)
    {
        dots = new TextView[num_screens];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i)
    {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen()
    {
        //prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {

        @Override
        public void onPageSelected(int position)
        {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == num_screens - 1)
            {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            }
            else
            {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter
    {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter()
        {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.grid_item_help_screen, container, false);
            RelativeLayout rootLayout = (RelativeLayout)view.findViewById(R.id.root_layout);
            rootLayout.setBackgroundColor(backgroundColors[position]);
            TextView txtTitle = (TextView) rootLayout.findViewById(R.id.title);
            txtTitle.setText(titles[position]);
            TextView txtDescription = (TextView) rootLayout.findViewById(R.id.description);
            txtDescription.setText(descriptions[position]);
            ImageView image = (ImageView) rootLayout.findViewById(R.id.image);
            image.setImageResource(images.getResourceId(position, -1));
            container.addView(view);

            return view;
        }

        @Override
        public int getCount()
        {

            return num_screens;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj)
        {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View view = (View) object;
            container.removeView(view);
        }
    }
}