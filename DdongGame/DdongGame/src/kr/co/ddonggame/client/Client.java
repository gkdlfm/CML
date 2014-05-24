package kr.co.ddonggame.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Client implements ChatIF{
	private String host = "203.249.22.116";
	private int port = 8087;
	private Socket clientSocket;
	private String login = "android";
	private ObjectOutputStream output;
	private ChatClient client;
	public Client(){
		client = new ChatClient(host, port, login, this);
	}

	public void handleMessage(String msg){
		client.handleMessageFromClientUI(msg);
	}
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * private ObjectOutputStream output; private Socket socket;
	 * 
	 * private BufferedReader networkReader; private BufferedWriter
	 * networkWriter;
	 * 
	 * private String ip = "203.249.22.116"; // IP private int port = 8087; //
	 * PORT번호
	 * 
	 * // Server로 보낼 String을 정의 public String message = "test";
	 * 
	 * public Client() throws IOException { Log.i("test", "2"); try {
	 * setSocket(ip, port); } catch (IOException e1) { Log.i("test",
	 * e1.toString()); e1.printStackTrace(); } Log.i("test", "3");
	 * checkUpdate.start(); Log.i("test", "4"); //out = new
	 * PrintWriter(networkWriter, true); Log.i("test", "5");
	 * 
	 * 
	 * //out.println(message); //Log.i("test", "6"+msg); }
	 * 
	 * public void test() {
	 * 
	 * }
	 * 
	 * protected void onStop() {
	 * 
	 * try { socket.close(); } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * private Thread checkUpdate = new Thread() {
	 * 
	 * public void run() { try { String line; Log.w("ChattingStart",
	 * "Start Thread"); while (true) { Log.w("Chatting is running",
	 * "chatting is running"); line = networkReader.readLine(); //html = line;
	 * // mHandler.post(showUpdate); } } catch (Exception e) {
	 * 
	 * } } };
	 * 
	 * public void setSocket(String ip, int port) throws IOException {
	 * Log.i("test", "set1"); try { socket = new Socket(ip, port); output = new
	 * ObjectOutputStream(socket.getOutputStream()); String msg =
	 * "sssssssssssssssssss"; output.writeObject(msg);
	 * 
	 * networkWriter = new BufferedWriter(new OutputStreamWriter(
	 * socket.getOutputStream())); networkReader = new BufferedReader(new
	 * InputStreamReader( socket.getInputStream())); Log.i("test", "set33"); }
	 * catch (IOException e) { Log.i("test", "set2"); System.out.println(e);
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

}