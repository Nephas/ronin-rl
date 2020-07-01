(ns lib.template)

(def YARI_TEMPLATE {:base     [[1 1] [1 2] [-1 1] [-1 2] [0 2] [0 3]]
                    :left     [[1 1] [1 2]]
                    :right    [[-1 1] [-1 2]]
                    :straight [[0 2] [0 3]]})

(def TEMPLATE {:base     [[-1 1] [0 1] [1 1] [0 2]]
               :left     [[0 1] [1 1]]
               :right    [[-1 1] [0 1]]
               :straight [[0 1] [0 2]]})

(def TEMPLATE {:katana {:base     [[-1 1] [0 1] [1 1] [0 2]]
                        :left     [[0 1] [1 1]]
                        :right    [[-1 1] [0 1]]
                        :straight [[0 1] [0 2]]}
               :yari   {:base     [[1 1] [1 2] [-1 1] [-1 2] [0 2] [0 3]]
                        :left     [[1 1] [1 2]]
                        :right    [[-1 1] [-1 2]]
                        :straight [[0 2] [0 3]]}})

(defn add [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn rotate-template [positions new-dir]
  (map (cond (= :up new-dir) (fn [[x y]] [(- x) (- y)])
             (= :left new-dir) (fn [[x y]] [(- y) x])
             (= :right new-dir) (fn [[x y]] [y (- x)])
             (= :down new-dir) identity)
       positions))

(defn project-template
  ([pos template facing]
  (let [rotated-template (rotate-template template facing)]
    (map #(add % pos) rotated-template)))
  ([pos weapon stance facing]
   (project-template pos (get-in TEMPLATE [weapon stance]) facing)))