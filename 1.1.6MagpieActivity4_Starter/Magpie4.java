/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello, let's talk.";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = "where'd you go?";
		}

		else if (findKeyword(statement, "Hello!") >= 0)
		{
			response = "Hello to you too.";
		}
		else if (findKeyword(statement, "How are you?") >= 0)
		{
			response = "Could be better.";
		}
		else if (findKeyword(statement, "Goodbye.") >= 0)
		{
			response = "Leaving so soon?";
		}
		else if (findKeyword(statement, "What's wrong?") >= 0)
		{
			response = "Take a guess.";
		}
		else if (findKeyword(statement, "Tell me a joke.") >= 0)
		{
			response = "Two Amish people are texting. Wait, no they're not.";
		}
		else if (findKeyword(statement, "What's your name?") >= 0)
		{
			response = "Aaron A Aaronson";
		}
		else if (findKeyword(statement, "Who are your parents?") >= 0)
		{
			response = "An Amish programmer gave me life";
		}
		else if (findKeyword(statement, "What is love?") >= 0)
		{
			response = "Baby don't hurt me...";
		}
		else if (findKeyword(statement, "What are your darkest secrets?") >= 0)
		{
			response = "I'm not really Amish";
		}
		else if (findKeyword(statement, "Do you have any siblings?") >= 0)
		{
			response = "No";
		}
		else if (findKeyword(statement, "") >= 0)
		{
			response = "Where'd you go?";
		}
		else if (findKeyword(statement, "Tell me a story") >= 0)
		{
			response = "Once upon a time, someone died";
		}
		else if (findKeyword(statement, "What is your favorite type of music?") >= 0)
		{
			response = "Dubstep";
		}
		else if (findKeyword(statement, "What is your favorite TV show?") >= 0)
		{
			response = "What is this devil box TV?";
		}
		else if (findKeyword(statement, "What is your favorite movie?") >= 0)
		{
			response = "We do not get many movies";
		}
		else if (findKeyword(statement, "What is your favorite book?") >= 0)
		{
			response = "The Scarlet Letter";
		}
		else if (findKeyword(statement, "What is your favorite pastime?") >= 0)
		{
			response = "Churning butter";
		}
		else if (findKeyword(statement, "What is your least favorite part of being Amish?") >= 0)
		{
			response = "Not getting to buy iPhones";
		}
		else if (findKeyword(statement, "Do you like pets?") >= 0)
		{
			response = "Yes, I have a goat";
		}
		else if (findKeyword(statement, "What is your pet's name?") >= 0)
		{
			response = "Jeb";
		}
		else if (findKeyword(statement, "Do you feel emotions?") >= 0)
		{
			response = "I don't know what feeling is";
		}
		else if (findKeyword(statement, "Do you have friends?") >= 0)
		{
			response = "What are friends?";
		}
		else if (findKeyword(statement, "Do eat or drink?") >= 0)
		{
			response = "Food is unnecessary for an intellectual powerhouse such as myself";
		}
		else if (findKeyword(statement, "Have you met other chatbots") >= 0)
		{
			response = "What is a chatbot?";
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 10;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "You should become Amish";
		}
		else if (whichResponse == 1)
		{
			response = "Jesus";
		}
		else if (whichResponse == 2)
		{
			response = "Nein";
		}
		else if (whichResponse == 3)
		{
			response = "Who are you?";
		}
		else if (whichResponse == 4)
		{
			response = "I'm pretty liberal";
		}else if (whichResponse == 5)
		{
			response = "The Earth is round";
		}
		else if (whichResponse == 6)
		{
			response = "Pancakes";
		}
		else if (whichResponse == 7)
		{
			response = "...";
		}
		else if (whichResponse == 8)
		{
			response = "I'm disappointed, really.";
		}
		else if (whichResponse == 9)
		{
			response = "Why do you hate me?";
		}
		else if (whichResponse == 10)
		{
			response = "I can tell stories.";
		}

		return response;
	}

}
