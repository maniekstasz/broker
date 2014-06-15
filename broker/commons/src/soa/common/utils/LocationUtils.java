package soa.common.utils;

public class LocationUtils {

	
	public static Long getIdFromLocation(String location){
		int index = location.lastIndexOf("/");
		return new Long(location.substring(index + 1));
	}
}
