package net.spetnix.project;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        DOMConfigurator.configure("log4j.xml");

        new Game().runMenu();
    }

    public static void display(String msg) {
        logger.info(msg);
        System.out.println(msg);
    }
}
