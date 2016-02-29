import java.util.Scanner;
public class Game2048
{

		public static void main(String[] args)
		{
				/*this code is to create a replica of the game 2048
				 Nick Churchill
				 2/16/2015
				 EECS1510*/
				
				
				System.out.println("WELCOME TO 2048!!!");
				boolean restart;//creates a variable to determine if the player will restart or quit
				do{
					restart=false;
					//establish the initial graph here
				int board[][]= new int[4][4],backBoard[][]=new int[4][4],moveCount=0, score=0, backScore=0;
				boolean good=true,noMoves=true;//good is used to determine if the move is doable,noMove is used to determine if there are any moves left
				initial2(board);//set the first two random digits
				print(board,moveCount,score);//output the board the score and the and the move counter
				
				/*
				 test to see if the player has won the game and if so see if they want to continue playing
				check all of the places, if any of the locations in the board contain a 2048 then the player wins.
				if the player wins then check to see if the player still wants to play or not.if yes continue if not
				say thanks
				*/
				Scanner input = new Scanner(System.in);
				boolean noWin=true,goOn=false;//noWin determines if the player has won yet or no, it is true while they haven't won. goOn will be used to get the players decision as to whether they want to continue or not after they win.
				final String CONTINUE="c",CONTINUEE="C",CONTINUECHOICE="y",CONTINUECHOICEE="Y";//these are the options to used for determining if the player wishes to continue playing once they have won or if they choose to restart
				int onlyOne=0,backMoveCount=0;//creates a variable to be used in the undo to determine if the player has used their undo or not(onlyOne) and it creates another to be used to reset the move counter to what it needs to be
				do//main body of the game
				{
					noMoves=testLoose(board,good,noMoves);//determine if there are any moves left available to the player and if not end the game
					if(noMoves)
					{
					if (!goOn)//if continue is false it will check to see if the player won,if the player has won and selected
							  //continue it will not run the win check
					{
					for(int row=0;row<board.length;row++)//checking for win
					{
						for(int column=0; column<board[row].length;column++)
						{
						if (board[column][row]==2048)
							noWin=false;
						}
					}
					
					if (noWin==false)//checking if to continue
					{
						System.out.println("\n\n\t\t\t\u2606\u2605\u2606Congratulations!\u2606\u2605\u2606\n\n "
								+ "You have won the game in "+moveCount+" turns with a score of "+score+".\nIf you'd like to keep playing please type C now, otherwise press any key.");
						String choice2 = input.next();//determine if the player wants to continue their current game or start a new one
						if (choice2.equals(CONTINUE)||choice2.equals(CONTINUEE))
						{
							goOn=true;
							print(board,moveCount,score);
						}
					}
					}
					if(goOn||noWin)
					{
					// get the players choice of direction
				System.out.println("\n\n Please enter U(8),D(2),L(4), or R(6) to move the numbers in the chosen direction. To start a new game enter Q.\n If you need help"
						+ " you can use H(?) to enter the help menu.");
				String choice = input.next();
				
				final String HELP="h",HELPP="H",HELPPP="?",UNDO="z",UNDOO="Z",QUIT="q",QUITT="Q",//set the special options
						UP="u",UPP="U",DOWN="d",DOWNN="D",LEFT="l",LEFTT="L",RIGHT="r",RIGHTT="R",//set the alphabetic moves
						UPPP="8",DOWNNN="2",LEFTTT="4",RIGHTTT="6";//set the numeric moves
				
				if (choice.equals(UP)||choice.equals(UPP)||choice.equals(UPPP))//going up
				{
					good=checkUp(board,good);//check to see if it is a valid move
					if(good)//if it was a valid move perform the following operations
					{
					backBoard=undoBoard(board,backBoard); backScore=score; backMoveCount=moveCount;//set the backup board move count and score for the undo feature
					board=up(board);					//shift board up
					score=upScore(board,score);			//figure out the new score
					board=upCombo(board);				//merge the numbers
					RandomDig(board);					//add the random number
					++moveCount;						//increases the move count
					onlyOne=0;							//resets the undo feature so it can be used again if the player used it the last turn
					}
					else System.out.println("That move can not be made, please try again.");//if it was an invalid move the player will get this message
					print(board,moveCount,score);//output the board ,the move counter, and the score
					
				}
				else if (choice.equals(DOWN)||choice.equals(DOWNN)||choice.equals(DOWNNN))//going down
				{
					good=checkDown(board,good);
					if(good)
					{
					backBoard=undoBoard(board,backBoard); backScore=score; backMoveCount=moveCount;
					board=down(board);
					score=downScore(board,score);
					downCombo(board);
					RandomDig(board);
					++moveCount;
					onlyOne=0;
					}
					else System.out.println("That move can not be made, please try again.");
					print(board,moveCount,score);
				}
				else if (choice.equals(LEFT)||choice.equals(LEFTT)||choice.equals(LEFTTT))//going left
				{
					good=checkLeft(board,good);
					if(good)
					{
					backBoard=undoBoard(board,backBoard); backScore=score; backMoveCount=moveCount;
					board=left(board);
					score=leftScore(board,score);
					leftCombo(board);
					RandomDig(board);
					++moveCount;
					onlyOne=0;
					}
					else System.out.println("That move can not be made, please try again.");
					print(board,moveCount,score);
				}
				else if (choice.equals(RIGHT)||choice.equals(RIGHTT)||choice.equals(RIGHTTT))//going right
				{
					good=checkRight(board,good);
					if(good)
					{
					backBoard=undoBoard(board,backBoard); backScore=score; backMoveCount=moveCount;
					board=right(board);
					score=rightScore(board,score);
					rightCombo(board);
					RandomDig(board);
					++moveCount;
					onlyOne=0;
					}
					else System.out.println("That move can not be made, please try again.");
					print(board,moveCount,score);
				}
				else if (choice.equals(UNDO)||choice.equals(UNDOO))//undo a move
				{
					if (onlyOne==0)//if the player didn't use the undo feature on their last turn, perform the undo
					{
						board=setBoard(board,backBoard);//reset the board to the saved board from last turn
						score=backScore;//reset the players score
						moveCount=backMoveCount;++onlyOne;//set the move counter to previous state and set the undo counter so that the player will have to make a move before they can undo again
					}
					else System.out.println("Sorry, you can only go back one move.");//if the player has used their undo and tries to do it a second time in a row they will see the preceding message appear
					print(board,moveCount,score);//out put the board
				}
				else if (choice.equals(HELP)||choice.equals(HELPP)||choice.equals(HELPPP))//enter the help menu
				{
					help(board,good);//open the help menu
					print(board,moveCount,score);//out put the board, score, and move count.
				}
				else if (choice.equals(QUIT)||choice.equals(QUITT))//player wants to restart or quit the game
				{
					System.out.print("\n\nYou have quit after "+moveCount+" moves with a score of "+score);
					noWin=goOn=false;//set the conditions so that way the game will enter into a loosing state
				}
				else//improper value entered for movement
				{
					System.out.println("That is not a valid move, please try another entery.");
				}
					}
					}
					else//if the player looses this is done
					{
						if(goOn)System.out.println("\n\nThere are no more moves available. You scored "+score+" points in "+moveCount+" moves. Congratulations on the victory!");//message output upon running out of space after winning and entering overtime
						else System.out.println("\n\nYou have lost. There are no more moves available. You scored "+score+" points in "+moveCount+" moves.");//message output upon loosing
						noWin=goOn=false;//set loose conditions so the game will either end or restart
					
				}
				}
				while(noWin||goOn);//if the conditions aren't set to their loosing state, restart the move loop so the player can pick another move
				//Determine if the player wants to start a new game or quit the game
				System.out.println("\n\nWould you like to start a new game, if yes please enter y, if not, use any other key.");
				String continueChoice = input.next();//gets the players decision as to whether they want to start over or not
				if (continueChoice.equals(CONTINUECHOICE)||continueChoice.equals(CONTINUECHOICEE))
				{
					restart=true;//sets the games state so that the game will restart with a new board
				}
				else System.out.println("Thank you for playing.");//if the player decides to quit the game this is what will be output prior to program termination
				}
				
				while(restart);
			}//this is the end of the main body of the program
			
			
			
			
			
