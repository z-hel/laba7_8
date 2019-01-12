package com.example.tester3.laba7_8.models;


public class Joke {

    public Joke (String type, String setup, String punchline) {
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }

    public String getType() { return type; }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }


    private String type;
    private String setup;
    private String punchline;

}
