package voice;

import edu.cmu.sphinx.frontend.endpoint.SpeechClassifiedData;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;


public class voice {

    public static void main(String[] arg) {

        ConfigurationManager cm;

        if (arg.length > 0) {
            cm = new ConfigurationManager(arg[0]);
        } else {
            cm = new ConfigurationManager(voice.class.getResource("config.xml"));
        }

        Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
        recognizer.allocate();

        // start the microphone or exit if the programm if this is not possible
        Microphone microphone = (Microphone) cm.lookup("microphone");
        if (!microphone.startRecording()) {
            System.out.println("Cannot start microphone.");
            recognizer.deallocate();
            System.exit(1);
        }

        //System.out.println("Say: Open or Close or Dog or Cat");

        // loop the recognition until the programm exits.
        while (true) {
            System.out.println("say 'close all emails' to quit.\n");

            Result result = recognizer.recognize();
            int count = 1;

            if (result != null) {
                String resultText = result.getBestFinalResultNoFiller();
                System.out.println("You said: " + resultText + '\n');
                if (resultText.equals("open emails")) {
                    Speech.speak("You said: " + resultText);
                        try {
                            new emailFrame().setVisible(true);
                        } catch (MessagingException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                
                if (resultText.equals("first email")) {
                    Speech.speak("You said: " + resultText);
                    String[] email;
                    try {
                            email = getEmails.getSingleEmail(0);
                            new singleEmail(email[1], email[2], email[3], email[4]).setVisible(true);
                        } catch (MessagingException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                
                if (resultText.equals("next email")) {
                    count++;
                    Speech.speak("You said: " + resultText);
                    String[] email;
                    try {
                            email = getEmails.getSingleEmail(count);
                            new singleEmail(email[1], email[2], email[3], email[4]).setVisible(true);
                        } catch (MessagingException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                
                if (resultText.equals("last email")) {
                    Speech.speak("You said: " + resultText);
                    String[] email;
                    try {
                            email = getEmails.getSingleEmail(10);
                            new singleEmail(email[1], email[2], email[3], email[4]).setVisible(true);
                        } catch (MessagingException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(emailFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                
                if (resultText.equals("close all emails")) {
                    System.exit(0);
                }
            } else {
                System.out.println("I can't hear what you said.\n");
            }
        }
    }
}

