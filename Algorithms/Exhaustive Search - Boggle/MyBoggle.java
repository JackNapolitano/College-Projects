import java.io.*;
import java.util.*;


public class MyBoggle
{
    public static void main(String[] args) throws Exception
    {
        int boardArgument = 5;
        String[] commandLineArgs = new String[5];
        int dictStruct = 5;
        for(int i = 0; i < args.length; i++)
        {
            if (args[i] != null)
            {
                commandLineArgs[i] = args[i];
                if (commandLineArgs[i].equals("-b"))
                    boardArgument = i+1;
                if (commandLineArgs[i].equals("-d"))
                    dictStruct = i+1;
            }            
        }
        String[][] board;
        String[][] boardWild;

        Scanner scan = new Scanner(System.in);
        if(boardArgument>4)
        {
            System.out.print(" Enter the file name of the board you want to load.\n ( board1.txt | board2.txt | etc ) : ");
            board = loadBoard(scan.next());
        }
        else
            board = loadBoard(commandLineArgs[boardArgument]);
        if(dictStruct == 5)
        {
            System.out.print(" Enter the type of dictionary you want to use (simple/dlb): ");
            commandLineArgs[0] = scan.next();     
            dictStruct = 0;   
        }
        Stack myStack = new Stack(); 
        int size = (board.length-1);
        System.out.println("\n\n LETS GET READY TO BOGGLE!!!!\n\n");
        PrintBoard(board);
        String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String guess = "";
        int g = 0;
        String userInput = "";
        System.out.print("\nEnter words you find on the board one at a time.\nType 'X' to stop guessing.\nType 'B' to display the board.\n");
        BST myBST = new BST(); 
        BST guesses = new BST();
        Scanner fileScan = new Scanner(new FileInputStream("dictionary.txt"));
        String st;
        StringBuilder sb;
        DictionaryInterface D = new SimpleDictionary();
        DictionaryInterface wordsOnBoard = new SimpleDictionary();;
        if(dictStruct<5)
        {
            if(commandLineArgs[dictStruct].equals("dlb"))
            {
                D = new Trie();
                wordsOnBoard = new Trie();
            }
            if(commandLineArgs[dictStruct].equals("simple"))
            {
                D = new SimpleDictionary();
                wordsOnBoard = new SimpleDictionary();
            }
        }        
        //add dictionary
        while (fileScan.hasNext())
        {
            st = fileScan.nextLine();
            if(st.length() < 17 && st.length() > 2)
                D.add(st);
        }
        //find all words on board
        for(int i = 0; i<=size; i++ )
        {
            for(int j = 0; j<=size; j++)
            {
                myStack.push(i,j,board[i][j]);
                searchBoard(size, i, j, board, myStack, D, wordsOnBoard, myBST);
                myStack.makeEmpty();
            }
        }

        //User plays the game
        while(!guess.equals("X"))
        {
            System.out.print("Guess:");
            userInput = scan.next();
            if(userInput.equals("X"))
                guess = "X";
            else if(userInput.equals("B"))
                PrintBoard(board);
            else
            {
                userInput = userInput.toLowerCase();
                if(guesses.search(userInput))
                {
                    System.out.println("      You already guessed that word");
                }    
                else if(myBST.search(userInput))
                {
                    System.out.println("      Correct!");
                    guesses.insert(userInput);
                }           
                if(!myBST.search(userInput))
                {
                    System.out.println("      Incorrect.");
                }
            }              
        }
       
        // ////////////////////////////////
        int correctGuesses = 0;
        System.out.println("---------------");
        System.out.println("CORRECT GUESSES");
        correctGuesses = guesses.countWords();
        System.out.println("---------------");
        int allpossible = myBST.countWords();
        double percentCorrect = (double)correctGuesses/(double)allpossible*100;
        System.out.println("You had "+ correctGuesses +" correct guesses.\nThere were "+ allpossible +" possible words.\nYou guessed %"+ String.format("%.3g", percentCorrect) +" correct.");
        System.out.println("\n\nPress enter key to display all possible words...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
        System.out.println("\n\nHere were all of the possible words\n--------------------------------");
        myBST.inOrderPrint();
    } // END MAIN

    //LOADS THE BOGGLE BOARD INTO AN ARRAY
    private static String[][] loadBoard( String infileName) throws Exception
    {
        Scanner infile = new Scanner( new File(infileName) );
        int rows = 4;
        int i = 0;
        int cols = rows;
        String boardString = infile.next();
        boardString = boardString.toLowerCase();
        String array[] = boardString.split("(?!^)");
        String[][] board = new String[rows][cols];
        for(int r = 0; r < rows ; r++)
            for(int c = 0; c < cols; c++){
                 board[r][c] = array[i];
                 i++;
            }
        
        infile.close();
        return board;
    }
    public static void PrintBoard(String board[][])
    {
        System.out.println("The Board:");
        for(int ww = 0; ww < 4 ; ww++)
        {
            System.out.println("\t ---------------------");
            System.out.print("\t");
            for(int cc = 0; cc < 4; cc++){
                 System.out.print(" | " + board[ww][cc].toUpperCase() + " ");
            }
            System.out.println(" |");
        }
        System.out.println("\t ---------------------");
    }
    public static void searchBoard(int size, int row, int col, String[][] board, Stack myStack, DictionaryInterface t, DictionaryInterface wordsOnBoard, BST myBST)
    {
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        StringBuilder modWord = new StringBuilder(myStack.toString());
        int index1 = 0;
        int index2 = 0;
        if(t.search(modWord) == 0 && !myStack.toString().contains("*"))
        {  

        }
        else
        {

            //Handles wild cards, changes wild cards to each letter in the alphabet one by one then tests the new word against the trie
            if (myStack.toString().contains("*")  && myStack.toString().length() >=3) {
                index1 = modWord.toString().indexOf('*');
                for(int o = 0; o < alphabet.length; o++){
                    modWord.setCharAt(index1, alphabet[o]);
                    if (modWord.toString().contains("*"))
                    {
                        index2 = modWord.toString().indexOf('*');
                        for(int i = 0; i < alphabet.length; i++){
                            modWord.setCharAt(index2, alphabet[i]);
                            if(t.search(modWord)==3 || t.search(modWord)==2)
                            {
                                wordsOnBoard.add(modWord.toString());
                                myBST.insert(modWord.toString());
                            }
                        }
                        modWord.setCharAt(index2, '*');

                    }
                    else{
                        if(t.search(modWord)==3 || t.search(modWord)==2)
                        {
                            myBST.insert(modWord.toString());
                            wordsOnBoard.add(modWord.toString());
                        }
                    }
                   
                }          
            }

            if((t.search(modWord)==3 || t.search(modWord)==2) && myStack.toString().length() >=3 && !myStack.toString().contains("*"))
            {
                wordsOnBoard.add(myStack.toString());
                myBST.insert(myStack.toString());
            }
            if((row-1)>=0)
            {
                if(myStack.search(row-1,col) == false)//up
                {
                    myStack.push(row-1,col,board[row-1][col]);
                    searchBoard(size, (row-1), col, board, myStack, t, wordsOnBoard, myBST);
                }
            }
            if((row-1)>=0 && (col+1)<=size)
            {
                if(myStack.search(row-1,col+1) == false)   //upright
                {
                    myStack.push(row-1,col+1,board[row-1][col+1]);
                    searchBoard(size, (row-1), (col+1), board, myStack, t, wordsOnBoard, myBST);
                }
            }
            if((col+1)<=size)
            {
                if(myStack.search(row,col+1) == false )   //right
                {
                    myStack.push(row,col+1,board[row][col+1]);
                    searchBoard(size, (row), (col+1), board, myStack, t, wordsOnBoard, myBST);
                }
            }
            if((row+1)<=size && (col+1)<=size)
                 if(myStack.search(row+1,col+1) == false)   //down right
                {
                    myStack.push(row+1,col+1,board[row+1][col+1]);
                    searchBoard(size, (row+1), (col+1), board, myStack, t, wordsOnBoard, myBST);
                }

            if((row+1)<=size)
            {
                 if(myStack.search(row+1,col) == false)     //down
                {
                    myStack.push(row+1,col,board[row+1][col]);
                    searchBoard(size, (row+1), col, board, myStack, t, wordsOnBoard, myBST);

                }
            }
            if((col-1)>=0 && (row+1)<=size)
            {
                 if(myStack.search(row+1,col-1) == false)   //downleft
                {
                    myStack.push(row+1,col-1,board[row+1][col-1]);
                    searchBoard(size, (row+1), (col-1), board, myStack, t, wordsOnBoard, myBST);
                }
            }
            if((col-1)>=0)
            {
                 if(myStack.search(row,col-1) == false)   //left
                {
                    myStack.push(row,col-1,board[row][col-1]);
                    searchBoard(size, row, (col-1), board, myStack, t, wordsOnBoard, myBST);
                }
            }

            if((row-1)>=0 && (col-1)>=0)
            {
                 if(myStack.search(row-1,col-1) == false)   //upleft
                {
                    myStack.push(row-1,col-1,board[row-1][col-1]);
                    searchBoard(size, (row-1), (col-1), board, myStack, t, wordsOnBoard, myBST);
                }
            }
        }
        myStack.pop();
    } 
    // THIS STACK IS TO STORE THAT IS USED TO KEEP TRACK OF WHERE I HABE BEEN ON THE BOARD
    public static class Stack
    {
        LE stack;
        LE temp;
       
        public void push(int x, int y, String z)
        {
            stack = insertAtTailRe(stack, x, y, z); 
        }

        private  LE insertAtTailRe(LE stack, int x, int y, String z)
        {
            if (stack == null)
                stack = new LE(x, y ,z,null);
            else
                stack.setNext( insertAtTailRe(stack.getNext(), x, y, z) );
            return stack;
         }
        public void pop()
        {

            temp = stack;
            while(temp.getNext() != null)
            {
                if(null == temp.getNext().getNext())
                {
                    temp.setNext(null); 
                    break;
                }
                
                temp = temp.getNext();
        
            }
        }
        public boolean search( int x, int y )
        {
            temp = stack;
            if(x == temp.getx() && y == temp.gety())
                    return true;
            while(temp.getNext() != null)
            {
                temp = temp.getNext();
                if(x == temp.getx() && y == temp.gety())
                    return true;
                
                
            }
            return false;  
        }
        
        public int getX()
        {
           return stack.getx();
        }
        public int getY()
        {
           return stack.gety();
        }
        public String getZ()
        {
           return stack.getz();
        }
        public boolean empty()
        {
            if(stack == null)
            {
                return true;  
            }
            else
            {
                return false;
            }
        }
        public void  makeEmpty()
        {
            stack = null;
        }

        
            public String toString()
            {
                String s= "";
                if (empty()) return s;
                LE iter = stack;
                while (iter!=null)
                {
                    s += iter.getz();
                    iter = iter.getNext();
                }
                return s;
            }
    }
    //LE CLASS FOR THE STACK
    public static class LE
    {
      
      private int x, y;
      private String z;
      private LE next;

      public LE(int x, int y, String z)
      {
        this( x, y, z, null );
      }

      public LE(int x, int y,String z, LE next)
      {
        setData( x, y, z );
        setNext( next );
      }

      public int getx()
      {
        return x;
      }
      public int gety()
      {
        return y;
      }
       public String getz()
      {
        return z;
      }

      public LE getNext()
      {
        return next;
      }

      public void setData(int x, int y, String z)
      {
        this.x = x;
        this.y = y;
        this.z = z;
      }

      public void setNext(LE next)
      {
        this.next = next;
      }
    }
    public static class BST
    {
        private BSTNode root;

        // all of the following methods require recursive helper methods
        // because they require the root be passed in and recursed on.
        // the code in main can't pass in the root

        public void insert( String data )
        {
            root = insertHelper( root, data );
        }
        private BSTNode insertHelper( BSTNode root, String data )
        {
            if(root == null)
            {
                return new BSTNode(data, null ,null);
            }

            else if ((root.getData().compareTo(data)) > 0)//traverse left
            {
                root.setLeft(insertHelper(root.getLeft(), data));
            }
            else if ((root.getData().compareTo(data)) < 0)//traverse right
            {
                root.setRight(insertHelper(root.getRight(), data));
            }

            return root;
        }
        public void makeEmpty()
        {
            root=null;
        }
        public boolean search( String data )
        {
            return searchHelper( root, data );
        }
        private boolean searchHelper( BSTNode root, String key )
        {
            if(root == null)
            {
                return false;
            }
            else if(root.getData().compareTo(key) == 0)
            {
                return true;
            }
            else if ((root.getData().compareTo(key)) > 0)//traverse left
            {
                return searchHelper(root.getLeft(), key);
            }
            else if ((root.getData().compareTo(key)) < 0)//traverse right
            {
                return searchHelper(root.getRight(), key);
            }
            return false;
        }

        public void inOrderPrint()
        {
            inOrderPrintHelper( root );
            System.out.println(); 
        }
        private void inOrderPrintHelper( BSTNode root )
        {
            if (root==null) return;
            inOrderPrintHelper( root.getLeft() );
            System.out.println(root.getData());
            inOrderPrintHelper( root.getRight() );
        }
        public int countWords()
        {
            return countWordsHelper(root); 
        }
        private int countWordsHelper( BSTNode root)
        {
            if (root==null) return 0;
            return 1 + countWordsHelper( root.getLeft()) + countWordsHelper( root.getRight());
        }
    }
    //BSTNODE CLASS FOR BST
    public static class BSTNode
    {
        String data;
        BSTNode left,right;

        public BSTNode( String data, BSTNode left, BSTNode right )
        {
            this.data = data;
            this.left = left;
            this.right=right;
        }

        public BSTNode getLeft()
        {
            return left;
        }

        public BSTNode getRight()
        {
            return right;
        }


        public void setRight( BSTNode right)
        {
            this.right = right;
        }

        public void setLeft( BSTNode left)
        {
            this.left = left;
        }

        public String getData()
        {
            return data;
        }

        public void setData( String data)
        {
            this.data = data;
        }
    }
}