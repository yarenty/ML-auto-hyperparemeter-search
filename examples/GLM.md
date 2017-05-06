GLM Test
--------

## Hyper-Params


"_nfolds":0,  
"_keep_cross_validation_predictions":false,  
"_keep_cross_validation_fold_assignment":false,  
"_parallelize_cross_validation":true,"_auto_rebalance":true,
"_seed":8924180439658350971,  
"_fold_assignment":"AUTO",  
"_categorical_encoding":"AUTO",  
"_distribution":"AUTO",  
"_tweedie_power":1.5,  
"_quantile_alpha":0.5,  
"_huber_alpha":0.9,  
"_ignored_columns":null,  
"_ignore_const_cols":true,  
"_weights_column":null,  
"_offset_column":null,  
"_fold_column":null,  
"_is_cv_model":false,  
"_score_each_iteration":false,  
"_max_runtime_secs":0.0,  
"_stopping_rounds":3,  
"_stopping_metric":"deviance",  
"_stopping_tolerance":1.0E-4,  
"_response_column":"dep_delayed_15min",  
"_balance_classes":false,  
"_max_after_balance_size":5.0,  
"_class_sampling_factors":null,  
"_max_confusion_matrix_size":20,  
"_checkpoint":null,  
"_pretrained_autoencoder":null,  
"_standardize":true,  
"_family":"binomial",  
"_link":"logit",  
"_solver":"IRLSM",  
"_tweedie_variance_power":0.0,  
"_tweedie_link_power":1.0,  
"_alpha":[1.0],  
"_lambda":[0.0],  
"_missing_values_handling":"MeanImputation",  
"_prior":-1.0,  
"_lambda_search":false,  
"_nlambdas":-1,  
"_non_negative":false,  
"_exactLambdas":false,  
"_lambda_min_ratio":1.0E-4,  
"_use_all_factor_levels":false,  
"_max_iterations":50,  
"_intercept":true,  
"_beta_epsilon":1.0E-4,  
"_objective_epsilon":1.0E-6,  
"_gradient_epsilon":1.0E-6,  
"_obj_reg":1.0E-4,  
"_compute_p_values":false,  
"_remove_collinear_columns":false,  
"_interactions":null,  
"_early_stopping":true,  
"_beta_constraints":null,  
"_max_active_predictors":5000,  
"_stdOverride":fals  


## OUTPUT

```
AUCC:Model Metrics Type: BinomialGLM
 Description: N/A
 model id: GLM_model_1494062066672_1
 frame id: test.hex
 MSE: 0.16192816
 RMSE: 0.402403
 AUC: 0.67141247
 logloss: 0.5090892
 mean_per_class_error: 0.3690781
 default threshold: 0.17581778764724731
 CM: Confusion Matrix (vertical: actual; across: predicted):
            N      Y   Error              Rate
     N  48107  30276  0.3863   30,276 / 78,383
     Y   7607  14010  0.3519    7,607 / 21,617
Totals  55714  44286  0.3788  37,883 / 100,000
Gains/Lift Table (Avg response rate: 21.62 %):
  Group  Cumulative Data Fraction  Lower Threshold      Lift  Cumulative Lift  Response Rate  Cumulative Response Rate  Capture Rate  Cumulative Capture Rate        Gain  Cumulative Gain
      1                0.01000000         0.671170  2.058565         2.058565       0.445000                  0.445000      0.020586                 0.020586  105.856502       105.856502
      2                0.02000000         0.599404  2.350002         2.204284       0.508000                  0.476500      0.023500                 0.044086  135.000231       120.428367
      3                0.03000000         0.557998  2.271361         2.226643       0.491000                  0.481333      0.022714                 0.066799  127.136050       122.664261
      4                0.04000000         0.527002  2.044687         2.181154       0.442000                  0.471500      0.020447                 0.087246  104.468705       118.115372
      5                0.05000000         0.500662  1.966045         2.138132       0.425000                  0.462200      0.019660                 0.106907   96.604524       113.813203
      6                0.10000000         0.412577  1.837443         1.987787       0.397200                  0.429700      0.091872                 0.198779   83.744275        98.778739
      7                0.15000000         0.354472  1.623722         1.866432       0.351000                  0.403467      0.081186                 0.279965   62.372207        86.643228
      8                0.20000000         0.309921  1.537679         1.784244       0.332400                  0.385700      0.076884                 0.356849   53.767868        78.424388
      9                0.30000000         0.244123  1.340149         1.636212       0.289700                  0.353700      0.134015                 0.490864   34.014896        63.621224
     10                0.40000000         0.193692  1.130592         1.509807       0.244400                  0.326375      0.113059                 0.603923   13.059166        50.980710
     11                0.50000000         0.153322  0.962206         1.400287       0.208000                  0.302700      0.096221                 0.700143   -3.779433        40.028681
     12                0.60000000         0.120719  0.788731         1.298361       0.170500                  0.280667      0.078873                 0.779017  -21.126891        29.836086
     13                0.70000000         0.093090  0.644400         1.204938       0.139300                  0.260471      0.064440                 0.843457  -35.559976        20.493791
     14                0.80000000         0.068284  0.587038         1.127700       0.126900                  0.243775      0.058704                 0.902160  -41.296202        12.770042
     15                0.90000000         0.043324  0.501457         1.058118       0.108400                  0.228733      0.050146                 0.952306  -49.854281         5.811784
     16                1.00000000         0.000635  0.476939         1.000000       0.103100                  0.216170      0.047694                 1.000000  -52.306055         0.000000
 null DOF: 99999.0
 residual DOF: 99411.0
 null deviance: 104787.734
 residual deviance: 101817.83

```