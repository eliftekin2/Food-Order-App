package com.eliftekin.foodorderapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.eliftekin.foodorderapp.fragments.BurgerFragment;
import com.eliftekin.foodorderapp.fragments.PizzaFragment;
import com.eliftekin.foodorderapp.fragments.SandwichFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    //verilen position için bir fragment döndürür
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PizzaFragment();
            case 1:
                return new SandwichFragment();
            case 2:
                return new BurgerFragment();
            default:
                return new SandwichFragment();
        }
    }

    @Override
    //Adaptörün içinde kaç sayfa olduğunu döndürür.
    public int getItemCount() {
        return 3;
    }
}
