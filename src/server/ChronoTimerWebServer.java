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
	private static Racer[] lastRacerUpdate = null;
	
	
	public void initialize(IChronoTimerWebServerResolveRacer resolveRacer) throws IOException {
		if (isInitialized) return;
		
		//create a http server for browsers.
		httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
		httpServer.createContext("/", new IndexHandler(resolveRacer));
		httpServer.start();
		
		//create a http server specifically for the chronotimer to connect to.
		chronoServer = HttpServer.create(new InetSocketAddress(8080), 0);
		chronoServer.createContext("/", new ChronoTimerPostHandler(resolveRacer));
		chronoServer.start();
		
		isInitialized = true;
	}
	
	public interface IChronoTimerWebServerResolveRacer {
		ServerSideRunner resolve(Racer racer);
	}
	
    static class IndexHandler implements HttpHandler {
    	private IChronoTimerWebServerResolveRacer resolveRacerFunc = null;
    	
    	public IndexHandler(IChronoTimerWebServerResolveRacer resolveRacer) {
    		resolveRacerFunc = resolveRacer;
    	}
    	
        public void handle(HttpExchange t) throws IOException {
        	System.out.println("Received GET request from a browser.");
        	
            // write out the response
        	String header = "<html><head><title>ChronoTimer</title></head><body>";
        	String footer =	"</body></html>";
        	String body = "";
        	
        	if (lastRacerUpdate == null) {
        		body = "<p>No race times available.</p>";
        	} else {
        		body = "<table class=\"racer-table\"><tr><th>Place</th><th>Number</th><th>Name</th><th>Time</th></tr>";
        		
        		for(Racer racer : lastRacerUpdate) {
        			//magic
        			ServerSideRunner runner = this.resolveRacerFunc.resolve(racer);
        			body += "<tr>";
        			
        			body += "<td>" + "place here" + "</td>";
        			body += "<td>" + runner.getBibNumber() + "</td>";
        			body += "<td>" + runner.getName() + "</td>";
        			body += "<td>" + "N/A" + "</td>";
        			
        			body += "</tr>";
        		}
        		
        		body += "</table>";
        	}
        	
        	//ugly code, i know.
        	String html = header + body + footer;
            t.sendResponseHeaders(200, html.length());
            OutputStream os = t.getResponseBody();
            os.write(html.getBytes());
            os.close();
        }
    }
    
    static class ChronoTimerPostHandler implements HttpHandler {
    	private IChronoTimerWebServerResolveRacer resolveRacerFunc = null;
    	
    	public ChronoTimerPostHandler(IChronoTimerWebServerResolveRacer resolveRacer) {
    		resolveRacerFunc = resolveRacer;
    	}
    	
    	public void handle(HttpExchange t) throws IOException {
    		String method = t.getRequestMethod();
    		if (method.equals("POST")) {
    			//handle the post.
    			Gson g = new Gson();
    			//grab the response string from the request
    			String response = readResponseAsString(t);
    			
    			//deserialize response (assuming it is json) into an object using the Gson lib.
    			Racer[] racers = (Racer[])g.fromJson(response, Racer[].class);
    			
    			//todo null check
    			System.out.println("Received POST from ChronoTimer with " + racers.length + " racers.");
    			
    			lastRacerUpdate = racers;
    			
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
