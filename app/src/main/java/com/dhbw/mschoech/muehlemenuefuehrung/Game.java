package com.dhbw.mschoech.muehlemenuefuehrung;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhbw.mschoech.muehlemenuefuehrung.util.ActivityRegistry;



public class Game extends ActionBarActivity{
    public static Token token = null;
    static boolean toggle = true;
    static short amountToken = 3;
    static short play = 0;
    static short gamestate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityRegistry.register(this);

        //display appropriate Field
        Intent intent = getIntent();
        final String field = intent.getStringExtra("Field");
        FieldPositions fieldPositions = displayBackground(field);

        start(intent, fieldPositions);

    }

    private void start(Intent i, final FieldPositions fieldPositions){

        final ImageButton butUndo = (ImageButton) findViewById(R.id.buttonUndo);
        final ImageButton butRedo = (ImageButton) findViewById(R.id.buttonRedo);

        final RelativeLayout gameLayout = (RelativeLayout) (this.findViewById(R.id.gameLayout));


        System.out.println("Width: " + gameLayout.getWidth() +" Height: " + gameLayout.getHeight());
        //get data from previous page
        final String player1        = i.getStringExtra("Player 1");
        final String player2        = i.getStringExtra("Player 2");
        final String field          = i.getStringExtra("Field");
        final TextView texPlayer    = (TextView) findViewById(R.id.textPlayerTurn);
        final TextView texStep      = (TextView) findViewById(R.id.textNextStep);
        texPlayer.setText(player1);
        texStep.setText(getString(R.string.step1));
        //create Buttons and initialize their functions
        butActHome();
        butActOptions(field);
        endDialog();
/*
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int margins = (gameLayout.getHeight()-gameLayout.getWidth());
        System.out.println("MARGINS################: " +gameLayout.getHeight() + " W: " + gameLayout.getWidth());
        //params.setMargins(0,margins,0,margins);
        params.setMargins(0,101,0,101);

        gameLayout.setLayoutParams(params);
        gameLayout.requestLayout();
        */
/*************************************************/
        int dark    = R.drawable.spielstein_dunkel;
        int light   = R.drawable.spielstein_hell;



        final Token ligToken[] = new Token[amountToken];
        final Token darToken[] = new Token[amountToken];

        for ( int index = 0; index < amountToken; index++){
            ligToken[index] = new Token(Game.this, light);
            darToken[index] = new Token(Game.this, dark);
        }

        gameLayout.setOnTouchListener(
                new RelativeLayout.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent m) {
                        short tokenCounter = 0;
                        if (m.getAction() == MotionEvent.ACTION_DOWN ) {
                            switch (play) {
                                case 0:
                                    GameFuncs.gamestate_set(gameLayout, m, tokenCounter, fieldPositions, texPlayer, texStep, ligToken, darToken, player1, player2);
                                    break;
                                case 1:
                                    GameFuncs.gamestate_play(gameLayout, m, fieldPositions, texPlayer, texStep, ligToken, darToken, player1, player2);
                                    break;
                                default:
                                    break;
                            }
                        }
                        return true;
                    }
                }
        );
    }



    /****Generic**Stuff****************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
/**********************************************************************************/

    public FieldPositions displayBackground(String field){
        //static value

        switch (field) {
            case "field1":  setContentView(R.layout.activity_game_field1);
                            final FieldPositions fieldPositions = new Field1pos();
                            return fieldPositions;

            case "field2":  setContentView(R.layout.activity_game_field2);
                            break;
            case "field3":  setContentView(R.layout.activity_game_field3);
                            break;
            case "field4":  setContentView(R.layout.activity_game_field4);
                            break;

        }

        return null;
    }

    private void endDialog(){
        final ImageButton butEnd = (ImageButton) findViewById(R.id.buttonEnd);
        //end dialog
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle(getString(R.string.strEndPopUp));
        helpBuilder.setMessage(getString(R.string.strEndQuestionPopUp));
        helpBuilder.setNegativeButton(getString(R.string.strNoPopUp),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                    }
                });
        helpBuilder.setPositiveButton(getString(R.string.strYesPopUp),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        ActivityRegistry.finishAll();
                    }
                });
        final AlertDialog helpDialog = helpBuilder.create();

        butEnd.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        helpDialog.show();
                    }
                }
        );
    }
    private void butActHome(){
        final ImageButton butHome = (ImageButton) findViewById(R.id.buttonHome);
        final Intent intHome = new Intent(this, Main.class);

        butHome.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {
                        startActivity(intHome);
                    }
                }
        );

    }
    private void butActOptions(final String field){
        final ImageButton butOptions = (ImageButton) findViewById(R.id.buttonOptions);
        final Intent intOptions = new Intent(this, Options.class);
        butOptions.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        intOptions.putExtra("source", "game");
                        intOptions.putExtra("Field", field);
                        startActivity(intOptions);
                    }
                }
        );

    }

    protected class Token extends View {
        float xPos = 0;
        float yPos = 0;
        int width;
        int height;

        int tok;

        public Token(Context context, int tok) {
            super(context);
            setTok(tok);

        }
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            setwidth(canvas.getWidth());
            setheight(canvas.getHeight());
            canvas.drawColor(Color.TRANSPARENT);
            tok = getTok();
            Bitmap token = BitmapFactory.decodeResource(getResources(), tok);
            int targetWidth  = token.getWidth() /20;
            int targetHeight = token.getHeight() / 20;
            Bitmap tok = Bitmap.createScaledBitmap(token, targetWidth, targetHeight, true);
            token.recycle();

            canvas.drawBitmap(tok, xPos, yPos, new Paint());

        }

        /***GETTER**SETTER****************/
        public float getxPos() {
            return xPos;
        }
        public void setxPos(float xPos) {
            this.xPos = xPos;
        }
        public float getyPos() {
            return yPos;
        }
        public void setyPos(float yPos) {
            this.yPos = yPos;
        }
        public void setwidth(int width) {
            this.width = width;
        }
        public void setheight(int height) {
            this.height = height;
        }
        public int getwidth() {
            return width;
        }
        public int getheight() {
            return height;
        }

        public int getTok() {
            return tok;
        }
        public void setTok(int tok) {
            this.tok = tok;
        }

        /*********************************/



    }


}
