package bookmyshow;

import java.util.concurrent.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server
{
	static Socket s;
	
	public static void main(String args[]) throws Exception
	{
		ServerSocket ss = new ServerSocket(10102);
		System.out.println("Server started");
		
		while(true)
		{
            s = ss.accept();
            new S2C(s).start();
		}
	}
}

class S2C extends Thread
{
	public static String name;
	public static int show, seat, temp, invalid=0;
	static Semaphore mutex = new Semaphore(1);	

	Socket s;
	BufferedReader br;

	S2C(Socket s)
	{
		this.s=s;
	}	

	public void run()
	{
		try
		{
			confirm="x";
		    OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		    PrintWriter out = new PrintWriter(os,true);
		    out.println("Select Movies:\n");
		    out.println("	1.Thor: The Ragnarok");
			out.println("	2.Black Panther");
			out.println("	3.Infinity War\n");
			out.println("Select ShowTime:	9 AM	11 AM	2 PM	5 PM	7 PM	10 PM");
			out.println("Enter the Movie & ShowTime");
			out.println("stop");
			

		    br = new BufferedReader(new InputStreamReader(s.getInputStream()));

			name = br.readLine();
			show = Integer.parseInt(br.readLine());

		    switch(name)
		    {
		    	case "1" : name="Thor";
		    		break;
		    	case "2" : name="Black";
		    		break;
		    	case "3" : name="Infinity";
		    		break;
		    }
		    out.println(name);

		    out.println("Enter the Seat no.");
		    seat = Integer.parseInt(br.readLine());

		    System.out.println("Movie: "+name+"\tShowtime: "+show+"\tSeat: "+seat); 

			
		    Thread tt = new Thread(new booking(name,show,seat));
		    if((temp=Theatre.seats[seat/10][seat%10])==48)
		    {
		    //	System.out.println("?");
		    	Theatre.seats[seat/10][seat%10]=42;
		    	Theatre.w();
		    	Theatre.r();
		    	out.println("Confirm the seat? y:n");
		    	confirm = br.readLine();
		    	if(confirm.equals("n"))
		    	{	
		    		Theatre.seats[seat/10][seat%10]=48;
		    		Theatre.w();
		    		Theatre.r();
		    	}
		    	
		    }
		    mutex.acquire();
		    if(confirm.equals("y"))
		    {	
		    //	System.out.println("??");
		    	Theatre.seats[seat/10][seat%10]=48;
		    	Theatre.w();
		    	Theatre.r();
		        tt.start();
				tt.join();
		    }
 			mutex.release();
			
			if(confirm.equals("y") || !confirm.equals("n"))
			{
				if(Theatre.vacant)
					out.println("Vacant, BOOKED!!!");	
				else
					out.println("Not Vacant, Try another seat");
			}

			
			out.close();
			os.close();
			br.close();
		}
		catch(Exception e){}
	}
}
