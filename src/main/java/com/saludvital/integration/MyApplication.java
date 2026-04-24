package com.saludvital.integration;

import org.apache.camel.main.Main;

public class MyApplication {
    public static void main(String... args) throws Exception {
        Main main = new Main(MyApplication.class);
        main.run(args);
    }
}