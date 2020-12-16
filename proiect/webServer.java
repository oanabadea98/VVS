package webServer;


import GUI.webServerGUI;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

class webServer extends Thread {
    protected Socket clientSocket;

    static final String INDEX_HTML_PATH = "E:\\Servieta Oana\\UPT\\AN IV\\SEM 1\\VERIFICARE SI VALIDARE SOFTWARE\\LABORATOR\\proiect\\index.html";
    static final String MAINTENANCE_HTML_PATH = "E:\\Servieta Oana\\UPT\\AN IV\\SEM 1\\VERIFICARE SI VALIDARE SOFTWARE\\LABORATOR\\proiect\\Maintenance.html";
    static final String LINK_HTML_PATH = "E:\\Servieta Oana\\UPT\\AN IV\\SEM 1\\VERIFICARE SI VALIDARE SOFTWARE\\LABORATOR\\proiect\\Link.html";
    static final String ERROR_HTML_PATH = "E:\\Servieta Oana\\UPT\\AN IV\\SEM 1\\VERIFICARE SI VALIDARE SOFTWARE\\LABORATOR\\proiect\\404.html";
    static final String STOP_HTML_PATH = "E:\\Servieta Oana\\UPT\\AN IV\\SEM 1\\VERIFICARE SI VALIDARE SOFTWARE\\LABORATOR\\proiect\\stop.html";

    private static webServerGUI frame;

    public static int stare = 1;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        frame= new webServerGUI();

        frame.startGUIStopped();

        try {
            serverSocket = new ServerSocket(10006);
            System.out.println("Connection Socket Created");
            try {
                while (true) {
                    System.out.println("Waiting for Connection");
                    new webServer(serverSocket.accept());
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }

    public webServer(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public Socket getSocket() {
        return clientSocket;
    }

    public void run() {
        System.out.println("New Communication Thread Started");


        int fileDeschis;


        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));

            String inputLine;

            inputLine = in.readLine();
            StringTokenizer tokenizedLine = new StringTokenizer(inputLine);
            String method = tokenizedLine.nextToken().toUpperCase();

            String requestedFile = tokenizedLine.nextToken().toLowerCase();

            if (stare == 1) {
                fileDeschis = Running(out, in, requestedFile);
            }

            if(stare == 2){
                Maintenance(out, in);
            }

            if(stare == 3)
            {
                Stopped(out, in);
            }
            out.close();
            in.close();
            clientSocket.close();


        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }

    protected int Running(PrintWriter out, BufferedReader in, String requestedFile) {
        File file = null;
        String fileContent;
        int fileDeschis = 0; //1 pentru index.html, 2 pentru link.html, 3 pentru 404.html
        try {

            if(requestedFile.endsWith("/") || requestedFile.endsWith("index.html")) {
                file = new File(INDEX_HTML_PATH);
                int numOfBytes = (int) file.length();


                FileInputStream inFile = new FileInputStream(file);

                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);
                inFile.close();

                fileContent = new String(fileInBytes);
                //System.out.println(fileContent);


                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("\r\n");
                out.flush();

                out.println(fileContent);
                out.flush();

                fileDeschis = 1;

            } else if(requestedFile.endsWith("link.html"))
            {
                file = new File(LINK_HTML_PATH);
                int numOfBytes = (int) file.length();


                FileInputStream inFile = new FileInputStream(file);

                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);
                inFile.close();

                fileContent = new String(fileInBytes);
                //System.out.println(fileContent);


                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println("\r\n");
                out.flush();

                out.println(fileContent);
                out.flush();

                fileDeschis = 2;
            }

            else {
                file = new File(ERROR_HTML_PATH);
                int numOfBytes = (int) file.length();


                FileInputStream inFile = new FileInputStream(file);

                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);
                inFile.close();

                fileContent = new String(fileInBytes);
                //System.out.println(fileContent);


                out.println("HTTP/1.1 404 File Not Found");
                out.println("Content-Type: text/html");
                out.println("\r\n");
                out.flush();

                out.println(fileContent);
                out.flush();

                fileDeschis = 3;
            }
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
        return fileDeschis;
    }

    public int Maintenance(PrintWriter out, BufferedReader in)
    {
        try {

            File file = new File(MAINTENANCE_HTML_PATH);

            int numOfBytes = (int) file.length();


            FileInputStream inFile = new FileInputStream(file);


            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            inFile.close();

            String fileContent = new String(fileInBytes);



            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            out.flush();

            out.println(fileContent);
            out.flush();

            return 1;


        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }

        return 0;

    }

    public int Stopped (PrintWriter out, BufferedReader in) {
        try {

            File file = new File(STOP_HTML_PATH);

            int numOfBytes = (int) file.length();


            FileInputStream inFile = new FileInputStream(file);

            String inputLine;

            inputLine = in.readLine();
            StringTokenizer tokenizedLine = new StringTokenizer(inputLine);
            String method = tokenizedLine.nextToken().toUpperCase();

            byte[] fileInBytes = new byte[numOfBytes];
            inFile.read(fileInBytes);
            inFile.close();

            String fileContent = new String(fileInBytes);

            out.println("HTTP/1.1 522 Connection Timeout");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            out.flush();

            out.println(fileContent);
            out.flush();

        return 1;

        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }

        return 0;
    }


}

