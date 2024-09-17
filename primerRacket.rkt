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


;---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
;filtrar pares de una lista
(define (filtrar-pares lista)
         (if (null? lista) '()
             (if (even? (first lista))
                 (cons (first lista) (filtrar-pares (rest lista)))
                 (filtrar-pares (rest lista)))))

;(filtrar-pares '(1 2 3 4 5 6))

;validar un correo
(require racket/string)
(define (correo-valido? correo)
  (regexp-match? #px"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z][2,]$" correo))

(define (filtrar-correos-validos lista-correos)
  (filter correo-valido? lista-correos))

(define correos '("juangui@dominio.com"
                  "juangui2@dominio."
                  "notieneformato@dominio"
                  "estesaleenlalista@correo.net"
                  "juliestasporaca@ejemplo.com"))

;(filtrar-correos-validos correos)

;crear un log (creo)
(struct config (settings) #:transparent)
(define (default-config)
(config (hash 'host "localhost"
              'port 8080
              'timeout 300)))

(define (apply-config base-config custom-settings)
  (let ([base-settings (config-settings base-config) ])
    (config (foldl (lambda (key acc)
                     (hash-set acc key (hash-ref custom-settings key (hash-ref base-settings key) )))
                   base-settings
                   (hash-keys custom-settings) ))))
(define (get-config-value config key)
  (hash-ref (config-settings config) key))

(define (display-config config)
  (for-each (lambda (key)
              (printf "~a: ~a\n" key (hash-ref (config-settings config) key) ))
            (hash-keys (config-settings config))))

(define base-config (default-config))

(define custom-config (apply-config base-config
                                    (hash 'host "example.com"
                                          'timeout 600)))
;(displayln "Base Config:")
;(display-config base-config)
;(displayln "\nCustom Config:") (display-config custom-config)
;(printf "\nHost in custom config: ~a\n" (get-config-value custom-config 'host) )


;sacar de una lista de objetos la suma de los mayores a 100
(struct pedido (id cliente monto))

(define pedidos
  (list (pedido 1 "Cliente A" 150.0)
        (pedido 2 "Cliente B" 75.0)
        (pedido 3 "Cliente C" 200.0)
        (pedido 4 "Cliente D" 50.0)
        (pedido 5 "Cliente E" 300.0)))

(define umbral 100.0)

(define pedidos-filtrados
  (filter (lambda (p) (> (pedido-monto p) umbral)) pedidos))

(define total-ingresos
  (foldl (lambda (p acc) (+ (pedido-monto p) acc)) 0 pedidos-filtrados))

;(printf "Total de ingresos de pedidos que superan el umbral de ~a: ~a\n" umbral total-ingresos)




