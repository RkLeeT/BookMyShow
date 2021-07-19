package bookmyshow;

import java.net.Socket;
import java.io.*;
import java.util.*;

public class Client
{
	public static String name, reserve;
	public static int show, seat, temp;

	public static void main(String args[]) throws Exception
	{
		String i,v;
		int st;
		BufferedReader br;
		String ip = "localhost";
		int port = 10102;
		Socket s = new Socket(ip,port);

		Scanner sc = new Scanner(System.in);
		
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));

		while(!((i=br.readLine()).equals("stop")))
				System.out.println(i);

		name = sc.next();
		if(name.charAt(0)>51 || name.charAt(0)<49)
		{
			System.out.println("Invalid movie no.");
			System.exit(0);
		}	
		st = sc.nextInt();

		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		PrintWriter out = new PrintWriter(os,true);

		out.println(name);
		out.println(st);

		name = br.readLine();
		System.out.println("Movie name: "+name);

		new Theatre(name,st).printseats();

		System.out.println("Book the Tickets");
		System.out.println(br.readLine());

		seat = sc.nextInt();
		out.println(seat);

		if((temp=Theatre.seats[seat/10][seat%10])==48)
		{
		//	System.out.println("?"+booking.full+" "+S2C.temp+" "+Theatre.seats[seat/10][seat%10]);
			v=br.readLine();
			System.out.println(v);
				
			reserve = sc.next();
			out.println(reserve);
		}
		
		if(S2C.confirm.equals("y") || temp!=48)
		{
		//	System.out.println("??");
			v=br.readLine();
			System.out.println(v);
		}

		sc.close();
		out.close();	
		os.close();
		br.close();
	//	s.close();
	
	}

}
