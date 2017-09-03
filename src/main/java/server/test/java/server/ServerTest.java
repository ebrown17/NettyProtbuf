package server.test.java.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import server.Server;

public class ServerTest {
  final static Logger logger = LoggerFactory.getLogger("server.test.java.server.ServerTest");
  
  public static void main(String... args) {
    
    try {
      logger.debug("Server Starting... ");
      InetSocketAddress socketAddress = new InetSocketAddress(26002);

      Server server = new Server(socketAddress);
      server.configure();
      server.startServer();
      // Thread.sleep(5000); 
      // server.shutdownServer();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}