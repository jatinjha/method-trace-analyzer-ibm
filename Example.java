class Example{
	
	
  public void calculateMessage(){
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
    
	Example e = new Example();
	int sum = e.calculateSum(10,20);
	int div = e.calculatediv(30,30);
	
	System.out.println("sum is :- "+sum);
	System.out.println("div is :- "+div);
 }
}