package a2;

import java.util.Arrays;

/** NetId: gec83, nnnnn.
 * What I thought about this assignment:
 *
 */

/** A collection of static functions. <br>
 * All methods assume that String parameters are non-null.
 *
 * If a method is called with arguments that do not satisfy the Preconditions,<br>
 * the behavior is undefined (the method can do anything). You do not have to use assert<br>
 * statements to test preconditions. We will not test with test cases that do <br>
 * not satisfy Preconditions. */
public class A2 {
	/* Each function you write has a "//TODO comment". Look on the right; click a blue
	 * rectangle to get to the corresponding "//TODO comment". DO NOT DELETE THESE COMMENTS.
	 *
	 * Wherever possible, prefer library functions to writing your own loops.
	 *
	 * The more complicated your loops become, the more important it is to
	 * explain the logic in comments.
	 *
	 * See the JavaHyperText entries for if-statement, while-loop, and for-loop.
	 * Use of the break-statement and continue-statement is discouraged but not
	 * forbidden. They make loops and programs harder to understand. Usually,
	 * they can be eliminated by restructuring/reorganizing code --perhaps writing
	 * extra methods.
	 *
	 * For some functions, you may be writing a loop to append character after
	 * character to an initially empty string. See the JavaHyperText entry for
	 * class StringBuilder and a discussion of why it may be better to use that
	 * class for this purpose. But for this assignment, use either String or
	 * StringBuilder, it doesn't matter which you use.
	 *
	 * We give complete test cases except for the last two methods. You need practice
	 * in thinking about how to test well.
	 *  */

	/** Replace "-1" by the time you spent on A2 in hours.<br>
	 * Example: for 3 hours 15 minutes, use 3.25<br>
	 * Example: for 4 hours 30 minutes, use 4.50<br>
	 * Example: for 5 hours, use 5 or 5.0 */
	public static double timeSpent= -1;

	/** Return either s1 + s2 or s1 - s2, depending on b. <br>
	 * If b is true, return the sum, otherwise return the difference. */
	public static int sumDif(boolean b, int s1, int s2) {
		// This method is already implemented; it is here to
		// show you different ways of writing simple code.
		if (b) {
			int s;
			s= s1 + s2;
			return s;

			/* equivalently: int s = s1 + s2; return s;
			 *
			 * or simply: return s1 + s2;
			 */
		}

		// b is false;
		return s1 - s2;
	}

	/** Return true iff (i.e. if and only if) the middle characters of s are the same. <br>
	 * Note: If s has an odd number of chars, there is ONE middle char, so return true.<br>
	 * If s has an even number of chars, there are two middle chars, so return true only if they are
	 * the same.<br>
	 * Please look at the examples: <br>
	 * For s = "" return true <br>
	 * For s = "$" return true <br>
	 * For s = "23" return false <br>
	 * For s = "44" return true <br>
	 * For s = "22AB" return false <br>
	 * For s = "2AAB" return true <br>
	 * For s = "abcdefaabcdefg" return true <br>
	 * For s = "abcdef$abcdefg" return false <br>
	 * For s = "aaaaaaaaaaaaaaaa" return true <br>
	 * For s = "aaaaaaa$aaaaaaaa" return false<br>
	 * For s = "aaaaaaa$aaaaaaaaa" return true */
	public static boolean isMidSame(String s) {
		// TODO 1. Do not use a loop.
		// This can be done cleanly in 4 statements (but you can use more).
		// Hint: Follow these Principles:
		// Principle: Avoid unnecessary case analysis
		// Principle: Avoid the same expression in several places.
		// Principle: Keep the structure of the method as simple as possible.
		
		if (s.length() % 2 != 0) {
			return true;
		} else {
			if (s.charAt(s.length() / 2 - 1) == s.charAt(s.length() / 2)) {
				return true;
			} 
		}
		return false;

		//throw new UnsupportedOperationException();

	}

