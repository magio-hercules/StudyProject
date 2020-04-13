package com.fund.iam.ui.intro;

import com.fund.iam.data.DataManager;
import com.fund.iam.di.provider.ResourceProvider;
import com.fund.iam.di.provider.SchedulerProvider;
import com.fund.iam.ui.base.BaseViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class IntroViewModel extends BaseViewModel<IntroNavigator> {

    public IntroViewModel(DataManager dataManager, SchedulerProvider schedulerProvider, ResourceProvider resourceProvider) {
        super(dataManager, schedulerProvider, resourceProvider);
        getCompositeDisposable().add(
                Observable.combineLatest(getDataManager().postJobs().toObservable(), getDataManager().postLocations().toObservable(), Observable.timer(3, TimeUnit.SECONDS), (jobs, locations, timeSet) -> {
                    getDataManager().setJobs(jobs.body());
                    getDataManager().setLocations(locations.body());
                    return timeSet;
                })
                        .subscribe(timeSet -> checkAuth()));

    }

    private void checkAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            getNavigator().startLoginActivity();
//            getNavigator().startMainActivity();
        } else {
            getNavigator().startMainActivity();
        }
    }


}
