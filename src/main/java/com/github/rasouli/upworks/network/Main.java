package com.github.rasouli.upworks.network;

public class Main {
    public static void main(String[] args) throws Exception {

        Network network = new Network(8);

        network.connect(1,2);
        network.connect(2,6);
        network.connect(1,6);
        network.connect(2,4);
        network.connect(5,8);

    }
}
