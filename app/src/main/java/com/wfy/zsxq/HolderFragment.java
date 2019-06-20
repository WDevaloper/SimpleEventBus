package com.wfy.zsxq;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;


/**
 * 隐藏Fragment
 */
public class HolderFragment extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("HolderFragment", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HolderFragment", "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("HolderFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("HolderFragment", "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("HolderFragment", "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("HolderFragment", "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("HolderFragment", "onDestroy");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("HolderFragment", "onActivityCreated");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.e("HolderFragment", "onAttachFragment");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("HolderFragment", "onDetach");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("HolderFragment", "onLowMemory");
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.e("HolderFragment", "onViewStateRestored");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("HolderFragment", "onSaveInstanceState");
    }
}
