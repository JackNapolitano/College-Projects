/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int W = 9;         // codeword width
    private static int L = 512;       // number of codewords = 2^W
    private static String mode = "n";
    private static double savedratio = 0;
    private static double num = 0;
    private static double den = 0;
    private static double currentratio = 0;
    private static boolean noRatio = true;


    public static void compress() { 
        if(mode.equals("r"))  BinaryStdOut.write('r', 8);
        if(mode.equals("m"))  BinaryStdOut.write('m', 8);
        if(mode.equals("n"))  BinaryStdOut.write('n', 8);
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {

            L = (int)Math.pow(2, W);
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            num += s.length() * 8;
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            den += W;
            currentratio = num/den;
            int t = s.length();
            if (t < input.length() && code < L) 
            {
                 // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            }
            if( (W < 16) && ( (int)Math.pow(2, W) == code) )
            {
                    W++;
                    L = (int)Math.pow(2, W);
                    st.put(input.substring(0, t + 1), code++);

            }
            if (code == 65536 && mode.equals("r"))
            {
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                code = R+1;  // R is codeword for EOF
                W = 9;
                L = 512;
            }
            if (code == 65536 && mode.equals("m"))
            {
                if(noRatio)
                {
                    savedratio = currentratio;
                    noRatio = false;
                }

                if(savedratio/currentratio > 1.1)
                {
                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                        code = R+1;  // R is codeword for EOF
                    W = 9;
                    L = 512;
                    savedratio = 0;
                    currentratio = 0;
                    noRatio = true;
                }
            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    } 


    public static void expand() {
        char c;
        c = BinaryStdIn.readChar(8);
        if(c == 'r') mode = "r";
        if(c == 'm') mode = "m";
        String[] st = new String[(int)Math.pow(2, 16)];
        int i; // next available codeword value
        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];
        while (true) {
            
            BinaryStdOut.write(val);
            num += val.length() * 8;
            codeword = BinaryStdIn.readInt(W);
            den += W;
            currentratio = num/den;
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L-1) st[i++] = val + s.charAt(0);

            if(i == L-1 && W < 16)
            {
                st[i++] = val + s.charAt(0);
                W++;
                L = (int)Math.pow(2, W);
            }
            val = s;

            if(mode.equals("r") && i == 65535)//IF WE WANT TO DECOMPRESS WITH RESET
            {
                W=9;
                L=512 ;
                st = new String[(int)Math.pow(2, 16)];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";   
                

                codeword = BinaryStdIn.readInt(W);
                if (codeword == R) return;           // expanded message is empty string
                val = st[codeword];
            }
            if (i == 65535 && mode.equals("m"))
            {
                if(noRatio)
                {
                    savedratio = currentratio;
                    noRatio = false;
                }
                System.err.println(savedratio/currentratio);
                if(savedratio/currentratio > 1.1)
                {
                    W=9;
                    L=512 ;
                    st = new String[(int)Math.pow(2, 16)];
                    for (i = 0; i < R; i++)
                        st[i] = "" + (char) i;
                    st[i++] = "";   
                

                    codeword = BinaryStdIn.readInt(W);
                    if (codeword == R) return;           // expanded message is empty string
                    val = st[codeword];
                    savedratio = 0;
                    currentratio = 0;
                    noRatio = true;
                }
            }
            

        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if (args[0].equals("-") && args[1].equals("n"))
        {
            mode = "n";
            compress();  
        } 
        else if (args[0].equals("+")) expand();
        else if (args[0].equals("-") && args[1].equals("r"))
        {
            mode = "r";
            compress();  
        } 
        else if (args[0].equals("-") && args[1].equals("m"))
        {
            mode = "m";
            compress();
        }
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
