package com.example.chatapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;


class sectionsAdapter  extends FragmentStatePagerAdapter {
            public sectionsAdapter(FragmentManager fm) {
                super(fm);
            }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                Chats chats=new Chats();
                return chats;
            case 1:
                Friends friends=new Friends();
                return friends;
            default:
                return null;
        }
    }
    public CharSequence getPageTitle(int position){
        switch(position){

            case 0:
                return "CHATS";
            case 1:
                return "USERS";
            default:
                return  null;
        }
    }
}
