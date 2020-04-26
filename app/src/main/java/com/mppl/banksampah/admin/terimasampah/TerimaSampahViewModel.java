package com.mppl.banksampah.admin.terimasampah;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TerimaSampahViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TerimaSampahViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
