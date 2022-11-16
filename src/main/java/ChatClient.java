import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class ChatClient {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String host;
    private int port;
    private String nickname;
    File logFile;
    BufferedWriter logger;

    public ChatClient() {
        try (BufferedReader reader = new BufferedReader(new FileReader("setting.txt"))) {
            String line = reader.readLine();
            host = line.substring(7);
            line = reader.readLine();
            port = Integer.parseInt(line.substring(7));
            logFile = new File("logger.txt");
            logFile.createNewFile();
            logger = new BufferedWriter(new FileWriter(logFile));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.pressNickname();
            new Writer(this).start();
            new Reader(this).start();
        } catch (IOException e) {
            downService();
        }
    }


    private void pressNickname() {
        System.out.print("Press your nick: ");
        try {
            nickname = inputUser.readLine();
            out.write("Hello " + nickname + "\n");
            out.flush();
        } catch (IOException ignored) {
        }

    }


    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public BufferedReader getInputUser() {
        return inputUser;
    }

    public BufferedWriter getOut() {
        return out;
    }

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getHost() {
        return host;
    }

    public String getNickname() {
        return nickname;
    }
}
