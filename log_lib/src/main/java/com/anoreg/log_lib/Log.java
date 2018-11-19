package com.anoreg.log_lib;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Environment;
import android.text.TextUtils;

public class Log {

	private final static String FILE_TYPE = ".txt";
	public static Map<String, File> map = null;
	public static LogFiles logFiles;
	private static String root = Environment.getExternalStorageDirectory() + "/HSLog/";
	private static String path;
	private static String defultPath = "Log";
	private static long fileLength;
	private static boolean isPrint = false;
	private static boolean isWrite = false;
	private static boolean isError = false;

	private static String customTagPrefix = "HSLog_"; // TAG 前缀

	private static String generateTag(StackTraceElement caller) {
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
		tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
		return tag;
	}

	public Log() {
		initLogFiles();
	}

	// -------------------------------------------------------------------
	public static void print(String msg) {
		if (isPrint()) {
			System.out.println(msg == null ? " " : msg);
		}
		if (isWrite()) {
			writeLogToFile("OUT", "", msg);
		}
	}

	public static void println(String msg) {
		if (isPrint()) {
			System.out.println(msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("OUT", "", msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isPrint()) {
			android.util.Log.i(tag, msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("INFO", tag, msg);
		}

	}

	public static void i(String tag, String msg, Throwable tr) {
		if (isPrint()) {
			android.util.Log.i(tag, msg == null ? "" : msg, tr);
		}
		if (isWrite()) {
			writeLogToFile("INFO", tag, getErrorInfo(tr));
		}
	}

	public static void d(String tag, String msg) {
		if (isPrint()) {
			android.util.Log.d(tag, msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("DEBUG", tag, msg);
		}

	}

	public static void d(String tag, String msg, Throwable tr) {
		if (isPrint()) {
			android.util.Log.d(tag, msg == null ? "" : msg, tr);
		}
		if (isWrite()) {
			writeLogToFile("DEBUG", tag, getErrorInfo(tr));
		}

	}

	public static void e(String tag, String msg) {
		if (isPrint()) {
			android.util.Log.e(tag, msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("ERROR", tag, msg);
		} else if (isError()) {
			writeLogToFile("ERROR", tag, msg);
		}

	}

	public static void e(String tag, String msg, Throwable tr) {
		if (isPrint()) {
			android.util.Log.e(tag, msg == null ? "" : msg, tr);
		}
		if (isWrite()) {
			writeLogToFile("ERROR", tag, getErrorInfo(tr));
		} else if (isError()) {
			writeLogToFile("ERROR", tag, getErrorInfo(tr));
		}

	}

	public static void v(String tag, String msg) {
		if (isPrint()) {
			android.util.Log.v(tag, msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("VERBOSE", tag, msg);
		}

	}

	public static void v(String tag, String msg, Throwable tr) {
		if (isPrint()) {
			android.util.Log.v(tag, msg == null ? "" : msg, tr);
		}
		if (isWrite()) {
			writeLogToFile("VERBOSE", tag, getErrorInfo(tr));
		}

	}

	public static void w(String tag, String msg) {
		if (isPrint()) {
			android.util.Log.w(tag, msg == null ? "" : msg);
		}
		if (isWrite()) {
			writeLogToFile("WARN", tag, msg);
		}

	}

	public static void w(String tag, String msg, Throwable tr) {
		if (isPrint()) {
			android.util.Log.w(tag, msg == null ? "" : msg, tr);
		}
		if (isWrite()) {
			writeLogToFile("WARN", tag, getErrorInfo(tr));
		}
	}

	// ********************************************************************

	public static void i(String msg) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			i(tag, msg);
		}
	}

	public static void i(String msg, Throwable tr) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			i(tag, msg, tr);
		}
	}

	public static void d(String msg) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			d(tag, msg);
		}

	}

