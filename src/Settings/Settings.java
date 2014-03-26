package Settings;

import java.io.Serializable;

public class Settings implements Serializable{
	
	private static final long serialVersionUID = 7371835551559025257L;
	private static Settings instance = null;
	private String userTonePath;
	private boolean isDefaultTone;
	private String theme;
	private boolean isExitAnimation;
	private boolean isWidget;
	
	public static String SYSTEM = "system";
	public static String NIMBUS = "nimbus";
	public static String WEBLAF = "weblaf";
	
	
	private Settings(){
		
	}
	
	public static Settings getInstance(){
		if(instance == null){
			instance = new Settings();
		}
		return instance;
	}

	public String getUserTonePath() {
		return userTonePath;
	}

	public void setUserTonePath(String userTonePath) {
		this.userTonePath = userTonePath;
	}

	public boolean isDefaultTone() {
		return isDefaultTone;
	}

	public void setDefaultTone(boolean isDefaultTone) {
		this.isDefaultTone = isDefaultTone;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public boolean isExitAnimation() {
		return isExitAnimation;
	}

	public void setExitAnimation(boolean isExitAnimation) {
		this.isExitAnimation = isExitAnimation;
	}
	
	public void setIsWidget(boolean b){
		isWidget = b;
	}

	public boolean getIsWidget(){
		return isWidget;
	}
}
