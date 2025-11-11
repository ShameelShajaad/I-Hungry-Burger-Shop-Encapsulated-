import java.util.*;

class Burger{
	public static final double BurgerPrice=500;
	
	private String orderID;
	private String customerID;
	private String customerName;
	private double price;
	private int qty;
	private String status;
	
	public void setOrderID(String orderID){
		this.orderID=orderID;
	}
	public void setCustomerID(String customerID){
		this.customerID=customerID;
	}
	public void setCustomerName(String customerName){
		this.customerName=customerName;
	}
	public void setStatus(String status){
		this.status=status;
	}
	public void setQty(int qty){
		this.qty=qty;
	}
	public void setPrice(double price){
		this.price=price;
	}
	
	public String getOrderID(){
		return orderID;
	}
	public String getCustomerID(){
		return customerID;
	}
	public String getCustomerName(){
		return customerName;
	}
	public String getStatus(){
		return status;
	}
	public int getQty(){
		return qty;
	}
	public double getPrice(){
		return price;
	}
}
class IHungryBurgerShop{
	public static Burger[] BurgerArray=new Burger[0];
	
	public static void clearConsole(){
		try{
			final String os=System.getProperty("os.name");
			if(os.contains("Windows")){
				new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
			}else{
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		}catch(final Exception e){
			e.printStackTrace();
			//Handle any exceptions.
		}
	}
	public static void exit(){
		clearConsole();
		System.out.println("\n\t\tExited.....\n");
		System.exit(0);
	}
	public static void extendBurgerArray(){
		Burger[] TempBurgerArray=new Burger[BurgerArray.length+1];
		
		for (int i = 0; i < BurgerArray.length; i++){
			TempBurgerArray[i]=BurgerArray[i];
		}
		
		String newincr="";
		if(BurgerArray.length==0){
			newincr="B0001";
		}else{
			String incr=BurgerArray[BurgerArray.length-1].getOrderID();
			char ch=incr.charAt(0);
			String numbers=incr.substring(1);
			
			int num=Integer.parseInt(numbers);
			num++;
			String num1=Integer.toString(num);
			String zero="";
			for (int i = 0; i < (incr.length()-num1.length())-1; i++){
				zero+="0";
			}
			newincr=ch+zero+num1;
		}
		
		BurgerArray=TempBurgerArray;
		BurgerArray[BurgerArray.length-1]=new Burger();
		BurgerArray[BurgerArray.length-1].setOrderID(newincr);
	}
	public static int isValidPhoneNo(String phone){
		if(phone.length()!=10){
			return 0;
		}
		char ch=phone.charAt(0);
		if(ch!='0'){
			return 1;
		}
		for (int i = 0; i < phone.length(); i++){
			ch=phone.charAt(i);
			if(ch<'0' || ch>'9'){
				return 2;
			}
		}
		return 3;
	}
	public static void checkCustomer(String phone){
		Scanner s=new Scanner(System.in);
		
		boolean bool=true;
		int count=0;
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getCustomerID()!=null && BurgerArray[i].getCustomerID().equals(phone)){
				System.out.print("Customer Name                  :  "+BurgerArray[i].getCustomerName());
				System.out.println();
				count=i;
				bool=false;
				break;
			}
		}
		String name="";
		if(count!=-1){
			name=BurgerArray[count].getCustomerName();
		}
		if(bool){
			System.out.print("Customer Name                  :  ");
			name=s.nextLine();
		}
		
