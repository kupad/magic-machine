(ns magic-machine.chaos.chaos1
    (require [simple-plotter.core :as plotter])
    (:gen-class))

;; inspired by CHAOS1 from "The Strange Attractions of Chaos," The Magic Machine, A.K Dewdney


(defn compute [r x]
  (* r x (- 1 x)))

(defn iter [n x width y r plot?]
  (loop [i 0,
         x x]
        (if (>= i n)
          x
          (let [next-x (compute r x)]
            (when plot?
              (plotter/plot (* width next-x) y))
            (recur (inc i) next-x)))))

(defn plot-r [r width y-coord density]
  (->
    0.3
    ((fn [x] (iter 200 x width y-coord r false))) ;eliminate transients
    ((fn [x] (iter density x width y-coord r true))))) ;plot it


(def r-start 2.90)
(def r-end 4)

(defn chaos1 
  [ & {:keys [density width height] :or {density 300, width 200, height 200}}]
  (plotter/create-window "chaos1" width height)
  (let [r r-start
        r-step (/ (- r-end r-start) height)]
    (loop [r r
           y 0]
          (when (<= y height)
            (plot-r r width y density)
            (recur (+ r r-step) (inc y))))))

(defn -main [& args] (apply chaos1 args))
