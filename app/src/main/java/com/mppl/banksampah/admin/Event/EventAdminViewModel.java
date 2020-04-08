package com.mppl.banksampah.admin.Event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EventAdminViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is Dashboard Fragment");
    }

    public LiveData<String> getText(){
        return mText;
    }
}
