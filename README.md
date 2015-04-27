# hpc_project
Authors: 
   Dillon Yeh <yehda194@potsdam.edu>,
   John Kosowsky <kosowsjm195@potsdam.edu>

Description: 
    Using genetic algorithms to create an AI for the game Othello.

What will be parallelized:
    1) Creating initial AI's
    2) The population plays against eachother.
        - losers are eliminated
    3) Apply a fitness to winners.
        - Taking the average squares taken from the winners
        - Consider those above that average to be fit.
    4) Crossbreed and apply mutations.
    5) Jump to step 2.

Notes:
    - Make sure you either add src/ to your CLASSPATH or compile using java's classpath flag (-cp)
    