	/** Protect the letters in 'a'..'z' by surrounding them with the corresponding capital letters.
	 * That is: Return a copy of string s but with all letters in 'a'..'z' surrounded by the
	 * corresponding upper-case letter. <br>
	 * Examples: <br>
	 * For s = "", return "". <br>
	 * For s = "b", return "BbB". <br>
	 * For s = "B", return "B". <br>
	 * For s = "å", return "å" <br>
	 * For s = "$", return "$" <br>
	 * For s = "1ABCDEFx", return "1ABCDEFXxX".<br>
	 * For s = "1Z$Bby", return "1Z$BBbBYyY"<br>
	 * For s = "abcdefghi", <br>
	 * ......... return "AaABbBCcCDdDEeEFfFGgGHhHIiI" */
	public static String protectLittles(String s) {
		/* TODO 2. Look at the fourth example, for s = "å".
		 * 'å' is NOT a character in a..z, so it is NOT surrounded by
		 * a corresponding upper-case letter.
		 * If this isn't working for you, you may be using Eclipse on a
		 * Windows 10 computer, and the wrong Text File coding is being used.
		 * It should be UTF-8. You have to change it.
		 * Look at the last item on this JavaHyperText webpage to see how to do it:
		 *   https://www.cs.cornell.edu/courses/JavaAndDS/eclipse/Ecl01eclipse.html .
		 */
		
		String alph = "abcdefghijklmnopqrstuvwxyz";
		
		for(int i = 0; i < s.length(); i++) {
			String letter = String.valueOf(s.charAt(i));
			if(alph.contains(letter)) {
				String upper = letter.toUpperCase();
				s = s.substring(0,i) + upper + letter + upper + s.substring(i + 1);
			}
		}
		
		return s;
		
		//throw new UnsupportedOperationException();
	}

	/** Return s but with all letters in 'a'..'z' moved to the front, and in the same order.<br>
	 * Examples: <br>
	 * putLittlesFirst("") = "" <br>
	 * putLittlesFirst("$") = "$" <br>
	 * putLittlesFirst("åç") = "cå" <br>
	 * // Note: 'å' is not in 'a'..'z'. <br>
	 * Examples:<br>
	 * putLittlesFirst("aAbBcCdDxXy$zZ") = "abcdxyzABCDX$Z" <br>
	 * putLittlesFirst("mnopqrst") = "mnopqrst" <br>
	 * putLittlesFirst("1z$aàēĤƀ") = "za1$bàēĤƀ" */
	public static String putLittlesFirst(String s) {
		// TODO 3. The same things about the UTF-8 encoding discussed in
		// the previous method addCapsToSmalls apply here also.
		
		String littles = "";
		String alph = "abcdefghijklmnopqrstuvwxyz";
		
		for(int i = 0; i < s.length(); i++) {
			String letter = String.valueOf(s.charAt(i));
			if(alph.contains(letter)) {
				littles = littles + letter;
				s = littles + s.replace(String.valueOf(s.charAt(i)), "");
			}
		}
		
		return s;
		//throw new UnsupportedOperationException();
	}

	/** Precondition: s and s1 are not null. <br>
	 * Return true iff s contains more than one occurrence of s1. <br>
	 * Examples: For s = "" and s1 = "", return false <br>
	 * For s = "a" and s1 = "", return true: <br>
	 * .... The empty string occurs before and after each character! <br>
	 * For s = "abc" and s1 = "", return true <br>
	 * For s = "" and s1 = "a", return false. <br>
	 * For s = "abcb" and s1 = "c", return false. <br>
	 * For s = "acbc" and s1 = "c", return true. <br>
	 * For s = "abbc" and s1 = "ab", return false. <br>
	 * For s = "aaa" and s1 = "aa", return true. <br>
	 * For s = "abbbabc" and s1 = "ab", return true. */
	public static boolean moreThan1(String s, String s1) {
		// TODO 4 Do not use a loop or recursion. Instead, look through the
		// methods of class String and see how you can tell that the first
		// and last occurrences of s1 in s are the same occurrence. Be sure
		// you handle correctly the case that s1 does not occur in s.
		//
		// Hint: Follow this Principle:
		// Principle: Be aware of efficiency considerations.
		// Don't repeat expensive work that has already been done.
		// Note that a call like s.indexOf(s1) may take time proportional to the
		// length of string s. If s contains 1,000 characters and s1 contains 5 chars,
		// then about 9996 tests may have to be made in the worst case. So you don't
		// want to have the same method call executed several times. Even the call
		// of contains in the code below is wasteful.
		//
		// if (s.contains(s1)) {
		// int k= s.indexOf(s1);
		// ...
		// }
		if(s.indexOf(s1) < 0) {
			return false;
		} else if (s.lastIndexOf(s1) != s.indexOf(s1)) {
			return true;
		} else {
			return false;
		}
		
		//throw new UnsupportedOperationException();
	}

