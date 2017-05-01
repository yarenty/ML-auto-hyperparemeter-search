# ML-auto-hyperparemeter-search
Machine Learning automatic hyper-parameter search


Second inception or "machine learning on top of machine learning" - it sounds like movie "Inception",
 as idea maybe is not so complicated but you need to keep in mind that we will have two layers of machine learnings.

When one have our initial data and one don't know what ML algorithm to use,
we will perform initial "wide assignment" where one will run some default algorithms with some default (and different) parameters.
Output of this first step will give us a table where columns will be algorithms, parameters, and accuracy (or error: MSE etc)

A| P1| P2| P3| P4| ....| ACCU
GBM| 0.1| 10| 0.3|... |0.75
GBM| 0.4| 10| 0.3|... |0.76
GBM| 0.8| 10| 0.3|... |0.72
GBM| 0.1| 3| 0.3|... |0.78
DL| - | 70| 0.8|... |0.71
DL| - | 20| 0.8|... |0.72

Size of this table  will be defined on first stage - lets say 200 rows.

This table will be input to our second layer of machine learning,
 where all those parameters will be input variables and we will train this ML to accurately predict ACCU!
Output of work on top of previous one - will be "prediction/optimisation model"
 and next step will be to using that model to predict what will be ACCU for all possible combinations of parameters
 (lets say 10000 rows) - as this prediction phase will take milliseconds.

Using this prediction we will get rows where predicted ACCU is best - and that will be input to the next round of calculations
 on first level of ML (the one working with real data).


At the moment we are thinking about using bayesian algorithm,
but would like to be open for different possibilities as bayesian is classification solution
and we will need to define  "quota" of top X best ACCU  predictions.
//But this part is my "future" thinking - we do not need to explore this on current stage.


http://blog.h2o.ai/tag/bayesian-parameter-optimization/

