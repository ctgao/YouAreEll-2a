package youareell;

import controllers.*;

import javax.swing.*;

public class YouAreEll {

    TransactionController tt;
    IdController idctrl;
    MessageController msgctrl;

    public YouAreEll (){
        this.tt = new TransactionController();
        this.idctrl = new IdController(this.tt);
        this.msgctrl = new MessageController(this.tt);
    }

    public static void main(String[] args) {
        YouAreEll shell = new YouAreEll();
        shell.start();
    }

    private void start() {
        this.hello();
        boolean done = false;
        while (!done) {
            Command cmd = Command.getCommand("Z?");
            if (cmd.isIdCmd()) {
                this.idctrl.doCommand(cmd);
                continue;
            }
            if (cmd.isMsgCmd()) {
                this.msgctrl.doCommand(cmd);
                continue;
            }
            if (cmd.isHelp()) {
                cmd.printHelp();
                continue;
            }
            if (cmd.isQuit()) {
                done = true;
                continue;
            }
            this.displayErr("Unknown commandmessa. Type `help` for examples.");
        }

    }

    private void displayErr(String s) {
        System.err.println(s);
    }

    private void hello() {
        String hellomsg = "Z Client by kyounger.\nhi ya.";
        System.out.println(hellomsg);
    }


}
