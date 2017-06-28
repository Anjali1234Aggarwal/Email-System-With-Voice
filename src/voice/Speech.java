package voice;



import com.sun.speech.freetts.*;

public class Speech {

    private static final String VOICENAME_kevin = "kevin";

    public static void speak(String text) {
        Voice voice;
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICENAME_kevin);
        voice.allocate();
        voice.speak(text);
    }
}
