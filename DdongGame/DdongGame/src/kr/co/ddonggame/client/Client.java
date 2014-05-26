package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements ChatIF {
	private String host = "203.249.22.116";
	private int port = 8087;
	private Socket clientSocket;
	private String login = "android";
	private ObjectOutputStream output;
	private ChatClient client;
	private String messageFromServer;

	public Client() {
		client = new ChatClient(host, port, login, this);
	}

	public void handleMessage(String msg) {
		client.handleMessageFromClientUI(msg);
	}

	@Override
	public void display(String message) {
		messageFromServer = message;

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