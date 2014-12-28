package com.xidian.udpchat.utils;

public class ConnectUtils {
	public static String localIp = "172.26.88.3";
	public static int localPort = 8888;
	public static String remoteIp = "172.26.88.2"; 
	public static int  remotePort = 10000; 
	public static String getLocalIp() {
		return localIp;
	}
	public static void setLocalIp(String localIp) {
		ConnectUtils.localIp = localIp;
	}
	public static int getLocalPort() {
		return localPort;
	}
	public static void setLocalPort(int localPort) {
		ConnectUtils.localPort = localPort;
	}
	public static String getRemoteIp() {
		return remoteIp;
	}
	public static void setRemoteIp(String remoteIp) {
		ConnectUtils.remoteIp = remoteIp;
	}
	public static int getRemoteport() {
		return remotePort;
	}
	public static void setRemoteport(int remoteport) {
		ConnectUtils.remotePort = remoteport;
	}
	
}
