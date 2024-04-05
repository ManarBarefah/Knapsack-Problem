/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manar
 */






import java.util.Scanner;




    
class Item {
    int weight;
    int profit;

    public Item(int weight, int profit) {
        this.weight = weight;
        this.profit = profit;
    } 
}
    
public class Knapsack {
    
//method to sort the items (sequential)
public static double[][] sort(double[][] arr)
    {   
        
        int n = arr.length;
        
   //sort in increasing order
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) 
        {
            if (arr[i][0] > arr[j][0]) {
                // Swap arr[i] and arr[j]
                double temp = arr[i][0];
                double tempindex=arr[i][1];
                arr[i][0] = arr[j][0];
                arr[i][1] = arr[j][1];
                arr[j][0] = temp;
                arr[j][1] = tempindex;
            }
        }
    
        
    
    }
   
     return arr;
    
    }
        
    
   
    
    public static int GreedyKnapsack(Item[] items,int capacity) 
    {
        
        int n = items.length;
        int totalValue = 0;
        int totalWeight=0;
        int[] selecteditems=new int[n];
        int  count=0;
        double[][] ratio=new double[n][2];//2,one ratio ,one index(columns)
        double temp;
       
       
        for(int i=0;i<n;i++) 
        { 
            selecteditems[i]=0;//initial array of selected =0
        }
     
        
        
        long startTimeR = System.nanoTime();
       for(int i=0;i<n;i++)// to calulate the ratio and store the index
       {
           
        ratio[i][0]= (double) items[i].profit / items[i].weight;//value of ratio
        ratio[i][1]=i;//index  
           
       }
       long endTimeR = System.nanoTime();
      // System.out.println("time of RATIO:"+(endTimeR - startTimeR));
       long startsort = System.nanoTime();
   
      ratio=sort(ratio);
       long endsort = System.nanoTime();
    
        //System.out.println("time of soring:"+(endsort - startsort));
      for(int i=0;i<n;i++)// print ratio after sorting
       {
          System.out.print(String.format("\n"+ "The ratio of the item %.2f", ratio[i][0])+String.format(" The index of the item %.0f",ratio[i][1] ));     
       }
       long start = System.nanoTime();
          for (int i = n-1; i >=0; i--) // greedy start working
        {
          
          if (items[(int)ratio[i][1]].weight<= capacity)
          {
               ++count;
            totalValue += items[(int)ratio[i][1]].profit;
            totalWeight+=items[(int)ratio[i][1]].weight;
            capacity -= items[(int)ratio[i][1]].weight;
             selecteditems[(int)ratio[i][1]]=1;
             
           } 
         
        
       }  
          long end = System.nanoTime();
         long greedy=(end - start);
        System.out.println("\n---------Results of GREEDY algorithm---------");
        System.out.println("Total weight of selected items(Greedy): " + totalWeight);
        System.out.println("Total profit of selected items(Greedy): " + totalValue);
        System.out.println("Number of nodes visited by Greedy: " + count);
        
        System.out.print("Selected items of Greedy algorithm: {");
        for (int m = 0; m < n; m++)
        {
             System.out.print(selecteditems[m]+ " ");
        }
       System.out.print("}\n");
       
        
        System.out.println("Execution time of Greedy algorithm:"+((endsort - startsort)+(greedy)+(endTimeR - startTimeR))+" nanoseconds");
        System.out.println("The vlaue of the objective function(Greedy):"+objective_function( items,selecteditems));
        return totalValue;
       
    }
      
            public static int DynammicProgrammingKnapsack (Item[] items, int capacity)
            {
            int counter=0;
            int n = items.length;
            int[][] dp = new int[n + 1][capacity + 1];
            boolean[][] keep = new boolean[n + 1][capacity + 1];//selected items 
            int [] binary =new int [n];
            
            
            
            for (int i = 1; i <= n; i++) 
            {
                for (int j = 1; j <= capacity; j++) 
                {
                    counter++;
                     if (i == 0 || j == 0)
                     {// if item 0 or capasity 0 add 0 to the table (Initial conditions)
                        dp[i][j] = 0;
                     }
                     else if(items[i - 1].weight <= j) 
                    {
                        int valueWithItem = items[i - 1].profit + dp[i - 1][j - items[i - 1].weight];
                        int valueWithoutItem = dp[i - 1][j];
                        if (valueWithItem > valueWithoutItem){//back matrix 
                            dp[i][j] = valueWithItem;
                            keep[i][j] = true;
                        } else 
                        {
                            dp[i][j] = valueWithoutItem;
                        }
                    }
                     else 
                      {
                        dp[i][j] = dp[i - 1][j];
                      }
                }
                   
            }
            
            System.out.println("\n---------Results of DYNAMIC PROGRAMMING algorithm---------");
            System.out.println("Total weight of selected items(Dynammic Programming): " + getSelectedWeight(items, dp, capacity));
            System.out.println("Total profit of selected items(Dynammic Programming): " + dp[n][capacity]);
            System.out.println("Number of nodes visited by Dynamic Programming: " + counter);
            
            
            // Backtrack to find the items selected in the optimal solution
            int i = n, j = capacity;
           
            while (i > 0 && j > 0) 
            {
                if (keep[i][j])
                {
                    binary[i-1]=1;
                    j -= items[i - 1].weight;
                }
                i--;
            }
                 System.out.println("The vlaue of the objective function(Dynamic):"+objective_function(items,binary));
                 System.out.print("Selected items of Dynamic programming algorithm: {"); 
                
               for(int m=0;m<items.length;m++)
               {
                   System.out.print(binary[m]+" ");
               }
           
                System.out.print("}\n");
               
               
                
    return dp[n][capacity];
}
            
            
public static int getSelectedWeight(Item[] items, int[][] dp, int capacity) {
        int weight = 0;
        int n = items.length;
        for (int i = n; i > 0 && capacity > 0; i--) 
        {
            if (dp[i][capacity] != dp[i - 1][capacity]) 
            {
                weight += items[i - 1].weight;
                capacity -= items[i - 1].weight;
            }
        }
        return weight;
    
        }

  


public static double objective_function(Item[] items,int[] select)
 {
        double Objectivefunction=0;
       

      for(int h=0;h<select.length;h++)
       {
           if(select[h]==1)
           {
               Objectivefunction= Objectivefunction+(items[h].profit*select[h]);  
               
           }
      
        }


       return Objectivefunction ;   

 }






    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of items: ");
        int n = scanner.nextInt();
        Item[] items = new Item[n];

        System.out.println("Enter the weights and profits of the items:");
        for (int i = 0; i < n; i++) 
        {
            System.out.print("Enter the weight of item " + (i+1) + ": ");
            int weight = scanner.nextInt();
            System.out.print("Enter the profit of item " + (i+1) + ": ");
            int profit = scanner.nextInt();
            items[i] = new Item(weight, profit);
        }

        System.out.print("Enter the capacity of the knapsack: ");
        int capacity = scanner.nextInt();
        int greedyResult = GreedyKnapsack(items, capacity);
          
        long startTime2 = System.nanoTime();
        
        int dpResult = DynammicProgrammingKnapsack(items, capacity);
        long endTime2 = System.nanoTime();
        System.out.println("Execution time of Dynamic Programming algorithm: " + (endTime2 - startTime2) + " nanoseconds");
        if (greedyResult == dpResult) {
            System.out.println("Both algorithms produced the same result.");
        } else {
            System.out.println("The results of the algorithms are different.");
        }
        
        
    }
}

