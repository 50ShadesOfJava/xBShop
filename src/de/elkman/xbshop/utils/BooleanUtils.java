package de.elkman.xbshop.utils;

public class BooleanUtils {

	
	public static String getColor(Boolean b){
		if (b){
			return "§a";
		}
		return "§c";
	}

	public static String getEnabledDisabled(Boolean b){
		if (b){
			return "Aktiviert";
		}
		return "Deaktiviert";
	}
	public static String getYesNo(Boolean b){
		if (b){
			return "Ja";
		}
		return "Nein";
	}
	
}
