#lang racket
;https://learnxinyminutes.com/docs/racket/

(define (suma operando1 operando2)
  (+ operando1 operando2))
;(print (suma 4 5))

(define (loop i)
  (when(< i 10)
    (printf "i=~a\n" i)
    (loop (add1 i))))
;(loop 0)

(for ([i (in-list '(M i n o m b r e e s J U A N))])
      (displayln i))

(member 'Juan' '(Name Juan Gui))
(if (member 'Juan '(Name Juan Gui))
    'yep ;si se cumple
    'nop) ;else
 
