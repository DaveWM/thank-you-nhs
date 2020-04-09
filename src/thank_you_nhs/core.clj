(ns thank-you-nhs.core
  (:require [hiccup.core :as h]))

(def head
  [:head
   [:link {:rel :stylesheet
           :href "styles/main.css"}]
   [:link {:href "https://fonts.googleapis.com/css2?family=Indie+Flower&display=swap" :rel "stylesheet"}]
   [:link {:rel :icon
           :href "favicon.ico"}]
   [:title "Thank you NHS!"]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]])

(def rainbow
  (let [colours ["red" "orange" "yellow" "green" "blue" "indigo" "violet"]
        rainbow-height 15
        r-step (int (/ (- 50 rainbow-height) (inc (count colours))))]
    [:div.rainbow
     [:svg
      {:viewBox "0 0 100 50"
       :preserveAspectRatio "xMidYMax slice"
       :width "auto"}
      [:defs
       [:mask {:id "hole"}
        [:rect {:width "100%" :height "100%" :fill "white"}]
        [:circle {:cx 50 :cy 50 :r (* 1.5 rainbow-height) :fill "black"}]]]
      (->> (concat
            [:g {:mask "url(#hole)"}]
            (->> colours
                 (map-indexed
                  (fn [idx colour]
                    [:circle {:cx 50 :cy 50 :r (- 50 (* r-step idx)) :fill colour}]))))
           (into []))]]))

(def page
  [:html
   head
   [:body
    [:div.main
     [:h1 "Thank you NHS!"]
     rainbow
     ]]])


(defn main [& args]
  (println "Building...")
  (spit "public/index.html" (h/html page)))

(main)
