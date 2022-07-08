package com.library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Books {
	public int Bid;
	public String Bname;
	public String Bauthor;
	public int Bqty;

	public Books(int bid, String bname, String bauthor, int bqty) {
		super();
		Bid = bid;
		Bname = bname;
		Bauthor = bauthor;
		Bqty = bqty;
	}

	public Books(String bname, String bauthor, int bqty) {
		super();
		Bname = bname;
		Bauthor = bauthor;
		Bqty = bqty;
	}

	@Override
	public String toString() {
		return "Books [Bid=" + Bid + ", Bname=" + Bname + ", Bauthor=" + Bauthor + ", Bqty=" + Bqty + "]";
	}

	public static boolean insertBook(Books st) {
		boolean f = false;
		try {
			// stablih connection
			Connection con = CP.createC();
			// query
			String q = "insert into books(Bname,Bauthor,Bqty) values(?,?,?)";
			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);
			// setting the value of parameter
			pstmt.setString(1, st.Bname);
			pstmt.setString(2, st.Bauthor);
			pstmt.setInt(3, st.Bqty);

			// execute
			pstmt.executeUpdate();
			f = true;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;

	}

	public static boolean deleteBook(int Bid) {
		boolean f = false;
		try {
			// stablih connection
			Connection con = CP.createC();
			String q = "delete from books where Bid=?";
			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);
			// setting the value of parameter
			pstmt.setInt(1, Bid);
			// execute
			pstmt.executeUpdate();
			f = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return f;

	}

	public static void showBook()

	{
	
		try {
			// stablih connection
			Connection con = CP.createC();
			// query
			String q = "select * from books where Bqty!=0; ";
			Statement stmt=con.createStatement();
			ResultSet set=  stmt.executeQuery(q);
			while(set.next())
			{
				int Bid=set.getInt(1);
				String Bname=set.getString(2);
				String Bauthor=set.getString(3);
				int Bqty=set.getInt(4);
				System.out.println("BID: "+Bid);
				System.out.println("Bname: "+Bname);
				System.out.println("Bauthor: "+Bauthor);
				System.out.println("Bqty: "+Bqty);
				System.out.println("________________________________________________________________________");
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		

		
	}

	public static void getBook()
{
		Scanner sc = new Scanner(System.in);
		int sid,bid;
		System.out.println("Enter the student Id");
		sid=sc.nextInt();
//		System.out.println(sid);
		try {
			// stablish connection
			Connection con = CP.createC();
			// query
			String q = "select Bhave from students where Sid ="+sid;
			Statement stmt=con.createStatement();
			ResultSet set=  stmt.executeQuery(q);
			set.next();
			int qty=set.getInt(1);
			if(qty>=3)
			{
				System.out.println("Return some books You have more than 3 books ");
				System.out.println("________________________________________________________________________");
				return;
			}
//			else
//			{
//				System.out.println("u can have books");
//			}
			System.out.println("The book which are present in the Library are: ");
			System.out.println("*************************************************************************");
			Books.showBook();
		//Issuing the book to a student
			System.out.println("Enter the book Id");
			bid=sc.nextInt();
			
	//checking if student alreday have this book;
			//query
			q="select* from issuedbooks where Sid=? && Bid =?;";
			//preparing statemnet
			PreparedStatement pstmt = con.prepareStatement(q);
			pstmt.setInt(1, sid);
			pstmt.setInt(2, bid);
			// execute
			set=pstmt.executeQuery();
			
			if(set.next())
			{
				System.out.println("you already have this book");
				return;
			}
			
			
			LocalDate iDate = LocalDate.now();
//			System.err.println(iDate);
			//query
			q="insert into issuedbooks(Sid,Bid,IssuedDate) values(?,?,?);";
			// prepared statement
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, sid);
			pstmt.setInt(2, bid);
			pstmt.setObject(3,iDate);
			// execute
			pstmt.executeUpdate();
		//updateing book qnty;	
			q="select Bqty from books where Bid="+bid;
			stmt=con.createStatement();
			set=  stmt.executeQuery(q);
			set.next();
			int bqty=set.getInt(1);
			bqty--;
//			System.out.println(bqty);
			//query
			q="update books "+"set Bqty = ?"+" where Bid =? ;";
			// prepared statement
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1,bqty);
			pstmt.setInt(2,bid);
			// execute
			pstmt.executeUpdate();
		//updating book student have
			q="select Bhave from students where Sid="+sid;
			stmt=con.createStatement();
			set=  stmt.executeQuery(q);
			set.next();
			int bhave=set.getInt(1);
			bhave++;
			q="update students "+"set Bhave = ?"+" where Sid =? ;";
			pstmt= con.prepareStatement(q);
			pstmt.setInt(1, bhave);
			pstmt.setInt(2, sid);
			// execute
			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
}

	public static void returnBook() {
		Scanner sc = new Scanner(System.in);
		int bid,sid;
		System.out.println("please enter the BookId");
		bid=sc.nextInt();
		System.out.println("please enter the StudentId");
		sid=sc.nextInt();
		try {
			// stablih connection
			Connection con = CP.createC();
	//updating the returnBook column
			//query
			String q = "update issuedbooks set ReturnDate =? where Bid=? && Sid=? && ReturnDate IS NULL";
			// prepared statement
			PreparedStatement pstmt = con.prepareStatement(q);
			// setting the value of parameter
			pstmt.setObject(1,LocalDate.now());
			pstmt.setInt(2,bid);
			pstmt.setInt(3,sid);
			// execute
			int row =pstmt.executeUpdate();
			if(row==0)
			{
				System.out.println("*****you dont have this book get it from library****");
				return;
			}
			
			//updateing book qnty;	
			q="select Bqty from books where Bid="+bid;
			Statement stmt=con.createStatement();
			ResultSet set=  stmt.executeQuery(q);
			set.next();
			int bqty=set.getInt(1);
			bqty++;
//			System.out.println(bqty);
			//query
			q="update books "+"set Bqty = ?"+" where Bid =? ;";
			// prepared statement
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1,bqty);
			pstmt.setInt(2,bid);
			// execute
			pstmt.executeUpdate();
	//updating book student have
			q="select Bhave from students where Sid="+sid;
			stmt=con.createStatement();
			set=  stmt.executeQuery(q);
			set.next();
			int bhave=set.getInt(1);
			bhave--;
			q="update students "+"set Bhave = ?"+" where Sid =? ;";
			pstmt= con.prepareStatement(q);
			pstmt.setInt(1, bhave);
			pstmt.setInt(2, sid);
			// execute
			pstmt.executeUpdate();
			System.out.println("\"*********Book is returned successfully**************\"");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
