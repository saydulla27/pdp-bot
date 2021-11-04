package uz.pdp.pdpbot.service;


public class ThreadService extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
        }

    }

