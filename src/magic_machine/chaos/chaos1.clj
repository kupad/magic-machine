(ns magic-machine.chaos.chaos1
    (require [simple-plotter.core :as plotter])
    (require [quil.core :as q])
    (:gen-class))

;; inspired by CHAOS1 from "The Strange Attractions of Chaos," The Magic Machine, A.K Dewdney

;; our window dimensions 
(def width 640)
(def height 480)


;; the equation we will use to generate x values
;; x <- rx(1-x)
(defn f [r x]
  (* r x (- 1 x)))

;; generate x vals for a given r value
;; we start x at 0.3
;; we want 300 values. 
;; we iterate 500 times and we drop the first 200 iterations "to allow transients to drop away"
(defn genx [r]
  (drop 200 (take 500 (iterate #(f r %) 0.3))))

;; this function returns the points to be plotted
;; we generate r-values from 2.90 -> 4.0. 
;; The step size will be determined by our window height. 
;; each row on the screen represents an r value.
;; for each r value, we will generate x values by iterating the function f (x <- rx(1-x)) 
(defn chaos1 []
  (->>
    (range 2.90 4.0 (/ (- 4.0 2.90) height)) ;; generate the rvals
    (map genx) ;;generate the xvalues for each rvalue
    (map-indexed (fn [y xs]  ;;turn the x values into points to plot
                     (map (fn [x] [(* width x) y]) xs)))
    (mapcat identity))) ;;flatten points

;;;
(defn draw [points]
  (doseq [[x y] points]
         (q/point x y)))

(defn setup []
  (q/background 255))
;;;

(defn -main [& args]
  (let [points (chaos1)]
    (q/defsketch trigonometry
      :size [width height]
      :setup setup
      :draw (fn [] (draw points)))))

