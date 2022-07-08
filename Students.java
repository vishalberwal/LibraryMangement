package com.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Students {

	public int  Sid;
	public String Sname;
	
	
	public Students(int sid, String sname) {
		super();
		Sid = sid;
		Sname = sname;
	}


	@Override
	public String toString() {
		return "Students [Sid=" + Sid + ", Sname=" + Sname + "]";
	}
	
	
	public static void showStudent()

{
	
		try {
			// stablih connection
			Connection con = CP.createC();
			// query
			String q = " select *from students; ";
			Statement stmt=con.createStatement();
			ResultSet set=  stmt.executeQuery(q);
			while(set.next())
			{
				int Sid=set.getInt(1);
				String Sname=set.getString(2);
				int Bhave=set.getInt(3);
				System.out.println("BID: "+Sid);
				System.out.println("Bname: "+Sname);
				System.out.println("Bhave: "+Bhave);
				System.out.println("________________________________________________________________________");
			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
}
}