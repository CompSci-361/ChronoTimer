package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

import com.google.gson.Gson;
import com.sun.corba.se.spi.orbutil.fsm.Action;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import core.Chronotimer;
import core.Racer;

public class ChronoTimerWebServer {
	private boolean isInitialized = false;
	private HttpServer httpServer = null;
	private HttpServer chronoServer = null;
	private IChronoTimerWebServerResolveRacer resolveRacerFunc = null;
	
	
	public void initialize(IChronoTimerWebServerResolveRacer resolveRacer) throws IOException {
		if (isInitialized) return;
		
		resolveRacerFunc = resolveRacer;
		
		//create a http server for browsers.
		httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
		httpServer.createContext("/", new IndexHandler());
		httpServer.start();
		
		//create a http server specifically for the chronotimer to connect to.
		chronoServer = HttpServer.create(new InetSocketAddress(8080), 0);
		chronoServer.createContext("/display", new ChronoTimerPostHandler());
		chronoServer.start();
		
		isInitialized = true;
	}
	
	public interface IChronoTimerWebServerResolveRacer {
		ServerSideRunner resolve(Racer racer);
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
    
    static class ChronoTimerPostHandler implements HttpHandler {
    	public void handle(HttpExchange t) throws IOException {
    		if (t.getRequestMethod() == "POST" || t.getRequestMethod() == "PUT") {
    			//handle the post.
    			Gson g = new Gson();
    			//grab the response string from the request
    			String response = readResponseAsString(t);
    			
    			//deserialize response (assuming it is json) into an object using the Gson lib.
    			Racer[] racers = (Racer[])g.fromJson(response, Racer[].class);
    			
    			//todo correlate bib numbers with "names"
    			
    			//http status code 201 "created"
    			t.sendResponseHeaders(201, -1);
    			
    		} else {
    			//http status code 501 "not implemented"
    			//https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html#sendResponseHeaders-int-long-
    			t.sendResponseHeaders(501, -1);
    		}
    		t.close();
    	}
    	
    	private String readResponseAsString(HttpExchange transmission) throws IOException {
    		// set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }
           
            return sb.toString();
    	}
    }
}
