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
        ;; (Richard of York gave battle in vain)
        viewbox-width 100
        ;; the rainbow is a semicircle, so we need our SVG to be in a 2:1 ratio
        viewbox-height (/ viewbox-width 2)
        ;; this is the height of the arch - try setting it to different values
        arch-height 25
        ;; the width of a single colour band
        ;; equal to the difference between viewbox height and the arch height, divided by the number of colours
        band-width (/ (- viewbox-height arch-height) (count colours))]
    [:svg
     {:viewBox (str "0 0 " viewbox-width " " viewbox-height)
      :preserveAspectRatio "xMidYMax slice"
      :width "auto"}
     [:defs
      ;; This mask cuts out a hole in the rainbow, for the bit underneath the arch
      [:mask {:id "arch"}
       [:rect {:width "100%" :height "100%" :fill "white"}]
       ;; note - SVG coordinates are upside down (y = 0 is at the top), so "viewbox-height" is at the bottom
       [:circle {:cx viewbox-height :cy viewbox-height :r arch-height :fill "black"}]]]
     [:g {:mask "url(#arch)"}
      (->> colours
           (map-indexed
            (fn [idx colour]
              [:circle {:cx (/ viewbox-width 2)             ;; this is the horizontal centre
                        :cy viewbox-height
                        ;; start with the largest band (red), and make each successive colour circle smaller by "band-width"
                        :r (- viewbox-height (* band-width idx))
                        :fill colour}])))]]))

(def page
  [:html
   head
   [:body
    [:div.main
     [:h1 "Thank you NHS!"]
     [:div.rainbow rainbow]]]])


(defn main [& args]
  (println "Building...")
  (spit "public/index.html" (h/html page)))

(main)
