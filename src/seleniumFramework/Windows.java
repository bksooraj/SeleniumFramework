package seleniumFramework;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.*;
import com.sun.jna.win32.W32APIOptions;

public class Windows {

	public interface User32 extends W32APIOptions {

		User32 instance = (User32) Native.loadLibrary("user32", User32.class, DEFAULT_OPTIONS);

		boolean ShowWindow(HWND hWnd, int nCmdShow);

		boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);

		boolean SetForegroundWindow(HWND hWnd);

		HWND FindWindow(String winClass, String title);

		int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);

		int SW_SHOW = 1;

	}

	private static Dictionary<String, HWND> getWindows() {
		final User32 user32 = User32.instance;
		final Dictionary<String, HWND> winList = new Hashtable<>();
		user32.EnumWindows(new WNDENUMPROC() {
			int count;

			public boolean callback(HWND hWnd, Pointer userData) {

				byte[] windowText = new byte[512];
				user32.GetWindowTextA(hWnd, windowText, 512);
				String wText = Native.toString(windowText);
				wText = (wText.isEmpty()) ? "" : "; text: " + wText;
				System.out.println("Found window " + hWnd + ", total " + ++count + wText);
				winList.put(wText, hWnd);
				return true;
			}
		}, null);
		return winList;
	}

	public static void ActWindow(String strWindowName) {
		User32 user32 = User32.instance;
		Dictionary<String, HWND> winList = getWindows();
		Enumeration<String> arrKeys = winList.keys();
		Enumeration<HWND> arrHWNDS = winList.elements();
		String strTitle;
		HWND hwn;
		while (arrKeys.hasMoreElements()) {
			strTitle = arrKeys.nextElement();
			hwn = arrHWNDS.nextElement();
			System.out.println(strTitle);
			if (strTitle.contains(strWindowName)) {
				user32.ShowWindow(hwn, User32.SW_SHOW);
				user32.SetForegroundWindow(hwn);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			}
		}
		/*
		 * User32 user32 = User32.instance; HWND hWnd = user32.FindWindow(null,
		 * strWindowName); // Sets focus to my // opened // 'Downloads' //
		 * folder user32.ShowWindow(hWnd, User32.SW_SHOW);
		 * user32.SetForegroundWindow(hWnd);
		 */
	}
}