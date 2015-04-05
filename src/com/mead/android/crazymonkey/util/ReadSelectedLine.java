package com.mead.android.crazymonkey.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadSelectedLine {

	public static List<String> readDevicesList(String fileName) {
		BufferedReader reader = null;
		List<String> devicesList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line = reader.readLine();

			while (line != null) {
				if (line.contains("5555")) {
					devicesList.add(line.split("\t")[0]);
				}
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return devicesList;

	}

}
