package unitTesting;

public class Trie
{
  
    public final static char ENDOFWORD = '\u0001';


    public Trie()
    {
        root = new Node('r');
    }
     
    public class Node
    {
        public int value;
        public Node firstChild;
        public Node nextSibling;

        public Node(int value)
        {
            this.value = value;
            firstChild = null;
            nextSibling = null;
        }
    }

    public boolean add(String word)
    {
        return addRE(root, word + ENDOFWORD, 0);
    }
    private boolean addRE(Node root, String word, int wordIndex)
    {
        if (wordIndex == word.length())
            return false;
        int c = word.charAt(wordIndex);
        Node last = null;
        Node next = root.firstChild;

        //this while loop recuseivly finds the starting point of the word. then parses through the trie to see if the word/ or a prefix of the word, has already been added.
        while (next != null)
        {
            if (next.value < c)
            {
                last = next;
                next = next.nextSibling;
            }
            else if (next.value == c)
            {
                return addRE(next, word, wordIndex + 1);
            }
            else break;
        }
        Node node = new Node(c);
        if (last == null)
        {
            root.firstChild = node;
            node.nextSibling = next;
        }
        else
        {
            last.nextSibling = node;
            node.nextSibling = next;
        }
        for (int i = wordIndex + 1; i < word.length(); i++)
        {
            node.firstChild = new Node(word.charAt(i));
            node = node.firstChild;
        }
        return true;
    }

    public boolean search(String word)
    {
        return searchRE(root, word + ENDOFWORD, 0);
    }
    private boolean searchRE(Node root, String word, int wordIndex)
    {
        if (wordIndex == word.length())
            return true;
        int c = word.charAt(wordIndex);
        Node next = root.firstChild;
        while (next != null)
        {
            if (next.value < c) next = next.nextSibling;
            else if (next.value == c) return searchRE(next, word, wordIndex + 1);
            else return false;
        }
        return false;
    }
    
    public boolean checkPrefix(String prefix)
    {
        return checkPrefixRE(root, prefix, 0);
    }
    private boolean checkPrefixRE(Node root, String word, int wordIndex)
    {
        if (wordIndex == word.length())
        {
            return true;
        }
        int c = word.charAt(wordIndex);
        Node next = root.firstChild;
        while (next != null)
        {
            if (next.value < c) next = next.nextSibling;
            else if (next.value == c) return checkPrefixRE(next, word, wordIndex + 1);
            else break;
        }
        return false;
    }   

    public Node root;
    private boolean ignoreCase;

}   