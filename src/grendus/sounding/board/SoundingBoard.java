package grendus.sounding.board;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import java.util.HashMap;
import android.content.Intent;
import android.util.DisplayMetrics;

public class SoundingBoard extends Activity implements OnClickListener, OnLongClickListener {
    /** Called when the activity is first created. */
    private HashMap<Integer, Integer> soundValues;
    private SoundPool sounds;
    private Button[] buttons;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Retrieve buttons from XML and assign them names
        soundValues = Data.soundMap;
        
        //initialize SoundPool object and load sounds.
        sounds = Data.soundPlayer;
        buttons = Data.buttons;
        assignButtons();
    }
    
    //sets up the buttons array
    private void assignButtons()
    {
    	//assign this class as the OnClickListener and OnLongClickListener for each button
        buttons[0] = ((Button)findViewById(R.id.Button1));
        buttons[1] = ((Button)findViewById(R.id.Button2));
        buttons[2] = ((Button)findViewById(R.id.Button3));
        buttons[3] = ((Button)findViewById(R.id.Button4));
        buttons[4] = ((Button)findViewById(R.id.Button5));
        buttons[5] = ((Button)findViewById(R.id.Button6));
        buttons[6] = ((Button)findViewById(R.id.Button7));
        buttons[7] = ((Button)findViewById(R.id.Button8));
        buttons[8] = ((Button)findViewById(R.id.Button9));
        buttons[9] = ((Button)findViewById(R.id.Button10));
        buttons[10] = ((Button)findViewById(R.id.Button11));
        buttons[11] = ((Button)findViewById(R.id.Button12));
        buttons[12] = ((Button)findViewById(R.id.Button13));
        buttons[13] = ((Button)findViewById(R.id.Button14));
        buttons[14] = ((Button)findViewById(R.id.Button15));
        buttons[15] = ((Button)findViewById(R.id.Button16));
        buttons[16] = ((Button)findViewById(R.id.Button17));
        buttons[17] = ((Button)findViewById(R.id.Button18));
        buttons[18] = ((Button)findViewById(R.id.Button19));
        buttons[19] = ((Button)findViewById(R.id.Button20));
        buttons[20] = ((Button)findViewById(R.id.Button21));
        buttons[21] = ((Button)findViewById(R.id.Button22));
        buttons[22] = ((Button)findViewById(R.id.Button23));
        buttons[23] = ((Button)findViewById(R.id.Button24));
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels/9;
        int width = displaymetrics.widthPixels/4;

    	//assign this class as the OnClickListener and OnLongClickListener for each button, and portion the height and width of the buttons
	    for(Button temp: buttons)
	    {
	        temp.setOnClickListener(this);
	        temp.setOnLongClickListener(this);
	        temp.setHeight(height);
	        temp.setWidth(width);
	    }
	    
	    ((Button)findViewById(R.id.save_load_button)).setOnClickListener(this);
	    soundValues.put(Integer.valueOf(buttons[1].getId()), sounds.load(this, R.raw.rimshot, 1));
    }
    
    public void onClick(View v)
    {
    	if(v.getId() == R.id.save_load_button)
    	{
    		startActivity(new Intent(getBaseContext(), BoardSaveLoad.class));
    	}
    	else
    	{
	    	AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
	    	float volume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)/(float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	    	if (soundValues.get(Integer.valueOf(v.getId())) != null)
	    		sounds.play(soundValues.get(Integer.valueOf(v.getId())), volume, volume, 1, 0, 1f);
    	}
    }
    
    public boolean onLongClick(View v)
    {
    	Intent buttonIntent = new Intent(getBaseContext(), SoundLoader.class);
    	try
    	{
    		//store the current button in an external, static class so you can pass it between objects
    		Data.activeButton = (Button)v;
    		startActivity(buttonIntent);
    	}
    	catch(Exception e)
    	{
    		//show the exception text in an alertdialog
    		AlertDialog AD = new AlertDialog.Builder(this).create();
    		AD.setMessage(e.getMessage()); 
    		AD.show();
    	}
    	return true;
    }
    
}