			// this will be my printer
			public static int[][] print(int[][] board, int moveCount, int score)
			{
				//top of board
				System.out.println("\u2554\u2550\u2550\u2550\u2550\u2564\u2550\u2550\u2550\u2550\u2564\u2550\u2550\u2550\u2550\u2564\u2550\u2550\u2550\u2550\u2557");
				//body of the board. set the spacing of the numbers and then add in the side walls and then add the row separators at the end of each row
				for (int row=0;row<board.length;row++)
				{
					System.out.print("\u2551");
					for (int column=0;column<board[row].length;column++)
					{
					if (board[row][column]==0)
						System.out.print("    ");
						else if(board[row][column]>=1&&board[row][column]<10)
						System.out.print("  "+board[row][column]+" ");
						else if(board[row][column]>=10&&board[row][column]<100)
						System.out.print(" "+board[row][column]+" ");
						else if(board[row][column]>=100&&board[row][column]<1000)
						System.out.print(board[row][column]+" ");
						else
						System.out.print(board[row][column]);
					if (column<3)System.out.print("\u2502");
					if(column==3)
					{
						System.out.print("\u2551\n");
						
					if(row<3)System.out.print("\u255F\u2500\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u2500\u253C\u2500\u2500\u2500\u2500\u2562\n");
						}
					}
			}
				//bottom of board, and the move and score
			System.out.println("\u255A\u2550\u2550\u2550\u2550\u2567\u2550\u2550\u2550\u2550\u2567\u2550\u2550\u2550\u2550\u2567\u2550\u2550\u2550\u2550\u255D");
				System.out.print("Moves: "+moveCount+"		Score:"+score+"     ");
				return board;
					}

