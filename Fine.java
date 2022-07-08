package com.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Fine {
	public static void main(String[] args) {
		
		cfine();
	}
	public static int cfine()
	{
		Scanner sc =new Scanner(System.in);
		System.out.println("Enter your Sid");
		int sid=sc.nextInt();
		try {
			// stablih connection
			Connection con = CP.createC();
			// query
			String q = "select IssuedDate,ReturnDate from issuedbooks where Sid="+sid;
			Statement stmt=con.createStatement();
			ResultSet set=  stmt.executeQuery(q);
			int fine=0,fixedDay=15;
			while(set.next())
			{
				
				LocalDate iDate= set.getObject(1,LocalDate.class);
				LocalDate rDate= set.getObject(2,LocalDate.class);
//				System.out.println(rDate);
				if(rDate==null)
				{
					rDate=LocalDate.now();
					
				}
				
				Period period = Period.between(iDate,rDate);
				int yr=period.getYears();
		        int month =period.getMonths();
				int days =period.getDays();
				days=((yr*12*30)+(month*30)+days)-fixedDay;
				if(days<=14)
				{
					days=0;
					
				}
//				System.out.println(days);
				fine=fine+(days*10);		
			}
			//query
			q ="select Sname from students where Sid="+sid;
			//prepare staement
			stmt=con.createStatement();
			set=  stmt.executeQuery(q);
			set.next();
			String sname=set.getString(1);
			System.out.println("Student Id: "+sid);
			System.out.println("Student Name: "+sname);
			System.out.println("your fine is = "+fine);
			System.out.println("________________________________________________________________________");
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return 0;
	}

}
