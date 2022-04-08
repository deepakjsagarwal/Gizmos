package com.deepakagarwal.gizmos;

import static com.deepakagarwal.gizmos.AskNumberOfPlayers.numOfPlayers;
import static com.deepakagarwal.gizmos.AskNumberOfPlayers.player;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.deepakagarwal.gizmos.model.Card;
import com.deepakagarwal.gizmos.model.GameDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameScreen extends AppCompatActivity {

    ImageView[][] cardImages;
    Card[][] cards;
    GameDeck deck;
    ImageView[] marketTokens;       // are random
    ImageView[] playerTokens;       // 0 black 1 blue 2 red 3 yellow
    TextView[] limitsForPlayer;     // 0 token 1 file 2 research
    TextView[] playerVP;       // 0 numOfCards 1 vp tokens 2 vp
    TextView playerNameVp;
    byte currentPlayer = 0;
    TextView currentPlayerName;
    LinearLayout[] playerCardsLayout;   // 0 converter 1 file 2 pick 3 build 4 reservedCards 5 upgrade
    public static Map<Integer,String> mapColorCodes;
    byte[] extraTokens;
    int levelCard;
    int whichCard;
    LinearLayout layoutCards;
    HorizontalScrollView scrollResearchCards;
    LinearLayout layout;
    ArrayList<Card> shownResearchCards = new ArrayList<Card>();
    ArrayList<Integer> tokens;
    LinearLayout[] layoutForChangingTokens;
    TextView drawRandom;
    TextView randomTokens;
    int[] a = new int[]{-1,-1};
    LinearLayout playerInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        playerNameVp = (TextView) findViewById(R.id.playerNameVp);
        drawRandom = (TextView) findViewById(R.id.drawRandom);
        randomTokens = (TextView) findViewById(R.id.randomBalls);

        cardImages = new ImageView[3][];
        cards = new Card[3][];

        cardImages[0] = new ImageView[4];
        cardImages[1] = new ImageView[3];
        cardImages[2] = new ImageView[2];
        cards[0] = new Card[4];
        cards[1] = new Card[3];
        cards[2] = new Card[2];

        cardImages[0][0] = findViewById(R.id.l11card);
        cardImages[0][1] = findViewById(R.id.l12card);
        cardImages[0][2] = findViewById(R.id.l13card);
        cardImages[0][3] = findViewById(R.id.l14card);
        cardImages[1][0] = findViewById(R.id.l21card);
        cardImages[1][1] = findViewById(R.id.l22card);
        cardImages[1][2] = findViewById(R.id.l23card);
        cardImages[2][0] = findViewById(R.id.l31card);
        cardImages[2][1] = findViewById(R.id.l32card);

        playerCardsLayout = new LinearLayout[6];
        playerCardsLayout[0] = findViewById(R.id.converterLayout);
        playerCardsLayout[1] = findViewById(R.id.fileLayout);
        playerCardsLayout[2] = findViewById(R.id.pickLayout);
        playerCardsLayout[3] = findViewById(R.id.buildLayout);
        playerCardsLayout[4] = findViewById(R.id.upgradeLayout);
        playerCardsLayout[5] = findViewById(R.id.reservedLayout);


        marketTokens = new ImageView[6];
        marketTokens[0] = findViewById(R.id.marketToken1);
        marketTokens[1] = findViewById(R.id.marketToken2);
        marketTokens[2] = findViewById(R.id.marketToken3);
        marketTokens[3] = findViewById(R.id.marketToken4);
        marketTokens[4] = findViewById(R.id.marketToken5);
        marketTokens[5] = findViewById(R.id.marketToken6);

        playerTokens = new ImageView[4];
        playerTokens[0] = findViewById(R.id.playerBlackToken);
        playerTokens[1] = findViewById(R.id.playerBlueToken);
        playerTokens[2] = findViewById(R.id.playerRedToken);
        playerTokens[3] = findViewById(R.id.playerYellowToken);

        limitsForPlayer = new TextView[3];
        limitsForPlayer[0] = findViewById(R.id.tokenLimit);
        limitsForPlayer[1] = findViewById(R.id.fileLimit);
        limitsForPlayer[2] = findViewById(R.id.researchLimit);


        playerVP = new TextView[3];
        playerVP[0] = findViewById(R.id.numberOfCards);
        playerVP[1] = findViewById(R.id.vpTokensInNumber);
        playerVP[2] = findViewById(R.id.playerVP);

        currentPlayerName = (TextView) findViewById(R.id.currentPlayerName);
        playerInfoText = (LinearLayout) findViewById(R.id.playerInfoText);

        layoutForChangingTokens = new LinearLayout[3];
        layoutForChangingTokens[0] = findViewById(R.id.layoutForTokenChange);
        layoutForChangingTokens[1] = findViewById(R.id.layoutForPlayerToken);
        layoutForChangingTokens[2] = findViewById(R.id.layoutForPlayerOptions);
        layoutForChangingTokens[0].setVisibility(View.INVISIBLE);

        tokens = new ArrayList<Integer>();
        for (int i = 0; i < 13; i++) {
            tokens.add(0);
            tokens.add(1);
            tokens.add(2);
            tokens.add(3);
        }

        for (int i = 0; i < 6; i++) {
            setImageMarketTokenWhilePick(marketTokens[i]);
        }


        deck = new GameDeck();
        deck.reset();
        deck.shuffle();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < cardImages[i].length; j++) {
                cards[i][j] = deck.drawCard(i);
                imageSetCard(cardImages[i][j], cards[i][j], i);
            }
        }

        mapColorCodes = new HashMap<Integer, String>();
        mapColorCodes.put(0, "black");
        mapColorCodes.put(1, "blue");
        mapColorCodes.put(2, "red");
        mapColorCodes.put(3, "yellow");
        extraTokens = new byte[]{0, 0, 0, 0};
        for(int x = 0;x<numOfPlayers;x++){
            player[x].cards[1].add(deck.cardl0);
        }
        setTableForPlayer(currentPlayer);
        currentPlayerName.setText(player[currentPlayer].name+"'s Turn");
        player[currentPlayer].isItFirstTime = true;
        for (int x = 0; x < 4; x++) {
            player[currentPlayer].isAllowed[x].clear();
            player[currentPlayer].isAllowed[x].add(true);
        }

        for (int y = 0; y < numOfPlayers; y++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textView.setText("" + player[y].name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInfo(v);
                }
            });
            textView.setTag("" + y);
            playerInfoText.addView(textView);
        }

    }

    public void setImageMarketTokenWhilePick(ImageView i){
        Random random = new Random();
        int index = random.nextInt(tokens.size());
        int color = tokens.get(index);
        switch(color){
            case 0:
                int c1 = getResources().getIdentifier("blacktoken","drawable",getPackageName());
                i.setImageResource(c1);
                i.setTag("0");
                break;
            case 1:
                int c2 = getResources().getIdentifier("bluetoken","drawable",getPackageName());
                i.setImageResource(c2);
                i.setTag("1");
                break;
            case 2:
                int c3 = getResources().getIdentifier("redtoken","drawable",getPackageName());
                i.setImageResource(c3);
                i.setTag("2");
                break;
            case 3:
                int c4 = getResources().getIdentifier("yellowtoken","drawable",getPackageName());
                i.setImageResource(c4);
                i.setTag("3");
                break;
        }
        tokens.remove(index);
    }

    public void changeCurrentPlayer(View view){
        if(player[currentPlayer].isItFirstTime){
            Toast.makeText(this, "Perform something.", Toast.LENGTH_SHORT).show();
        }
        else {
            for (int x = 0; x < 4; x++) {
                player[currentPlayer].isAllowed[x].clear();
            }
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < player[currentPlayer].cards[x].size(); y++) {
                    player[currentPlayer].cards[x].get(y).isActive = null;
                }
            }
            for (int y = 0; y < player[currentPlayer].cards[4].size(); y++) {
                if (player[currentPlayer].cards[4].get(y).isActive == null) ;
                else if (!player[currentPlayer].cards[4].get(y).isActive)
                    player[currentPlayer].cards[4].get(y).isActive = true;
            }
            player[currentPlayer].special = new byte[]{0, 0, 0, 0, 0, 0};


            extraTokens = new byte[]{0, 0, 0, 0};

            currentPlayer = (byte) ((++currentPlayer) % numOfPlayers);
            currentPlayerName.setText(player[currentPlayer].name + "'s Turn");

            for (int y = 0; y < player[currentPlayer].cards[0].size(); y++) {
                player[currentPlayer].cards[0].get(y).isActive = true;
                player[currentPlayer].cards[0].get(y).partialActivate.clear();
            }

            for (int x = 1; x < 4; x++) {
                for (int y = 0; y < player[currentPlayer].cards[x].size(); y++) {
                    player[currentPlayer].cards[x].get(y).isActive = null;
                }
            }
            for (int y = 0; y < player[currentPlayer].cards[4].size(); y++) {
                if (player[currentPlayer].cards[4].get(y).isActive == null) ;
                else if (!player[currentPlayer].cards[4].get(y).isActive)
                    player[currentPlayer].cards[4].get(y).isActive = true;
            }

            for (int x = 0; x < 4; x++) {
                player[currentPlayer].isAllowed[x].clear();
                player[currentPlayer].isAllowed[x].add(true);
            }
            player[currentPlayer].isItFirstTime = true;
            for (int x = 0; x < 4; x++) {
                player[currentPlayer].virtualTokens[x] = player[currentPlayer].tokens[x];
                player[currentPlayer].afterBuildTokens[x] = player[currentPlayer].tokens[x];
            }
            player[currentPlayer].special = new byte[]{0, 0, 0, 0, 0, 0};
            for (int x = 0; x < 3; x++) {
                for (int y = 3 - x; y >= 0; y--) {
                    cardImages[x][y].setVisibility(View.VISIBLE);
                    cardImages[x][y].bringToFront();
                }
            }
            if (layout == null) ;
            else {
                layout.setVisibility(View.INVISIBLE);
                for (int i = 0; i < 4; i++) {
                    playerCardsLayout[i].setVisibility(View.VISIBLE);
                    playerCardsLayout[i].bringToFront();
                }
            }
            setTableForPlayer(currentPlayer);
            MediaPlayer ringNextPlayer = MediaPlayer.create(GameScreen.this, R.raw.nextplayerturn);
            ringNextPlayer.start();
        }
    }
    public void changeCurrentPlayer() {
        for (int x = 0; x < 4; x++) {
            player[currentPlayer].isAllowed[x].clear();
        }
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < player[currentPlayer].cards[x].size(); y++) {
                player[currentPlayer].cards[x].get(y).isActive = null;
            }
        }
        for (int y = 0; y < player[currentPlayer].cards[4].size(); y++) {
            if (player[currentPlayer].cards[4].get(y).isActive == null) ;
            else if (!player[currentPlayer].cards[4].get(y).isActive)
                player[currentPlayer].cards[4].get(y).isActive = true;
        }
        player[currentPlayer].special = new byte[]{0, 0, 0, 0, 0, 0};
        extraTokens = new byte[]{0, 0, 0, 0};

        currentPlayer = (byte) ((++currentPlayer) % numOfPlayers);
        currentPlayerName.setText(player[currentPlayer].name + "'s Turn");

        for (int y = 0; y < player[currentPlayer].cards[0].size(); y++) {
            player[currentPlayer].cards[0].get(y).isActive = true;
            player[currentPlayer].cards[0].get(y).partialActivate.clear();
        }

        for (int x = 1; x < 4; x++) {
            for (int y = 0; y < player[currentPlayer].cards[x].size(); y++) {
                player[currentPlayer].cards[x].get(y).isActive = null;
            }
        }
        for (int y = 0; y < player[currentPlayer].cards[4].size(); y++) {
            if (player[currentPlayer].cards[4].get(y).isActive == null) ;
            else if (!player[currentPlayer].cards[4].get(y).isActive)
                player[currentPlayer].cards[4].get(y).isActive = true;
        }

        for (int x = 0; x < 4; x++) {
            player[currentPlayer].isAllowed[x].clear();
            player[currentPlayer].isAllowed[x].add(true);
        }
        player[currentPlayer].isItFirstTime = true;
        for (int x = 0; x < 4; x++) {
            player[currentPlayer].virtualTokens[x] = player[currentPlayer].tokens[x];
            player[currentPlayer].afterBuildTokens[x] = player[currentPlayer].tokens[x];
        }
        player[currentPlayer].special = new byte[]{0, 0, 0, 0, 0, 0};
        for (int x = 0; x < 3; x++) {
            for (int y = 3 - x; y >= 0; y--) {
                cardImages[x][y].setVisibility(View.VISIBLE);
                cardImages[x][y].bringToFront();
            }
        }
        if (layout == null) ;
        else {
            layout.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 4; i++) {
                playerCardsLayout[i].setVisibility(View.VISIBLE);
                playerCardsLayout[i].bringToFront();
            }
        }
        setTableForPlayer(currentPlayer);
        MediaPlayer ringNextPlayer = MediaPlayer.create(GameScreen.this, R.raw.nextplayerturn);
        ringNextPlayer.start();

        if(isGameOver()){
            if(currentPlayer == 0){
                Intent intent = new Intent(this,WinnerPage.class);
                intent.putExtra("Number of players",numOfPlayers);
                for(int x = 0;x<numOfPlayers;x++) {
                    intent.putExtra("Player "+x+" Details", (Parcelable) player[x]);
                }
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this, player[currentPlayer].name+", It is your last turn.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("value",-1);
                if( result == 0){
                    buildingCard(levelCard,whichCard);
                }
                else if(result == 1){
                    filingCard(levelCard,whichCard);
                }
                else if(result == 2){
                    buildingCard(cardNumberReserved);
                }
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("level",-1);
                if(result>=0){
                    researchCard(result);
                }
            }
        }

        else if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String tag = data.getStringExtra("tag");
                int value = Integer.parseInt(tag);
                int result = data.getIntExtra("value",-1);
                if( result == 0){
                    buildingResearchCard(value);
                }
                else if(result == 1){
                    filingResearchCard(value);
                }
            }
        }

    }
    int cardNumberReserved;
    public void pick(View view){
        try{
           if(player[currentPlayer].limits[0]>player[currentPlayer].totalTokens() && player[currentPlayer].isAllowed[2].get(0)) {
                player[currentPlayer].tokens[Integer.parseInt(String.valueOf(view.getTag()))]++;
                player[currentPlayer].virtualTokens[Integer.parseInt(String.valueOf(view.getTag()))]++;
                player[currentPlayer].afterBuildTokens[Integer.parseInt(String.valueOf(view.getTag()))]++;
                checkActiveCardsWhilePickAndFile(currentPlayer, Byte.parseByte(String.valueOf(view.getTag())), (byte) 2);

                setImageMarketTokenWhilePick((ImageView)view);

                if(player[currentPlayer].isItFirstTime) {
                    for(int x = 0; x<4;x++){
                        player[currentPlayer].isAllowed[x].clear();
                    }
                    player[currentPlayer].isItFirstTime = false;
                }
                else{
                    player[currentPlayer].isAllowed[2].remove(0);
                }
                if(!checkActivePlayer()){
                    changeCurrentPlayer();
                }
                setTableForPlayer(currentPlayer);
            }
            else{
                Toast.makeText(this, "You can't pick more. You can't cross the limit.", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(this, "You can't pick more.", Toast.LENGTH_SHORT).show();
        }
    }
    public void build(View view) {
        String s = String.valueOf(view.getTag());

        Card c = null;

        if(s.length() == 2) {
            levelCard = Integer.parseInt(String.valueOf(s.charAt(0)));
            whichCard = Integer.parseInt(String.valueOf(s.charAt(1)));
            c = cards[levelCard][whichCard];
        }
        else if(s.length() == 1){
            cardNumberReserved = Integer.parseInt(s);
            c = player[currentPlayer].reservedCards.get(cardNumberReserved);
        }
        Intent intent = new Intent(this, AskForFileOrBuild.class);

            if (c.isItFiledCard)
                intent.putExtra("isItFiledCard", 1);
            else
                intent.putExtra("isItFiledCard", 0);
            startActivityForResult(intent, 1);
    }
    public void research(View view){
        try{
            if(player[currentPlayer].isAllowed[0].get(0) && player[currentPlayer].limits[2] != 0) {
                Intent intent = new Intent(this, Research.class);
                startActivityForResult(intent, 2);
            }
            else{
                Toast.makeText(this, "Your research limit is 0.", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(this, "You can't research more.", Toast.LENGTH_SHORT).show();
        }

    }

    public void researchCard(int result){

        layoutCards = findViewById(R.id.layoutResearchCards);
        scrollResearchCards = findViewById(R.id.scrollResearchCards);
        layout = findViewById(R.id.layout);

        layoutCards.removeAllViews();
        layout.setVisibility(View.VISIBLE);
        layout.bringToFront();

        for(int x = 0; x<3;x++){
            for(int y = 3-x; y>=0;y--){
                cardImages[x][y].setVisibility(View.INVISIBLE);
            }
        }
        shownResearchCards.clear();
        for(int x = 0;x<player[currentPlayer].limits[2];x++){
            Card c = deck.drawCard(result);
            c.isItResearchedCard = true;
            shownResearchCards.add(c);

            ImageView imageView = new ImageView(this);
            int cardImageCode = getResources().getIdentifier("c"+shownResearchCards.get(x).idNumber,"drawable",getPackageName());
            imageView.setImageResource(cardImageCode);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,LinearLayout.LayoutParams.WRAP_CONTENT);//300 wrap
            imageView.setLayoutParams(layoutParams);
            imageView.setTag(x);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildResearchCards(v);
                }
            });
            layoutCards.addView(imageView);
        }

        if (player[currentPlayer].isItFirstTime) {
            for (int i = 0; i < 4; i++) {
                player[currentPlayer].isAllowed[i].clear();
            }
            player[currentPlayer].isItFirstTime = false;
        } else {
            player[currentPlayer].isAllowed[0].remove(0);
        }
    }
    public void buildResearchCards(View view){
        String s = String.valueOf(view.getTag());

        Intent intent = new Intent(this, AskForFileOrBuild.class);
        intent.putExtra("tag",s);
        startActivityForResult(intent, 3);
    }
    public void cancelResearch(View view){
        int level = 0;
        if(shownResearchCards.get(0).idNumber >72) level =2;
        else if(shownResearchCards.get(0).idNumber >36) level =1;
        for(int i = 0 ;i<shownResearchCards.size();i++){
            deck.deckCards[level].add(shownResearchCards.get(i));
        }
        shownResearchCards.clear();

        for(int x = 0; x<3;x++){
            for(int y = 3-x; y>=0;y--){
                cardImages[x][y].setVisibility(View.VISIBLE);
            }
        }
        layout.setVisibility(View.INVISIBLE);
        if(!checkActivePlayer()){
            changeCurrentPlayer();
        }
        setTableForPlayer(currentPlayer);
    }

    public void buildingCard(int x, int y){
        try {
            if (player[currentPlayer].isAllowed[3].get(0)) {
                int level = 0;
                if(cards[x][y].idNumber >72) level =2;
                else if(cards[x][y].idNumber >36) level =1;
                byte cost = (byte) (cards[x][y].cost[1] - player[currentPlayer].special[level+1]);
                if(player[currentPlayer].virtualTokens[cards[x][y].cost[0]] >= cost ) {
                    checkActiveCardsWhileBuild(currentPlayer, cards[x][y]);
                    player[currentPlayer].special[level+1] =0;
                    player[currentPlayer].numOfCards++;
                    if (cards[x][y].idNumber > 72 && cards[x][y].idNumber < 109)
                        player[currentPlayer].numOfL3Cards++;

                    if (cards[x][y].effect == 4) {
                        if(cards[x][y].result[0] == 13) {
                            player[currentPlayer].limits[0] += cards[x][y].result[1];
                            if(player[currentPlayer].limits[1] !=0)
                                player[currentPlayer].limits[1] += cards[x][y].result[2];
                            if(player[currentPlayer].limits[2] !=0)
                                player[currentPlayer].limits[2] += cards[x][y].result[3];
                        }
                        else if(cards[x][y].result[0] == 11){
                            player[currentPlayer].limits[1] =0;
                        }
                        else if(cards[x][y].result[0] == 12){
                            player[currentPlayer].limits[2] =0;
                        }
                        else{
                            cards[x][y].isActive = true;
                        }
                    }

                    player[currentPlayer].virtualTokens[cards[x][y].cost[0]]-=cost;
                    for(int i = 0; i<4;i++){
                        for(int j= 0;j<player[currentPlayer].tokens[i] - Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);j++){
                            tokens.add(i);
                        }
                    }
                    for(int i = 0;i<4;i++){
                        player[currentPlayer].tokens[i] = (byte) Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);
                        player[currentPlayer].virtualTokens[i] =player[currentPlayer].tokens[i];
                        player[currentPlayer].afterBuildTokens[i]  =player[currentPlayer].tokens[i];
                    }
                    extraTokens =new byte[]{0,0,0,0};
                    player[currentPlayer].cards[cards[x][y].effect].add(cards[x][y]);
                    cards[x][y] = deck.drawCard(x);
                    imageSetCard(cardImages[x][y], cards[x][y], x);
                    if (player[currentPlayer].isItFirstTime) {
                        for (int i = 0; i < 4; i++) {
                            player[currentPlayer].isAllowed[i].clear();
                        }
                        player[currentPlayer].isItFirstTime = false;
                    } else {
                        player[currentPlayer].isAllowed[3].remove(0);
                    }
                    for (int i = 0; i < player[currentPlayer].cards[0].size(); i++) {
                        if (player[currentPlayer].cards[0].get(i).isActive == null) {
                            player[currentPlayer].cards[0].get(i).partialActivate.clear();
                            player[currentPlayer].cards[0].get(i).isActive = false;
                        }
                        else if(player[currentPlayer].cards[0].get(i).isActive && player[currentPlayer].cards[0].get(i).result.length ==2 ){
                            if( player[currentPlayer].cards[0].get(i).partialActivate.size() == 1){
                                player[currentPlayer].cards[0].get(i).partialActivate.add(-1);
                            }
                            else if(player[currentPlayer].cards[0].get(i).partialActivate.size() == 2 &&player[currentPlayer].cards[0].get(i).partialActivate.get(1)>=0 ){
                                player[currentPlayer].cards[0].get(i).partialActivate.clear();
                                player[currentPlayer].cards[0].get(i).isActive = false;
                            }
                        }
                    }
                    if(!checkActivePlayer()){
                        changeCurrentPlayer();
                    }
                    setTableForPlayer(currentPlayer);
                }
                else{
                    Toast.makeText(this, "You don't have enough resources.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "You can not build a card.", Toast.LENGTH_SHORT).show();
        }
    }
    public void buildingCard(int x){
        try {
            if (player[currentPlayer].isAllowed[3].get(0)) {
                Card c = player[currentPlayer].reservedCards.get(x);
                int level = 0;
                if(c.idNumber >72) level =2;
                else if(c.idNumber >36) level =1;
                byte cost = (byte) (c.cost[1] - player[currentPlayer].special[level+1] - player[currentPlayer].special[4]);
                if(player[currentPlayer].virtualTokens[c.cost[0]] >= cost ) {
                    checkActiveCardsWhileBuild(currentPlayer, c);
                    player[currentPlayer].special[level+1] = 0;
                    player[currentPlayer].special[4] = 0;
                    player[currentPlayer].numOfCards++;
                    if (c.idNumber > 72 && c.idNumber < 109)
                        player[currentPlayer].numOfL3Cards++;

                    if (c.effect == 4) {
                        if(c.result[0] == 13) {
                            player[currentPlayer].limits[0] += c.result[1];
                            if(player[currentPlayer].limits[1] !=0)
                                player[currentPlayer].limits[1] += c.result[2];
                            if(player[currentPlayer].limits[2] !=0)
                                player[currentPlayer].limits[2] += c.result[3];
                        }
                        else if(c.result[0] == 11){
                            player[currentPlayer].limits[1] =0;
                        }
                        else if(c.result[0] == 12){
                            player[currentPlayer].limits[2] =0;
                        }
                        else{
                            c.isActive = true;
                        }
                    }

                    player[currentPlayer].virtualTokens[c.cost[0]]-=cost;
                    for(int i = 0; i<4;i++){
                        for(int j= 0;j<player[currentPlayer].tokens[i] - Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);j++){
                            tokens.add(i);
                        }
                    }
                    for(int i = 0;i<4;i++){
                        player[currentPlayer].tokens[i] = (byte) Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);
                        player[currentPlayer].virtualTokens[i] =player[currentPlayer].tokens[i];
                        player[currentPlayer].afterBuildTokens[i]  =player[currentPlayer].tokens[i];
                    }

                    c.isItFiledCard =false;
                    extraTokens =new byte[]{0,0,0,0};
                    player[currentPlayer].cards[c.effect].add(c);
                    player[currentPlayer].reservedCards.remove(x);

                    if (player[currentPlayer].isItFirstTime) {
                        for (int i = 0; i < 4; i++) {
                            player[currentPlayer].isAllowed[i].clear();
                        }
                        player[currentPlayer].isItFirstTime = false;
                    } else {
                        player[currentPlayer].isAllowed[3].remove(0);
                    }

                    for (int i = 0; i < player[currentPlayer].cards[0].size(); i++) {
                        if (player[currentPlayer].cards[0].get(i).isActive == null) {
                            player[currentPlayer].cards[0].get(i).partialActivate.clear();
                            player[currentPlayer].cards[0].get(i).isActive = false;
                        }
                        else if(player[currentPlayer].cards[0].get(i).isActive && player[currentPlayer].cards[0].get(i).result.length ==2 ){
                            if( player[currentPlayer].cards[0].get(i).partialActivate.size() == 1){
                                player[currentPlayer].cards[0].get(i).partialActivate.add(-1);
                            }
                            else if(player[currentPlayer].cards[0].get(i).partialActivate.size() == 2 &&player[currentPlayer].cards[0].get(i).partialActivate.get(1)>=0 ){
                                player[currentPlayer].cards[0].get(i).partialActivate.clear();
                                player[currentPlayer].cards[0].get(i).isActive = false;
                            }
                        }
                    }

                    if(!checkActivePlayer()){
                        changeCurrentPlayer();
                    }
                    setTableForPlayer(currentPlayer);
                }
                else{
                    Toast.makeText(this, "You don't have enough resources.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "You can not build a card.", Toast.LENGTH_SHORT).show();
        }
    }
    public void buildingResearchCard(int index){

        Card c = shownResearchCards.get(index);
        int level = 0;
        if(c.idNumber >72) level =2;
        else if(c.idNumber >36) level =1;
        byte cost = (byte) (c.cost[1] - player[currentPlayer].special[level+1] - player[currentPlayer].special[5]);
        if(player[currentPlayer].virtualTokens[c.cost[0]] >= cost ) {
            checkActiveCardsWhileBuild(currentPlayer, c);
            player[currentPlayer].special[level+1] = 0;
            player[currentPlayer].special[5] = 0;
            player[currentPlayer].numOfCards++;
            if (c.idNumber > 72 && c.idNumber < 109)
                player[currentPlayer].numOfL3Cards++;

            if (c.effect == 4) {
                if(c.result[0] == 13) {
                    player[currentPlayer].limits[0] += c.result[1];
                    if(player[currentPlayer].limits[1] !=0)
                        player[currentPlayer].limits[1] += c.result[2];
                    if(player[currentPlayer].limits[2] !=0)
                        player[currentPlayer].limits[2] += c.result[3];
                }
                else if(c.result[0] == 11){
                    player[currentPlayer].limits[1] =0;
                }
                else if(c.result[0] == 12){
                    player[currentPlayer].limits[2] =0;
                }
                else{
                    c.isActive = true;
                }
            }
            player[currentPlayer].virtualTokens[c.cost[0]]-=cost;
            for(int i = 0; i<4;i++){
                for(int j= 0;j<player[currentPlayer].tokens[i] - Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);j++){
                    tokens.add(i);
                }
            }
            for(int i = 0;i<4;i++){
                player[currentPlayer].tokens[i] = (byte) Math.min(player[currentPlayer].virtualTokens[i], player[currentPlayer].afterBuildTokens[i]);
                player[currentPlayer].virtualTokens[i] =player[currentPlayer].tokens[i];
                player[currentPlayer].afterBuildTokens[i]  =player[currentPlayer].tokens[i];
            }
            c.isItResearchedCard = false;
            extraTokens =new byte[]{0,0,0,0};
            player[currentPlayer].cards[c.effect].add(c);

            for (int i = 0; i < player[currentPlayer].cards[0].size(); i++) {
                if (player[currentPlayer].cards[0].get(i).isActive == null) {
                    player[currentPlayer].cards[0].get(i).partialActivate.clear();
                    player[currentPlayer].cards[0].get(i).isActive = false;
                }
                else if(player[currentPlayer].cards[0].get(i).isActive && player[currentPlayer].cards[0].get(i).result.length ==2 ){
                    if( player[currentPlayer].cards[0].get(i).partialActivate.size() == 1){
                        player[currentPlayer].cards[0].get(i).partialActivate.add(-1);
                    }
                    else if(player[currentPlayer].cards[0].get(i).partialActivate.size() == 2 &&player[currentPlayer].cards[0].get(i).partialActivate.get(1)>=0 ){
                        player[currentPlayer].cards[0].get(i).partialActivate.clear();
                        player[currentPlayer].cards[0].get(i).isActive = false;
                    }
                }
            }
            if(!checkActivePlayer()){
                changeCurrentPlayer();
            }
            setTableForPlayer(currentPlayer);

            shownResearchCards.remove(index);
            for (int x = 0; x < shownResearchCards.size(); x++) {
                shownResearchCards.get(x).isItResearchedCard = false;
                deck.deckCards[level].add(shownResearchCards.get(x));
            }

            for (int x = 0; x < 3; x++) {
                for (int y = 3 - x; y >= 0; y--) {
                    cardImages[x][y].setVisibility(View.VISIBLE);
                }
            }
            layout.setVisibility(View.INVISIBLE);
        }
        else{
            Toast.makeText(this, "You don't have enough resources.", Toast.LENGTH_SHORT).show();
        }
    }

    public void filingCard(int x, int y){
        try {
            if (player[currentPlayer].isAllowed[1].get(0)  && player[currentPlayer].limits[1] != 0) {
                if(player[currentPlayer].limits[1]>player[currentPlayer].reservedCards.size()) {

                    checkActiveCardsWhilePickAndFile(currentPlayer, cards[x][y].cost[0], (byte) 1);
                    cards[x][y].isItFiledCard = true;
                    player[currentPlayer].reservedCards.add(cards[x][y]);
                    cards[x][y] = deck.drawCard(x);
                    imageSetCard(cardImages[x][y], cards[x][y], x);
                    if (player[currentPlayer].isItFirstTime) {
                        for (int i = 0; i < 4; i++) {
                            player[currentPlayer].isAllowed[i].clear();
                        }
                        player[currentPlayer].isItFirstTime = false;
                    } else {
                        player[currentPlayer].isAllowed[1].remove(0);
                    }
                    if(!checkActivePlayer()){
                        changeCurrentPlayer();
                    }
                    setTableForPlayer(currentPlayer);
                }
                else{
                    Toast.makeText(this, "You can't cross the limit.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "You can not file a card.", Toast.LENGTH_SHORT).show();
        }
    }
    public void filingResearchCard(int index){

        if(player[currentPlayer].limits[1]>player[currentPlayer].reservedCards.size()) {
            Card c = shownResearchCards.get(index);
            c.isItFiledCard = true;
            checkActiveCardsWhilePickAndFile(currentPlayer, c.cost[0], (byte) 1);
            player[currentPlayer].reservedCards.add(c);
            if(!checkActivePlayer()){
                changeCurrentPlayer();
            }
            setTableForPlayer(currentPlayer);

            int level = 0;
            if (c.idNumber > 36 && c.idNumber < 73) level = 1;
            else if (c.idNumber > 72 && c.idNumber < 109) level = 2;
            shownResearchCards.remove(index);
            for (int x = 0; x < shownResearchCards.size(); x++) {
                deck.deckCards[level].add(shownResearchCards.get(x));
            }

            for (int x = 0; x < 3; x++) {
                for (int y = 3 - x; y >= 0; y--) {
                    cardImages[x][y].setVisibility(View.VISIBLE);
                }
            }
            layout.setVisibility(View.INVISIBLE);
        }
        else{
            Toast.makeText(this, "You can't cross the limit.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickPlayerChosenToken(int x,int y,int colorCode,int colorMade){
        if(extraTokens[colorCode]>0){
            extraTokens[colorCode]--;
            extraTokens[colorMade]++;
        }
        else if(player[currentPlayer].tokens[colorCode]>0){
            player[currentPlayer].afterBuildTokens[colorCode]--;
            extraTokens[colorMade]++;
        }
        player[currentPlayer].cards[x].get(y).partialActivate.add(colorCode);
        if(player[currentPlayer].cards[x].get(y).result.length == 2) {
            if (player[currentPlayer].cards[x].get(y).partialActivate.size() ==3)
                player[currentPlayer].cards[x].get(y).isActive = null;
            else if(player[currentPlayer].cards[x].get(y).partialActivate.size() ==2 && player[currentPlayer].cards[x].get(y).partialActivate.get(1)>=0){
                player[currentPlayer].cards[x].get(y).isActive = null;
            }
        }
        else{
            player[currentPlayer].cards[x].get(y).isActive = null;
        }
        for(int i = 0;i<4;i++){
            player[currentPlayer].virtualTokens[i] = (byte) (player[currentPlayer].afterBuildTokens[i] + extraTokens[i]);
        }
        for(int i = 0;i<4;i++){
            playerCardsLayout[i].setVisibility(View.VISIBLE);
        }
        layoutForChangingTokens[0].setVisibility(View.INVISIBLE);

        setTableForPlayer(currentPlayer);

    }
    public void cancelTokenChange(View view){
        for(int x = 0;x<4;x++){
            playerCardsLayout[x].setVisibility(View.VISIBLE);
        }
        layoutForChangingTokens[0].setVisibility(View.INVISIBLE);
        a = new int[]{-1,-1};
    }

    public void checkActiveCardsWhilePickAndFile(byte playerCode, byte color,byte effect){
        byte numOfCards = (byte) player[playerCode].cards[effect].size();
        if(effect == 1){
            for(int x = 0 ;x<numOfCards;x++){
                if (player[playerCode].cards[effect].get(x).isActive == null) {
                    player[playerCode].cards[effect].get(x).isActive = true;
                }
            }
        }
        else if(effect ==2) {
            for (byte x = 0; x < numOfCards; x++) {
                boolean b = false;
                for (byte y = 0; y < player[playerCode].cards[effect].get(x).effectColor.length; y++) {

                    if (color == player[playerCode].cards[effect].get(x).effectColor[y]) {
                        b = true;
                        break;
                    }
                }
                if (player[playerCode].cards[effect].get(x).isActive == null && b) {
                    player[playerCode].cards[effect].get(x).isActive = true;
                }
            }
        }
    }
    public void checkActiveCardsWhileBuild(byte playerCode,Card c){
        byte color = c.cost[0];
        byte numOfCards = (byte) player[playerCode].cards[3].size();
        byte level = 0;
        if(c.idNumber >72) level =2;
        else if(c.idNumber >36) level =1;
        for (byte x = 0; x < numOfCards; x++) {
            boolean b = false;
            for (byte y = 0; y < player[playerCode].cards[3].get(x).effectColor.length; y++) {
                if(player[playerCode].cards[3].get(x).effectColor[y] == 4){
                    if(c.isItFiledCard){
                        b =true;
                        break;
                    }
                }
                else if(player[playerCode].cards[3].get(x).effectColor[y] == 6){
                    if(level == 1){
                        b =true;
                        break;
                    }
                }
                else if (color == player[playerCode].cards[3].get(x).effectColor[y]) {
                    b = true;
                    break;
                }
            }
            if (player[playerCode].cards[3].get(x).isActive == null && b) {
                player[playerCode].cards[3].get(x).isActive = true;
            }
        }
    }

    public void click(View view){
        String s = (String) view.getTag();
        byte x = Byte.parseByte(String.valueOf(s.charAt(0)));
        byte y = Byte.parseByte(String.valueOf(s.charAt(1)));

            Card c = player[currentPlayer].cards[x].get(y);
            if(c.isActive == null){
                Toast.makeText(this, "This is not an Active Card", Toast.LENGTH_SHORT).show();
            }
            else if (c.isActive) {
                switch (c.result[0]) {
                    // 0 - vp,                1 - pick,             2 - random draw ball,   3 - allow filing       ++4- wild ball(converter)   ++5 - same double
                    // 6- build I for free    7-allow research      8- build II at -1       9 -build filed card at -1           10 - build research card at -1
                    // 11 - no filing         12- no research
                    case 0:
                        player[currentPlayer].vpTokens += c.result[1];
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        break;
                    case 1:
                        for(int i = 0;i<c.result[1];i++) {
                            player[currentPlayer].isAllowed[2].add(true);
                        }
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Now you can pick "+c.result[1]+" token.", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        player[currentPlayer].special[0]+=c.result[1];
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "You have given "+c.result[1]+" random draws.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        player[currentPlayer].isAllowed[1].add(true);
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Perform File Action", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        if(player[currentPlayer].virtualTokens[c.effectColor[0]] > 0) {
                            layoutForChangingTokens[0].setVisibility(View.VISIBLE);

                            for(int i = 0 ;i<4;i++){
                                playerCardsLayout[i].setVisibility(View.INVISIBLE);
                            }
                            layoutForChangingTokens[0].bringToFront();
                            layoutForChangingTokens[1].removeAllViews();
                            layoutForChangingTokens[2].removeAllViews();
                            ImageView imageView = new ImageView(this);
                            //Toast.makeText(this, "map "+ GameScreen.mapColorCodes.get(colorCode), Toast.LENGTH_SHORT).show();
                            int cardImageCode = getResources().getIdentifier(mapColorCodes.get((int)c.effectColor[0]) + "token", "drawable", getPackageName());
                            //Toast.makeText(this, "Again map "+ GameScreen.mapColorCodes.get(colorCode), Toast.LENGTH_SHORT).show();
                            imageView.setImageResource(cardImageCode);

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(140 , 140);
                            imageView.setLayoutParams(layoutParams);
                            layoutForChangingTokens[1].addView(imageView);

                            for (int i = 0; i < 4; i++) {
                                if (i == c.effectColor[0]) continue;
                                ImageView imageView1 = new ImageView(this);
                                int cardImageCode1 = getResources().getIdentifier(mapColorCodes.get(i) + "token", "drawable", getPackageName());
                                imageView1.setImageResource(cardImageCode1);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(140, 140);
                                imageView1.setLayoutParams(layoutParams1);
                                imageView1.setTag(""+i);
                                layoutForChangingTokens[2].addView(imageView1);
                                imageView1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickPlayerChosenToken(x,y,c.effectColor[0],Integer.parseInt((String) v.getTag()));
                                    }
                                });
                            }

                        }
                        else{
                            Toast.makeText(this, "You don't have this token to convert.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5:
                        if(extraTokens[c.effectColor[0]] > 0){
                            extraTokens[c.effectColor[0]]++;
                            player[currentPlayer].cards[x].get(y).isActive = null;
                            player[currentPlayer].cards[x].get(y).partialActivate.add(1);
                            Toast.makeText(this, "Conversion done.", Toast.LENGTH_SHORT).show();
                        }
                        else if(player[currentPlayer].tokens[c.effectColor[0]] > 0){
                            player[currentPlayer].afterBuildTokens[c.effectColor[0]]--;
                            extraTokens[c.effectColor[0]]+=2;
                            player[currentPlayer].cards[x].get(y).isActive = null;
                            player[currentPlayer].cards[x].get(y).partialActivate.add(1);
                            Toast.makeText(this, "Conversion done.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "You don't have this token to convert.", Toast.LENGTH_SHORT).show();
                        }
                        for(int i = 0;i<4;i++){
                            player[currentPlayer].virtualTokens[i] = (byte) (player[currentPlayer].afterBuildTokens[i] + extraTokens[i]);
                        }
                        break;
                    case 6:
                        player[currentPlayer].special[1]++;
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Click on Level 1 Card to build for free.", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        player[currentPlayer].isAllowed[0].add(true);
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Perform Research Action", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        player[currentPlayer].special[2]++;
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Discount of 1 token for Level 2 Card.", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        player[currentPlayer].special[4]++;
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Discount of 1 token for Building Filed Card.", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        player[currentPlayer].special[5]++;
                        player[currentPlayer].cards[x].get(y).isActive = false;
                        Toast.makeText(this, "Discount of 1 token for Building Researched Card.", Toast.LENGTH_SHORT).show();
                        break;

                    case 14:
                        layoutForChangingTokens[1].removeAllViews();
                        layoutForChangingTokens[2].removeAllViews();
                        if(player[currentPlayer].totalTokens() >0) {
                            layoutForChangingTokens[0].setVisibility(View.VISIBLE);

                            for(int i = 0 ;i<4;i++){
                                playerCardsLayout[i].setVisibility(View.INVISIBLE);
                            }
                            layoutForChangingTokens[0].bringToFront();
                            for (int i = 0; i < 4; i++) {
                                if (player[currentPlayer].virtualTokens[i] > 0) {
                                    ImageView imageView1 = new ImageView(this);
                                    int cardImageCode1 = getResources().getIdentifier(mapColorCodes.get(i) + "token", "drawable", getPackageName());
                                    imageView1.setImageResource(cardImageCode1);
                                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(140, 140);
                                    imageView1.setLayoutParams(layoutParams1);
                                    imageView1.setTag("" + i);
                                    layoutForChangingTokens[1].addView(imageView1);
                                    imageView1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            playerChosenToken(v);
                                        }
                                    });
                                }
                            }
                            for(int i=0;i<4;i++){
                                ImageView imageView1 = new ImageView(this);
                                int cardImageCode1 = getResources().getIdentifier(mapColorCodes.get(i) + "token", "drawable", getPackageName());
                                imageView1.setImageResource(cardImageCode1);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(140, 140);
                                imageView1.setLayoutParams(layoutParams1);
                                imageView1.setTag("" + i);
                                layoutForChangingTokens[2].addView(imageView1);
                                imageView1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        playerChangedToken(v,x,y);
                                    }
                                });
                            }
                        }
                        else{
                            Toast.makeText(this, "You don't have any token to convert.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 15:
                        layoutForChangingTokens[1].removeAllViews();
                        layoutForChangingTokens[2].removeAllViews();
                        if(c.partialActivate.size() == 0) {
                            layoutForChangingTokens[0].setVisibility(View.VISIBLE);

                            for(int i = 0 ;i<4;i++){
                                playerCardsLayout[i].setVisibility(View.INVISIBLE);
                            }
                            layoutForChangingTokens[0].bringToFront();
                            for (int i = 0; i < 2; i++) {
                                if (player[currentPlayer].virtualTokens[c.effectColor[i]] > 0) {
                                    int a = c.effectColor[i];
                                    ImageView imageView1 = new ImageView(this);
                                    int cardImageCode1 = getResources().getIdentifier(mapColorCodes.get(a) + "token", "drawable", getPackageName());
                                    imageView1.setImageResource(cardImageCode1);
                                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(140, 140);
                                    imageView1.setLayoutParams(layoutParams1);
                                    imageView1.setTag("" + a);
                                    layoutForChangingTokens[1].addView(imageView1);
                                    imageView1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            playerChooseToken(v, x, y);
                                        }
                                    });
                                }
                            }
                        }
                        else if(c.partialActivate.size() == 1){
                            layoutForChangingTokens[0].setVisibility(View.VISIBLE);

                            for(int i = 0 ;i<4;i++){
                                playerCardsLayout[i].setVisibility(View.INVISIBLE);
                            }
                            layoutForChangingTokens[0].bringToFront();
                            int b = c.partialActivate.get(0);
                            for (int i = 0; i < 2; i++) {
                                if (player[currentPlayer].virtualTokens[c.effectColor[i]] > 0 && c.effectColor[i] != b) {
                                    int a = c.effectColor[i];
                                    ImageView imageView1 = new ImageView(this);
                                    int cardImageCode1 = getResources().getIdentifier(mapColorCodes.get(a) + "token", "drawable", getPackageName());
                                    imageView1.setImageResource(cardImageCode1);
                                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(140, 140);
                                    imageView1.setLayoutParams(layoutParams1);
                                    imageView1.setTag("" + a);
                                    layoutForChangingTokens[1].addView(imageView1);
                                    imageView1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            playerChooseToken(v, x, y);
                                        }
                                    });
                                }
                            }
                        }
                        break;

                }
                if(!checkActivePlayer()){
                    changeCurrentPlayer();
                }
                setTableForPlayer(currentPlayer);
            }
            else {
                Toast.makeText(this, "This Card is Deactivated for this turn.", Toast.LENGTH_SHORT).show();
            }

    }
    public void playerChooseToken(View view,int x, int y){
        int a = Integer.parseInt((String)view.getTag());
        if(extraTokens[a] > 0){
            extraTokens[a]++;
            Toast.makeText(this, "Conversion done.", Toast.LENGTH_SHORT).show();
        }
        else if(player[currentPlayer].tokens[a] > 0){
            player[currentPlayer].afterBuildTokens[a]--;
            extraTokens[a]+=2;
            Toast.makeText(this, "Conversion done.", Toast.LENGTH_SHORT).show();
        }
        player[currentPlayer].cards[x].get(y).partialActivate.add(a);
        if(player[currentPlayer].cards[x].get(y).result.length == 2) {
            if (player[currentPlayer].cards[x].get(y).partialActivate.size() ==3)
                player[currentPlayer].cards[x].get(y).isActive = null;
            else if(player[currentPlayer].cards[x].get(y).partialActivate.size() ==2 && player[currentPlayer].cards[x].get(y).partialActivate.get(1)>=0){
                player[currentPlayer].cards[x].get(y).isActive = null;
            }
        }
        else{
            player[currentPlayer].cards[x].get(y).isActive = null;
        }
        for(int i = 0;i<4;i++){
            player[currentPlayer].virtualTokens[i] = (byte) (player[currentPlayer].afterBuildTokens[i] + extraTokens[i]);
        }
        for(int i = 0;i<4;i++){
            playerCardsLayout[i].setVisibility(View.VISIBLE);
        }
        layoutForChangingTokens[0].setVisibility(View.INVISIBLE);
        setTableForPlayer(currentPlayer);
    }
    public void playerChosenToken(View view){
        a[0] = Integer.parseInt((String)view.getTag());
        if(a[1] >=0){
            a[1] = -1;
        }
        Toast.makeText(this, "Now choose from below. ", Toast.LENGTH_SHORT).show();
    }
    public void playerChangedToken(View view,int x,int y){
        a[1] = Integer.parseInt((String)view.getTag());
        if(a[0] >=0){
            clickPlayerChosenToken(x,y,a[0],a[1]);
        }
        else if (a[0]<0){
            Toast.makeText(this, "Not a valid attempt.", Toast.LENGTH_SHORT).show();
        }
    }
    public void drawRandom(View view){
        if(player[currentPlayer].special[0] >0) {
            if (player[currentPlayer].totalTokens() < player[currentPlayer].limits[0]) {
                Random r = new Random();
                int random = r.nextInt(tokens.size());
                player[currentPlayer].tokens[tokens.get(random)]++;
                player[currentPlayer].virtualTokens[tokens.get(random)]++;
                player[currentPlayer].afterBuildTokens[tokens.get(random)]++;
                Toast.makeText(this, "You have been given " + mapColorCodes.get(tokens.get(random)), Toast.LENGTH_SHORT).show();
                tokens.remove(random);
                player[currentPlayer].special[0]--;
                if(!checkActivePlayer()){
                    changeCurrentPlayer();
                }
                setTableForPlayer(currentPlayer);
            } else {
                Toast.makeText(this, "You have your maximum tokens.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "You DO NOT have Random Draws", Toast.LENGTH_SHORT).show();
        }
    }

    public void showActualTokens(View view){
        TextView showVirtual = (TextView)findViewById(R.id.showVirtual);
        TextView showAfterBuild = (TextView)findViewById(R.id.afterBuild);
        showVirtual.setTextColor(Color.BLACK);
        showAfterBuild.setTextColor(Color.BLACK);
        for(int i = 0; i<4;i++){
            int c = getResources().getIdentifier(mapColorCodes.get(i)+"token"+player[currentPlayer].tokens[i],"drawable",getPackageName());
            playerTokens[i].setImageResource(c);
        }
    }
    public void showVirtualTokens(View view){
        TextView showVirtual = (TextView)findViewById(R.id.showVirtual);
        TextView showAfterBuild = (TextView)findViewById(R.id.afterBuild);
        showVirtual.setTextColor(Color.RED);
        showAfterBuild.setTextColor(Color.BLACK);
        for(int i = 0; i<4;i++){
            int c = getResources().getIdentifier(mapColorCodes.get(i)+"token"+player[currentPlayer].virtualTokens[i],"drawable",getPackageName());
            playerTokens[i].setImageResource(c);
        }
    }
    public void showAfterBuildTokens(View view){
        TextView showVirtual = (TextView)findViewById(R.id.showVirtual);
        TextView showAfterBuild = (TextView)findViewById(R.id.afterBuild);
        showVirtual.setTextColor(Color.BLACK);
        showAfterBuild.setTextColor(Color.RED);
        for(int i = 0; i<4;i++){
            int c = getResources().getIdentifier(mapColorCodes.get(i)+"token"+player[currentPlayer].afterBuildTokens[i],"drawable",getPackageName());
            playerTokens[i].setImageResource(c);
        }
    }
    public void resetConversion(View view){
        extraTokens = new byte[]{0,0,0,0};
        for(int x = 0;x<player[currentPlayer].cards[0].size();x++){
            if(player[currentPlayer].cards[0].get(x).isActive == null && player[currentPlayer].cards[0].get(x).result.length == 1){
                player[currentPlayer].cards[0].get(x).isActive = true;
                player[currentPlayer].cards[0].get(x).partialActivate.clear();
            }
            else if(player[currentPlayer].cards[0].get(x).isActive == null && player[currentPlayer].cards[0].get(x).result.length == 2){
                if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 1){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.clear();
                }
                else if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 2 && player[currentPlayer].cards[0].get(x).partialActivate.get(1)>=0){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.clear();
                }
                else if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 3){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.remove(2);
                }
            }
            else if(player[currentPlayer].cards[0].get(x).isActive && player[currentPlayer].cards[0].get(x).result.length == 1){
                player[currentPlayer].cards[0].get(x).isActive = true;
                player[currentPlayer].cards[0].get(x).partialActivate.clear();
            }
            else if(player[currentPlayer].cards[0].get(x).isActive && player[currentPlayer].cards[0].get(x).result.length == 2){
                if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 1){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.clear();
                }
                else if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 2 && player[currentPlayer].cards[0].get(x).partialActivate.get(1)>=0){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.clear();
                }
                else if(player[currentPlayer].cards[0].get(x).partialActivate.size() == 3){
                    player[currentPlayer].cards[0].get(x).isActive = true;
                    player[currentPlayer].cards[0].get(x).partialActivate.remove(2);
                }
            }
        }
        for(int x = 0;x<4;x++){
            player[currentPlayer].virtualTokens[x] = player[currentPlayer].tokens[x];
            player[currentPlayer].afterBuildTokens[x] = player[currentPlayer].tokens[x];
        }
        setTableForPlayer(currentPlayer);
    }

    public void setTableForPlayer(int playerCode) {
        for (int x = 0; x < 4; x++) {
            playerCardsLayout[x].removeAllViews();
            for (int y = 0; y < player[playerCode].cards[x].size(); y++) {
                ImageView imageView = new ImageView(this);
                int cardImageCode = getResources().getIdentifier("d" + player[playerCode].cards[x].get(y).idNumber, "drawable", getPackageName());
                imageView.setImageResource(cardImageCode);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,110 );//match parent,75
                imageView.setTag("" + x + y);
                imageView.setPadding(2, 4, 2, 4);
                imageView.setLayoutParams(layoutParams);
                if(playerCode == currentPlayer) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            click(v);
                        }
                    });
                }
                playerCardsLayout[x].addView(imageView);

                if (player[playerCode].cards[x].get(y).isActive == null) {
                        imageView.setBackgroundResource(R.drawable.whitebackground);
                } else if (!player[playerCode].cards[x].get(y).isActive) {
                    imageView.setBackgroundResource(R.drawable.redbackground);
                } else {
                    if(x==0 &&player[playerCode].cards[0].get(y).partialActivate.size() != 0 && player[playerCode].cards[0].get(y).result.length !=1) {
                        if(player[playerCode].cards[0].get(y).partialActivate.size()==1) {
                            imageView.setBackgroundResource(R.drawable.orangebackground);
                        }
                        else if(player[playerCode].cards[0].get(y).partialActivate.size()==2) {
                            imageView.setBackgroundResource(R.drawable.orangebackground);
                        }
                        else{
                            imageView.setBackgroundResource(R.drawable.orangebackground);
                        }
                    }
                    else {
                        imageView.setBackgroundResource(R.drawable.background);
                    }
                }
            }
        }

        playerCardsLayout[4].removeAllViews();
        for (int y = 0; y < player[playerCode].cards[4].size(); y++) {
            ImageView imageView = new ImageView(this);
            int cardImageCode = getResources().getIdentifier("d" + player[playerCode].cards[4].get(y).idNumber, "drawable", getPackageName());
            imageView.setImageResource(cardImageCode);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.MATCH_PARENT);    // 150 MATCH PARENT
            imageView.setLayoutParams(layoutParams);
            imageView.setTag("4" + y);
            imageView.setPadding(0, 4, 0, 4);
            if (playerCode == currentPlayer) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v);
                    }
                });
            }
            playerCardsLayout[4].addView(imageView);
            if (player[playerCode].cards[4].get(y).isActive == null) {
                imageView.setBackgroundResource(R.drawable.blackbackground);
            } else if (!player[playerCode].cards[4].get(y).isActive) {
                imageView.setBackgroundResource(R.drawable.redbackground);
            } else {
                imageView.setBackgroundResource(R.drawable.background);
            }
        }

        playerCardsLayout[5].removeAllViews();
        for (int x = 0; x < player[playerCode].reservedCards.size(); x++) {
            ImageView imageView = new ImageView(this);
            int cardImageCode = getResources().getIdentifier("c" + player[playerCode].reservedCards.get(x).idNumber, "drawable", getPackageName());
            imageView.setImageResource(cardImageCode);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.MATCH_PARENT);    // 100 MATCHPARENT
            imageView.setLayoutParams(layoutParams);
            imageView.setTag("" + x);
            if (playerCode == currentPlayer) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        build(v);
                    }
                });
            }
            playerCardsLayout[5].addView(imageView);
        }


        if (playerCode == currentPlayer){
            drawRandom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawRandom(v);
                }
            });
        }

        randomTokens.setText(""+player[playerCode].special[0]);
        playerNameVp.setText(player[playerCode].name+" VP");
        playerNameVp.setTag(""+playerCode);
        playerVP[0].setText(""+player[playerCode].numOfCards);
        playerVP[1].setText(""+player[playerCode].vpTokens);
        playerVP[2].setText(""+player[playerCode].getVP());
        for(int x = 0; x<3;x++){
           limitsForPlayer[x].setText(""+player[playerCode].limits[x]);
        }
        for(int i = 0; i<4;i++){
            int c = getResources().getIdentifier(mapColorCodes.get(i)+"token"+player[playerCode].tokens[i],"drawable",getPackageName());
            playerTokens[i].setImageResource(c);
        }

    }
    public void showInfo(View view){
        setTableForPlayer(Integer.parseInt(view.getTag().toString()));
    }
    public void imageSetCard(ImageView i, Card c,int drawCI){
        int card = deck.drawCardImage(c,drawCI);
        int cardImage = getResources().getIdentifier("c"+card,"drawable",getPackageName());
        i.setImageResource(cardImage);
    }
    public void goToRulePage(View view){
        Intent intent = new Intent(this,RulesPage.class);
        startActivity(intent);
    }
    public boolean checkActivePlayer(){
        for(int x = 0;x<4;x++){
            try {
                if (player[currentPlayer].isAllowed[x].get(0) == null) ;
                else return true;
            }
            catch(Exception ignored){

            }
        }
        for(int x = 1;x<4;x++){
            for(int y = 0;y<player[currentPlayer].cards[x].size();y++){
                if(player[currentPlayer].cards[x].get(y).isActive == null);
                else if(player[currentPlayer].cards[x].get(y).isActive) return true;
            }
        }
        if(player[currentPlayer].totalTokens() < player[currentPlayer].limits[0] && player[currentPlayer].special[0]>0) return true;
        return false;
    }

    public boolean isGameOver(){
        for(int x = 0;x<numOfPlayers;x++){
            if(player[x].numOfCards>=16 || player[x].numOfL3Cards>=4) {
                return true;
            }
        }
        return false;
    }
}