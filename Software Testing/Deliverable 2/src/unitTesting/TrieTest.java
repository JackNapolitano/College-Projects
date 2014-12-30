package unitTesting;

import static org.junit.Assert.*;
import org.mockito.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class TrieTest {

	
	@Mock
    Trie mockedTrie1 = Mockito.mock(Trie.class);
    Trie mockedTrie2 = Mockito.mock(Trie.class);
    Trie mockedTrie3 = Mockito.mock(Trie.class);
    Trie mockedTrie4 = Mockito.mock(Trie.class);

    
    

    
    @Before
    public void setup() throws Exception {
		MockitoAnnotations.initMocks(mockedTrie1);
		
		
		 //Stubs
        Mockito.when(mockedTrie2.add(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedTrie3.search(Mockito.anyString())).thenReturn(true);
        Mockito.when(mockedTrie4.checkPrefix(Mockito.anyString())).thenReturn(true);

    }
	
    
    
    
    
    
    
    //------------------------mock tests------------------------------------
	// mocked -- adding the same word to the trie returns false and does not add dupe
	@Test
	public void mockTestDuplicate(){
		mockedTrie1.add("string");
		assertFalse(mockedTrie1.add("string"));
	}
	
	//See if mockedTrie is equal to new trie
	@Test
	public void testTrieEqualDiffTrie(){
		Trie t1 = new Trie();
		assertSame(t1, mockedTrie1);
	}

	//Here we create one Trie and see if it is equal to itself.
	@Test
	public void testIfTrieEqualSelf(){
		assertEquals(mockedTrie1, mockedTrie1);
	}
	//--------------------end mock tests-------------------------
	
	//--------------------Stub tests ----------------------------
	//first stub test - add string to trie
	@Test
	public void addStringWithStub(){
		assertTrue(mockedTrie2.add("Stub"));
	}
	//search for a string - stub test
	@Test
	public void searchStringWithStub(){
		mockedTrie3.add("Stub");
		assertTrue(mockedTrie3.search("Stub"));
	}
	//Check for a prefix of a string - stub test
	@Test
	public void checkPrefixStringWithStub(){
		mockedTrie4.add("Stub");
		assertTrue(mockedTrie4.checkPrefix("St"));
	}
	//------------------------end stub tests--------------------------
	
	
	//Search for a word that is not in the trie
	@Test
	public void searchStringNotInTrie(){
		Trie t1 = new Trie();
		assertFalse(t1.search("BillLaboon"));
	}
	
	//Create two tries, see if the value contained in root is equal for both
	@Test
	public void testRootValues(){
		Trie t1 = new Trie();
		Trie t2 = new Trie();
		assertSame(t1.root.value, t2.root.value);
	}
	
	//Create Trie, test if first child = null
	@Test
	public void testFirstChileEqualNull(){
		Trie t1 = new Trie();
		Trie.Node firstChild = t1.root.firstChild;
		assertNull(firstChild);
		
	}
	
	//Create Trie, test if root = self
	@Test
	public void testRootEqualNull(){
		Trie t1 = new Trie();
		Trie.Node root = t1.root;
		assertEquals(root, root);		
	}
	
	//Add word to Trie, See if True is returned (successful add)
	@Test
	public void addWordSuccess(){
		Trie t1 = new Trie();
		assertTrue(t1.add("test"));
	}
	
	//Search for word in Trie, Return to if found
	@Test
	public void searchWordSuccess(){
		Trie t1 = new Trie();
		t1.add("test");
		assertTrue(t1.search("test"));
	}
	
	//Add a word, made sure first child of root is not null
	@Test
	public void firstChildNotNull(){
		Trie t1 = new Trie();
		t1.add("test");
		assertNotNull(t1.root.firstChild);
	}
	
	//Add the same word to two different Tries, see if first child value is the same
	@Test
	public void compareTwoFirstChildren(){
		Trie t1 = new Trie();
		Trie t2 = new Trie();
		t1.add("test");
		t2.add("test");
		assertEquals(t1.root.firstChild.value, t2.root.firstChild.value);
	}
	
	//add the same word to two different Tries, see if searching both returns same value.. Subtle joke within
	@Test
	public void twoTriesOneWord(){
		Trie t1 = new Trie();
		Trie t2 = new Trie();
		t1.add("cup");
		t2.add("cup");
		assertEquals(t1.search("cup"), t2.search("cup"));
	}
	
	//Add a word, call checkPrefix method to see if prefix is in the trie
	@Test
	public void testPrefix(){
		Trie t1 = new Trie();
		t1.add("billlaboon");
		assertTrue(t1.checkPrefix("bill"));
	}
	
	//Add a word that has a smaller word within it. ex: programmer also contains program
	//Then do search on the word "program", should be false
	//also do checkPrefix on "program"
	//Will assertNotSame
	//Add a word, call checkPrefix method to see if prefix is in the trie
	@Test
	public void prefixButNotWord(){
		Trie t1 = new Trie();
		t1.add("programmer");
		assertNotSame(t1.checkPrefix("program"), t1.search("program"));
	}
	
	//add a word and make sure checkPrefix returns true
	@Test
	public void notPrefixYesWord(){
		Trie t1 = new Trie();
		t1.add("program");
		assertTrue(t1.checkPrefix("program"));
	}
	
	//Add a word and search for a prefix of that word, but using the search function. should return false
	@Test
	public void searchValidPrefixWithSearch(){
		Trie t1 = new Trie();
		t1.add("programmer");
		assertFalse(t1.search("program"));
	}
	
	//Sibling Test.. Here I add the word test and quest. I go to the first child which will
	//be the node that contains the letter "t". Then I check to see if there exists node. There
	//Should exist a sibling node that contains the letter "q". Thus is should return true.
	@Test
	public void siblingTest(){
		Trie t1 = new Trie();
		t1.add("test");
		t1.add("quest");
		assertNotNull(t1.root.firstChild.nextSibling);
	}
	
	//make sure the end of the string is null
	@Test
	public void endStringNull(){
		Trie t1 = new Trie();
		t1.add("bill");
		assertNull(t1.root.firstChild.firstChild.firstChild.firstChild.firstChild.firstChild);
	}
	
	//Value of root ("r") is greater than the value of "a"
	@Test
	public void valueOfRoot(){
		Trie t1 = new Trie();
		t1.add("a");
		assertTrue(t1.root.firstChild.value < t1.root.value);
	}
	
	//add super long string with spaces to Trie
	@Test
	public void longWordInTrie(){
		Trie t1 = new Trie();
		assertFalse(t1.add("How This Killer Lover Received Her Company Might Just Make This World A Better Place This Driver Was Just Hiking In Costa Rica. I Wish That Didn't Scare Me"));
	}
	
	
	//Sibling Test.. Here I add the word toast and tut. I go to the first child which will
	//be the node that contains the letter "t". Then I will go to that nodes child which is
	//The node that contains the letter o from toast. It should have a sibling "u" on the same level
	@Test
	public void siblingTestTwoDown(){
		Trie t1 = new Trie();
		t1.add("toast");
		t1.add("tut");
		assertNotNull(t1.root.firstChild.firstChild.nextSibling);
	}
	
	//Add an uppercase word and search for that word in lowercase
	@Test
	public void caseInsensitiveSearch(){
		Trie t1 = new Trie();
		t1.add("HELLO");
		assertTrue(t1.search("hello"));
	}
	
	//Add an escape character to the Trie
	@Test
	public void addEscapeKey(){
		Trie t1 = new Trie();
		assertFalse(t1.add("\n"));
		
	}
	
	//Add an invalid input into the trie. Should (but apparently doesn't) return False from add method
	@Test
	public void addNumber(){
		Trie t1 = new Trie();
		assertFalse(t1.add("2"));
	}
	
	//Add a bunch of characters to the Trie. SHOULD (but apparently doesn't) return false from add
	@Test
	public void addChars(){
		Trie t1 = new Trie();
		assertFalse(t1.add("!@#$%^&*()"));
	}
	
	//Search for prefix that does not exist in Trie
	@Test
	public void noPrefix(){
		Trie t1 = new Trie();
		t1.add("hello");
		assertFalse(t1.checkPrefix("BLOOP"));
	}
	
	//Set trie 1 equal to another trie 2
	@Test
	public void setT1toT2(){
		Trie t1 = new Trie();
		Trie t2 = t1;
		t2.add("Word");
		t2.add("Wordy");
		t1.add("Words");
		assertEquals(t1, t2);
	}
	
	
	
	
	
}