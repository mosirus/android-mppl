package com.mppl.banksampah.ui.riwayatpoin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RiwayatPoinViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RiwayatPoinViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}