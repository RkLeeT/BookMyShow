package bookmyshow;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server
{
	public static String str;
	public static int show;
	public static int seat;
	
	public static void main(String args[]) throws Exception
	{
		Socket s;
		BufferedReader br;		
		System.out.println("Server started");
		ServerSocket ss = new ServerSocket(2002);

		
		
	/*	System.out.println("\nSelect Movies:\nThor: The Ragnarok");
		System.out.println("Black Panther");
		System.out.println("Infinity War");
		System.out.println("\nSelect ShowTime:\n8 AM\n11 AM\n3 PM\n6 PM\n9 PM\n11 PM");*/

		while(true)
		{
			System.out.println("\nServer waiting for req");
            s = ss.accept();
            System.out.println("\nClient connected");
            OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
            PrintWriter out = new PrintWriter(os,true);
            out.println("Select Movies:\n");
            out.println("	Thor: The Ragnarok");
			out.println("	Black Panther");
			out.println("	Infinity War\n");
			out.println("Select ShowTime:	8 AM	11 AM	3 PM	6 PM	9 PM	11 PM");
			out.println("Enter the movie,showtime,seat no.");

            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        
        	str = br.readLine();
        	show = Integer.parseInt(br.readLine());
            seat = Integer.parseInt(br.readLine());
        


            System.out.println("Movie:"+str+" showtime"+show+" Seat"+seat); 
            System.out.println("Client data:"+seat);

            Thread tt = new Thread(new booking(str,show,seat));
			tt.start();
			tt.join();

			


			if(Theatre.vacant)
			{
				out.println("Vacant");	
			}
			else
				out.println("Not Vacant");

			
			out.close();
			os.close();
			br.close();
		//	s.close();
		}

	//	ss.close();

	}

}
