# ML-auto-hyperparemeter-search
Machine Learning automatic hyper-parameter search


This is like inception, but as that term was used in AlexNet (and other - multiple layered ML algorithms), lets name it "second inception".

Second inception is "machine learning on top of machine learning", ML trying to teach another ML to behave in "better way",
Initial idea was covered by the need to find optimal hyperparameters, and there are few different approache, ie:  http://blog.h2o.ai/tag/bayesian-parameter-optimization/

This one is simpler version - you just need to keep in mind that we will have two layers of machine learnings.

More in [motivation](motivation/motivation.md)


## Problem
When you have data, you want to predict / learn something from that data, but you don't know what ML algorithm to use,
 and even when you know algorithm - you don't know what parameters to use to get best results.

## Solution
We will perform initial "wide assignment" where we will run some default algorithms with some default (and different) parameters.
Output of this first step will give us a table, where columns will be algorithms, parameters, and accuracy (or error: MSE, logloss, etc)


Output of first layer -> input for second layer of ML:

Algo | P1 | P2 | P3 | P4 | ... | ACCU
:--- | ---: | ---:| ---:| ---:| ---:| --:
GBM| 0.1| 10| 0.3| |... |0.75
GBM| 0.4| 10| 0.3| |... |0.76
GBM| 0.8| 10| 0.3| |... |0.72
GBM| 0.1| 3| 0.3| |... |0.78
DL| - | 70| 0.8| |... |0.71
DL| - | 20| 0.8| |... |0.72


Size of this table  will be defined on first stage - lets say 200 rows.


This table will be input to our second layer of machine learning,
 where all those parameters will be input variables and we will train this ML to accurately predict our ACCU!

That output of work will be "prediction/optimisation model"
 and the next step will be to use that model, to predict what will be ACCU for all possible combinations of parameters
 (lets say 10000 rows) - as this prediction phase will take milliseconds.

Using this prediction we will get rows where predicted ACCU is best - and that will be input to the next round of calculations
 on first level of ML (the one working with real data).



At the moment I started thinking about using bayesian algorithm,
but would like to be open for different possibilities as bayesian is classification solution
and we will need to define  "quota" of top X best ACCU  predictions, classify them as "1" and other as "0".
However I would like to test here possibilities of using RF/GBM or even linear regression.
//But this part is my "future" thinking - we do not need to explore this on current stage.


## TODO - PROGRESS
1. get initial data (airlines) - DONE
- run some algorithms on that data (step 1: just linear) - DONE
- run ML on top of output of linear output with different params - WIP
- check if predicted algo params from first layer - will get better results



## Conclusion

WIP



GML

GLM initial output on airlines:
```   
                 blind guess=0.6714124454842434
          initial max values=0.6877173209002059;0.6877173209002059;0.6877018721171844;0.6877018721171844;0.6877018721171844
  predicted by DRF inception=0.6761178016662598;0.6761178016662598;0.6761178016662598;0.6761178016662598;0.6761178016662598
           real after retest=0.6877018721171844;0.6877018721171844;0.6877018721171844;0.6877018721171844;0.6877018721171844
```      
this is ... bad  - still better on blind guess but need to narrow down data/ params   
still this is 1st round!!!   

lets try with DRF?  
      