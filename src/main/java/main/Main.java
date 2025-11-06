package main;

import model.Card;
import model.Client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.setFirstName("Alex");
        client.setLastName("Pescaru");
        Card card = new Card(client, "2", "23543");
        System.out.println(card.toString());

    }
}
