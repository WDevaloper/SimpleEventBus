package com.wfy.zsxq.data.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.wfy.zsxq.data.protocol.User;
import com.wfy.zsxq.data.repository.UserRepository;

public class UserModel extends ViewModel {
    private UserRepository mUserRepository = new UserRepository();
    private MutableLiveData<User> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        mutableLiveData.setValue(mUserRepository.getUser());
        return mutableLiveData;
    }
}
