package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Search {
	
	public static void search()
	{
		Scanner sc =new Scanner(System.in);
		int a ;
		System.out.println("1)track a book");
		System.out.println("2)Search a book");
		
		a=sc.nextInt();
		
		switch (a) {
		case 1:
			track_book();

			
			break;

		case 2:
			boolean ans2=Search_book();
			if(ans2==true)
				System.out.println("*****book found*****");
			else
				System.out.println("*****book not found*****");
			
		break;
		

		default:
			break;
		}
		
		



	}

	
public static void track_book()

{
	Scanner sc=new Scanner(System.in);
	System.out.println("enter the book Id");  
	int bid=sc.nextInt();
	
	try {
		// stablih connection
		Connection con = CP.createC();
		// query
		String q = "select Sid from issuedbooks where Bid =? && ReturnDate IS NULL;";
		PreparedStatement pstmt = con.prepareStatement(q);
		pstmt.setInt(1, bid);
		ResultSet set= pstmt.executeQuery();
		if(set.next()==false)
		{
			System.out.println("***the book is in library***");
			return;
			
		}
		int sid=set.getInt(1);
//		System.out.println(sid);
		
		q="select Sname from students where Sid="+sid;
		Statement stmt=con.createStatement();
		set= stmt.executeQuery(q);
		while(set.next())
		{
			String sname=set.getString(1);
			System.out.println("Student ID which have this book is : "+sid);
			System.out.println("Student name which have this book is : "+sname);
			System.out.println("________________________________________________________________________");

		}
		

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	
	
}


public static boolean Search_book()
{
	Scanner sc = new Scanner(System.in);
	String name;
	System.out.println("enter the book name");
	name=sc.next();

	boolean f = false;
	try {
		// stablih connection
		Connection con = CP.createC();
		// query
					String q = "select * from books where Bname=?";
					PreparedStatement pstmt = con.prepareStatement(q);
					pstmt.setString(1,name);
			//execute
					 ResultSet res =	pstmt.executeQuery();
					
				    while(res.next())
	                {
	                    int id = res.getInt("Bid");
	                    String bname = res.getString("Bname");
	                    int bqty = res.getInt("Bqty");
	                    System.out.println("BOOk ID : "+id);
	                    System.out.println("Book Name:"+bname);
	                    System.out.println("Book Qunty in library :"+bqty);
	                    f=true;
	                }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return f;
	
	
	

	
}


}
