package com.black.web.base.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    
    public static String readerPath(String path) {
		BufferedReader in = null;
		StringBuffer text = new StringBuffer();
		try {
			in = new BufferedReader(new FileReader(path));
			String line = in.readLine();
			while (line!=null) {
				text.append(line);
				line=in.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text.toString();
	}
}
