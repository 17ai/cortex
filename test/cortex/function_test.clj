(ns cortex.function-test
  (:use [clojure.test])
  (:use [cortex.core])
  (:require [clojure.core.matrix :as m]))

(deftest test-logistic-module
  (testing "basic logistic functionality"
    (let [m (logistic-module (m/array :vectorz [0 0 0]))]
      (is (m/equals [0 0 0] (output m)))
      (let [cm (calc m [-1000 0 1000])]
        (is (m/equals [0 0.5 1] (output cm))))))
  
  (testing "logistic applied to scalar. vectos and matrices"
    (let [m (logistic-module)]
      (let [cm (calc m [-1000 0 1000])]
        (is (m/equals [0 0.5 1] (output cm))))
      (let [cm (calc m 0)]
        (is (m/equals 0.5 (output cm))))))
  
  (testing "forward and backward pass"
    (let [m (logistic-module (m/array :vectorz [0 0 0]))]
      
      (let [fm (forward m [-1000 0 1000])]
        (is (m/equals [0 0.5 1] (output fm)))
        
        (let [bm (backward fm [10 10 -10])]
          (is (m/equals [0 2.5 0] (input-gradient bm))))))))

