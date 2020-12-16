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
    public static void setUp()
    {
        try{
            ServerSocket serverSocket = new ServerSocket(10006);
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
    public void verifyGetSocket()
    {   //returneaza socket-ul webserverului si il compara cu portul 10007
        //un socket este un port cu tot cu adresa ip
        try {
            Assert.assertEquals(ws.getSocket(), new ServerSocket(10006));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfRunningGoesInFirstIfOnce() //running returneaza un int si anume 1 daca e tot ok
    {
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
    public void checkIfRunningGoesInFirstIfTwice() //runnig returneaza 1 si trebuie sa faca ce face testul de sus
    {
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
    public void checkIfRunningGoesInElseIf() // running returneaza 2 daca deschide link
    {
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
    public void checkIfRunningGoesInElse() //runnig returneaza 3 daca deschide un fisier care nu exista (file not found)
    {
        try {
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())), "a");
            Assert.assertEquals(res, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkRunningNotOK()
    {
        try{
            PrintWriter pr = new PrintWriter(ws.clientSocket.getOutputStream(),
                    true);
            pr.close();
            int res = ws.Running(pr,new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())),"a");
            Assert.assertEquals(res, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void checkRunningNotOK1()
    {
        try{
            BufferedReader br =new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream()));
            br.close();
            int res = ws.Running(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true),br,"a");
            Assert.assertEquals(res, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkMaintenanceOK()
    {
        try{
            int res = ws.Maintenance(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true), new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())));
            Assert.assertEquals(res, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkMaintenanceNotOK() //apare ioException si maintenance returneaza 0
    {
        try
        {
            PrintWriter pr = new PrintWriter(ws.clientSocket.getOutputStream(),
                true);
            pr.close();
            int res = ws.Maintenance(pr,new BufferedReader(new InputStreamReader(
                ws.clientSocket.getInputStream())));
            Assert.assertEquals(res, 0);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkMaintenanceNotOK1()
    {
        try{
            BufferedReader br =new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream()));
            br.close();
            int res = ws.Maintenance(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true),br);
            Assert.assertEquals(res, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkStoppedOK()
    {
        try{

            int res = ws.Stopped(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true),new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())));
            Assert.assertEquals(res, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkStoppedNotOK()
    {
        try{
            PrintWriter pr = new PrintWriter(ws.clientSocket.getOutputStream(),
                    true);
            pr.close();
            int res = ws.Stopped(pr,new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream())));
            Assert.assertEquals(res, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkStoppedNotOK1()
    {
        try{
            BufferedReader br =new BufferedReader(new InputStreamReader(
                    ws.clientSocket.getInputStream()));
            br.close();
            int res = ws.Stopped(new PrintWriter(ws.clientSocket.getOutputStream(),
                    true),br);
            Assert.assertEquals(res, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

