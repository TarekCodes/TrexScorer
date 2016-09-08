package com.tareksaidee.android.trexscorer;

import android.content.Intent;
import android.view.View.OnKeyListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnKeyListener {

    EditText team1, team2;
    Button goToButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToButton = (Button) findViewById(R.id.beginButton);
        team1 = (EditText) findViewById(R.id.team1Name);
        team2 = (EditText) findViewById(R.id.team2Name);
        team2.setOnKeyListener(this);
    }

    public void goTo(View view) {
        String team1_name, team2_name;
        team1_name = team1.getText().toString();
        team2_name = team2.getText().toString();
        if (team1_name.equals("") || team2_name.equals(""))
            Toast.makeText(
                    MainActivity.this,
                    "Name Fields Can't Be Empty",
                    Toast.LENGTH_SHORT
            ).show();
        else {
            Intent intent = new Intent(this, Scores.class);
            intent.putExtra("teamName1", team1_name);
            intent.putExtra("teamName2", team2_name);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                goToButton.performClick();
                return true;
            }
        return false;
    }
}
