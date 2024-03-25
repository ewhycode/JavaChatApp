/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.simplechatapp;

/**
 *
 * @author EYC
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatAppJava {
    
    //port8080
    public static void main(String[] args) {
        //start server in one thread
        new Thread(() -> startServer(8080)).start();

        //start client in another thread port 8080
        new Thread(() -> startClient("localhost", 8080)).start();
    }

    // Server side
    public static void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            //while waiting for client to connect
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            //create reader and writer for communication
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            //read messages from client and print to console
            String receivedMessage;
            while ((receivedMessage = reader.readLine()) != null) {
                System.out.println("Client: " + receivedMessage);
                
            }

            
            //close
            reader.close();
            writer.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //client side
    public static void startClient(String host, int port) {
        try {
            //connect to server
            Socket socket = new Socket(host, port);
            System.out.println("Connected to server: " + socket);

            //create reader and writer for communication
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            //read messages from console and send to server
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = consoleReader.readLine()) != null) {
                writer.println(message);
            }

            
            //Close
            consoleReader.close();
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        }
}
