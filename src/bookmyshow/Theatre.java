package bookmyshow;

import java.util.Date;
import java.util.concurrent.*;
import java.io.*;

public class Theatre
{
    static public boolean vacant;
    static char[][] seats,res;
    static Date date;
    static StringBuilder builder;
    static BufferedWriter writer;
    static BufferedReader reader;
    static String movie;
    static int show;

    Theatre(String m, int s)
    {
        movie=m;
        show=s;
        seats = new char[10][10];
        File file = new File(m+""+s+".txt");    
        if(!(file.exists()))
        {
            System.out.println("File not exist");
            for(int i=0;i<seats.length;i++)
                for(int j=0 ; j<seats[0].length ; j++)
                    seats[i][j]=48;
        }
        else
        {
            System.out.println("File Exist");    
            try{r();}
            catch(Exception e){}
        }
    }
    
    public static void main(String args[]) throws Exception
    {/*
        seats = new int[10][10];
        for(int i=0;i<seats.length;i++)
            for(int j=0 ; j<seats[0].length ; j++)
                seats[i][j]=0;
        
        System.out.println("Enter no of users");
        Scanner sc = new Scanner(System.in);
        n=sc.nextInt();
        Random r = new Random();
        
        for(int i=0;i<n;i++)
        {
            new Thread(new booking(r.nextInt(99)+1)).start();
            Thread.sleep(1);
            
        }   
    */  
    }
    
    public static void printseats()
    {
        System.out.print("\t");    
        for(int x=0;x<10;x++)
            System.out.print("["+x+"]\t"); 
        System.out.println("");
        for(int x=0;x<10;x++)
        {
            System.out.print("["+x+"]\t");
            for(int y=0;y<10;y++)
            {
                System.out.print((char)seats[x][y]+"\t");
            }
            System.out.println("");
        }
    }

    public static void w() throws Exception
    {
        builder = new StringBuilder();
        date = new Date();
        for(int i = 0; i < seats.length; i++)
        {
            for(int j = 0; j < seats.length; j++)
            {
                builder.append(seats[i][j]);
                if(j < seats.length - 1)
                    builder.append(",");
            }
            builder.append(System.getProperty("line.separator"));
        }
        System.out.println("FILE "+date);
        writer = new BufferedWriter(new FileWriter(movie+""+show+".txt"));
        writer.write(builder.toString());
        writer.flush();
        writer.close();
    }

    public static void r() throws Exception
    {
        reader = new BufferedReader(new FileReader(movie+""+show+".txt"));
        String line = "";
        int row = 0;
        while((line = reader.readLine()) != null)
        {
            String[] cols = line.split(","); 
            int col = 0;
            for(String  c : cols)
            {
            //    seats[row][col] = Integer.parseInt(c);
                seats[row][col] = c.charAt(0);
                col++;
            }
            row++;
        }
        reader.close();
    }

}

class booking extends Theatre implements Runnable 
{
    Semaphore mutex = new Semaphore(1);
    static int id,i,j;
    static int full;
   
    booking(String m, int s, int id)
    {
        super(m,s);
        this.id=id;
        i=id/10;
        j=id%10;
        full=seats[i][j];
        vacant=false;
    }

    public void run()
    {
        label: try
        {
            r();
            vacant=false;
            mutex.acquire();
            System.out.println("\nRow:"+i+" Column:"+j+" ["+seats[i][j]+"]");
            if(seats[i][j]==42)
                break label;
            if(seats[i][j]==48)
            {
                seats[i][j]=49;
                System.out.println("Vacant");
                vacant=true;
            }
            else
                System.out.println("Not Vacant");

            printseats();
            w();
            mutex.release();    
        }
            catch(Exception e){}
    //    System.out.println("Name"+Server.str+" showtime"+Server.show+" Seat"+Server.seat);    

    }
}



