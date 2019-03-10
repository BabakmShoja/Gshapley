// CS6410
// Programming Assignment 01
// Babak Maleki Shoja
// Compilation command: javac GShap.java
// execution command for finding stable matching: java GShap find men women matching
// execution command for checking a matching: java GShap check men women doubtful
// men is preference list of men
// women is preference list of women
// matching is the name of output file showing matches
// doutful is the matching list to be checked
// It is assumed that the input preference lists are in a correct form and with the same size based on the assignment statements
// It is assumed that there is no missing value for the pairs when it is in check mode. For example in the list of pairs there is number for the man but there is no number for woman. If this is the case, there is a for loop which is commented now. If you need to check the code with missing values like this, please uncomment that loop starting from line 180 to 208

import java.io.*;
import java.lang.*;
import java.util.*;




public class GShap {
    public static void main(String[] args){
//        Check if the problem is stable matching or checking

        // read women preference list in an array using readFileArray
        int[][] women = readFileArray(args[2]);

        // reversing women preference list to construct reversed list
        int[][] InvWomen = new int[women.length][women.length];
        for (int i = 0; i < women.length; i++) {
            for (int j = 0; j < women.length; j++) {
                InvWomen[i][women[i][j]] = j;
            }
        }

        //read men to an array
        int[][] menS = readFileArray(args[1]);
        //preparing inverse preference list for men to be used only in the check part
        int[][] InvMen = new int[menS.length][menS.length];
        for (int i = 0; i < menS.length; i++) {
            for (int j = 0; j < menS.length; j++) {
                InvMen[i][menS[i][j]] = j;
            }
        }

        // queue of men
        myQueue.Queue[] men = new myQueue.Queue[menS.length];
        for (int i = 0; i < menS.length; i++) {
            men[i] = new myQueue.Queue();
            for (int j = 0; j < menS.length; j++) {
                men[i].insert(menS[i][j]);
            }
        }


        switch (args[0]) {
            case "find":


                // Unmatched Stack list
                myStack UnmarriedMen = new myStack(menS.length);
                for (int i = menS.length; i > 0; i--) {
                    UnmarriedMen.push(i-1);
                }

//            Finding the stable match using Gale-Shaply algorithm
//            initiallizing wife and husband arrays
                int[] Wife = new int[women.length];
                int[] Husband = new int[women.length];
//                initialize Husband
                for (int i = 0; i < women.length; i++) {
                    Husband[i] = -1;
                }

//                Assigning the first man which is not required to be in the loop
                int MCandid;
                int Propose;
                while (!UnmarriedMen.empty()){
//                    Top Unmarried man to be assigned
                    MCandid = UnmarriedMen.Peek();
                    Propose = men[MCandid].Pop();

//                    Checking the proposal
//                    Preferred woman unmatched

                    if (Husband[Propose]==-1){
//                        New Match
                        Wife[MCandid] = Propose;
                        Husband[Wife[MCandid]] = MCandid;
//                        Poping matched man
                        UnmarriedMen.Pop();

                    }

//                    Checking the proposal for matched woman
                     if(InvWomen[Propose][Husband[Propose]]>InvWomen[Propose][MCandid]){
//                        Poping matched man for new match
                        UnmarriedMen.Pop();
//                        returning rejected man to the stack
                        UnmarriedMen.push(Husband[Propose]);
//                        New Match
                        Wife[MCandid] = Propose;
                        Husband[Wife[MCandid]] = MCandid;
                    }

//                    else{
//                        The woman who rejected the proposal is already out of corresponding man preference list Then we do not need to do anything here
//                         I know I did not need to write this else, I just put it here for explanation
//                    }

                }

//                Creating output file - The format of the file is that the first column shows men and second one shows women
                try {
                    File Output = new File(args[3]);
                    if (!Output.exists()) {
                        Output.createNewFile();
                    }
                    PrintWriter Write = new PrintWriter(Output);
                    int K;
                    for (int i = 0; i < women.length; i++) {
                        Wife[i]++;
                        K = i+1;
                        Write.println(K+" "+Wife[i]);

                    }
                    Write.close();
                } catch (IOException e){
                    System.out.println("File Error!");
                }

                break;
            case "check":

//                Read the pairing matrix
                int[][] Pairs = readFileArray(args[3]);

//                Check if it is a matching and perfect (it is considered that the preference lists of men and women are the same since it is stated that the input files are in a correct form
//                counting the number of occurrence of each man and woman
//                if a man/woman occurs more than one time it is not matching
//                Checking both for Men and Women
                int Brk = 0;
                int CtrM;
                int CtrW;
                int err;

                for (int i = 0; i < Math.max(menS.length,women.length); i++) {
                    CtrM = 0;
                    CtrW = 0;
                    for (int j = 0; j < Pairs.length; j++) {
                        if (Pairs[j][0]==i){
                            CtrM++;
                        }
                        if (Pairs[j][1]==i){
                            CtrW++;
                        }
                    }
                    if (CtrM>1){
                        err = i+1;
                        System.out.println("This is not a matching for the following reason: ");
                        System.out.println("Man number "+ err +" has appeared more than one time i.e. " + CtrM + " times");
                        Brk = 1;
                        break;
                    }

                    if (CtrW>1){// Checking matching
                        err = i+1;
                        System.out.println("This is not a matching for the following reason: ");
                        System.out.println("Woman number "+ err +" has appeared more than one time i.e. " + CtrW + " times");
                        Brk = 1;
                        break;
                    }

                }
                if (Brk==1){
                    break;
                }
//              Following part is for the case that there is missing value! For example a pair has a value of a man but no value for a woman
//                I commented this part, but if you provide a pairs list like the example above to check the code please uncomment following for loop
//                for (int i = 0; i < Math.max(menS.length,women.length); i++) {
//                    CtrM = 0;
//                    CtrW = 0;
//                    for (int j = 0; j < Pairs.length; j++) {
//                        if (Pairs[j][0]==i){
//                            CtrM++;
//                        }
//                        if (Pairs[j][1]==i){
//                            CtrW++;
//                        }
//                    }
//
//
//                    if (CtrM==0){//I tried to be very specific about the reason, not just the sizing
//                        err = i+1;
//                        System.out.println("The list of pairs provided has missing value: ");
//                        System.out.println("Man number "+ err +" does not exist in the matching");
//                        Brk = 1;
//                        break;
//                    }
//
//                    if (CtrW==0){//Checking perfect matching
//                        err = i+1;
//                        System.out.println("The list of pairs provided has missing value: ");
//                        System.out.println("Woman number "+ err +" does not exist in the matching");
//                        Brk = 1;
//                        break;
//                    }
//                }

//                Check if it is perfect matching based on the size of men (again, it is assumed that men and women have the same size of preference list
                if (Pairs.length != menS.length){
                    System.out.println("It is a matching but it is not a perfect matching because the size of "+args[3]+" is "+Pairs.length+" and the size of "+args[1]+" is "+menS.length);
                    break;
                }else if (Pairs.length != women.length){
                    System.out.println("It is a matching but it is not a perfect matching because the size of "+args[3]+" is "+Pairs.length+" and the size of "+args[2]+" is "+women.length);
                    break;
                }


//                Check if the matching is stable
//                The algorithm for checking stability is as follows.
//                We go over the input pairs.
//                First we check the man and woman if the current match is at the first position of the man
//                if this is true, an unstable match does not exist for the corresponding
//                else, we check the women in the man's preference list before current matching one by one.
//                if a woman in the preference list before the man's current match prefer this man too, then there is an unstable match
//                if not, we go for the next man
//                it should be noted that we do not need to check women, because if there is a man who prefers some woman over current match, it will be checked by that man later
//                since one counterexample is enough, the loop will break if one is found
//                Also, we break out of the case for not showing the stable matching message

                int ManSeeking;//the woman that man X prefer to the current match
                int UnstableMan;//for message sake
                int UnstableWoman;//for message sake
//                Constructing pairs based on women-men structure
                int[][] WPairs = new int[Pairs.length][2];
                for (int i = 0; i < Pairs.length; i++) {
                    WPairs[Pairs[i][1]][0]=Pairs[i][1];
                    WPairs[Pairs[i][1]][1]=Pairs[i][0];
                }
                for (int i = 0; i < Pairs.length; i++) {
//                    Checking if the man's current match is at the first place of the preference list
//                    if it is true we do nothing, then it is of our concern when it is true.
//                    I used inverse preference list of men in the rest of the code to be more efficient with the same logic about women

                    if (InvMen[Pairs[i][0]][Pairs[i][1]]!=0){//Check if the man's match is the first in the preference list
                        while((ManSeeking=men[Pairs[i][0]].Pop()) != Pairs[i][1]){//going over all preferences before current match for the man
                            if (InvWomen[ManSeeking][Pairs[i][0]]<InvWomen[ManSeeking][WPairs[ManSeeking][1]]){//Checking the one the man prefers is a better match for that woman
                                System.out.println("This is matching and perfect matching but it is not a stable matching. ");
                                UnstableMan = Pairs[i][0]+1;
                                UnstableWoman = ManSeeking+1;
                                System.out.println("Pair of Man "+UnstableMan+" and Woman "+ UnstableWoman + " is an unstable pair");
                                Brk=1;
                                break;
                            }
                        }
                    }
                    if (Brk==1) break;
                }
                if (Brk==1) break;


                System.out.println("It is a matching, perfect matching and stable matching");



                break;
            default: // Best Practice when the problem is specified other than find or check
                System.out.println("Error: You can only specify either find or check.");
                break;
        }


    }

    // Read preferences/Matches from text file to array

    public static int[][] readFileArray(String file){
        try{
            File f = new File(file);
            Scanner s = new Scanner(f);

            // Number of lines and Columns
            int Lines = 0;
            int Columns = 0;
            int Breaker = 0;

            while (s.hasNextLine()) {
                Lines++;
                String line = s.nextLine();
                Scanner s2 = new Scanner(line);
                while(s2.hasNext() && Breaker == 0){
                    Columns++;
                    s2.next();
                }
                Breaker++;
            }

            // Constructing the array knowing the input files have the same rows and columns
            int[][] arr = new int[Lines][Columns];
            for (int i = 0; i < Lines; i++) for (int j = 0; j < Columns; j++) arr[i][j] = -1;
            Scanner s1 = new Scanner(f);
            int ii;
            int jj = 0;
            while (s1.hasNextLine()) {
                ii=0;
                String line = s1.nextLine();
                Scanner  s3= new Scanner(line);
                while(s3.hasNextInt()) {
                    arr[jj][ii] = s3.nextInt() - 1;
                    ii++;
                }
                jj++;
            }
            return arr;
        }
        catch (FileNotFoundException e)
        {
            //Best practice
            System.out.println("File does not exist");
            return null;
        }

    }

}


