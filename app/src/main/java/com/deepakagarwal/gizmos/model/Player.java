package com.deepakagarwal.gizmos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Player implements Parcelable {
    public String name;
    public byte[] tokens;     // 0 black, 1 blue, 2 red, 3 yellow
    public byte[] virtualTokens;
    public byte[] afterBuildTokens;
    public byte[] limits;   // 0 token limit, 1 filing limit, 2 research limit
    public byte numOfCards = 1;
    public byte numOfL3Cards = 0;
    public ArrayList<Card>[] cards;
    public byte[] special;      // 0-random balls 1-build 1 free 2-build II -1 3- build III at discount = 0  4-build filed -1  5 - build research -1
    /*
    public ArrayList<Card> converterCards;
    public ArrayList<Card> fileCards;
    public ArrayList<Card> pickCards;
    public ArrayList<Card> buildCards;
    public ArrayList<Card> upgradeCards;*/
    public ArrayList<Card> reservedCards;
    public byte vpTokens = 0;
    public ArrayList<Boolean>[] isAllowed;  //0 research 1 file 2 pick 3 build
    public boolean isItFirstTime;

    public Player(){
        name = "";
        tokens = new byte[]{0,0,0,0};
        virtualTokens = new byte[]{0,0,0,0};
        afterBuildTokens = new byte[]{0,0,0,0};
        limits = new byte[]{5,1,3};
        cards = new ArrayList[5];
        for(int x =0; x<5;x++){
            cards[x] = new ArrayList<Card>();
        }
        reservedCards = new ArrayList<Card>();
        isAllowed = new ArrayList[6];
        for(int x =0; x<6;x++){
            isAllowed[x] = new ArrayList<Boolean>();
        }
        special = new byte[]{0,0,0,0,0,0};
    }

    protected Player(Parcel in) {
        name = in.readString();
        tokens = in.createByteArray();
        virtualTokens = in.createByteArray();
        afterBuildTokens = in.createByteArray();
        limits = in.createByteArray();
        numOfCards = in.readByte();
        numOfL3Cards = in.readByte();
        special = in.createByteArray();
        vpTokens = in.readByte();
        isItFirstTime = in.readByte() != 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public byte totalTokens(){
        byte sum = 0;
        for(int x = 0;x<4;x++){
            sum+=tokens[x];
        }
        return sum;
    }
    public byte getVP(){
        byte sum = 0;
        for(int x = 0;x<5;x++){
            for(int y= 0 ;y<cards[x].size();y++){
                sum+=cards[x].get(y).vp;
            }
        }
        sum+=vpTokens;
        return sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByteArray(tokens);
        dest.writeByteArray(virtualTokens);
        dest.writeByteArray(afterBuildTokens);
        dest.writeByteArray(limits);
        dest.writeByte(numOfCards);
        dest.writeByte(numOfL3Cards);
        dest.writeByteArray(special);
        dest.writeByte(vpTokens);
        dest.writeByte((byte) (isItFirstTime ? 1 : 0));
    }

    public byte getTokens(){
        byte sum = 0;
        for(int x = 0;x<4;x++){
            sum+=tokens[x];
        }
        return sum;
    }
}
