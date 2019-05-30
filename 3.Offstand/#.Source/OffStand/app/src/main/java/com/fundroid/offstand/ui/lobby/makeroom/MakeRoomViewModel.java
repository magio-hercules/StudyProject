package com.fundroid.offstand.ui.lobby.makeroom;


import android.util.Log;

import com.fundroid.offstand.data.DataManager;
import com.fundroid.offstand.data.model.ApiBody;
import com.fundroid.offstand.data.remote.ConnectionManager;
import com.fundroid.offstand.model.User;
import com.fundroid.offstand.ui.base.BaseViewModel;
import com.fundroid.offstand.utils.rx.ClientPublishSubjectBus;
import com.fundroid.offstand.utils.rx.SchedulerProvider;
import com.google.gson.Gson;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;

import static com.fundroid.offstand.core.AppConstant.ROOM_PORT;
import static com.fundroid.offstand.data.remote.ApiDefine.API_ENTER_ROOM;
import static com.fundroid.offstand.data.remote.ApiDefine.API_ROOM_INFO;

public class MakeRoomViewModel extends BaseViewModel<MakeRoomNavigator> {

    public MakeRoomViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getCompositeDisposable().add(ClientPublishSubjectBus.getInstance().getEvents(String.class)
                .map(json -> new Gson().fromJson((String) json, ApiBody.class))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(result -> {
                    Log.d("lsc", "MakeRoomViewModel result " + result);
                    switch (((ApiBody) result).getNo()) {
                        case API_ROOM_INFO:
                            getNavigator().goToRoomActivity();
                            break;
                    }

                }, onError -> {
                    Log.d("lsc", "MakeRoomViewModel onError " + onError);
                }, () -> Log.d("lsc", "MakeRoomViewModel onCompleted"))
        );
    }

    public void makeRoomClick() {
        //Test
        createSocket(ROOM_PORT, 4);
    }

    public void createSocket(int roomPort, int roomMaxAttendee) {
        getCompositeDisposable().add(ConnectionManager.createServerThread(roomPort, roomMaxAttendee)
                .andThen(ConnectionManager.createClientThread(null, ROOM_PORT))
                .andThen(Completable.timer(500, TimeUnit.MILLISECONDS))
                .andThen(ConnectionManager.sendMessage(new ApiBody(API_ENTER_ROOM, new User(1, true, getDataManager().getUserName(), getDataManager().getUserAvatar(), getDataManager().getUserTotal(), getDataManager().getUserWin()))))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(() -> {
                    Log.d("lsc", "MakeRoomViewModel createSocket result ");
                }, onError -> {
                    Log.d("lsc", "MakeRoomViewModel createSocket onError " + onError);
                })
        );
    }



    public void onNavBackClick() {
        getNavigator().goBack();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("lsc", "MakeRoomViewModel enterRoom onCleared");
    }
}
