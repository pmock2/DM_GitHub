package teamHarambe;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(1234);
		
		while (true) {
			Socket s = server.accept();
			Runnable connectionHandler = new ConnectionHandler(s);
			new Thread(connectionHandler).start();
		}
	}
}
