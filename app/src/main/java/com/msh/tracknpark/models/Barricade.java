package com.msh.tracknpark.models;

public class Barricade {
    private boolean response;
    private String command;

    public Barricade() {
        this.command="close";
        this.response=true;
    }

    public Barricade(boolean response, String command) {
        this.response = response;
        this.command = command;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isResponse() {
        return response;
    }

    public String getCommand() {
        return command;
    }
    @Override
    public String toString(){
        return "command:"+command+" response:"+response;

    }
}
