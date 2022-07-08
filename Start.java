import java.util.Scanner;

import com.library.Books;
import com.library.Fine;
import com.library.Search;
import com.library.Students;

public class Start {

	public static void main(String[] args) {
		int a;
		Scanner sc = new Scanner(System.in);
		while(true)
			{
			System.out.println("*****Menu*****");
			System.out.println("0)show books");
			System.out.println("1)Get a book");
			System.out.println("2)View user");
			System.out.println("3)track book");
			System.out.println("4)Return a book");
			System.out.println("5)Add book");
			System.out.println("6)delete book");
			System.out.println("7)Caluclate the fine");
			System.out.println("8)Exit");
			a=sc.nextInt();
			
			switch(a)
			{
			case 0:
				//System.out.println("book is shown");
				Books.showBook();
				break;
			case 1:
			
				Books.getBook();
				break;
//***********************************************************************************


			case 2:
//			System.out.println();
				Students.showStudent();
			break;
//***********************************************************************************

			case 3:
				//System.out.println("you have found the book");
				Search.search();
				
				break;
		
//***********************************************************************************

			case 4:
				Books.returnBook();
				break;

//***********************************************************************************
			case 5:
			//taking input 
			System.out.println("You are now adding book");
			System.out.println("Enter the book name");
			String Bname =sc.next();
			System.out.println("Enter the book Author");
			String Bauthor=sc.next();
			System.out.println("Enter the book Qantity");
			int Bqty=sc.nextInt(); 
			//creating object to store book
			Books bk= new Books(Bname,Bauthor,Bqty);
			boolean ans5=Books.insertBook(bk);
			if(ans5==true)
			{
				System.out.println("Book is entered successfully");
			}
			else
				System.out.println("*********Something went worng**************");
			
			System.out.println(bk);

			break;
//***********************************************************************************

			case 6:
				System.out.println("Enter the Book ID for deletion");
				int Bid=sc.nextInt();
				boolean ans6 =Books.deleteBook(Bid);
				if(ans6==true)
				{
					System.out.println("Book is deleted successfully");
					
				}
				else
					System.out.println("*********Something went worng**************");
				break;
//***********************************************************************************
			case 7:
				Fine.cfine();
				break;

}
			
			if (a==8)
			{
				System.out.println("Thanks for using ");

				break;

			}
			System.out.println("________________________________________________________________________");

			}
		
		
	}

}
