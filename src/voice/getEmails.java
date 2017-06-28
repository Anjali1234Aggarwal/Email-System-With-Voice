package voice;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class getEmails {

    public static String[][] getAllEmails(int start, int end) throws MessagingException, IOException {
        ArrayList<String[]> outerArr = null;
        String mails[][] = null;
        Folder folder = null;
        Store store = null;
        Speech.speak("Please be patient for waiting for a response");
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", "anjaliabc02@gmail.com", "bangbang123");
            folder = store.getFolder("[Gmail]/Drafts");
            /* Others GMail folders :
             * [Gmail]/All Mail   This folder contains all of your Gmail messages.
             * [Gmail]/Drafts     Your drafts.
             * [Gmail]/Sent Mail  Messages you sent to other people.
             * [Gmail]/Spam       Messages marked as spam.
             * [Gmail]/Starred    Starred messages.
             * [Gmail]/Trash      Messages deleted from Gmail.
             */
            folder.open(Folder.READ_WRITE);
            Message messages[] = folder.getMessages();
            System.out.println("No of Messages : " + folder.getMessageCount());
            
            System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
            //for (int i=0; i < messages.length; ++i) {
            mails = new String[start + end][4];
            for (int i = start; i < end; ++i) {
                System.out.println("MESSAGE #" + (i + 1) + ":");
                Message msg = messages[i];
                Multipart mp = (Multipart) msg.getContent();
                BodyPart bp = mp.getBodyPart(0);
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + msg.getSubject());
                System.out.println("From: " + msg.getFrom()[0]);
                System.out.println("Text: " + bp.getContent().toString());

                Speech.speak((i+1)+" Subject: " + msg.getSubject());
                
                /*
                 if we don''t want to fetch messages already processed
                 if (!msg.isSet(Flags.Flag.SEEN)) {
                 String from = "unknown";
                 ...
                 }
                 */
                outerArr = new ArrayList<String[]>();
                int pos = i;
                String emails[] = {
                    String.valueOf(pos + 1).toString(),
                    msg.getFrom()[0].toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "),
                    msg.getSubject().toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "),
                    msg.getReceivedDate().toString(),};
                outerArr.add(emails);

                mails[i][0] = String.valueOf(pos + 1).toString();
                mails[i][1] = msg.getFrom()[0].toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                mails[i][2] = msg.getSubject().toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                mails[i][3] = msg.getReceivedDate().toString();

                /* if(emails[i][0].isEmpty()) {
                 emails[i][0] = String.valueOf(i).toString();
                 }
                 try {
                 emails[i][0] = (emails[i][0]==null)?String.valueOf(i).toString():"";
                 //emails[i][1] = msg.getFrom()[0].toString();
                 //emails[i][2] = "";
                 //emails[i][2] = "";
                 System.err.println(emails.length);
                 } catch(Exception ex){ System.err.println(ex.getMessage()); } */

                String from = "unknown";
                if (msg.getReplyTo().length >= 1) {
                    from = msg.getReplyTo()[0].toString();
                } else if (msg.getFrom().length >= 1) {
                    from = msg.getFrom()[0].toString();
                }
                String subject = msg.getSubject();
                System.out.println("Saving ... " + subject + " " + from);
                // you may want to replace the spaces with "_"
                // the TEMP directory is used to store the files
                //String filename = "c:/temp/" +  subject;
                //saveParts(msg.getContent(), filename);
                //msg.setFlag(Flags.Flag.SEEN,true);
                // to delete the message
                //msg.setFlag(Flags.Flag.DELETED, true);
            }
        } finally {
            if (folder != null) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        }
        //System.out.println(outerArr.size());
        //return outerArr;
        Speech.speak("You have total"+folder.getMessageCount()+" emails. Please be patient for waiting for a response");
        return mails;
    }

    public static String[] getSingleEmail(int id) throws MessagingException, IOException {
        String email[] = new String[5];
        Folder folder = null;
        Store store = null;
        Speech.speak("Please be patient for waiting for a response");
        try {
            System.err.println("Email No - " + id);
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", "anjaliabc02@gmail.com", "bangbang123");
            folder = store.getFolder("[Gmail]/Drafts");
            folder.open(Folder.READ_WRITE);
            Message messages[] = folder.getMessages();
            System.out.println("No of Messages : " + folder.getMessageCount());
            Message msg = messages[id];
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            email[0] = String.valueOf(id).toString();
            email[1] = msg.getFrom()[0].toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            email[2] = msg.getSubject().toString().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            email[3] = msg.getReceivedDate().toString();
            email[4] = bp.getContent().toString();

            Speech.speak("Email detail is ");
            Speech.speak("From. "+email[1]);
            Speech.speak("Subject. "+email[2]);
            Speech.speak("Date. "+email[3]);
            Speech.speak("Message. "+email[4]);
            
        } finally {
            if (folder != null) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        }
        return email;
    }
}
