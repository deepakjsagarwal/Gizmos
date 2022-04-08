package com.deepakagarwal.gizmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deepakagarwal.gizmos.model.Player;

public class AskNumberOfPlayers extends AppCompatActivity {

    int i;
    public static int numOfPlayers;
    public static Player[] player;
    TextView[] players;
    TextView[] names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_number_of_players);
        i = 1;
        names = new TextView[4];
        names[0] = (TextView)findViewById(R.id.player1name);
        names[1] = (TextView)findViewById(R.id.player2name);
        names[2] = (TextView)findViewById(R.id.player3name);
        names[3] = (TextView)findViewById(R.id.player4name);
        players = new TextView[4];
        players[0] = (TextView)findViewById(R.id.player1);
        players[1] = (TextView)findViewById(R.id.player2);
        players[2] = (TextView)findViewById(R.id.player3);
        players[3] = (TextView)findViewById(R.id.player4);

    }


    public void save(View view){
        if(checkName()) {
            numOfPlayers = i + 1;
            player = new Player[numOfPlayers];
            for (int x = 0; x <= i; x++) {
                player[x] = new Player();
                player[x].name += names[x].getText().toString();
            }
            Intent intent = new Intent(this, GameScreen.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Name can't be Empty.", Toast.LENGTH_SHORT).show();
        }
    }
    public void plus(View view){
        i++;
        if(i>=3) i =3;
        players[i].setVisibility(View.VISIBLE);
        names[i].setVisibility(View.VISIBLE);
    }
    public void minus(View view){
        if(i>1) {
            players[i].setVisibility(View.INVISIBLE);
            names[i].setVisibility(View.INVISIBLE);
        }
        i--;
        if(i<1) i =1;
    }
    public boolean checkName(){
        for (int x = 0; x <= i; x++){
            if(names[x].getText().toString().equals("")) return false;
        }
        return true;
    }
}