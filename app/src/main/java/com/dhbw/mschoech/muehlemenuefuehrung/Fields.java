package com.dhbw.mschoech.muehlemenuefuehrung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dhbw.mschoech.muehlemenuefuehrung.util.ActivityRegistry;


public class Fields extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityRegistry.register(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields);

        final Button butBack = (Button) findViewById(R.id.buttonBack);
        final ImageButton butField1 = (ImageButton) findViewById(R.id.buttonField1);
        final ImageButton butField2 = (ImageButton) findViewById(R.id.buttonField2);
        final ImageButton butField3 = (ImageButton) findViewById(R.id.buttonField3);
        final ImageButton butField4 = (ImageButton) findViewById(R.id.buttonField4);

        final Intent intPlayer = new Intent(this, Player.class);
        final Intent intField  = new Intent(this, Game.class);
        Intent i = getIntent();
        final String mode = i.getStringExtra("Mode");
        final String player1 = i.getStringExtra("Player 1");
        final String player2 = i.getStringExtra("Player 2");


        TextView texVersus = (TextView) findViewById(R.id.textVersus);

        if(mode.equals("0")){
            texVersus.setText(player1 + " vs. Computer");
        }else{
            texVersus.setText(player1 + " vs. " + player2);
        }


        butBack.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        intPlayer.putExtra("Mode", mode);
                        intPlayer.putExtra("Player 1", player1);
                        intPlayer.putExtra("Player 2", player2);
                        startActivity(intPlayer);
                    }
                }
        );

        butField1.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        System.out.println(player1);
                        intField.putExtra("Player 1", player1);
                        intField.putExtra("Player 2", player2);
                        intField.putExtra("Field", "field1");
                        startActivity(intField);
                    }
                }
        );
        butField2.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        intField.putExtra("Player 1", player1);
                        intField.putExtra("Player 2", player2);
                        intField.putExtra("Field", "field2");
                        startActivity(intField);
                    }
                }
        );
        butField3.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        intField.putExtra("Player 1", player1);
                        intField.putExtra("Player 2", player2);
                        intField.putExtra("Field", "field3");
                        startActivity(intField);
                    }
                }
        );
        butField4.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        intField.putExtra("Player 1", player1);
                        intField.putExtra("Player 2", player2);
                        intField.putExtra("Field", "field4");
                        startActivity(intField);
                    }
                }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fields, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
