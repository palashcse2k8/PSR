package com.example.psr.psrupload.viewmodels;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import java.util.Observable;

public class PSRViewModel extends AndroidViewModel {

    public final ObservableField<String> accountName = new ObservableField<>("Md. Mosiur Rahman");
    public final ObservableField<String> accountNumber = new ObservableField<>("1312801024500");
    public final ObservableField<String> tinNumber = new ObservableField<>("123456789012");
    public final ObservableField<String> selectedTaxAssessmentYear = new ObservableField<>("");
    public final ObservableField<String> selectedTaxAssessmentYearStatus = new ObservableField<>("");
    public final ObservableField<Bitmap> psrImage = new ObservableField<>();
    public final ObservableField<Boolean> dataLoading = new ObservableField<>(false);


    public PSRViewModel(@NonNull Application application, Context context) {
        super(application);
    }

    public void setSelectedTaxAssessmentYear(@NonNull String str) {
        this.selectedTaxAssessmentYear.set(str);
    }
    public String getSelectTaxAssessmentYear () {
        return this.selectedTaxAssessmentYear.get();
    }
    public void setSelectedTaxAssessmentYearStatus(@NonNull String str) {
        this.selectedTaxAssessmentYearStatus.set(str);
    }
    public String getSelectTaxAssessmentYearStatus () {
        return this.selectedTaxAssessmentYearStatus.get();
    }

    public void setPsrImage (Bitmap bitmap) {
        this.psrImage.set(bitmap);
    }
    public Bitmap getPsrImage () {
        return this.psrImage.get();
    }
}
