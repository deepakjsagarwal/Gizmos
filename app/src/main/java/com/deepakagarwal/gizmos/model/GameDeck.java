package com.deepakagarwal.gizmos.model;

import java.util.ArrayList;
import java.util.Random;

public class GameDeck {

    Card l1back;
    Card l2back;
    Card l3back;
    public ArrayList<Card>[] deckCards;
    public Card cardl0;

    public GameDeck(){
        deckCards = new ArrayList[3];
        for(int i = 0;i<3;i++){
            deckCards[i] = new ArrayList<Card>();
        }
    }
    // cost 0 for color, 1 for cost
    // color 0 - black, 1 - blue, 2 - red, 3 -yellow 4 - build a filed Card 5 -wild 6- build II
    // effect - 0 converter 1 file 2 pick 3 build 4 upgrade
    // result --> 2 numbers: type and numOfTimes
    // 0 - vp,                1 - pick,             2 - random draw ball,   3 - allow filing       4- wild ball(converter)          5 - same double
    // 6- build I for free    7-allow research      8- build II at -1       9 -build filed card at -1           10 - build research card at -1
    // 11 - no filing         12- no research      13 - normal upgrade      14 - wild to wild           15 - a-aa & b-bb


    public void reset(){

        cardl0 = new Card(null, (byte) 1,null,new byte[]{2,1},(byte)0,(byte)0);
                                            //byte[] cost,  effect,byte[] effectColor,byte[] result,byte vp,byte idNumber
                                                                                        // build -0,1 file - 1, converter-4, pick - 2
        deckCards[0].add(0, new Card(new byte[]{3, 1},(byte)3,new byte[]{1},new byte[]{0,1}, (byte) 1,(byte) 1));
        deckCards[0].add(1, new Card(new byte[]{3, 1},(byte)3,new byte[]{2},new byte[]{1,1}, (byte) 1,(byte) 2));
        deckCards[0].add(2, new Card(new byte[]{2, 1},(byte)3,new byte[]{0},new byte[]{1,1}, (byte) 1,(byte) 3));
        deckCards[0].add(3, new Card(new byte[]{2, 1},(byte)3,new byte[]{3},new byte[]{0,1}, (byte) 1,(byte) 4));
        deckCards[0].add(4, new Card(new byte[]{0, 1},(byte)3,new byte[]{2},new byte[]{0,1}, (byte) 1,(byte) 5));
        deckCards[0].add(5, new Card(new byte[]{0, 1},(byte)3,new byte[]{1},new byte[]{1,1}, (byte) 1,(byte) 6));
        deckCards[0].add(6, new Card(new byte[]{1, 1},(byte)3,new byte[]{0},new byte[]{0,1}, (byte) 1,(byte) 7));
        deckCards[0].add(7, new Card(new byte[]{1, 1},(byte)3,new byte[]{3},new byte[]{1,1}, (byte) 1,(byte) 8));

        deckCards[0].add(8, new Card(new byte[]{2, 1},(byte)1,null,new byte[]{1,1}, (byte) 1,(byte) 9));
        deckCards[0].add(9, new Card(new byte[]{0, 1},(byte)1,null,new byte[]{1,1}, (byte) 1,(byte) 10));
        deckCards[0].add(10,new Card(new byte[]{3, 1},(byte)1,null,new byte[]{1,1}, (byte) 1,(byte) 11));
        deckCards[0].add(11,new Card(new byte[]{1, 1},(byte)1,null,new byte[]{1,1}, (byte) 1,(byte) 12));

        deckCards[0].add(12,new Card(new byte[]{2, 1},(byte)0,new byte[]{0},new byte[]{4}, (byte) 1,(byte) 13));
        deckCards[0].add(13,new Card(new byte[]{2, 1},(byte)0,new byte[]{1},new byte[]{4}, (byte) 1,(byte) 14));
        deckCards[0].add(14,new Card(new byte[]{0, 1},(byte)0,new byte[]{2},new byte[]{4}, (byte) 1,(byte) 15));
        deckCards[0].add(15,new Card(new byte[]{0, 1},(byte)0,new byte[]{3},new byte[]{4}, (byte) 1,(byte) 16));
        deckCards[0].add(16,new Card(new byte[]{1, 1},(byte)0,new byte[]{3},new byte[]{4}, (byte) 1,(byte) 17));
        deckCards[0].add(17,new Card(new byte[]{1, 1},(byte)0,new byte[]{2},new byte[]{4}, (byte) 1,(byte) 18));
        deckCards[0].add(18,new Card(new byte[]{3, 1},(byte)0,new byte[]{0},new byte[]{4}, (byte) 1,(byte) 19));
        deckCards[0].add(19,new Card(new byte[]{3, 1},(byte)0,new byte[]{1},new byte[]{4}, (byte) 1,(byte) 20));

        deckCards[0].add(20,new Card(new byte[]{2, 1},(byte)2,new byte[]{3},new byte[]{2,1}, (byte) 1,(byte) 21));
        deckCards[0].add(21,new Card(new byte[]{2, 1},(byte)2,new byte[]{1},new byte[]{2,1}, (byte) 1,(byte) 22));
        deckCards[0].add(22,new Card(new byte[]{3, 1},(byte)2,new byte[]{2},new byte[]{2,1}, (byte) 1,(byte) 23));
        deckCards[0].add(23,new Card(new byte[]{3, 1},(byte)2,new byte[]{0},new byte[]{2,1}, (byte) 1,(byte) 24));
        deckCards[0].add(24,new Card(new byte[]{1, 1},(byte)2,new byte[]{2},new byte[]{2,1}, (byte) 1,(byte) 25));
        deckCards[0].add(25,new Card(new byte[]{1, 1},(byte)2,new byte[]{0},new byte[]{2,1}, (byte) 1,(byte) 26));
        deckCards[0].add(26,new Card(new byte[]{0, 1},(byte)2,new byte[]{3},new byte[]{2,1}, (byte) 1,(byte) 27));
        deckCards[0].add(27,new Card(new byte[]{0, 1},(byte)2,new byte[]{1},new byte[]{2,1}, (byte) 1,(byte) 28));

        deckCards[0].add(28,new Card(new byte[]{2, 1},(byte)4,null,new byte[]{13,1,0,1}, (byte) 1,(byte) 29));
        deckCards[0].add(29,new Card(new byte[]{2, 1},(byte)4,null,new byte[]{13,1,1,0}, (byte) 1,(byte) 30));
        deckCards[0].add(30,new Card(new byte[]{1, 1},(byte)4,null,new byte[]{13,1,0,1}, (byte) 1,(byte) 31));
        deckCards[0].add(31,new Card(new byte[]{1, 1},(byte)4,null,new byte[]{13,1,1,0}, (byte) 1,(byte) 32));
        deckCards[0].add(32,new Card(new byte[]{0, 1},(byte)4,null,new byte[]{13,1,0,1}, (byte) 1,(byte) 33));
        deckCards[0].add(33,new Card(new byte[]{0, 1},(byte)4,null,new byte[]{13,1,1,0}, (byte) 1,(byte) 34));
        deckCards[0].add(34,new Card(new byte[]{3, 1},(byte)4,null,new byte[]{13,1,0,1}, (byte) 1,(byte) 35));
        deckCards[0].add(35,new Card(new byte[]{3, 1},(byte)4,null,new byte[]{13,1,1,0}, (byte) 1,(byte) 36));


                                             //byte[] cost,  effect, byte[] effectColor,byte[] result,byte vp,byte idNumber
                                                                                        //build - 0,1 pick - 2 converter - 4,5
        deckCards[1].add(0, new Card(new byte[]{1,2},(byte)0,new byte[]{0,0},new byte[]{4,2},(byte)2, (byte) 37));
        deckCards[1].add(1, new Card(new byte[]{1,3},(byte)0,new byte[]{3},new byte[]{5},(byte)3, (byte) 38));
        deckCards[1].add(2, new Card(new byte[]{1,3},(byte)0,new byte[]{2},new byte[]{5},(byte)3, (byte) 39));

        deckCards[1].add(3, new Card(new byte[]{2,3},(byte)0,new byte[]{1},new byte[]{5},(byte)3, (byte) 40));
        deckCards[1].add(4, new Card(new byte[]{2,3},(byte)0,new byte[]{0},new byte[]{5},(byte)3, (byte) 41));
        deckCards[1].add(5, new Card(new byte[]{2,2},(byte)0,new byte[]{3,3},new byte[]{4,2},(byte)2, (byte) 42));

        deckCards[1].add(6, new Card(new byte[]{0,3},(byte)0,new byte[]{2},new byte[]{5},(byte)3, (byte) 43));
        deckCards[1].add(7, new Card(new byte[]{0,3},(byte)0,new byte[]{3},new byte[]{5},(byte)3, (byte) 44));
        deckCards[1].add(8, new Card(new byte[]{0,2},(byte)0,new byte[]{1,1},new byte[]{4,2},(byte)2, (byte) 45));

        deckCards[1].add(9, new Card(new byte[]{3,3},(byte)0,new byte[]{0},new byte[]{5},(byte)3, (byte) 46));
        deckCards[1].add(10,new Card(new byte[]{3,3},(byte)0,new byte[]{1},new byte[]{5},(byte)3, (byte) 47));
        deckCards[1].add(11,new Card(new byte[]{3,2},(byte)0,new byte[]{2,2},new byte[]{4,2},(byte)2, (byte) 48));

        deckCards[1].add(12,new Card(new byte[]{2,3},(byte)3,new byte[]{0,3},new byte[]{0,1},(byte)3, (byte) 49));
        deckCards[1].add(13,new Card(new byte[]{2,3},(byte)3,new byte[]{4},new byte[]{1,2},(byte)3, (byte) 50));
        deckCards[1].add(14,new Card(new byte[]{2,2},(byte)3,new byte[]{1,3},new byte[]{1,1},(byte)2, (byte) 51));
        deckCards[1].add(15,new Card(new byte[]{3,3},(byte)3,new byte[]{4},new byte[]{1,2},(byte)3, (byte) 52));
        deckCards[1].add(16,new Card(new byte[]{3,3},(byte)3,new byte[]{1,2},new byte[]{0,1},(byte)3, (byte) 53));
        deckCards[1].add(17,new Card(new byte[]{2,2},(byte)3,new byte[]{0,1},new byte[]{1,1},(byte)2, (byte) 54));
        deckCards[1].add(18,new Card(new byte[]{3,2},(byte)3,new byte[]{0,1},new byte[]{1,1},(byte)2, (byte) 55));
        deckCards[1].add(19,new Card(new byte[]{3,2},(byte)3,new byte[]{0,2},new byte[]{1,1},(byte)2, (byte) 56));
        deckCards[1].add(20,new Card(new byte[]{1,3},(byte)3,new byte[]{4},new byte[]{1,2},(byte)3, (byte) 57));
        deckCards[1].add(21,new Card(new byte[]{1,2},(byte)3,new byte[]{0,3},new byte[]{1,1},(byte)2, (byte) 58));
        deckCards[1].add(22,new Card(new byte[]{1,2},(byte)3,new byte[]{2,3},new byte[]{1,1},(byte)2, (byte) 59));
        deckCards[1].add(23,new Card(new byte[]{1,3},(byte)3,new byte[]{0,2},new byte[]{0,1},(byte)3, (byte) 60));
        deckCards[1].add(24,new Card(new byte[]{0,2},(byte)3,new byte[]{2,3},new byte[]{1,1},(byte)2, (byte) 61));
        deckCards[1].add(25,new Card(new byte[]{0,2},(byte)3,new byte[]{1,2},new byte[]{1,1},(byte)2, (byte) 62));
        deckCards[1].add(26,new Card(new byte[]{0,3},(byte)3,new byte[]{1,3},new byte[]{0,1},(byte)3, (byte) 63));


        deckCards[1].add(27,new Card(new byte[]{0,3},(byte)4,null,new byte[]{13,2,1,2},(byte)3, (byte) 64));
        deckCards[1].add(28,new Card(new byte[]{1,3},(byte)4,null,new byte[]{13,2,1,2},(byte)3, (byte) 65));
        deckCards[1].add(29,new Card(new byte[]{0,3},(byte)3,new byte[]{4},new byte[]{1,2},(byte)3, (byte) 66));
        deckCards[1].add(30,new Card(new byte[]{2,3},(byte)4,null,new byte[]{13,2,1,2},(byte)3, (byte) 67));
        deckCards[1].add(31,new Card(new byte[]{3,3},(byte)4,null,new byte[]{13,2,1,2},(byte)3, (byte) 68));


        deckCards[1].add(32,new Card(new byte[]{0,2},(byte)2,new byte[]{2,3},new byte[]{2,1},(byte)2, (byte) 69));
        deckCards[1].add(33,new Card(new byte[]{1,2},(byte)2,new byte[]{0,3},new byte[]{2,1},(byte)2, (byte) 70));
        deckCards[1].add(34,new Card(new byte[]{3,2},(byte)2,new byte[]{1,2},new byte[]{2,1},(byte)2, (byte) 71));
        deckCards[1].add(35,new Card(new byte[]{2,2},(byte)2,new byte[]{0,1},new byte[]{2,1},(byte)2, (byte) 72));

                                            //byte[] cost,  effect,byte[] effectColor,byte[] result,byte vp,byte idNumber
                                                                                    //converter -  14 15
        deckCards[2].add(0,new Card(new byte[]{0,5},(byte)0,new byte[]{1,3},new byte[]{15,2},(byte)5,(byte)73));
        deckCards[2].add(1,new Card(new byte[]{1,5},(byte)0,new byte[]{0,2},new byte[]{15,2},(byte)5,(byte)74));
        deckCards[2].add(2,new Card(new byte[]{2,4},(byte)0,new byte[]{5},new byte[]{14},(byte)4,(byte)75));
        deckCards[2].add(3,new Card(new byte[]{3,4},(byte)0,new byte[]{5},new byte[]{14},(byte)4,(byte)76));
                                                                                    //file -  0 2
        deckCards[2].add(4,new Card(new byte[]{0,4},(byte)1,null,new byte[]{0,1},(byte)4,(byte)77));
        deckCards[2].add(5,new Card(new byte[]{1,4},(byte)1,null,new byte[]{2,3},(byte)4,(byte)78));
        deckCards[2].add(6,new Card(new byte[]{2,4},(byte)1,null,new byte[]{0,1},(byte)4,(byte)79));
        deckCards[2].add(7,new Card(new byte[]{3,4},(byte)1,null,new byte[]{2,3},(byte)4,(byte)80));

                                                                                //Build - 0 1 3 6 7
        deckCards[2].add(8,new Card(new byte[]{0,5},(byte)3,new byte[]{1,3},new byte[]{3},(byte)5,(byte)81));
        deckCards[2].add(9,new Card(new byte[]{0,5},(byte)3,new byte[]{1,2},new byte[]{0,2},(byte)5,(byte)82));
        deckCards[2].add(10,new Card(new byte[]{0,6},(byte)3,new byte[]{6},new byte[]{1,2},(byte)6,(byte)83));

        deckCards[2].add(11,new Card(new byte[]{1,6},(byte)3,new byte[]{2,3},new byte[]{6},(byte)6,(byte)84));
        deckCards[2].add(12,new Card(new byte[]{1,7},(byte)3,new byte[]{2,3},new byte[]{7},(byte)7,(byte)85));

        deckCards[2].add(13,new Card(new byte[]{2,5},(byte)3,new byte[]{4},new byte[]{0,2},(byte)5,(byte)86));
        deckCards[2].add(14,new Card(new byte[]{2,5},(byte)3,new byte[]{0,3},new byte[]{0,2},(byte)5,(byte)87));
        deckCards[2].add(15,new Card(new byte[]{2,6},(byte)3,new byte[]{6},new byte[]{1,2},(byte)6,(byte)88));
        deckCards[2].add(16,new Card(new byte[]{2,7},(byte)3,new byte[]{0,1},new byte[]{7},(byte)7,(byte)89));

        deckCards[2].add(17,new Card(new byte[]{3,5},(byte)3,new byte[]{0,2},new byte[]{3},(byte)5,(byte)90));
        deckCards[2].add(18,new Card(new byte[]{3,5},(byte)3,new byte[]{4},new byte[]{0,2},(byte)5,(byte)91));

        deckCards[2].add(19,new Card(new byte[]{3,6},(byte)3,new byte[]{0,1},new byte[]{6},(byte)6,(byte)93));

                                                                                 //upgrade - 8,9,10,11,12,13
        deckCards[2].add(20,new Card(new byte[]{0,4},(byte)4,null,new byte[]{13,4,0,0},(byte)4,(byte)94));
        deckCards[2].add(21,new Card(new byte[]{0,4},(byte)4,null,new byte[]{12},(byte)8,(byte)95));
        deckCards[2].add(22,new Card(new byte[]{0,6},(byte)4,null,new byte[]{10},(byte)6,(byte)96));

        deckCards[2].add(23,new Card(new byte[]{1,4},(byte)4,null,new byte[]{13,4,0,0},(byte)4,(byte)97));
        deckCards[2].add(24,new Card(new byte[]{1,5},(byte)4,null,new byte[]{9},(byte)5,(byte)98));
        deckCards[2].add(25,new Card(new byte[]{1,5},(byte)4,null,new byte[]{8},(byte)5,(byte)99));
        deckCards[2].add(26,new Card(new byte[]{1,4},(byte)4,null,new byte[]{11},(byte)7,(byte)100));

        deckCards[2].add(27,new Card(new byte[]{2,5},(byte)4,null,new byte[]{9},(byte)5,(byte)101));
        deckCards[2].add(28,new Card(new byte[]{2,4},(byte)4,null,new byte[]{11},(byte)7,(byte)102));

        deckCards[2].add(29,new Card(new byte[]{3,4},(byte)4,null,new byte[]{12},(byte)8,(byte)103));
        deckCards[2].add(30,new Card(new byte[]{3,5},(byte)4,null,new byte[]{8},(byte)5,(byte)92));
        deckCards[2].add(31,new Card(new byte[]{3,6},(byte)4,null,new byte[]{10},(byte)6,(byte)104));







    }

    public void shuffle(){
        Random random = new Random();
        for(int y= 0 ; y<3;y++) {
            for (int i = 0; i < deckCards[y].size(); i++) {
                int randomValue = i + random.nextInt(deckCards[y].size() - i);
                Card randomCard = deckCards[y].get(randomValue);
                deckCards[y].set(randomValue, deckCards[y].get(i));
                deckCards[y].set(i, randomCard);
            }
        }
    }

    public Card drawCard(int x){
        if(x ==0 && deckCards[0].size() == 0) return l1back;
        if(x ==1 && deckCards[1].size() == 0) return l2back;
        if(x ==2 && deckCards[2].size() == 0) return l3back;
        Card card = deckCards[x].get(0);
        deckCards[x].remove(0);
        return card;
    }

    public int drawCardImage(Card card,int x){
        if(deckCards[x].size() <= 0) return 1000+x;
        return card.idNumber;
    }
}
