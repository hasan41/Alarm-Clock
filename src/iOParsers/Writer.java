package iOParsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Settings.Settings;

import utility.Alarm;
import utility.AlarmUtilities;

public class Writer {

	public static void writeConfig(){
		
		if(! new File(System.getProperty("user.home") + "/.cache/alarmclock/").exists()){
			new File(System.getProperty("user.home") + "/.cache/alarmclock/").mkdir();
		}

		PrintWriter out = null;

		try {
			
			out = new PrintWriter(new File(System.getProperty("user.home") + "/.cache/alarmclock/AlarmClock.data"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		out.write("Alarms\n");

		for(Alarm alarm : AlarmUtilities.getInstance().getList()){
			out.write(alarm.getTime() + "\n" + alarm.getAlertMessage() + "\n" + alarm.getPriority() + "\n");
		}

		out.write("Settings\n");
		out.write(Settings.getInstance().getTheme() + "\n");
		out.write(Settings.getInstance().getUserTonePath()+ "\n");
		out.write(Settings.getInstance().getIsWidget()+ "\n");
		out.write(Settings.getInstance().isDefaultTone()+ "\n");
		out.write(Settings.getInstance().isExitAnimation()+ "\n");
		

		out.flush();
		out.close();
	}

}
