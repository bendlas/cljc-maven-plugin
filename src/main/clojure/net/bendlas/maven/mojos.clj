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

(gen-class
 :name
 ^{Mojo    {:name "split-tests"}
   Execute {:goal  "split-tests"
            :phase LifecyclePhase/GENERATE_TEST_SOURCES}}
 net.bendlas.maven.mojos.SplitTestsMojo
 :extends org.apache.maven.plugin.AbstractMojo
 :prefix "split-tests-")

(defn run-split! [this source-dir]
  (let [project (.get (.getPluginContext this) "project")]
    (try
      (cljsee-compile
       [{:source-paths [(str (io/file (.getBasedir project) source-dir))]
         :output-path (.. project getBuild getOutputDirectory)
         :rules :clj}])
      (catch Exception e
        (log/error e "During split of" source-dir)
        (throw e)))))

(defn split-execute [this]
  (run-split! this "src/main/clojure"))

(defn split-tests-execute [this]
  (run-split! this "src/test/clojure"))
