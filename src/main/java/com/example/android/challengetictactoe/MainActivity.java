package com.example.android.challengetictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean turnForPlayer1 = true;

    private int roundCount;

    private int pointsForPlayer1;
    private int pointsForPlayer2;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameReset();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (turnForPlayer1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()) {
            if (turnForPlayer1) {
                winForPlayer1();
            } else {
                winForPlayer2();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            turnForPlayer1 = !turnForPlayer1;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void winForPlayer1() {
        pointsForPlayer1++;
        Toast.makeText(this, "winner:Player1", Toast.LENGTH_SHORT).show();
        textPointUpdater();
        boardReset();
    }

    private void winForPlayer2() {
        pointsForPlayer2++;
        Toast.makeText(this, "winner:Player2", Toast.LENGTH_SHORT).show();
        textPointUpdater();
        boardReset();
    }

    private void draw() {
        Toast.makeText(this, "Its a Draw", Toast.LENGTH_SHORT).show();
        boardReset();
    }

    private void textPointUpdater() {
        textViewPlayer1.setText(getString(R.string.player_1, pointsForPlayer1));
        textViewPlayer2.setText(getString(R.string.player_2, pointsForPlayer2));
    }

    private void boardReset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        turnForPlayer1 = true;
    }

    private void gameReset() {
        pointsForPlayer1 = 0;
        pointsForPlayer2 = 0;
        textPointUpdater();
        boardReset();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("pointsForPlayer1", pointsForPlayer1);
        outState.putInt("pointsForPlayer2", pointsForPlayer2);
        outState.putBoolean("turnForPlayer1", turnForPlayer1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        pointsForPlayer1 = savedInstanceState.getInt("pointsForPlayer1");
        pointsForPlayer2 = savedInstanceState.getInt("pointsForPlayer2");
        turnForPlayer1 = savedInstanceState.getBoolean("turnForPlayer1");
    }
}
