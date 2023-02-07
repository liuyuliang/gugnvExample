package org.fileDownload.example;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.io.InputStream;

import java.io.RandomAccessFile;

import java.net.HttpURLConnection;

import java.net.MalformedURLException;

import java.net.URL;

import org.xml.sax.InputSource;

public class TestDownload {

	public static final String path = "http://192.168.1.247:8080/youdao.exe";

	public static void main(String[] args) throws Exception {

		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod("GET");

		conn.setConnectTimeout(5000);

		conn.setRequestProperty("User-Agent",

				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

		int code = conn.getResponseCode();

		if (code == 200) {

			int len = conn.getContentLength();

			RandomAccessFile file = new RandomAccessFile("/mnt/sdcard/" + getFilenName(path),

					"rwd");

			// 1.设置本地文件大小跟服务器的文件大小一致

			file.setLength(len);

			// 2 .假设开启3 个线程

			int threadnumber = 3;

			int blocksize = len / threadnumber;

			/**
			 * 
			 * 线程1 0~ blocksize 线程2 1*bolocksize ~ 2*blocksize 线程3 2*blocksize ~
			 * 
			 * 文件末尾
			 * 
			 */

			for (int i = 0; i < threadnumber; i++) {

				int startposition = i * blocksize;

				int endpositon = (i + 1) * blocksize;

				if (i == (threadnumber - 1)) {

					// 最后一个线程

					endpositon = len;

				}

				DownLoadTask task = new DownLoadTask(i, path, startposition,

						endpositon);

				task.start();

			}

		}

	}

	public static String getFilenName(String path) {

		int start = path.lastIndexOf("/") + 1;

		return path.substring(start, path.length());

	}

}

class DownLoadTask extends Thread {

	public static final String path = "http://dis.njtu.edu.cn/guide/incoming/interaction/xn003/1426082646467.zip";

	int threadid;

	String filepath;

	int startposition;

	int endpositon;

	public DownLoadTask(int threadid, String filepath, int startposition,

			int endpositon) {

		this.threadid = threadid;

		this.filepath = filepath;

		this.startposition = startposition;

		this.endpositon = endpositon;

	}

	@Override

	public void run() {

		try {

			File postionfile = new File(threadid + ".tar.gz");

			URL url = new URL(filepath);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			System.out.println("线程" + threadid + "正在下载 " + "开始位置 : "

					+ startposition + "结束位置 " + endpositon);

			if (postionfile.exists()) {

				FileInputStream fis = new FileInputStream(postionfile);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);

				byte[] result = buffer;

				int newstartposition = Integer.parseInt(new String(result));

				if (newstartposition > startposition) {

					startposition = newstartposition;

				}

			}

			// "Range", "bytes=2097152-4194303")

			conn.setRequestProperty("Range", "bytes=" + startposition + "-"

					+ endpositon);

			conn.setRequestMethod("GET");

			conn.setConnectTimeout(5000);

			conn.setRequestProperty("User-Agent",

					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			InputStream is = conn.getInputStream();

			RandomAccessFile file = new RandomAccessFile(getFilenName(path),

					"rwd");

			// 设置 数据从文件哪个位置开始写

			file.seek(startposition);

			byte[] buffer = new byte[1024];

			int len = 0;

			// 代表当前读到的服务器数据的位置 ,同时这个值已经存储的文件的位置

			int currentPostion = startposition;

			// 创建一个文件对象 ,记录当前某个文件的下载位置

			while ((len = is.read(buffer)) != -1) {

				file.write(buffer, 0, len);

				currentPostion += len;

				// 需要把currentPostion 信息给持久化到存储设备

				String position = currentPostion + "";

				FileOutputStream fos = new FileOutputStream(postionfile);

				fos.write(position.getBytes());

				fos.flush();

				fos.close();

			}

			file.close();

			System.out.println("线程" + threadid + "下载完毕");

			// 当线程下载完毕后 把文件删除掉

			if (postionfile.exists()) {

				postionfile.delete();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		super.run();

	}

	public static String getFilenName(String path) {

		int start = path.lastIndexOf("/") + 1;

		return path.substring(start, path.length());

	}

}