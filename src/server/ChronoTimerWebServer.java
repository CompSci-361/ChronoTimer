package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import core.Chronotimer;

public class ChronoTimerWebServer {
	private static boolean isInitialized = false;
	private static Chronotimer serverTimer = null;
	private static HttpServer httpServer = null;
	
	public static void initialize(Chronotimer chronoTimer) throws IOException {
		if (isInitialized) return;
		if (chronoTimer == null) throw new IllegalArgumentException("chronoTimer");
		serverTimer = chronoTimer;
		
		httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
		
        // create a context to get the request to display the results
		httpServer.createContext("/", new IndexHandler());
        //server.createContext("/style.css", new StyleHandler());

		httpServer.setExecutor(null); // creates a default executor
		
		httpServer.start();
		isInitialized = true;
	}
	
    static class IndexHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            // write out the response
        	String html = "<html><head><title>ChronoTimer</title></head><body><p>Index page.</p></body></html>";

            t.sendResponseHeaders(200, html.length());

            OutputStream os = t.getResponseBody();
            os.write(html.getBytes());
            os.close();
        }

    }
}
