package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import models.Id;
import models.Message;
import views.IdTextView;
import views.MessageTextView;
import youareell.Command;

public class MessageController {

    private TransactionController tctrl;

    public MessageController(TransactionController tt) {
        this.tctrl = tt;
    }

    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messagesSeen);
    }
    public ArrayList<Message> getMessagesForId(String idName) {
        ArrayList<Message> allMessages = getMessages();
        int arrListIdx = 0;
        while(arrListIdx < allMessages.size()){
            Message temp = allMessages.get(arrListIdx);
            if(!temp.getToid().equals(idName)){
                allMessages.remove(temp);
            }
            else{
                arrListIdx++;
            }
        }
        return allMessages;
    }
    public Message getMessageForSequence(String seq) {
        return null;
    }
    public ArrayList<Message> getMessagesFromFriend(String myId, String friendId) {
        ArrayList<Message> allMessages = getMessagesForId(myId);
        int arrListIdx = 0;
        while(arrListIdx < allMessages.size()){
            Message temp = allMessages.get(arrListIdx);
            if(!temp.getFromId().equals(friendId)){
                allMessages.remove(temp);
            }
            else{
                arrListIdx++;
            }
        }
        return allMessages;
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        return null;
    }

    public void doCommand(Command cmd) {
        if (cmd.getCmd() == Command.Verb.GETMESG) {
            List<Message> msgs = tctrl.getMessages();
            for (int i = 0; i < msgs.size(); i++) {
                System.out.println(new MessageTextView(msgs.get(i)).toString());
            }
        }
        if (cmd.getCmd() == Command.Verb.POSTMSG) {
            Message result = tctrl.postMessage(cmd.getArg(1), cmd.getArg(2), cmd.getRest(3));
            System.out.println(new MessageTextView(result).toString());
        }
        if (cmd.getCmd() == Command.Verb.GETMYMESG) {
            messagesSeen = new HashSet<>(tctrl.getMessages());
            ArrayList<Message> msgs = getMessagesForId(cmd.getArg(1));

            for (int i = 0; i < msgs.size(); i++) {
                System.out.println(new MessageTextView(msgs.get(i)).toString());
            }
        }
    }
}