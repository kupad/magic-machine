(ns magic-machine.chaos.chaos2
    (require [simple-plotter.core :as plotter])
    (import (java.math MathContext))
    (:gen-class))

;; inspired by CHAOS2 from "The Strange Attractions of Chaos," The Magic Machine, A.K Dewdney

(def math-context (MathContext/DECIMAL128))

(defn bd+ [a b] (. a add b math-context))
(defn bd- [a b] (. a subtract b math-context))
(defn bd* [a b] (. a multiply b math-context))
(defn bdpow [b e] (. b pow e math-context))

(defn compute [x y a]
  (let [xx (bd- (bd* x 
                     (BigDecimal. (Math/cos a)))
                (bd* (bd- y (bdpow x 2))
                     (BigDecimal. (Math/sin a))))
        yy (bd+ (bd* x
                     (BigDecimal. (Math/sin a)))
                (bd* (bd-  y (bdpow x 2))
                     (BigDecimal. (Math/cos a))))]
    [xx yy]))

(defn plot-orbit [x y a]
  (loop [i 0
         x x
         y x]
        ;(println x y)
        (when (< i 1000)
            (let [[x y] (compute x y a)]
              (when (and (< x 10) (< y 10))
                (plotter/plot (+ 100 (* 100 (. x doubleValue))) (+ 100 (* 100 (. y doubleValue))))
                (recur (inc i) x y))) )))

(defn henon [a]
  (let [step (BigDecimal. 0.01)] 
    (loop [x (BigDecimal. 0.01 math-context)
           y (BigDecimal. 0.01 math-context)]
          (when (and (< x 1) (< y 1)) 
            (try
              (plot-orbit x y a)
              (catch ArithmeticException ex
                     (println ex)))
            (recur (bd+ x step) (bd+ y step))))))

;; of interest:
(defn circles [] 
  (plotter/create-window "henon" 200 200)
  (henon 1.111))
(defn starfish [] 
  (plotter/create-window "henon" 200 200)
  (henon 1.5732))
(defn fishy [] 
  (plotter/create-window "henon" 200 200)
  (henon 0.264))

(defn -main [& args] 
  (plotter/create-window "henon" 200 200)
  (apply henon args))
