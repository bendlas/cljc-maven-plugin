(ns net.bendlas.maven.mojos
  (:import
   (org.apache.maven.plugins.annotations
    Execute Mojo LifecyclePhase Parameter))
  (:require
   [clojure.tools.logging :as log]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [clojure.pprint :refer [pprint]]
   [cljsee.core :refer [cljsee-compile]]))

(gen-class
 :name
 ^{Mojo    {:name "split"}
   Execute {:goal  "split"
            :phase LifecyclePhase/GENERATE_SOURCES}}
 net.bendlas.maven.mojos.SplitMojo
 :extends org.apache.maven.plugin.AbstractMojo
 :prefix "split-")

(defn split-execute [this]
  (let [project (.get (.getPluginContext this) "project")]
    (try
      (cljsee-compile
       [{:source-paths [(str (io/file (.getBasedir project) "src/main/clojure"))]
         :output-path (.. project getBuild getOutputDirectory)
         :rules :clj}])
      (catch Exception e
        (log/error e "During split")
        (throw e)))))