	public static void d(String msg, Throwable tr) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			d(tag, msg, tr);
		}

	}

	public static void e(String msg) {
		if (isPrint() || isWrite() || isError()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			e(tag, msg);
		}
	}

	public static void e(String msg, Throwable tr) {
		if (isPrint() || isWrite() || isError()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			e(tag, msg, tr);
		}

	}

	public static void v(String msg) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			v(tag, msg);
		}

	}

	public static void v(String msg, Throwable tr) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			v(tag, msg, tr);
		}

	}

	public static void w(String msg) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			w(tag, msg);
		}

	}

	public static void w(String msg, Throwable tr) {
		if (isPrint() || isWrite()) {
			StackTraceElement caller = OtherUtils.getCallerStackTraceElement();
			String tag = generateTag(caller);
			w(tag, msg, tr);
		}
	}

	// -------------------------------------------------------------------
	private static void initLogFiles() {
		try {
			if (map == null) {
				map = new HashMap<String, File>();
				logFiles = ParseXml.parserLogFiles(Util.readFile(getLogFilesInfoPath()));
				if (logFiles == null) {
					logFiles = new LogFiles();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}

	/**
	 * 
	 * @throws IOException
	 */
	private static File createFile(String level) throws IOException {
		if (isWrite()) {
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				System.out.println("sdcard 不存在");
				return null;
			}

			if (!Util.judgeStr(level)) {
				level = "OUT";
			}

			File file = new File(getPath(), level);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (file.exists()) {
				if (map == null) {
					initLogFiles();
				}
				File logFile = map.get(level);
				if (logFile == null) {
					String fileName = logFiles.getFileName(level);
					if (!Util.judgeStr(fileName)) {
						fileName = getSystemDate() + "_" + level + FILE_TYPE;
					}
					logFile = new File(file.getAbsolutePath(), fileName);
					logFiles.setFileByLevel(level, fileName);
					Util.writeFile(LogFiles.getSendParam(logFiles), getLogFilesInfoPath());
				}

				if (!logFile.exists()) {
					logFile.createNewFile();
				} else {
					logFile = judgeFileLength(logFile, level);
				}
				return logFile;
			}
		}
		return null;
	}

	private static File judgeFileLength(File logFile, String level) throws IOException {
		File newFile = null;
		if (logFile.isFile()) {
			if (logFile.length() > getFileLength()) {
				File file = new File(getPath(), level);
				if (!file.exists()) {
					file.mkdirs();
				}
				String fileName = getSystemDate() + "_" + level + FILE_TYPE;
				newFile = new File(file.getAbsolutePath(), fileName);
				if (!newFile.exists()) {
					newFile.createNewFile();
				}
				if (newFile != null) {
					logFiles.setFileByLevel(level, newFile.getName());
					Util.writeFile(LogFiles.getSendParam(logFiles), getLogFilesInfoPath());
					System.out.println("----judgeFileLength----->" + newFile.getName());
				}
			} else {
				newFile = logFile;
			}
		}
		return newFile;
	}

	private static void writeLogToFile(String level, String tag, String msg) {
		try {
			File file = createFile(level);
			if (file != null) {
				synchronized (file) {
					StringBuffer sb = new StringBuffer();
					sb.append(getSystemDate2());
					sb.append(": ");
					sb.append(level);
					sb.append(": ");
					sb.append(tag);
					sb.append(": ");
					sb.append(msg);
					sb.append("\n");
					RandomAccessFile raf = null;
					try {
						raf = new RandomAccessFile(file, "rw");
						raf.seek(file.length());
						raf.write(sb.toString().getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();

					} catch (IOException e) {
						e.printStackTrace();

					} finally {
						sb = null;
						if (raf != null) {
							try {
								raf.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("---writeLogToFile------->" + e.toString());
		}
	}

	public static String getSystemDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	public static String getSystemDate2() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	private static String getErrorInfo(Throwable arg1) {
		if(arg1 == null)
			return "";
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error = writer.toString();
		return error;
	}

	public static String getPath() {
		String pathStr = null;
		if (!Util.judgeStr(path)) {
			pathStr = defultPath;
		} else {
			pathStr = path;
		}
		return root + pathStr;
	}

	public static void setPath(String path) {
		Log.path = path;
	}

	public static long getFileLength() {
		if (fileLength == 0) {
			fileLength = 1024 * 50;
		}
		return fileLength;
	}

	public static void setFileLength(long fileLength) {
		Log.fileLength = fileLength;
	}

	public static boolean isPrint() {
		return isPrint;
	}

	public static void setPrint(boolean isPrint) {
		Log.isPrint = isPrint;
	}

	public static boolean isWrite() {
		return isWrite;
	}

	public static void setWrite(boolean isWrite) {
		Log.isWrite = isWrite;
	}

	public static boolean isError() {
		return isError;
	}

	public static void setError(boolean isError) {
		Log.isError = isError;
	}

	public static String getLogFilesInfoPath() {
		return getPath() + "/infos.txt";
	}

	public static String getCustomTagPrefix() {
		return customTagPrefix;
	}

	/**
	 * 自定义前缀
	 * 
	 * @param customTagPrefix
	 */
	public static void setCustomTagPrefix(String customTagPrefix) {
		Log.customTagPrefix = customTagPrefix;
	}

}
