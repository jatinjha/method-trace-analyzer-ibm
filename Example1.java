class Example1{
	
	
  public void calculateMessage(){
	  int[] a = new int[4];
	  a[10] = 5;
	  System.out.println("Hope this is going to work");
  }
	
  public int calculateSum(int a , int b){
	
	  calculateMessage();
	  return(a+b);
	  
  }
	
	
  public int calculatediv(int a , int b){
	
      	
	  return(a/b);
  }	  
	
 public static void main(String[] args){
    
	Example1 e1 = new Example1();
	int sum = e1.calculateSum(10,20);
	int div = e1.calculatediv(30,30);
	
	System.out.println("sum is :- "+sum);
	System.out.println("div is :- "+div);
 }
}


/*
class Example1{
  
  public int calculateSum(int a , int b){
	return(a+b);
	  
  }
	
	
  public int calculatediv(int a , int b){
	
      
 	  int[] arr = new int[3];
	  arr[4] = 12;
	   	
	  return(a/b);
  }	  
	
 public static void main(String[] args){
    
	Example1 e1 = new Example1();
	int sum = e1.calculateSum(10,20);
	int div = e1.calculatediv(30,0);
	
	System.out.println("sum is :- "+sum);
	System.out.println("div is :- "+div);
 }
}
*/