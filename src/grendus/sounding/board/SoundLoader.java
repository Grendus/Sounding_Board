package grendus.sounding.board;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.view.View;
import android.view.View.OnClickListener;
import java.io.File;
import java.util.ArrayList;

public class SoundLoader extends ListActivity implements OnClickListener
{
	TextView filePath;
	EditText buttonName;
	Button acceptButton;
	Button activeButton;
	String audioFile;
	ArrayList<String> fileList;
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.loader);
	    
	    //initialize
	    filePath = (TextView)findViewById(R.id.TextView1);
	    buttonName = (EditText)findViewById(R.id.editText2);
	    acceptButton = (Button)findViewById(R.id.accept_button);
	    acceptButton.setOnClickListener(this);
	    this.activeButton = Data.activeButton;
	    audioFile = null;
	    
	    buttonName.setText(activeButton.getText());
	    filePath.setText(Data.filePath);
	    
	    //create an arraylist and load it with all the files in the program's music directory
	    fileList = new ArrayList<String>();
	    for(File temp:getExternalFilesDir(Environment.DIRECTORY_MUSIC).listFiles())
	    	fileList.add(temp.getName());
	    
	    //put the music files in the list so the user can see them
	    setListAdapter(new ArrayAdapter<String>(this, R.layout.row ,fileList));
    }

	public void onClick(View v)
	{
		//Change the text on the button
		activeButton.setText(buttonName.getText());
		if(audioFile != null)
		{
			//load the file into the SoundPool and save its sound value in the sound map for playback
			Data.soundMap.put(Integer.valueOf(activeButton.getId()), new Integer(Data.soundPlayer.load(audioFile, 1)));
			//load the current button and the absolute path of the sound value into the file map for saving
			Data.fileMap.put(activeButton, audioFile);
		}
		finish();
	}
	
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		File[] files = getExternalFilesDir(Environment.DIRECTORY_MUSIC).listFiles();
		
		//if the file selected is a .ogg file, record the absolute path of the file so it can be loaded
		if(files[position].getName().endsWith(".ogg"))
		{
			audioFile = files[position].getAbsolutePath();
		}
			
		/*File[] files = new File(filePath.getText().toString()).listFiles();
		if (files[position].isDirectory() && files[position].canRead())
		{
			filePath.setText(files[position].getAbsolutePath());
			fileList.clear(); 
			File[] temp = files[position].listFiles();
			for(int i = 0; i<temp.length; i++)
			{
				fileList.add(temp[i].getName());
			}
			onContentChanged();
		}
		else if(fileList.get(position).endsWith(".ogg"))
			audioFile = filePath.getText().toString()+"/"+fileList.get(position);*/
			
	}

}
