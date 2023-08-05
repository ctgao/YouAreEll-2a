package youareell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Command read in string and then interprets the string for the controllers to handle.
public class Command {

    public enum Verb {
        POSTID("postid"),
        GETIDS("getids"),
        GETMESG("messages"),
        POSTMSG("send"),
        // you may need to add more here...
        // for more commands
        GETMYMESG("mymessages"),
        QUIT("quit"),
        HELP("help"),
        ERR("error"),
        NOOP("");

        public final String str;

        Verb(String str) {
            this.str = str;
        }

        private static final Map<String,Verb> ENUM_MAP;

        public String getString() {
            return this.str;
        }

        // Build an immutable map of String name to enum pairs.
        // Any Map impl can be used.

        static {
            Map<String,Verb> map = new ConcurrentHashMap<String, Verb>();
            for (Verb instance : Verb.values()) {
                map.put(instance.getString().toLowerCase(),instance);
            }
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        public static Verb get (String name) {
            return ENUM_MAP.getOrDefault(name.toLowerCase(), Verb.ERR);
        }

    }
    private static BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

    // Reading data using readLine
    public static Command getCommand(String prompt) {
        while (true) { //infinite loop! (because we stay here until valid command or QUIT
            try {
                System.out.println(prompt);
                String inputFromUser = reader.readLine();
                Command cmd = new Command();
                cmd.interpret(inputFromUser);
                return cmd; // jump out of loop
            } catch (IOException e) {
                System.out.println("command error, try again.");
            }
        }
    }

    private Verb currentCmd = Verb.NOOP;
    private String[] tokens;
    public String getArg(int idx) {
        if (idx >= tokens.length) {
            return null;
        }
        return tokens[idx];
    }
    public String getRest(int idx) {
        StringBuilder sb = new StringBuilder();
        for (int i = idx; i < tokens.length; i++) {
            sb.append(tokens[i]);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public Command() {}
    private Command(Verb v) { this.currentCmd = v;}

    public Command interpret(String s) {
        System.out.println("interpreting ["+s+"]");

        tokens = s.split(" ");
        Verb verb = Verb.get(tokens[0]);
        this.currentCmd = verb;
        return this;
    }

    private Command noOp() {
        this.currentCmd = Verb.NOOP;
        return this;
    }

    public Command.Verb getCmd() {
        return this.currentCmd;
    }
    public boolean isIdCmd() {
        // as you add ENUMs, add more to this condition.
        if (this.currentCmd == Verb.POSTID
        || this.currentCmd == Verb.GETIDS) return true;
        return false;
    }

    public boolean isMsgCmd() {
        // as you add ENUMs, add more to this condition.
        if (this.currentCmd == Verb.POSTMSG
                || this.currentCmd == Verb.GETMESG
        || this.currentCmd == Verb.GETMYMESG) return true;
        return false;
    }

    public boolean isHelp() {
        if (this.currentCmd == Verb.HELP) return true;
        return false;
    }

    public void printHelp() {
        System.out.println("print help? what help where?");
    }

    public boolean isQuit() {
        if (this.currentCmd == Verb.QUIT) return true;
        return false;
    }

    private class BadCommand extends Exception {
    }
}
