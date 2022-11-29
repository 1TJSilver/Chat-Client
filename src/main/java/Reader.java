import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reader extends Thread {
    private String nickname;
    ChatClient client;
    private Date time;
    private String dTime;
    private SimpleDateFormat dt1;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;

    public Reader(ChatClient client){
        this.client = client;
        nickname = client.getNickname();
        time = new Date();
        dt1 = new SimpleDateFormat("HH:mm:ss");
        dTime = dt1.format(time);
        in = client.getIn();
        out = client.getOut();
        inputUser = client.getInputUser();
    }

    @Override
    public void run() {
        while (true) {
            String userWord;
            try {
                userWord = inputUser.readLine();
                if (userWord.equals("stop")) {
                    out.write("stop" + "\n");
                    break;
                } else {
                    out.write("(" + dTime + ") " + nickname + ": " + userWord + "\n");
                }
                System.out.println("(" + dTime + ") " + nickname + ": " +userWord);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

