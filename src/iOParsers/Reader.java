package iOParsers;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import javax.swing.JOptionPane;

import utility.Alarm;
import utility.AlarmUtilities;
import Settings.Settings;

public class Reader {
	
	static boolean isOld = false;
	
	public static void readConfig(){
		Scanner sc = null;
		
		try{
			
			sc = new Scanner(new File(System.getProperty("user.home") + "/.cache/alarmclock/AlarmClock.data"));
			
		}catch(Exception e){
			Settings.getInstance().setTheme(Settings.SYSTEM);
			Settings.getInstance().setUserTonePath(null);
			Settings.getInstance().setIsWidget(false);
			Settings.getInstance().setDefaultTone(true);
			Settings.getInstance().setExitAnimation(false);
			return;
		}
		
		if(sc.nextLine().equals("Alarms")){
			while(true){
				String temp = sc.nextLine();
				if(temp.equals("Settings")){
					break;
				}
				else{
					try {
						DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
					    Date date =  df.parse(temp); 
						
						
						if(date.before(Calendar.getInstance().getTime())){
							sc.nextLine();
							sc.nextLine();
							isOld = true;
							continue;
						}
						
						AlarmUtilities.getInstance().addToList(new Alarm(date, sc.nextLine(), sc.nextLine()));
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		Settings.getInstance().setTheme(sc.nextLine());
		String path = sc.nextLine();
		Settings.getInstance().setUserTonePath(path.equals("null") ? null : path);
		Settings.getInstance().setIsWidget(sc.nextLine().equals("true") ? true : false);
		Settings.getInstance().setDefaultTone(sc.nextLine().equals("true") ? true : false);
		Settings.getInstance().setExitAnimation(sc.nextLine().equals("true") ? true : false);
		
		if(isOld){
			JOptionPane.showMessageDialog(null, "Some of the alarms have already passed. They will not appear in the table.", "Alarm clock", JOptionPane.INFORMATION_MESSAGE);
		}
		
		sc.close();
	}

}
