package grendus.sounding.board;

import java.util.HashMap;
import android.widget.Button;
import android.media.AudioManager;
import android.media.SoundPool;
public class Data 
{
	public static HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
	public static HashMap<Button, String> fileMap = new HashMap<Button, String>();
	public static Button activeButton;
	public static Button[] buttons = new Button[24];
	public static String filePath = "/";
	public static SoundPool soundPlayer = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
}