		if(BurgerArray.length>0){
			BurgerArray[BurgerArray.length-1].setCustomerID(phone);
			BurgerArray[BurgerArray.length-1].setCustomerName(name);
			BurgerArray[BurgerArray.length-1].setStatus("PREPARING");
		}
	}
	public static void ConfirmOrder(int qty){
		BurgerArray[BurgerArray.length-1].setQty(qty);
		BurgerArray[BurgerArray.length-1].setPrice((Burger.BurgerPrice*qty));
	}
	public static void BestCustomer(){
		if(BurgerArray.length == 0){
			System.out.println("\tNo customers yet!");
			return;
		}
		
		int count=0;
		for (int i = 0; i < BurgerArray.length; i++){
			boolean found =false;
			for (int j = 0; j < i; j++){
				if(BurgerArray[i].getCustomerID().equals(BurgerArray[j].getCustomerID())){
					found=true;
					break;
				}
			}
			if(!found){
				count++;
			}
		}
		
		String[] phone=new String[count];
		String[] customer=new String[count];
		double[] total=new double[count];
		
		int indx=0;
		for (int i = 0; i < BurgerArray.length; i++){
			boolean found =false;
			int count1 = -1;
			for (int j = 0; j < indx; j++){
				if(BurgerArray[i].getCustomerID().equals(BurgerArray[j].getCustomerID())){
					found=true;
					count1=j;
					break;
				}
			}
			if(!found){
				phone[indx]=BurgerArray[i].getCustomerID();
				customer[indx]=BurgerArray[i].getCustomerName();
				total[indx]=BurgerArray[i].getStatus().equals("CANCEL") ? 0.0 : BurgerArray[i].getPrice();
				indx++;
			}else{
				total[count1]+=BurgerArray[i].getPrice();
			}
		}
		
		for (int i = 0; i < phone.length; i++){
			for (int j = 0; j < phone.length-1; j++){
				if(total[j]<total[j+1]){
					double a=total[j+1];
					total[j+1]=total[j];
					total[j]=a;
					
					String b=phone[j+1];
					phone[j+1]=phone[j];
					phone[j]=b;
					
					String c=customer[j+1];
					customer[j+1]=customer[j];
					customer[j]=c;
				}
			}
		}
		
		System.out.println("----------------------------------------------------");
		System.out.println("    CustomerID      Name                   Total");
		System.out.println("----------------------------------------------------");
		for (int i = 0; i < phone.length; i++){
			System.out.printf("    %-12s    %-15s   %12.2f    \n", phone[i], customer[i], total[i]);
			System.out.println("----------------------------------------------------");
		}
	}
	public static boolean isValidOrderID(String id){
		if(id.length()!=5){
			return false;
		}
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getOrderID().equals(id)){
				return true;
			}
		}
		return false;
	}
	public static void PrintOrderIDDetails(String id){
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getOrderID().equals(id)){
				System.out.println();
				System.out.println("----------------------------------------------------------------------------------");
				System.out.println("   OrderID      CustomerID     Name      Quantity    OrderValue    OrderStatus   |");
				System.out.println("----------------------------------------------------------------------------------");
				System.out.printf("    %-10s  %-12s  %-12s  %-5d     %-10.2f    %-12s |\n", BurgerArray[i].getOrderID(), BurgerArray[i].getCustomerID(), BurgerArray[i].getCustomerName(), BurgerArray[i].getQty(), BurgerArray[i].getPrice(), BurgerArray[i].getStatus());
				System.out.println("----------------------------------------------------------------------------------");
			}
		}
	}
	public static boolean isValidCustomerID(String num){
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getCustomerID().equals(num)){
				return true;
			}
		}
		return false;
	}
	public static void PrintCustomerIDDetails(String num){
		System.out.println();
		System.out.println();
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getCustomerID().equals(num)){
				System.out.println("Customer ID  :  "+BurgerArray[i].getCustomerID());
				System.out.println("Name         :  "+BurgerArray[i].getCustomerName());
				break;
			}
		}
		System.out.println();
		System.out.println("Customer Order Details");
		System.out.println("=======================");
		System.out.println();
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println(" Order_ID   Order_Quantity    Total_value");
		System.out.println("--------------------------------------------");
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getCustomerID().equals(num)){
				System.out.printf("  %-8s       %-7d       %-12.2f  \n",BurgerArray[i].getOrderID(), BurgerArray[i].getQty(), BurgerArray[i].getPrice());
			}
		}
		System.out.println("--------------------------------------------");
	}
	public static void PrintDeliveredOrder(){
		System.out.println("---------------------------------------------------------------");
		System.out.println(" OrderID   CustomerID      Name        Quantity     OrderValue");
		System.out.println("---------------------------------------------------------------");
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getStatus().equals("DELIVERED")){
				System.out.printf("  %-5s    %-11s    %-15s %-5d  %12.2f \n",BurgerArray[i].getOrderID(), BurgerArray[i].getCustomerID(), BurgerArray[i].getCustomerName(), BurgerArray[i].getQty(), BurgerArray[i].getPrice());
				System.out.println("---------------------------------------------------------------");
			}
		}
	}
	public static void PrintPreparingOrder(){
		System.out.println("---------------------------------------------------------------");
		System.out.println(" OrderID   CustomerID      Name        Quantity     OrderValue");
		System.out.println("---------------------------------------------------------------");
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getStatus().equals("PREPARING")){
				System.out.printf("  %-5s    %-11s    %-15s %-5d  %12.2f \n",BurgerArray[i].getOrderID(), BurgerArray[i].getCustomerID(), BurgerArray[i].getCustomerName(), BurgerArray[i].getQty(), BurgerArray[i].getPrice());
				System.out.println("---------------------------------------------------------------");
			}
		}
	}
	public static void PrintCancelOrder(){
		System.out.println("---------------------------------------------------------------");
		System.out.println(" OrderID   CustomerID      Name        Quantity     OrderValue");
		System.out.println("---------------------------------------------------------------");
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getStatus().equals("CANCEL")){
				System.out.printf("  %-5s    %-11s    %-15s %-5d  %12.2f \n",BurgerArray[i].getOrderID(), BurgerArray[i].getCustomerID(), BurgerArray[i].getCustomerName(), BurgerArray[i].getQty(), BurgerArray[i].getPrice());
				System.out.println("---------------------------------------------------------------");
			}
		}
	}
	public static int CheckStatus(String id){
		int temp=0, count=0;
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getOrderID().equals(id)){
				temp=i;
				count++;
			}
		}
		
		if(count==0){
			return 0;
		}
		if(BurgerArray[temp].getStatus().equals("DELIVERED")){
			return 1;
		}
		if(BurgerArray[temp].getStatus().equals("CANCEL")){
			return 2;
		}
		return 3;
	}
	public static void UpdateOrderDetails(String id){
		Scanner s=new Scanner(System.in);
		int index=0;
		for (int i = 0; i < BurgerArray.length; i++){
			if(BurgerArray[i].getOrderID().equals(id)){
				index=i;
			}
		}
		System.out.println("Order ID      :  "+BurgerArray[index].getOrderID());
		System.out.println("Customer ID   :  "+BurgerArray[index].getCustomerID());
		System.out.println("Name          :  "+BurgerArray[index].getCustomerName());
		System.out.println("Quantity      :  "+BurgerArray[index].getQty());
		System.out.println("Order Value   :  "+BurgerArray[index].getPrice());
		System.out.println("Order Status  :  "+BurgerArray[index].getStatus());
		System.out.println();
		System.out.println("What do you want to update ?");
		System.out.println("\t(01) Quantity");
		System.out.println("\t(02) Status");
		System.out.println();
		System.out.print("Enter an option ->  ");
		int option=s.nextInt();
		
		switch(option){
			
			case 1:
			
			clearConsole();
			System.out.println("Quantity Update");
			System.out.println("================");
			System.out.println();
			System.out.println("Order ID      :  "+BurgerArray[index].getOrderID());
			System.out.println("Customer ID   :  "+BurgerArray[index].getCustomerID());
			System.out.println("Name          :  "+BurgerArray[index].getCustomerName());
			System.out.println();
			System.out.print("\tEnter your quantity update value ->  ");
			int qty=s.nextInt();
			
			BurgerArray[index].setQty(qty);
			BurgerArray[index].setPrice((Burger.BurgerPrice*qty));
			
			System.out.println();
			System.out.println("\tUpdate order quantity successfully.....");
			
			System.out.println();
			System.out.println("New order quantity  :  "+BurgerArray[index].getQty());
			System.out.println("New order value     :  "+BurgerArray[index].getPrice());
			
			break;
			
			case 2:
			
			clearConsole();
			System.out.println("Status Update");
			System.out.println("==============");
			System.out.println();
			System.out.println("Order ID      :  "+BurgerArray[index].getOrderID());
			System.out.println("Customer ID   :  "+BurgerArray[index].getCustomerID());
			System.out.println("Name          :  "+BurgerArray[index].getCustomerName());
			System.out.println();
			System.out.println("\t(0) Cancel");
			System.out.println("\t(1) Preparing");
			System.out.println("\t(2) Delivered");
			System.out.println();
			System.out.print("Enter new order status ->  ");
			int sts=s.nextInt();
			if(sts==0){
				BurgerArray[index].setStatus("CANCEL");
			}
			if(sts==1){
				BurgerArray[index].setStatus("PREPARING");
			}
			if(sts==2){
				BurgerArray[index].setStatus("DELIVERED");
			}
			System.out.println();
			System.out.println("\tUpdate order status successfully.....");
			System.out.println();
			System.out.println("New order status  :  "+BurgerArray[index].getStatus());
			
			break;
		}
	}
	public static void main(String args[]){
		
		Scanner s=new Scanner(System.in);
		
		L1:do{
			clearConsole();
			System.out.println("+---------------------------------------------------------+");
			System.out.println("|                     iHungry Burger                      |");
			System.out.println("+---------------------------------------------------------+");
			System.out.println();
			System.out.println("[1] Place Order             [2] Search Best Customer");
			System.out.println("[3] Search Order            [4] Search Customer");
			System.out.println("[5] View Orders             [6] Update Order Details");
			System.out.println("[7] Exit");
			System.out.println();
			System.out.print("Enter an Option to Continue ->  ");
			int option=s.nextInt();
			clearConsole();
			
			switch(option){
				
				case 1:
				
				
				do{
					clearConsole();
					System.out.println("+------------------------------------------------------+");
					System.out.println("|                      Place Order                      ");
					System.out.println("+------------------------------------------------------+");
					System.out.println();
					System.out.println();
					extendBurgerArray();
					System.out.println("ORDER ID  -  "+BurgerArray[BurgerArray.length-1].getOrderID());
					System.out.println("==================");
					System.out.println();
					System.out.println();
					String phone="";
					do{
						System.out.print("Enter Customer ID (Phone No.)  :  ");
						phone=s.next();
						int chk=isValidPhoneNo(phone);
						if(chk==0){
							System.out.println("\tPhone Number must contain 10 numbers..... Try Agian.....");
							System.out.println();
							continue;
						}
						if(chk==1){
							System.out.println("\tPhone Number must contain a '0' in the beginning..... Try Agian.....");
							System.out.println();
							continue;
						}if(chk==2){
							System.out.println("\tPhone Number must contain only numbers..... Try Agian.....");
							System.out.println();
							continue;
						}if(chk==3){
							break;
						}
					}while(true);
					
					checkCustomer(phone);
					int qty=0;
					do{
						System.out.print("Enter Burger Quantity          :  ");
						qty=s.nextInt();
						if(qty==0){
							System.out.println("\tQuantity can't be zero.....Try Again.....");
							System.out.println();
							continue;
						}
						if(qty<0){
							System.out.println("\tQuantity can't be negative.....Try Again.....");
							System.out.println();
							continue;
						}
						break;
					}while(true);
					
					System.out.print("Total value                    :  "+(Burger.BurgerPrice*qty));
					System.out.println();
					
					do{
						System.out.print("\t\tAre you want confirm the order (Y/N) ->  ");
						char ch=s.next().toUpperCase().charAt(0);
						if(ch=='Y'){
							ConfirmOrder(qty);
							System.out.println();
							System.out.println("\t\t\tYour order is entered to the system successfully.....");
							break;
						}else if(ch=='N'){
							ConfirmOrder(0);
							continue L1;
						}else{
							System.out.println("	\t\t\tWrong option.....Try Again.....");
							continue;
						}
					}while(true);
					
					System.out.println();
					do{
						System.out.print("Do you want to place another order (Y/N) ->  ");
						char ch=s.next().toUpperCase().charAt(0);
						if(ch=='Y'){
							break;
						}else if(ch=='N'){
							continue L1;
						}else{
							System.out.println("	\t\t\tWrong option.....Try Again.....");
							continue;
						}
					}while(true);
				}while(true);
				
				case 2:
				
				do{
					clearConsole();
					System.out.println("+--------------------------------------------------------+");
					System.out.println("|                     Best Customer                      |");
					System.out.println("+--------------------------------------------------------+");
					System.out.println();
					System.out.println();
					BestCustomer();
					System.out.println();
					do{
						System.out.print("\tDo you want to go back to main menu (Y/N) ->  ");
						char ch=s.next().toUpperCase().charAt(0);
						if(ch=='Y'){
							continue L1;
						}else if(ch=='N'){
							exit();
						}else{
							System.out.println("	\t\t\tWrong option.....Try Again.....");
							continue;
						}
					}while(true);
				}while(true);
				
				case 3:
				
				T1: do{
					clearConsole();
					System.out.println("+--------------------------------------------------------");
					System.out.println("|                 Search Order Details                  |");
					System.out.println("+--------------------------------------------------------");
					System.out.println();
					System.out.print("Enter Order ID  ->  ");
					String OrderID=s.next().toUpperCase();
					if(isValidOrderID(OrderID)){
						PrintOrderIDDetails(OrderID);
						System.out.println();
						do{
							System.out.print("Do you want to search another order detail (Y/N) ->  ");
							char ch=s.next().toUpperCase().charAt(0);
							if(ch=='Y'){
								continue T1;
							}else if(ch=='N'){
								continue L1;
							}else{
								System.out.println("\t\tWrong option.....Please try again.....");
								System.out.println();
								continue;
							}
						}while(true);
					}else{
						System.out.println();
						System.out.println();
						do{
							System.out.print("Invalid Order ID.....Do you want to try again (Y/N)  ");
							char ch=s.next().toUpperCase().charAt(0);
							if(ch=='Y'){
								continue T1;
							}else if(ch=='N'){
								continue L1;
							}else{
								System.out.println("\t\tWrong option.....Please try again.....");
								System.out.println();
								continue;
							}
						}while(true);
					}
				}while(true);
				
				case 4:
				
				T2: do{
					clearConsole();
					System.out.println("+---------------------------------------------------------+");
					System.out.println("|                 Search Customer Details                 |");
					System.out.println("+---------------------------------------------------------+");
					System.out.println();
					System.out.print("Enter Customer ID ->  ");
					String no=s.next();
					if(isValidCustomerID(no)){
						PrintCustomerIDDetails(no);
						do{
							System.out.print("Do you want to search another customer deatail (Y/N) ->  ");
							char ch=s.next().toUpperCase().charAt(0);
							if(ch=='Y'){
								continue T2;
							}else if(ch=='N'){
								continue L1;
							}else{
								System.out.println("\t\tWrong option.....Please try again.....");
								System.out.println();
								continue;
							}
						}while(true);
					}else{
						System.out.println();
						System.out.println();
						System.out.println("\t\tThis customer ID is not added yet.....");
						System.out.println();
						do{
							System.out.print("Do you want to search another customer deatail (Y/N) ->  ");
							char ch=s.next().toUpperCase().charAt(0);
							if(ch=='Y'){
								continue T2;
							}else if(ch=='N'){
								continue L1;
							}else{
								System.out.println("\t\tWrong option.....Please try again.....");
								System.out.println();
								continue;
							}
						}while(true);
					}
				}while(true);
				
				case 5:
				
				L2: do{
					clearConsole();
					System.out.println("+--------------------------------------------------------+");
					System.out.println("|                    View Order List                     |");
					System.out.println("+--------------------------------------------------------+");
					System.out.println();
					System.out.println("[1] Delivered order");
					System.out.println("[2] Preparing order");
					System.out.println("[3] Cancel order");
					System.out.println();
					System.out.print("Enter an option to continue ->  ");
					int option1=s.nextInt();
					
					switch(option1){
						
						case 1:
						
						do{
							System.out.println("+------------------------------------------------+");
							System.out.println("|                Delivered Order                 |");
							System.out.println("+------------------------------------------------+");
							System.out.println();
							System.out.println();
							PrintDeliveredOrder();
							System.out.println();
							do{
								System.out.print("Do you want to go to homepage (Y/N) ->  ");
								char ch=s.next().toUpperCase().charAt(0);
								if(ch=='Y'){
									continue L1;
								}else if(ch=='N'){
									continue L2;
								}else{
									System.out.println("\t\tWrong option.....Please try again.....");
									System.out.println();
									continue;
								}
							}while(true);
						}while(true);
						
						case 2:
						
						do{
							System.out.println("+------------------------------------------------+");
							System.out.println("|                Preparing Order                 |");
							System.out.println("+------------------------------------------------+");
							System.out.println();
							System.out.println();
							PrintPreparingOrder();
							System.out.println();
							do{
								System.out.print("Do you want to go to homepage (Y/N) ->  ");
								char ch=s.next().toUpperCase().charAt(0);
								if(ch=='Y'){
									continue L1;
								}else if(ch=='N'){
									continue L2;
								}else{
									System.out.println("\t\tWrong option.....Please try again.....");
									System.out.println();
									continue;
								}
							}while(true);
						}while(true);
						
						case 3:
						
						do{
							System.out.println("+------------------------------------------------+");
							System.out.println("|                  Cancel Order                  |");
							System.out.println("+------------------------------------------------+");
							System.out.println();
							System.out.println();
							PrintCancelOrder();
							System.out.println();
							do{
								System.out.print("Do you want to go to homepage (Y/N) ->  ");
								char ch=s.next().toUpperCase().charAt(0);
								if(ch=='Y'){
									continue L1;
								}else if(ch=='N'){
									continue L2;
								}else{
									System.out.println("\t\tWrong option.....Please try again.....");
									System.out.println();
									continue;
								}
							}while(true);
						}while(true);
						
						default:
						
						do{
							System.out.print("\t\tWrong option.....Do you want to try again (Y/N) ->  ");
							char ch=s.next().toUpperCase().charAt(0);
							if(ch=='Y'){
								continue L2;
							}else if(ch=='N'){
								continue L1;
							}else{
								System.out.println("\t\tWrong option.....Please try again.....");
								System.out.println();
								continue;
							}
						}while(true);
					}
				}while(true);
				
				case 6:
				
				L3: do{
					clearConsole();
					System.out.println("+--------------------------------------------------------+");
					System.out.println("|                  Update Order Details                  |");
					System.out.println("+--------------------------------------------------------+");
					System.out.println();
					System.out.print("Enter Order ID ->  ");
					String id=s.next().toUpperCase();
					System.out.println();
					int chk=CheckStatus(id);
					if(chk==0){
						System.out.println("\tInvalid Order ID.....Try Again");
					}
					if(chk==1){
						System.out.println("\tThis order is already delivered.....You cannot update this order.....");
					}
					if(chk==2){
						System.out.println("\tThis order is already cancelled.....You cannot update this order.....");
					}
					if(chk==3){
						System.out.println();
						UpdateOrderDetails(id);
					}
					do{
						System.out.println();
						System.out.println();
						System.out.print("Do you want to update another order detail (Y/N) ->  ");
						char ch=s.next().toUpperCase().charAt(0);
						if(ch=='Y'){
							continue L3;
						}else if(ch=='N'){
							continue L1;
						}else{
							System.out.println("\t\tWrong option.....Please try again.....");
							System.out.println();
							continue;
						}
					}while(true);
				}while(true);
				
				case 7:
				
				exit();
				
				break;
				
				default:
				
				do{
					System.out.print("\t\tWrong option.....Do you want to try again (Y/N) ->  ");
					char ch=s.next().toUpperCase().charAt(0);
					if(ch=='Y'){
						continue L1;
					}else if(ch=='N'){
						exit();
					}else{
						System.out.println("\t\tWrong option.....Please try again.....");
						System.out.println();
						continue;
					}
				}while(true);
			}
		}while(true);
	}
}
