package com.tareksaidee.android.trexscorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Scores extends AppCompatActivity implements View.OnClickListener {
    final static int SLAPS = 0, DIAMONDS = 1, QUEENS = 2, KOH = 3, TREX = 4;
    Intent intent;
    TextView team1Name, team2Name, team1Score, team2Score, team1Total, team2Total;
    TextView team1GameTotal, team2GameTotal, team1KingdomTotal, team2KingdomTotal;
    TextView matchTotalDifference;
    String team1NameString, team2NameString;
    Button gamesPopupMenuButton, kingdomsPopupMenuButton;
    Button team1Rank1Button, team1Rank2Button, team2Rank1Button, team2Rank2Button;
    Button team1QueensDoublesButton, team2QueensDoublesButton;
    Button team1KOHDoubleButton, team2KOHDoubleButton;
    Button scoresResetButton;
    LinearLayout team1RankLayout, team2RankLayout;
    LinearLayout team1ScoresLayout, team2ScoresLayout;
    PopupMenu gamesPopupMenu, kingdomsPopupMenu;
    int teamRanksInt[][][] = new int[2][4][2];
    int currentGameID = -1, currentKingdomID = -1;
    int scoresInt[][][] = new int[2][4][5];
    int gameTotals[][][] = new int[2][4][5];
    int kingdomTotals[][] = new int[2][4];
    int team1TotalInt = 0, team2TotalInt = 0;
    int currentTeamButton, otherTeamButton;
    int queensDoublesInt[][] = new int[2][4];
    int KOHDoubleInt[][] = new int[2][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        intent = getIntent();
        team1NameString = intent.getStringExtra("teamName1");
        team2NameString = intent.getStringExtra("teamName2");
        team1Score = (TextView) findViewById(R.id.team1Score);
        team2Score = (TextView) findViewById(R.id.team2Score);
        team1Name = (TextView) findViewById(R.id.team1_name);
        team2Name = (TextView) findViewById(R.id.team2_name);
        team1Name.setText(team1NameString);
        team2Name.setText(team2NameString);
        team1Total = (TextView) findViewById(R.id.team1Total);
        team2Total = (TextView) findViewById(R.id.team2Total);
        team1GameTotal = (TextView) findViewById(R.id.team1GameTotal);
        team2GameTotal = (TextView) findViewById(R.id.team2GameTotal);
        team1KingdomTotal = (TextView) findViewById(R.id.team1KingdomTotal);
        team2KingdomTotal = (TextView) findViewById(R.id.team2KingdomTotal);
        gamesPopupMenuButton = (Button) findViewById(R.id.games_menu);
        gamesPopupMenuButton.setOnClickListener(new GameMenu());
        gamesPopupMenu = new PopupMenu(this, gamesPopupMenuButton);
        gamesPopupMenu.getMenuInflater().inflate(R.menu.games_popup_menu, gamesPopupMenu.getMenu());
        kingdomsPopupMenuButton = (Button) findViewById(R.id.kingdoms_menu);
        kingdomsPopupMenuButton.setOnClickListener(this);
        kingdomsPopupMenu = new PopupMenu(this, kingdomsPopupMenuButton);
        kingdomsPopupMenu.getMenuInflater().inflate(R.menu.kingdoms_popup_menu, kingdomsPopupMenu.getMenu());
        kingdomsPopupMenu.getMenu().findItem(R.id.team1_kingdom1).setTitle(team1NameString + " Kingdom #1");
        kingdomsPopupMenu.getMenu().findItem(R.id.team1_kingdom2).setTitle(team1NameString + " Kingdom #2");
        kingdomsPopupMenu.getMenu().findItem(R.id.team2_kingdom1).setTitle(team2NameString + " Kingdom #1");
        kingdomsPopupMenu.getMenu().findItem(R.id.team2_kingdom2).setTitle(team2NameString + " Kingdom #2");
        team1ScoresLayout = (LinearLayout) findViewById(R.id.team1Scores_layout);
        team2ScoresLayout = (LinearLayout) findViewById(R.id.team2Scores_layout);
        team1Rank1Button = (Button) findViewById(R.id.team1Rank1_Button);
        team1Rank2Button = (Button) findViewById(R.id.team1Rank2_Button);
        team2Rank1Button = (Button) findViewById(R.id.team2Rank1_Button);
        team2Rank2Button = (Button) findViewById(R.id.team2Rank2_Button);
        team1RankLayout = (LinearLayout) findViewById(R.id.team1Rank_layout);
        team2RankLayout = (LinearLayout) findViewById(R.id.team2Rank_layout);
        team1QueensDoublesButton = (Button) findViewById(R.id.team1QueensDoubles_Button);
        team2QueensDoublesButton = (Button) findViewById(R.id.team2QueensDoubles_Button);
        team1KOHDoubleButton = (Button) findViewById(R.id.team1KOHDouble_Button);
        team2KOHDoubleButton = (Button) findViewById(R.id.team2KOHDouble_Button);
        matchTotalDifference = (TextView) findViewById(R.id.totalDifference);
        scoresResetButton = (Button) findViewById(R.id.resetButton);
    }

    //handles the plus buttons for all games except trex
    public void addScore(View view) {
        if (getCurrentKingdom() == -1)
            Toast.makeText(this, "Choose a Kingdom First", Toast.LENGTH_SHORT).show();
        else {
            if (view.getId() == R.id.add_button1 || view.getId() == R.id.team1QueensDoubles_Button
                    || view.getId() == R.id.team1KOHDouble_Button) {
                currentTeamButton = 0;
                otherTeamButton = 1;
            } else {
                currentTeamButton = 1;
                otherTeamButton = 0;
            }
            switch (currentGameID) {
                case R.id.slaps:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][SLAPS] != 13) {
                        if (scoresInt[otherTeamButton][getCurrentKingdom()][SLAPS] == 0)
                            scoresInt[otherTeamButton][getCurrentKingdom()][SLAPS] = 13;
                        scoresInt[currentTeamButton][getCurrentKingdom()][SLAPS]++;
                        scoresInt[otherTeamButton][getCurrentKingdom()][SLAPS]--;
                    }
                    break;
                case R.id.diamonds:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][DIAMONDS] != 13) {
                        if (scoresInt[otherTeamButton][getCurrentKingdom()][DIAMONDS] == 0)
                            scoresInt[otherTeamButton][getCurrentKingdom()][DIAMONDS] = 13;
                        scoresInt[currentTeamButton][getCurrentKingdom()][DIAMONDS]++;
                        scoresInt[otherTeamButton][getCurrentKingdom()][DIAMONDS]--;
                    }
                    break;
                case R.id.queens:
                    if (view.getId() == R.id.team1QueensDoubles_Button || view.getId() == R.id.team2QueensDoubles_Button) {
                        if (queensDoublesInt[currentTeamButton][getCurrentKingdom()] < scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS])
                            queensDoublesInt[currentTeamButton][getCurrentKingdom()]++;
                        else if (queensDoublesInt[currentTeamButton][getCurrentKingdom()] == scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS])
                            queensDoublesInt[currentTeamButton][getCurrentKingdom()] = 0;
                    } else {
                        if (scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS] != 4) {
                            if (scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS] == 0)
                                scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS] = 4;
                            scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS]++;
                            scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS]--;
                            if (queensDoublesInt[otherTeamButton][getCurrentKingdom()] > scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS])
                                queensDoublesInt[otherTeamButton][getCurrentKingdom()]--;
                        }
                    }
                    break;
                case R.id.kingOfHearts:
                    if (view.getId() == R.id.team1KOHDouble_Button || view.getId() == R.id.team2KOHDouble_Button) {
                        if (KOHDoubleInt[currentTeamButton][getCurrentKingdom()] < scoresInt[currentTeamButton][getCurrentKingdom()][KOH])
                            KOHDoubleInt[currentTeamButton][getCurrentKingdom()]++;
                        else if (KOHDoubleInt[currentTeamButton][getCurrentKingdom()] == scoresInt[currentTeamButton][getCurrentKingdom()][KOH])
                            KOHDoubleInt[currentTeamButton][getCurrentKingdom()] = 0;
                    } else {
                        if (scoresInt[currentTeamButton][getCurrentKingdom()][KOH] != 1) {
                            if (scoresInt[otherTeamButton][getCurrentKingdom()][KOH] == 0)
                                scoresInt[otherTeamButton][getCurrentKingdom()][KOH] = 1;
                            scoresInt[currentTeamButton][getCurrentKingdom()][KOH]++;
                            scoresInt[otherTeamButton][getCurrentKingdom()][KOH]--;
                            if (KOHDoubleInt[otherTeamButton][getCurrentKingdom()] > scoresInt[otherTeamButton][getCurrentKingdom()][KOH])
                                KOHDoubleInt[otherTeamButton][getCurrentKingdom()]--;
                        }
                    }
                    break;
                default:
                    Toast.makeText(this, "Choose a Game First", Toast.LENGTH_SHORT).show();
            }
            updateTotals();
            updateTotalsLayout();
        }

    }

    //handles the subtract buttons for all games except trex
    public void subtractScore(View view) {
        if (getCurrentKingdom() == -1)
            Toast.makeText(this, "Choose a Kingdom First", Toast.LENGTH_SHORT).show();
        else {
            if (view.getId() == R.id.minus_button1) {
                currentTeamButton = 0;
                otherTeamButton = 1;
            } else {
                currentTeamButton = 1;
                otherTeamButton = 0;
            }
            switch (currentGameID) {
                case R.id.slaps:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][SLAPS] == 0)
                        scoresInt[otherTeamButton][getCurrentKingdom()][SLAPS] = 13;
                    else {
                        scoresInt[currentTeamButton][getCurrentKingdom()][SLAPS]--;
                        scoresInt[otherTeamButton][getCurrentKingdom()][SLAPS]++;
                    }
                    break;
                case R.id.diamonds:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][DIAMONDS] == 0)
                        scoresInt[otherTeamButton][getCurrentKingdom()][DIAMONDS] = 13;
                    else {
                        scoresInt[currentTeamButton][getCurrentKingdom()][DIAMONDS]--;
                        scoresInt[otherTeamButton][getCurrentKingdom()][DIAMONDS]++;
                    }
                    break;
                case R.id.queens:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS] == 0)
                        scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS] = 4;
                    else {
                        scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS]--;
                        scoresInt[otherTeamButton][getCurrentKingdom()][QUEENS]++;
                        if (queensDoublesInt[currentTeamButton][getCurrentKingdom()] > scoresInt[currentTeamButton][getCurrentKingdom()][QUEENS])
                            queensDoublesInt[currentTeamButton][getCurrentKingdom()]--;
                    }
                    break;
                case R.id.kingOfHearts:
                    if (scoresInt[currentTeamButton][getCurrentKingdom()][KOH] == 0)
                        scoresInt[otherTeamButton][getCurrentKingdom()][KOH] = 1;
                    else {
                        scoresInt[currentTeamButton][getCurrentKingdom()][KOH]--;
                        scoresInt[otherTeamButton][getCurrentKingdom()][KOH]++;
                        if (KOHDoubleInt[currentTeamButton][getCurrentKingdom()] > scoresInt[currentTeamButton][getCurrentKingdom()][KOH])
                            KOHDoubleInt[currentTeamButton][getCurrentKingdom()]--;
                    }
                    break;
                default:
                    Toast.makeText(this, "Choose a Game First", Toast.LENGTH_SHORT).show();
                    break;
            }
            updateTotals();
            updateTotalsLayout();
        }
    }

    //Logic behind the scores reset button
    public void resetGame(View view) {
        if (getCurrentGame() != TREX) {
            scoresInt[0][getCurrentKingdom()][getCurrentGame()] = 0;
            scoresInt[1][getCurrentKingdom()][getCurrentGame()] = 0;
            if (getCurrentGame() == QUEENS) {
                queensDoublesInt[0][getCurrentKingdom()] = 0;
                queensDoublesInt[1][getCurrentKingdom()] = 0;
            }
            if (getCurrentGame() == KOH) {
                KOHDoubleInt[0][getCurrentKingdom()] = 0;
                KOHDoubleInt[1][getCurrentKingdom()] = 0;
            }
        } else {
            teamRanksInt[0][getCurrentKingdom()][0] = 0;
            teamRanksInt[0][getCurrentKingdom()][1] = 0;
            teamRanksInt[1][getCurrentKingdom()][0] = 0;
            teamRanksInt[1][getCurrentKingdom()][1] = 0;
        }
        updateTotals();
        updateTotalsLayout();
    }

    //A button to start a new game after confirming the user's intention
    public void newGame(View view) {
        final Intent intent = new Intent(this, MainActivity.class);
        new AlertDialog.Builder(this)
                .setTitle("New Match")
                .setMessage("Are you sure you want to start a new match?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //Overrides the back button to include a confirmation toast before starting a new game
    @Override
    public void onBackPressed() {
        newGame(new View(this));
    }

    //Kingdoms popup menu which handles updating the layout when a new kingdom is chosen
    @Override
    public void onClick(View view) {
        kingdomsPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                currentKingdomID = item.getItemId();
                currentGameID = -1;
                gamesPopupMenuButton.setText("Choose Game");
                team1QueensDoublesButton.setVisibility(View.GONE);
                team2QueensDoublesButton.setVisibility(View.GONE);
                team1KOHDoubleButton.setVisibility(View.GONE);
                team2KOHDoubleButton.setVisibility(View.GONE);
                team1ScoresLayout.setVisibility(View.GONE);
                team2ScoresLayout.setVisibility(View.GONE);
                team1RankLayout.setVisibility(View.GONE);
                team2RankLayout.setVisibility(View.GONE);
                scoresResetButton.setVisibility(View.GONE);
                kingdomsPopupMenuButton.setText(item.getTitle());
                team1GameTotal.setText("Game Total: 0");
                team2GameTotal.setText("Game Total: 0");
                team1KingdomTotal.setText("Kingdom Total: " + kingdomTotals[0][getCurrentKingdom()]);
                team2KingdomTotal.setText("Kingdom Total: " + kingdomTotals[1][getCurrentKingdom()]);
                return true;
            }
        });
        kingdomsPopupMenu.show();

    }

    //returns the current kingdom selected
    private int getCurrentKingdom() {
        switch (currentKingdomID) {
            case R.id.team1_kingdom1:
                return 0;
            case R.id.team1_kingdom2:
                return 1;
            case R.id.team2_kingdom1:
                return 2;
            case R.id.team2_kingdom2:
                return 3;
            default:
                return -1;
        }
    }

    //returns the current game selected
    private int getCurrentGame() {
        switch (currentGameID) {
            case R.id.slaps:
                return SLAPS;
            case R.id.diamonds:
                return DIAMONDS;
            case R.id.queens:
                return QUEENS;
            case R.id.kingOfHearts:
                return KOH;
            case R.id.trex:
                return TREX;
            default:
                return -1;
        }
    }

    //update game, match and kingdom totals
    private void updateTotals() {

        //updates game and match totals
        switch (getCurrentGame()) {
            case SLAPS:
                team1TotalInt -= gameTotals[0][getCurrentKingdom()][SLAPS];
                gameTotals[0][getCurrentKingdom()][SLAPS] = -(scoresInt[0][getCurrentKingdom()][SLAPS] * 15);
                team1TotalInt += gameTotals[0][getCurrentKingdom()][SLAPS];
                team2TotalInt -= gameTotals[1][getCurrentKingdom()][SLAPS];
                gameTotals[1][getCurrentKingdom()][SLAPS] = -(scoresInt[1][getCurrentKingdom()][SLAPS] * 15);
                team2TotalInt += gameTotals[1][getCurrentKingdom()][SLAPS];
                break;
            case DIAMONDS:
                team1TotalInt -= gameTotals[0][getCurrentKingdom()][DIAMONDS];
                gameTotals[0][getCurrentKingdom()][DIAMONDS] = -(scoresInt[0][getCurrentKingdom()][DIAMONDS] * 10);
                team1TotalInt += gameTotals[0][getCurrentKingdom()][DIAMONDS];
                team2TotalInt -= gameTotals[1][getCurrentKingdom()][DIAMONDS];
                gameTotals[1][getCurrentKingdom()][DIAMONDS] = -(scoresInt[1][getCurrentKingdom()][DIAMONDS] * 10);
                team2TotalInt += gameTotals[1][getCurrentKingdom()][DIAMONDS];
                break;
            case QUEENS:
                team1TotalInt -= gameTotals[0][getCurrentKingdom()][QUEENS];
                gameTotals[0][getCurrentKingdom()][QUEENS] = -((scoresInt[0][getCurrentKingdom()][QUEENS] * 25)
                        + queensDoublesInt[0][getCurrentKingdom()] * 25) + (queensDoublesInt[1][getCurrentKingdom()] * 25);
                team1TotalInt += gameTotals[0][getCurrentKingdom()][QUEENS];
                team2TotalInt -= gameTotals[1][getCurrentKingdom()][QUEENS];
                gameTotals[1][getCurrentKingdom()][QUEENS] = -((scoresInt[1][getCurrentKingdom()][QUEENS] * 25)
                        + queensDoublesInt[1][getCurrentKingdom()] * 25) + (queensDoublesInt[0][getCurrentKingdom()] * 25);
                team2TotalInt += gameTotals[1][getCurrentKingdom()][QUEENS];
                break;
            case KOH:
                team1TotalInt -= gameTotals[0][getCurrentKingdom()][KOH];
                gameTotals[0][getCurrentKingdom()][KOH] = -((scoresInt[0][getCurrentKingdom()][KOH] * 75)
                        + KOHDoubleInt[0][getCurrentKingdom()] * 75) + (KOHDoubleInt[1][getCurrentKingdom()] * 75);
                team1TotalInt += gameTotals[0][getCurrentKingdom()][KOH];
                team2TotalInt -= gameTotals[1][getCurrentKingdom()][KOH];
                gameTotals[1][getCurrentKingdom()][KOH] = -((scoresInt[1][getCurrentKingdom()][KOH] * 75)
                        + KOHDoubleInt[1][getCurrentKingdom()] * 75) + (KOHDoubleInt[0][getCurrentKingdom()] * 75);
                team2TotalInt += gameTotals[1][getCurrentKingdom()][KOH];
                break;
            case TREX:
                int tempAddition[][] = new int[2][2];
                for (int i = 0; i < 2; i++)
                    for (int x = 0; x < 2; x++)
                        switch (teamRanksInt[i][getCurrentKingdom()][x]) {
                            case 1:
                                tempAddition[i][x] = 200;
                                break;
                            case 2:
                                tempAddition[i][x] = 150;
                                break;
                            case 3:
                                tempAddition[i][x] = 100;
                                break;
                            case 4:
                                tempAddition[i][x] = 50;
                                break;
                            default:
                                break;
                        }
                team1TotalInt -= gameTotals[0][getCurrentKingdom()][TREX];
                gameTotals[0][getCurrentKingdom()][TREX] = tempAddition[0][0] + tempAddition[0][1];
                team1TotalInt += gameTotals[0][getCurrentKingdom()][TREX];
                team2TotalInt -= gameTotals[1][getCurrentKingdom()][TREX];
                gameTotals[1][getCurrentKingdom()][TREX] = tempAddition[1][0] + tempAddition[1][1];
                team2TotalInt += gameTotals[1][getCurrentKingdom()][TREX];
                break;
        }

        //update kingdom totals
        kingdomTotals[0][getCurrentKingdom()] = 0;
        kingdomTotals[1][getCurrentKingdom()] = 0;
        for (int i = 0; i < 5; i++) {
            kingdomTotals[0][getCurrentKingdom()] += gameTotals[0][getCurrentKingdom()][i];
            kingdomTotals[1][getCurrentKingdom()] += gameTotals[1][getCurrentKingdom()][i];
        }
    }

    //updates the totals and scores layouts after any changes that occur
    private void updateTotalsLayout() {
        if (getCurrentGame() == QUEENS) {
            if (queensDoublesInt[0][getCurrentKingdom()] != 0) {
                team1QueensDoublesButton.setText(queensDoublesInt[0][getCurrentKingdom()] + "");
            } else {
                team1QueensDoublesButton.setText("Doubles");
            }
            if (queensDoublesInt[1][getCurrentKingdom()] != 0) {
                team2QueensDoublesButton.setText(queensDoublesInt[1][getCurrentKingdom()] + "");
            } else {
                team2QueensDoublesButton.setText("Doubles");
            }
        }
        if (getCurrentGame() == KOH) {
            if (KOHDoubleInt[0][getCurrentKingdom()] != 0) {
                team1KOHDoubleButton.setText(KOHDoubleInt[0][getCurrentKingdom()] + "");
            } else {
                team1KOHDoubleButton.setText("Double");
            }
            if (KOHDoubleInt[1][getCurrentKingdom()] != 0) {
                team2KOHDoubleButton.setText(KOHDoubleInt[1][getCurrentKingdom()] + "");
            } else {
                team2KOHDoubleButton.setText("Double");
            }
        }
        if (getCurrentGame() == TREX) {
            team1Rank1Button.setText(teamRanksInt[0][getCurrentKingdom()][0] + "");
            team1Rank2Button.setText(teamRanksInt[0][getCurrentKingdom()][1] + "");
            team2Rank1Button.setText(teamRanksInt[1][getCurrentKingdom()][0] + "");
            team2Rank2Button.setText(teamRanksInt[1][getCurrentKingdom()][1] + "");
        }
        if (getCurrentGame() != TREX) {
            team1Score.setText(scoresInt[0][getCurrentKingdom()][getCurrentGame()] + "");
            team2Score.setText(scoresInt[1][getCurrentKingdom()][getCurrentGame()] + "");
        }
        team1GameTotal.setText("Game Total: " + gameTotals[0][getCurrentKingdom()][getCurrentGame()]);
        team2GameTotal.setText("Game Total: " + gameTotals[1][getCurrentKingdom()][getCurrentGame()]);
        team1KingdomTotal.setText("Kingdom Total: " + kingdomTotals[0][getCurrentKingdom()]);
        team2KingdomTotal.setText("Kingdom Total: " + kingdomTotals[1][getCurrentKingdom()]);
        team1Total.setText("Match Total: " + team1TotalInt);
        team2Total.setText("Match Total: " + team2TotalInt);
        if (team1TotalInt == team2TotalInt) {
            matchTotalDifference.setText("Teams are tied");
        } else {
            matchTotalDifference.setText(Math.abs(team1TotalInt - team2TotalInt) + " points difference" +
                    " in " + ((team1TotalInt > team2TotalInt) ? team1NameString : team2NameString) + "'s favor");
        }
    }

    //handles the logic for the Trex game
    public void trexButtons(View view) {
        int temp;
        switch (view.getId()) {
            case R.id.team1Rank1_Button:
                if (teamRanksInt[0][getCurrentKingdom()][0] == 4)
                    teamRanksInt[0][getCurrentKingdom()][0] = 0;
                else {
                    temp = teamRanksInt[0][getCurrentKingdom()][0];
                    teamRanksInt[0][getCurrentKingdom()][0] = ++teamRanksInt[0][getCurrentKingdom()][0] % 5;
                    while (teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[0][getCurrentKingdom()][1]
                            || teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[1][getCurrentKingdom()][0]
                            || teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[1][getCurrentKingdom()][1]
                            || teamRanksInt[0][getCurrentKingdom()][0] == 0)
                        teamRanksInt[0][getCurrentKingdom()][0] = ++teamRanksInt[0][getCurrentKingdom()][0] % 5;
                    if (temp >= teamRanksInt[0][getCurrentKingdom()][0])
                        teamRanksInt[0][getCurrentKingdom()][0] = 0;
                }
                break;
            case R.id.team1Rank2_Button:
                if (teamRanksInt[0][getCurrentKingdom()][1] == 4)
                    teamRanksInt[0][getCurrentKingdom()][1] = 0;
                else {
                    temp = teamRanksInt[0][getCurrentKingdom()][1];
                    teamRanksInt[0][getCurrentKingdom()][1] = ++teamRanksInt[0][getCurrentKingdom()][1] % 5;
                    while (teamRanksInt[0][getCurrentKingdom()][1] == teamRanksInt[0][getCurrentKingdom()][0]
                            || teamRanksInt[0][getCurrentKingdom()][1] == teamRanksInt[1][getCurrentKingdom()][0]
                            || teamRanksInt[0][getCurrentKingdom()][1] == teamRanksInt[1][getCurrentKingdom()][1]
                            || teamRanksInt[0][getCurrentKingdom()][1] == 0)
                        teamRanksInt[0][getCurrentKingdom()][1] = ++teamRanksInt[0][getCurrentKingdom()][1] % 5;
                    if (temp >= teamRanksInt[0][getCurrentKingdom()][1])
                        teamRanksInt[0][getCurrentKingdom()][1] = 0;
                }
                break;
            case R.id.team2Rank1_Button:
                if (teamRanksInt[1][getCurrentKingdom()][0] == 4)
                    teamRanksInt[1][getCurrentKingdom()][0] = 0;
                else {
                    temp = teamRanksInt[1][getCurrentKingdom()][0];
                    teamRanksInt[1][getCurrentKingdom()][0] = ++teamRanksInt[1][getCurrentKingdom()][0] % 5;
                    while (teamRanksInt[1][getCurrentKingdom()][0] == teamRanksInt[0][getCurrentKingdom()][0]
                            || teamRanksInt[1][getCurrentKingdom()][0] == teamRanksInt[0][getCurrentKingdom()][1]
                            || teamRanksInt[1][getCurrentKingdom()][0] == teamRanksInt[1][getCurrentKingdom()][1]
                            || teamRanksInt[1][getCurrentKingdom()][0] == 0)
                        teamRanksInt[1][getCurrentKingdom()][0] = ++teamRanksInt[1][getCurrentKingdom()][0] % 5;
                    if (temp >= teamRanksInt[1][getCurrentKingdom()][0])
                        teamRanksInt[1][getCurrentKingdom()][0] = 0;
                }
                break;
            case R.id.team2Rank2_Button:
                if (teamRanksInt[1][getCurrentKingdom()][1] == 4)
                    teamRanksInt[1][getCurrentKingdom()][1] = 0;
                else {
                    temp = teamRanksInt[1][getCurrentKingdom()][1];
                    teamRanksInt[1][getCurrentKingdom()][1] = ++teamRanksInt[1][getCurrentKingdom()][1] % 5;
                    while (teamRanksInt[1][getCurrentKingdom()][1] == teamRanksInt[0][getCurrentKingdom()][0]
                            || teamRanksInt[1][getCurrentKingdom()][1] == teamRanksInt[0][getCurrentKingdom()][1]
                            || teamRanksInt[1][getCurrentKingdom()][1] == teamRanksInt[1][getCurrentKingdom()][0]
                            || teamRanksInt[1][getCurrentKingdom()][1] == 0)
                        teamRanksInt[1][getCurrentKingdom()][1] = ++teamRanksInt[1][getCurrentKingdom()][1] % 5;
                    if (temp >= teamRanksInt[1][getCurrentKingdom()][1])
                        teamRanksInt[1][getCurrentKingdom()][1] = 0;
                }
                break;
            default:
                break;
        }
        updateTotals();
        updateTotalsLayout();
    }

    //A private class that handles the games popup menu
    private class GameMenu implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            gamesPopupMenu.setOnMenuItemClickListener(
                    new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            currentGameID = item.getItemId();
                            gamesPopupMenuButton.setText(item.getTitle());
                            team1QueensDoublesButton.setVisibility(View.GONE);
                            team2QueensDoublesButton.setVisibility(View.GONE);
                            team1KOHDoubleButton.setVisibility(View.GONE);
                            team2KOHDoubleButton.setVisibility(View.GONE);
                            team1ScoresLayout.setVisibility(View.GONE);
                            team2ScoresLayout.setVisibility(View.GONE);
                            team1RankLayout.setVisibility(View.GONE);
                            team2RankLayout.setVisibility(View.GONE);
                            if (getCurrentKingdom() == -1) {
                                team1Score.setText(0 + "");
                                team2Score.setText(0 + "");
                                team1Rank1Button.setText(0 + "");
                                team1Rank2Button.setText(0 + "");
                                team2Rank1Button.setText(0 + "");
                                team2Rank2Button.setText(0 + "");
                            } else {
                                if (getCurrentGame() != TREX) {
                                    if (getCurrentGame() == QUEENS) {
                                        if (queensDoublesInt[0][getCurrentKingdom()] != 0) {
                                            team1QueensDoublesButton.setText(queensDoublesInt[0][getCurrentKingdom()] + "");
                                        } else {
                                            team1QueensDoublesButton.setText("Doubles");
                                        }
                                        if (queensDoublesInt[1][getCurrentKingdom()] != 0) {
                                            team2QueensDoublesButton.setText(queensDoublesInt[1][getCurrentKingdom()] + "");
                                        } else {
                                            team2QueensDoublesButton.setText("Doubles");
                                        }
                                        team1QueensDoublesButton.setVisibility(View.VISIBLE);
                                        team2QueensDoublesButton.setVisibility(View.VISIBLE);
                                    }
                                    if (getCurrentGame() == KOH) {
                                        if (KOHDoubleInt[0][getCurrentKingdom()] != 0) {
                                            team2KOHDoubleButton.setText(KOHDoubleInt[0][getCurrentKingdom()] + "");
                                        } else {
                                            team2KOHDoubleButton.setText("Double");
                                        }
                                        if (KOHDoubleInt[1][getCurrentKingdom()] != 0) {
                                            team2KOHDoubleButton.setText(KOHDoubleInt[1][getCurrentKingdom()] + "");
                                        } else {
                                            team2KOHDoubleButton.setText("Double");
                                        }
                                        team1KOHDoubleButton.setVisibility(View.VISIBLE);
                                        team2KOHDoubleButton.setVisibility(View.VISIBLE);
                                    }
                                    team1ScoresLayout.setVisibility(View.VISIBLE);
                                    team2ScoresLayout.setVisibility(View.VISIBLE);
                                    team1Score.setText(scoresInt[0][getCurrentKingdom()][getCurrentGame()] + "");
                                    team2Score.setText(scoresInt[1][getCurrentKingdom()][getCurrentGame()] + "");
                                } else if (getCurrentGame() == TREX) {
                                    team1RankLayout.setVisibility(View.VISIBLE);
                                    team2RankLayout.setVisibility(View.VISIBLE);
                                    if (teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[0][getCurrentKingdom()][1] &&
                                            teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[1][getCurrentKingdom()][0] &&
                                            teamRanksInt[0][getCurrentKingdom()][0] == teamRanksInt[1][getCurrentKingdom()][1]) {
                                        team1Rank1Button.setText("Rank");
                                        team1Rank2Button.setText("Rank");
                                        team2Rank1Button.setText("Rank");
                                        team2Rank2Button.setText("Rank");
                                    } else {
                                        team1Rank1Button.setText(teamRanksInt[0][getCurrentKingdom()][0] + "");
                                        team1Rank2Button.setText(teamRanksInt[0][getCurrentKingdom()][1] + "");
                                        team2Rank1Button.setText(teamRanksInt[1][getCurrentKingdom()][0] + "");
                                        team2Rank2Button.setText(teamRanksInt[1][getCurrentKingdom()][1] + "");
                                    }
                                }
                                team1GameTotal.setText("Game Total: " + gameTotals[0][getCurrentKingdom()][getCurrentGame()]);
                                team2GameTotal.setText("Game Total: " + gameTotals[1][getCurrentKingdom()][getCurrentGame()]);
                                scoresResetButton.setVisibility(View.VISIBLE);
                            }
                            return true;
                        }
                    });
            gamesPopupMenu.show();
        }

    }
}


//TODO readme file
//TODO figure out sharing
//TODO fix switching horizontally deletes everything
//TODO style the app
//TODO put it on the store :D