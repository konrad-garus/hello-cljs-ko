(ns hello-clojurescript)

(defn app-view-model []
  (this-as this
           (set! (.-firstName this) (.observable js/ko "Bert"))
           (set! (.-lastName this) (.observable js/ko "Bertington"))
           (set! 
             (.-fullName this)
             (.computed js/ko 
               (fn []
                 (str (.firstName this) " " (.lastName this))) this))
           (set!
             (.-capitalizeLastName this) 
             (fn []
               (.lastName this (-> this .lastName .toUpperCase))
               )))
  ; Yes, I do need to have an explicit return. Otherwise it returns
  ; "return this.fullName = ...", and that does not work
  nil
  )

(.applyBindings js/ko (app-view-model.))

; This is pretty much equivalent to the above, 
; capitalizeLastName is pretty much copy/paste 

(comment
(defn app-view-model []
  (this-as this
           (aset this "firstName" (.observable js/ko "Bert"))
           (aset this "lastName" (.observable js/ko "Bertington"))
           (aset 
             this
             "fullName" 
             (.computed js/ko 
               (fn [] (str (.firstName this) " " (.lastName this))) this))
           nil
           ))

(.applyBindings js/ko (app-view-model.))
)

; This implementation doesn't work - complains that this.firstName is not a function
;
; I don't know how to define properties or methods on objects defined in 
; this way - is it possible at all?
(comment
(deftype AppViewModel []
  Object )

(set! 
  (.. AppViewModel -prototype -firstName) 
  (.observable js/ko "Bert"))
(set! 
  (.. AppViewModel -prototype -lastName) 
  (.observable js/ko "Bertington"))
(set! 
  (.. AppViewModel -prototype -fullName) 
  (.computed js/ko (fn [] (this-as this
   (str (.firstName this) " " (.lastName this))
   ))))

(.applyBindings js/ko (AppViewModel.))
)