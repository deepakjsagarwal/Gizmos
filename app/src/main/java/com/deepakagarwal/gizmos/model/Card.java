package com.deepakagarwal.gizmos.model;

import java.util.ArrayList;

public class Card {

    public byte[] cost;      // 0 for color, 1 for cost
    // 0 - black, 1 - blue, 2 - red, 3 -yellow
    public byte effect;     // 0 converter 1 file 2 pick 3 build
    public byte[] effectColor;
    public byte[] result;    // 2 numbers: type and numOfTimes
    // 0 - vp, 1 - pick, 2 - random draw ball,
    public byte vp;
    public byte idNumber;
    public Boolean isActive;
    public boolean isItFiledCard;
    public boolean isItResearchedCard;
    public ArrayList<Integer> partialActivate;

    public Card(byte[] cost, byte effect, byte[] effectColor, byte[] result, byte vp,byte idNumber) {
        this.cost = cost;
        this.effect = effect;
        this.effectColor = effectColor;
        this.result = result;
        this.vp = vp;
        this.idNumber = idNumber;
        if(this.effect == 0){
            isActive = true;
        }
        else{
            isActive = null;
        }
        isItFiledCard = false;
        isItResearchedCard = false;
        partialActivate = new ArrayList<>();
    }
}
