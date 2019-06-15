package com.fundroid.offstand.data.model;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

import com.fundroid.offstand.utils.rx.ClientPublishSubjectBus;

public class Room {

    public enum EnumStatus {

        SHUFFLE_NOT_AVAILABLE(0), SHUFFLE_AVAILABLE(1), INGAME(2), GAME_RESULT_AVAILABLE(3), REGAME(4);

        private int enumStatus;

        EnumStatus(int enumStatus) {
            this.enumStatus = enumStatus;
        }

        public int getEnumStatus() {
            return enumStatus;
        }
    }

    private String name;
    private String address;
    private EnumStatus roomStatus;

    public Room(String name, String address, EnumStatus roomStatus) {
        this.name = name;
        this.address = address;
        this.roomStatus = roomStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EnumStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(EnumStatus roomStatus) {
        this.roomStatus = roomStatus;
    }
}
