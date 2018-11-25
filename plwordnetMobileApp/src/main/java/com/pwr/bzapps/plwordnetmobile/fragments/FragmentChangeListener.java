package com.pwr.bzapps.plwordnetmobile.fragments;

import android.support.v4.app.Fragment;

public interface FragmentChangeListener
{
    public <T> void replaceFragment(Class<T> fragment);
}