	/** Return true iff s and t are anagrams.<br>
	 * Note: 2 strings are anagrams of each other if swapping the characters<br>
	 * around in one changes it into the other.<br>
	 * Note: 'a' and 'A' are different chars, and the space ' ' is a character.
	 *
	 * Examples: For s = "noon", t = "noon", return true. <br>
	 * For s = "mary", t = "army", return true. <br>
	 * For s = "tom marvolo riddle", t = "i am lordvoldemort", return true. <br>
	 * For s = "tommarvoloriddle", t = "i am lordvoldemort", return false. <br>
	 * For s = "hello", t = "world", return false. */
	public static boolean areAnagrams(String s, String t) {
		// TODO 5
		/* Do not use a loop or recursion! This can be done in
		 * five lines using methods of classes String and Arrays
		 * (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Arrays.html).
		 * Hint: how can a sequence of characters be uniquely ordered? You might
		 * need to first convert the string into an array of characters and then
		 * use methods in class Arrays. */
		char[] sarray = s.toCharArray();
		char[] tarray = t.toCharArray();
		
		Arrays.sort(sarray);
		Arrays.sort(tarray);
		
		return Arrays.equals(sarray, tarray);
		
		//throw new UnsupportedOperationException();
	}

	/** Return true iff s consists of x catenated with itself a number of times.<br>
	 * Examples: <br>
	 * For isCat("", "") return true<br>
	 * For isCat("xxx", "") return false<br>
	 * For isCat("x", "x") return true<br>
	 * For isCat("", "") return true <br>
	 * For isCat("", "x") return true ("" is "x" catenated with itself 0 times)<br>
	 * For isCat("xx", "x") return true <br>
	 * For isCat("ccbbbb", "bb") return false <br>
	 * For isCat("bbbbcc", "bb") return false <br>
	 * For isCat("bbbbbb", "bb") return true <br>
	 * For isCat("bbbbbb", "bbb") return true <br>
	 * For isCat("bbbbbb", "bbbb") return false <br>
	 * For isCat("bbbbbb", "bbbbb") return false <br>
	 * For isCat("bbbbbb", "bbbbbb") return true <br>
	 * For isCat("bbbbbb", "bbbbbbb") return false <br>
	 * For isCat("xyzxyz", "xyz") return true <br>
	 * For isCat("xyzxyz", "xyzxyz") return true <br>
	 * For isCat(s, s) (for any s) return true */
	public static boolean isCat(String s, String x) {
		// TODO 6. Directive. Do NOT create an array of chars simply to make
		// testing easier. Just use the two strings s and x and perhaps a
		// few local variables.
		// Hint: Follow this Principle:
		// Make the structure of a loop reflect the structure of the data it processes.
		// Use function equals, not ==, to test equality of strings.
		
		if(s.replaceAll(x,"").equals("")) {
			return true;
		} else {
			return false;
		}
		
		//throw new UnsupportedOperationException();

	}

	/** Return the shortest substring x of s such that s = x + x + ... + x. <br>
	 * Examples: <br>
	 * For s = "" return ""<br>
	 * For s = "xxxxxxxxx" return "x" <br>
	 * For s = "xyxyxyxy" return "xy" <br>
	 * For s = "012012012012" return "012" <br>
	 * For s = "hellohellohello" return "hello" <br>
	 * For s = "hellohelloworld" return "hellohelloworld" <br>
	 * For s = "hellohell" return "hellohell" */
	public static String findShortest(String s) {
		// TODO 7.
		// 1. To implement this one, start checking for the shortest
		// substring to have length 1, then 2, then 3, and stop when
		// the answer is found. To make each of those checks,
		// use the previous method nCat.
		//
		// 2. If the answer is found within the loop body, it is best to have
		// the method return within the loop body. That is far better than having
		// a break statement and then fiddling after the loop to figure out what
		// to return.

		// 3. Note that isCat(s, s) = 1, for any s.
		
		String repeat = "";
		for (int i = 0; i < s.length(); i++){
			repeat = repeat + s.charAt(i);
			if(s.replaceAll(repeat, "") == "") {
				return repeat;
			}
		}
		return s;
		
		//throw new UnsupportedOperationException();
	}
	
	/// testing
	
	public static void main(String args[]) {
		A2 test = new A2();
		System.out.println(putLittlesFirst("bvg"));
		System.out.println(putLittlesFirst("aAbBcCdDxXy$zZ"));
		System.out.println(putLittlesFirst("1z$aàēĤƀ"));
	}
}
