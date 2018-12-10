package com.example.nanu.lionortiger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player{
        ONE ,
        TWO,
        NO
    }

    Player [] playerChoices = new Player[9];
    Player currentPlayer = Player.ONE;
    int [][] winnerRowsColumns  = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    Button resetBtn;
    private boolean gameOver = false;
    private android.support.v7.widget.GridLayout gridLayout;
    private static boolean matchTied = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0;i<playerChoices.length;i++){
            playerChoices[i] = Player.NO;
        }

        resetBtn = findViewById(R.id.resetBtn);
        gridLayout = findViewById(R.id.gridLayout);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

    }

    private void resetGame() {

        for(int i=0;i<gridLayout.getChildCount();i++){

            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
        }

        currentPlayer = Player.ONE;
        gameOver = false;

       for (int i = 0;i<playerChoices.length;i++){
           playerChoices[i] = Player.NO;
       }

        resetBtn.setVisibility(View.GONE);
    }

    public void imageTapped(View imageView){

        ImageView tappedImageView = (ImageView) imageView;
        int tappedImageTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoices[tappedImageTag] == Player.NO && !gameOver){


            tappedImageView.setTranslationX(-2000);


            //initialized playerChoice
            playerChoices[tappedImageTag] = currentPlayer;


            if(currentPlayer == Player.ONE){
                tappedImageView.setImageResource(R.drawable.tiger);
                tappedImageView.animate().translationXBy(2000).alpha(1).rotationBy(3600).setDuration(1000);
                currentPlayer = Player.TWO;
            }else if (currentPlayer == Player.TWO){
                tappedImageView.setImageResource(R.drawable.lion);
                tappedImageView.animate().translationXBy(2000).alpha(1).rotationBy(3600).setDuration(1000);
                currentPlayer = Player.ONE;
            }

            //winner selection code

            for(int [] winnerColums : winnerRowsColumns){

                if((playerChoices[winnerColums[0]] == playerChoices[winnerColums[1]]) &&
                        (playerChoices[winnerColums[1]] == playerChoices[winnerColums[2]])&&
                            (playerChoices[winnerColums[0]] != Player.NO)){

                    resetBtn.setVisibility(View.VISIBLE);
                    gameOver = true;
                    String winnerOfGame = "";
                    if(currentPlayer == Player.ONE) {
                       winnerOfGame = "Player Two";
                       matchTied = false;
                    }else if(currentPlayer == Player.TWO){
                        winnerOfGame = "Player One";
                        matchTied = false;
                    }

                    Toast.makeText(this,winnerOfGame + " is winner",Toast.LENGTH_LONG).show();
                }
            }

            /*for(int [] noWinnerColumns : winnerRowsColumns){

                if ((playerChoices[noWinnerColumns[0]] == Player.ONE || playerChoices[noWinnerColumns[0]] == Player.TWO)
                        && (playerChoices[noWinnerColumns[1]] == Player.ONE || playerChoices[noWinnerColumns[1]] == Player.TWO)
                        && (playerChoices[noWinnerColumns[2]] == Player.ONE || playerChoices[noWinnerColumns[2]] == Player.TWO)
                        && playerChoices[noWinnerColumns[0]] != Player.NO
                        && playerChoices[noWinnerColumns[1]] != Player.NO
                        && playerChoices[noWinnerColumns[2]] != Player.NO  ){


                }
            }*/

        }else {
            Toast.makeText(this, " Match Tied",Toast.LENGTH_LONG).show();
            resetBtn.setVisibility(View.VISIBLE);
        }

    }
}
