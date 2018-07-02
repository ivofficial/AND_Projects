package gwg.ivo.android.popularmovies.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import gwg.ivo.android.popularmovies.MovieDetailsFragment;
import gwg.ivo.android.popularmovies.ReviewsFragment;
import gwg.ivo.android.popularmovies.TrailersFragment;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 3;
    private String[] tabTitles = new String[] {"Overview", "Reviews", "Trailers"};
    private Context context;
    private Bundle extras;

    public TabFragmentPagerAdapter(FragmentManager fm, Context _context, Bundle args) {
        super(fm);
        context = _context;
        extras = args;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MovieDetailsFragment.newInstance(extras);
            case 1:
                return ReviewsFragment.newInstance(extras);
            case 2:
                return TrailersFragment.newInstance(extras);
            default:
                return MovieDetailsFragment.newInstance(extras);
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