			//make test methods for each move choice to determine if the move is do-able or not
			public static boolean checkUp(int[][] board,boolean good)
			{
				boolean onesGood=false;//used to see if a row/column is good or not,if a single one is good then the move is possible
				good=false;//if false the move is bad and will not be performed
				for (int column=0;column<4;column++)
				{
					if (board[0][column]+board[1][column]+board[2][column]+board[3][column]!=0&& !onesGood)//check to see if the row is empty if so ignore it
					{
						if(board[0][column]!=0&&board[1][column]==0&&board[2][column]==0&&board[3][column]==0){onesGood=false;}//if the top number isn't zero and the rest are zero it can't be moved
						else if(board[0][column]!=0&&board[1][column]!=0&&board[2][column]==0&&board[3][column]==0&&board[0][column]!=board[1][column]){onesGood=false;}//if the top two are zero and can't be merged and the rest are zero it can't be moved
						else if(board[0][column]!=0&&board[1][column]!=0&&board[2][column]!=0&&board[3][column]==0&&board[0][column]!=board[1][column]&&board[1][column]!=board[2][column]){onesGood=false;}//if the first three equal zero and none can merge and the last spot is zero it can't move 
						else if(board[0][column]!=0&&board[1][column]!=0&&board[2][column]!=0&&board[3][column]!=0&&board[0][column]!=board[1][column]&&board[1][column]!=board[2][column]&&board[2][column]!=board[3][column]){onesGood=false;}//if none of the spots are zero and none can be merged the move can't be made
						else {onesGood=true;}//if it passes all of those conditions then that row can be moved
					}
				}
				if(onesGood)//the option was found to be good so the move is a do-able move and can be made
					{good=true;}//it is a legal move so the move can be made
				return good;
			}
			public static boolean checkDown(int[][] board,boolean good)
			{
				boolean onesGood=false;
				good=false;
				for (int column=0;column<4;column++)
				{
					if (board[0][column]+board[1][column]+board[2][column]+board[3][column]!=0&& !onesGood)
					{
						if(board[3][column]!=0&&board[2][column]==0&&board[1][column]==0&&board[0][column]==0){onesGood=false;}
						else if(board[3][column]!=0&&board[2][column]!=0&&board[1][column]==0&&board[0][column]==0&&board[3][column]!=board[2][column]){onesGood=false;}
						else if(board[3][column]!=0&&board[2][column]!=0&&board[1][column]!=0&&board[0][column]==0&&board[3][column]!=board[2][column]&&board[2][column]!=board[1][column]){onesGood=false;}
						else if(board[3][column]!=0&&board[2][column]!=0&&board[1][column]!=0&&board[0][column]!=0&&board[3][column]!=board[2][column]&&board[2][column]!=board[1][column]&&board[1][column]!=board[0][column]){onesGood=false;}
						else {onesGood=true;}
					}
				}
				if(onesGood)
					{good=true;}
				return good;
			}
			public static boolean checkLeft(int[][] board,boolean good)
			{
				boolean onesGood=false;
				good=false;
				for (int row=0;row<4;row++)
				{
					if (board[row][0]+board[row][1]+board[row][2]+board[row][3]!=0&& !onesGood)
					{
						if(board[row][0]!=0&&board[row][1]==0&&board[row][2]==0&&board[row][3]==0){onesGood=false;}
						else if(board[row][0]!=0&&board[row][1]!=0&&board[row][2]==0&&board[row][3]==0&&board[row][0]!=board[row][1]){onesGood=false;}
						else if(board[row][0]!=0&&board[row][1]!=0&&board[row][2]!=0&&board[row][3]==0&&board[row][0]!=board[row][1]&&board[row][1]!=board[row][2]){onesGood=false;}
						else if(board[row][0]!=0&&board[row][1]!=0&&board[row][2]!=0&&board[row][3]!=0&&board[row][0]!=board[row][1]&&board[row][1]!=board[row][2]&&board[row][2]!=board[row][3]){onesGood=false;}
						else {onesGood=true;}
					}
				}
				if(onesGood)
					{good=true;}
				return good;
			}
			public static boolean checkRight(int[][] board,boolean good)
			{
				boolean onesGood=false;
				good=false;
				for (int row=0;row<4;row++)
				{
					if (board[row][0]+board[row][1]+board[row][2]+board[row][3]!=0&& !onesGood)
					{
						if(board[row][3]!=0&&board[row][2]==0&&board[row][1]==0&&board[row][0]==0){onesGood=false;}
						else if(board[row][3]!=0&&board[row][2]!=0&&board[row][1]==0&&board[row][0]==0&&board[row][3]!=board[row][2]){onesGood=false;}
						else if(board[row][3]!=0&&board[row][2]!=0&&board[row][1]!=0&&board[row][0]==0&&board[row][3]!=board[row][2]&&board[row][2]!=board[row][1]){onesGood=false;}
						else if(board[row][3]!=0&&board[row][2]!=0&&board[row][1]!=0&&board[row][0]!=0&&board[row][3]!=board[row][2]&&board[row][2]!=board[row][1]&&board[row][1]!=board[row][0]){onesGood=false;}
						else {onesGood=true;}
					}
				}
				if(onesGood)
					{good=true;}
				return good;
			}
			
