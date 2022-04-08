package com.deepakagarwal.gizmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.deepakagarwal.gizmos.model.Player;

import java.util.ArrayList;

public class WinnerPage extends AppCompatActivity {

    int numOfPlayers = 0;
    Player[] players;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_page);

        Intent intent = getIntent();
        String s = intent.getStringExtra("Number of players");
        numOfPlayers = Integer.parseInt(s);
        for(int x = 0;x<numOfPlayers;x++){
            players[x] = intent.getParcelableExtra("Player "+x+" Details");
        }

        TextView t = (TextView)findViewById(R.id.winnerName);
        t.setText("Congratulations "+getWinner()+"! You have won the game.");
    }

    public String getWinner(){
        ArrayList<Integer> winner = new ArrayList<>();
        winner.add(0);
        int maxScore = players[0].getVP();

        for(int x = 1;x<numOfPlayers;x++){
            if(maxScore < players[x].getVP()){
                winner.clear();
                winner.add(x);
                maxScore = players[x].getVP();
            }
            else if(maxScore == players[x].getVP()){
                winner.add(x);
            }
        }
        if(winner.size() == 1) return players[winner.get(0)].name;
        else{
            int minNumOfCards = players[winner.get(0)].numOfCards;
            for(int x = 1;x<winner.size();x++){
                if(minNumOfCards > players[winner.get(x)].numOfCards){
                    winner.clear();
                    winner.add(x);
                    minNumOfCards = players[x].numOfCards;
                }
                else if(minNumOfCards == players[winner.get(x)].numOfCards){
                    winner.add(x);
                }
            }
            if(winner.size() == 1) return players[winner.get(0)].name;
            else{
                int maxNumOfToken = players[winner.get(0)].getTokens();
                for(int x = 1;x<winner.size();x++){
                    if(maxNumOfToken < players[winner.get(x)].getTokens()){
                        winner.clear();
                        winner.add(x);
                        maxNumOfToken =  players[winner.get(x)].getTokens();
                    }
                    else if(minNumOfCards == players[winner.get(x)].numOfCards){
                        winner.add(x);
                    }
                }
                if(winner.size() == 1) return players[winner.get(0)].name;
                else{
                    return players[winner.get(winner.size() -1)].name;
                }
            }
        }
    }

    public void backToGame(View v){
        Intent intent = new Intent(this,AskNumberOfPlayers.class);
        startActivity(intent);
        finish();
    }
}