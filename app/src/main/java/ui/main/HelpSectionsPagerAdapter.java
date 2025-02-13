package ui.main;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.aquamaris.Fragmentos.HPage1;
import com.example.aquamaris.Fragmentos.HPage2;

public class HelpSectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context mContext;

    public HelpSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        switch (position) {
            case 0:
                return new HPage1();
            case 1:
                return new HPage2();
            default:
                return null;

//                return int 0;
//                return new PlaceholderFragment();

        }
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
////        return mContext.getResources().getString(TAB_TITLES[position]);
//    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