			// make a grouping of methods to get the graph to go in the chosen direction
			public static int[][] up(int[][] board)
			{
				for (int column=0;column<4;column++)//go across each column
				{
					if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)//if the column has nothing in it this checks so no time is wasted
					{//the following will move the number up one space until the column has no empty spaces on the side specified
					while (board[0][column]==0)//while the top spot is empty move the numbers up until it is filled and then fill the bottom with zero
					{
						board[0][column]=board[1][column];
						board[1][column]=board[2][column];
						board[2][column]=board[3][column];
						board[3][column]=0;
					}
					if (board[1][column]==0)//if the second spot is empty move the numbers up and fill the bottom with zero
					{
						board[1][column]=board[2][column];
						board[2][column]=board[3][column];
						board[3][column]=0;
					}
					if (board[1][column]==0)//this does the same as the previous "if" does, but since it wouldn't work in a while loop, i got it working like this
					{
						board[1][column]=board[2][column];
						board[2][column]=board[3][column];
						board[3][column]=0;
					}
					if (board[2][column]==0)//if the second to last spot is empty move the bottom up to it and fill the bottom with zero
					{
						board[2][column]=board[3][column];
						board[3][column]=0;
					}
					}
				}
				return board;
			}
			public static int[][] down(int[][] board)
			{
				for (int column=0;column<4;column++)
				{
					if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)
					{
					while (board[3][column]==0)
					{
						board[3][column]=board[2][column];
						board[2][column]=board[1][column];
						board[1][column]=board[0][column];
						board[0][column]=0;
					}
					if (board[2][column]==0)
					{
						board[2][column]=board[1][column];
						board[1][column]=board[0][column];
						board[0][column]=0;
					}
					if (board[2][column]==0)
					{
						board[2][column]=board[1][column];
						board[1][column]=board[0][column];
						board[0][column]=0;
					}
					if (board[1][column]==0)
					{
						board[1][column]=board[0][column];
						board[0][column]=0;
					}
					}
				}
				return board;
				
			}
			public static int[][] left(int[][] board)
			{
				/*set the first number in the row equal to i and the second number to j(i+1)
				 * check to see if i=o or if j=i
				 * if either of those are true then add j to i's spot and set j to 0 else move i to the next number
				 * this process should need to be done 3 times if im correct*/
				for (int row=0;row<4;row++)
				{
					if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
					{
					while (board[row][0]==0)
					{
						board[row][0]=board[row][1];
						board[row][1]=board[row][2];
						board[row][2]=board[row][3];
						board[row][3]=0;
					}
					if (board[row][1]==0)
					{
						board[row][1]=board[row][2];
						board[row][2]=board[row][3];
						board[row][3]=0;
					}
					if (board[row][1]==0)
					{
						board[row][1]=board[row][2];
						board[row][2]=board[row][3];
						board[row][3]=0;
					}
					if (board[row][2]==0)
					{
						board[row][2]=board[row][3];
						board[row][3]=0;
					}
					}
				}
				return board;
			}
			public static int[][] right(int[][] board)
			{
				//same as left just start it out on the far side of the array
				for (int row=0;row<4;row++)
				{
					if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
					{
					while (board[row][3]==0)
					{
						board[row][3]=board[row][2];
						board[row][2]=board[row][1];
						board[row][1]=board[row][0];
						board[row][0]=0;
					}
					if (board[row][2]==0)
					{
						board[row][2]=board[row][1];
						board[row][1]=board[row][0];
						board[row][0]=0;
					}
					if (board[row][2]==0)
					{
						board[row][2]=board[row][1];
						board[row][1]=board[row][0];
						board[row][0]=0;
					}
					if (board[row][1]==0)
					{
						board[row][1]=board[row][0];
						board[row][0]=0;
					}
					}
				}
				return board;
			}
			
			//after moving all of the spaces together the next 4 methods will be for combining the numbers together
			public static int[][] upCombo(int[][] board)
			{
			for (int column=0;column<4;column++)
			{
				if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)//ignore if empty
				{
					if (board[0][column]==board[1][column])//if first two are the same merge
					{
						board[0][column]+=board[1][column];
						board[1][column]=0;
						if(board[2][column]==board[3][column])//if first two were the same and the last two are the same merge
						{
							board[2][column]+=board[3][column];
							board[3][column]=0;
						}
						
					}
					else if(board[1][column]==board[2][column])//if middle two are the same merge
					{
						board[1][column]+=board[2][column];
						board[2][column]=0;
					}
					else if(board[2][column]==board[3][column])//if last two are the same merge
					{
						board[2][column]+=board[3][column];
						board[3][column]=0;
					}
				}
			}
			up(board);//push all of the numbers together so theres no empty spaces
			return board;
			}
			public static int[][] downCombo(int[][] board)
			{
			for (int column=0;column<4;column++)
			{
				if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)
				{
					if (board[3][column]==board[2][column])
					{
						board[3][column]+=board[2][column];
						board[2][column]=0;
						if(board[1][column]==board[0][column])
						{
							board[1][column]+=board[0][column];
							board[0][column]=0;
						}
						
					}
					else if(board[2][column]==board[1][column])
					{
						board[2][column]+=board[1][column];
						board[1][column]=0;
					}
					else if(board[1][column]==board[0][column])
					{
						board[1][column]+=board[0][column];
						board[0][column]=0;
					}
				}
			}
			down(board);
			return board;
			}
			public static int[][] leftCombo(int[][] board)
			{
			for (int row=0;row<4;row++)
			{
				if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
				{
					if (board[row][0]==board[row][1])
					{
						board[row][0]+=board[row][1];
						board[row][1]=0;
						if(board[row][2]==board[row][3])
						{
							board[row][2]+=board[row][3];
							board[row][3]=0;
						}
						
					}
					else if(board[row][1]==board[row][2])
					{
						board[row][1]+=board[row][2];
						board[row][2]=0;
					}
					else if(board[row][2]==board[row][3])
					{
						board[row][2]+=board[row][3];
						board[row][3]=0;
					}
				}
			}
			left(board);
			return board;
			}
			public static int[][] rightCombo(int[][] board)
			{
			for (int row=0;row<4;row++)
			{
				if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
				{
					if (board[row][3]==board[row][2])
					{
						board[row][3]+=board[row][2];
						board[row][2]=0;
						if(board[row][1]==board[row][0])
						{
							board[row][1]+=board[row][0];
							board[row][0]=0;
						}
						
					}
					else if(board[row][2]==board[row][1])
					{
						board[row][2]+=board[row][1];
						board[row][1]=0;
					}
					else if(board[row][1]==board[row][0])
					{
						board[row][1]+=board[row][0];
						board[row][0]=0;
					}
				}
			}
			right(board);
			return board;
			}
			
			//after doing the move and before performing the merge add up all of the spots that will merge and add the numbers to the score
			public static int upScore(int[][]board,int score)
			{
				for (int column=0;column<4;column++)
				{
					if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)//ignore if empty
					{
						if (board[0][column]==board[1][column])//if the top two are the same add twice the number in that position to the score
						{
							score=score+2*board[0][column];
							if(board[2][column]==board[3][column])//if the top two were the same and the last two are the same add twice the number that the second set is to the score
								score=score+2*board[2][column];
							
						}
						else if(board[1][column]==board[2][column])//if the middle two numbers are the same add double the number to the score
							score=score+2*board[1][column];
						else if(board[2][column]==board[3][column])//if the last two are the same add two times the number to the score
							score=score+2*board[2][column];
					}
				}
				return score;
			}
			public static int downScore(int[][] board,int score)
			{
			for (int column=0;column<4;column++)
			{
				if (board[0][column]+board[1][column]+board[2][column]+board[3][column] != 0)
				{
					if (board[3][column]==board[2][column])
					{
						score=score+2*board[3][column];
						if(board[1][column]==board[0][column])
							score=score+2*board[1][column];
					}
					else if(board[2][column]==board[1][column])
						score=score+2*board[2][column];
					else if(board[1][column]==board[0][column])
						score=score+2*board[1][column];
				}
			}
			return score;
			}
			public static int leftScore(int[][] board,int score)
			{
			for (int row=0;row<4;row++)
			{
				if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
				{
					if (board[row][0]==board[row][1])
					{
						score=score+2*board[row][0];
						if(board[row][2]==board[row][3])
							score=score+2*board[row][2];
					}
					else if(board[row][1]==board[row][2])
						score=score+2*board[row][1];
					else if(board[row][2]==board[row][3])
						score=score+2*board[row][2];
				}
			}
			return score;
			}
			public static int rightScore(int[][] board,int score)

			{
			for (int row=0;row<4;row++)
			{
				if (board[row][0]+board[row][1]+board[row][2]+board[row][3] != 0)
				{
					if (board[row][3]==board[row][2])
					{
						score=score+2*board[row][3];
						if(board[row][1]==board[row][0])
							score=score+2*board[row][1];
					}
					else if(board[row][2]==board[row][1])
						score=score+2*board[row][2];
					else if(board[row][1]==board[row][0])
						score=score+2*board[row][1];
				}
			}

			return score;
			}
			
			//the following method will be used to put the board into the backup board so that way a undo can be performed
			public static int[][] undoBoard(int[][]board,int[][]backBoard)
			{
				for (int row=0;row<board.length;row++)
				{
					for (int column=0;column<board[row].length;column++)
					{
						backBoard[row][column]=board[row][column];//puts old values into backup board for undo
					}
				}
				return backBoard;
			}
			
			//this method will be used in undoing to set the board back to its prior state
			public static int[][] setBoard(int[][]board,int[][]backBoard)
			{
				for (int row=0;row<board.length;row++)
				{
					for (int column=0;column<board[row].length;column++)
					{
						board[row][column]=backBoard[row][column];//puts the old board back into the board
					}
				}
				return board;
			}
			
			//the following method will be used to get the player assistance
			public static void help(int[][] board,boolean good)
			{
				boolean leave=true;//if this is false you leave the menu
				System.out.println("Welcome to the help menu if you need help please press the number that relates to"+
						" what you need help with or enter L to leave the menu\n");
				do
				{
				Scanner input = new Scanner(System.in);
				System.out.println("1.)How do I win the game?\n2.)How does the board move?\n3.)I need a hint\nL.)Leave menu");
				//offer the player the choice to learn how the board moves, how the score and winning works, and offer them a hint
				String help = input.next();
				if (help.equals("1"))//give the player a quick run through on how to win the game
				{
					System.out.println("Winning:\nIn the game of 2048, the players goal is to shift the board until they get any of the "+
					"spaces to contain the number 2048.\n Once a space contains 2048 the player will have won the game and will be able to enter"
					+ " into over time and continue to play until they can\n no longer move.");
				}
				else if (help.equals("2"))//give the player a run through on how the board moves around
				{
					System.out.println("Movement:\n The player is offered four different options to move the board; U for up, D for down"
							+ ",L for left, and R for right.\n After entering any of the following commands, if the move is legal, all the numbers in the board"
							+ " will shift in the chosen direction.\n When two of the same number are pushed together they will merge and become one cell"
							+ " that is the sum of the two numbers.");
				}
				else if (help.equals("3"))//test all of the possible directions the player can move if they can move in a direction tell them so
						{
					System.out.println("Hint:\n Currently you have the following move(s) available:\n");
					if(checkUp(board,good))System.out.println("UP");
					if(checkDown(board,good))System.out.println("DOWN");
					if(checkLeft(board,good))System.out.println("LEFT");
					if(checkRight(board,good))System.out.println("RIGHT");
					System.out.println();
						}
				else if (help.equals("L")||help.equals("l"))leave=false;//leave the help menu
				else System.out.println("That is an invalid command please chose an option from the menu.");
				System.out.println();
				}while(leave);
			
		}
			
			//the following two methods will be utilized to create the random numbers for the moves
			public static int[][] initial2(int[][]board)
			{
				RandomDig(board);RandomDig(board);//set the initial two numbers
				return board;
			}
			public static int[][] RandomDig(int[][]board)
			{
				int h=0;//h is going to stand for the height of the new array and be used to find a valid location for the random number
				for(int row=0;row<board.length;row++)//determines number of empty slots, and makes an array that will hold each positions coordinates
				{
					for(int column=0; column<board[row].length;column++)
					{
						if(board[row][column]==0)
						{
							++h;
						}
					}
				}
					int empty[][]= new int[h][2];//create the new array
					h=0;
				for(int row=0;row<board.length;row++)//for each empty position get its location and put it into the next row of the new array
					{
						for(int column=0; column<board[row].length;column++)
						{
							if(board[row][column]==0)
							{
								empty[h][0]=row;
								empty[h][1]=column;
								++h;
							}
							if(row==board.length-1&&column==board[row].length-1)
							{
							h=(int)(Math.random()*h);
							int emptyrow=empty[h][0],emptycolumn=empty[h][1];//determine which spot to be filled in the board
							final double TWOORFOUR=(Math.random());//determine whether a 2 or a 4 will be added
							int theNumber;
							if (TWOORFOUR>.9)
								{
								theNumber=4;
								}
							else
							{
								theNumber=2;
							}
								
							board[emptyrow][emptycolumn]=theNumber;//set the random location equal to the selected number
							}
			}
			}
				return board;
			}
			
			//if the player can not make anymore moves inform them of such using this
			public static boolean testLoose(int[][] board, boolean good, boolean noMoves)
			{
				if(!checkUp(board,good)&&!checkDown(board,good)&&!checkLeft(board,good)&&!checkRight(board,good))//check every direction to see if its possible to make any moves
				{
					noMoves=false;//if the play has no available moves at all set noMoves to false so the game can then set the conditions to a state that will end the game
				}
				return noMoves;
			}
		}

