import java.util.Date;

public class Main

{
	public static void main(String[] args) {
		while(true) {
			System.out.println( "I'm alive: " + new Date());
			try
			{ Thread.sleep(2000); }
			catch (java.lang.InterruptedException ex)
			{ return; }
		}
	}
}