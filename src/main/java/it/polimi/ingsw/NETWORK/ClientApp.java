package it.polimi.ingsw.NETWORK;

import it.polimi.ingsw.NETWORK.CLIENT.ClientCLI;

import java.io.IOException;
import java.util.Scanner;

public final class ClientApp {
    public static void main(String[] args){
        ClientCLI client = new ClientCLI("CLI");
        try{
            Scanner stdin = new Scanner(System.in);

            System.out.println("Please enter the ip address: ");
            String ip = stdin.nextLine();//"127.0.0.1"

            System.out.println("Please enter the port");
            int port = Integer.parseInt(stdin.nextLine());//12345

            client.run(ip, port);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}