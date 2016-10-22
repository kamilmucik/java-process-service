import java.io.*;
import java.util.Properties;


/**
 * see http://stackoverflow.com/questions/1238145/how-to-run-a-jar-file
 */
public class HelloWorldServer implements Runnable {
    private static boolean m_started = false;

    public HelloWorldServer() {
    }

    public void run() {
        m_started = true;

        while(true) {
            System.out.println("running: "+  Thread.currentThread().getName());
            String s = null;

            try {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException var2) {
                    ;
                }
                Process p = Runtime.getRuntime().exec("WMIC PROCESS WHERE \"name like '%java%'\" get Processid,Caption,Commandline /format:csv");

                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream()));

                System.out.println("Here is the standard output of the command:");
                int i =0;
                while ((s = stdInput.readLine()) != null) {
                    String[] parts = s.split(",");
                    if (parts.length >=4) {
                        if (parts[2].contains("java")) {
                            System.out.println(i + ": " + parts[3]);
                        }
                    }

                    i++;
                }

                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }


            } catch (IOException e) {
                System.out.println("exception happened - here's what I know: ");
                e.printStackTrace();
                System.exit(-1);
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("Background Thread Test Running...");
        System.out.println("Launching background non-daemon threads...");
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);

        Properties prop = new Properties();
        InputStream input = null;
        try {

            input = new FileInputStream(workingDir + "/../conf/wrapper.conf");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println("smssender: " + prop.getProperty("wrapper.java.additional.1"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Properties systemProperties = System.getProperties();


        System.out.println("args: " + args.length);
        for (String param :args){
            System.out.println("arg: " + param);
        }


        HelloWorldServer app = new HelloWorldServer();

        Thread thread = new Thread(app, "App-Thread-");
        thread.start();

//        while(!m_started) {
//            try {
//                Thread.sleep(10L);
//            } catch (InterruptedException var4) {
//                ;
//            }
//        }

        System.out.println("The JVM should now continue to run indefinitely.");
        System.out.println("Background Thread Test Main Done...");
    }
}


