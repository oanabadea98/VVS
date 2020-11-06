package webServer;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class webServerTest {

    private static webServer ws;

    @BeforeClass
    public static void setUp() {
        try{
            ServerSocket serverSocket = new ServerSocket(10007);
            ws = new webServer(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void createInstanceOfwebServer(){
        Assert.assertNotNull(ws);
    }

    @Test
    public void verifyGetSocket() {
        try {
            Assert.assertEquals(ws.getSocket(), new ServerSocket(10007));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRunningGoesInFirstIfOnce() {
        try {
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())), "/");
            Assert.assertEquals(res, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRunningGoesInFirstIfTwice() {
        try {
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())), "index.html");
            Assert.assertEquals(res, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRunningGoesInElseIf() {
        try {
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())), "link.html");
            Assert.assertEquals(res, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRunningGoesInElse() {
        try {
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())), "a");
            Assert.assertEquals(res, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}