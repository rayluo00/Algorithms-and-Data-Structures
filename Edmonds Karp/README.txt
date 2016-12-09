Weiming Raymond Luo
W#01130104
CSCI 405 : Analysis of Algorithms II
Homework 3 - Edmonds-Karp Algorithm

FILES :
      EdmondsKarp.java, FlowData.java, Makefile, Input Text Graph Files
      (i.e. g1.txt, g2.txt, g100.txt)

This program is to compute the given directed graph using the Edmonds-Karp Algorithm.
The algoirthm performs multiple bredth-first searches on the graph to find the shortest
path from the source to the sink. While performing the search, it will obtain the max
flow, min cut, and min cut capacity.

In order to run this program, you can ue the Makefile provided or compile and run the
java files seperately. To compile the java files manually type 'javac EdmondsKarp.java FlowData.java'.
To execute the program, use the command, 'java EdmondsKarp FlowData [Input graph text file]',
and example of this is 'java EdmondsKap FlowData g100.txt'.
