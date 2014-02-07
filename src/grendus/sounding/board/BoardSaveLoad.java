package grendus.sounding.board;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import android.app.AlertDialog;

public class BoardSaveLoad extends ListActivity implements OnClickListener
{
	EditText fileName;
	ArrayList<String> fileNameList;
	
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.save_and_load_screen);

	    //set onclicklisteners
	    ((Button)findViewById(R.id.save_button)).setOnClickListener(this);
	    ((Button)findViewById(R.id.load_button)).setOnClickListener(this);
	    
	    fileName = (EditText)findViewById(R.id.file_name);
	    
	    //list existing saved files
	    fileNameList = new ArrayList<String>();
	    String[] fileNames = fileList();
	    for(String temp:fileNames)
	    	fileNameList.add(temp);
	    setListAdapter(new ArrayAdapter<String>(this, R.layout.row ,fileNameList));
	}
	
	protected void onListItemClick (ListView l, View v, int position, long id)
	{
		fileName.setText(fileList()[position]);
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.save_button:	saveFile();
								finish();
								break;
		case R.id.load_button:  loadFile();
								finish();
								break;
		default:				fileName.setText("We done goofed...");
								break;
								
		}
	}
	
	public void saveFile()
	{
		//if the filename isn't empty
		if(!fileName.getText().toString().equals(""))
		{
			FileOutputStream fos;
			try
			{
				fos = openFileOutput(fileName.getText().toString(), Context.MODE_PRIVATE);
				String saveString = "";
				Object[] buttons = Data.fileMap.keySet().toArray();
				if(buttons.length>0)
				{
					Button tempButton = (Button)buttons[0];
					//save each button as a string of ID|label|sound location
					saveString += tempButton.getId()+"~"+tempButton.getText().toString()+"~"+Data.fileMap.get(tempButton);
				}
				for(int i = 1; i<buttons.length; i++)
				{
					Button tempButton = (Button)buttons[i];
					//save each button as a string of ID|label|sound location
					saveString += "~"+tempButton.getId()+"~"+tempButton.getText().toString()+"~"+Data.fileMap.get(tempButton);
				}
				fos.write(saveString.getBytes());
			}
			catch(Exception e)
			{
				AlertDialog ad = new AlertDialog.Builder(this).create();
				ad.setMessage(e.getMessage());
				ad.show();
			}
		}
	}
	
	public void loadFile()
	{
		try
		{
			FileInputStream fis = openFileInput(fileName.getText().toString());
			String saved_data = "";
			int readByte = fis.read();
			while(readByte != -1)		//read the file byte by byte, converting each one to a character, and store it in the string
			{
				saved_data+=(char)readByte;
				readByte = fis.read();
			}
			
			String[] choppedData = saved_data.split("~");
			
			//clear current values
			Data.fileMap.clear();
			Data.soundMap.clear();
			for(Button temp:Data.buttons)
				temp.setText("");
			for(int i = 0; i<choppedData.length; i+=3)
			{
				//parse the save file. First chunk is the id for the button, second is it's name, third is the file location of 
				Button temp = getButton(Integer.parseInt(choppedData[i]));
				if(temp == null)
				{
					AlertDialog ad = new AlertDialog.Builder(this).create();
					ad.setMessage("fuuuuuuuuu");
					ad.show();
				}
				temp.setText(choppedData[i+1]);
				Data.fileMap.put(temp, choppedData[i+2]);
				Data.soundMap.put(Integer.valueOf(temp.getId()), Data.soundPlayer.load(choppedData[i+2], 1));
			}
			/*String bugtester = "";
			bugtester+=choppedData.length+"\n\n\n";
			for(String temp:choppedData)
			{
				bugtester+=temp+"\n";
			}
			
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setMessage(bugtester);
			ad.show();*/
		}
		catch(Exception e)
		{
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setMessage(e.getMessage()+" in bed ");
			ad.show();
		}
	}
	
	public Button getButton(int buttonId)
	{
		Button returnButton = null;
		for (int i = 0; i<23; i++)
		{
			if(Data.buttons[i].getId() == buttonId)
				returnButton = Data.buttons[i];
		}
		return returnButton;
	}
}