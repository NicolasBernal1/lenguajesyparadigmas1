#lang racket
#|
Crear cuentas bancarias: Cada cuenta tiene un saldo y un estado (activo o inactivo).
Realizar transacciones: Como deposito y retirar dinero, teniendo en cuenta que la cuenta esté activa y haya suficiente saldo.
Filtrar cuentas: Podría filtrar por las cuentas que están activas con saldo positivo.
Generar reportes: Listar todas las cuentas con su saldo y estado.
Condicional: Si las cuentas se encuentran en saldo negativo no permitir realizar ninguna transacción a menos que se le ingrese saldo y quede positivo
|#

;clase cuenta
(define Account%
  (class object%
    (super-new)
    ;atributos
    (init-field [balance 0] [active #t])
    
    ;metodos

    ;consultar estado
    (define/public (is-active)
      active)

    ;consultar balance
    (define/public (get-balance)
      balance)
    
    ;deposito
    (define/public (deposit quantity)
      (if (active)
          (begin
            (set! balance (+ balance quantity))
            (displayln "Successful deposit"))
          (displayln "Innactive account")))

    ;retiro
    (define/public (withdraw quantity)
      (if (active)
          (if (< quantity balance)
              (begin
                (set! balance (- balance quantity))
                (displayln "Successful withdraw"))
              (displayln "Insufficient funds"))
          (displayln "Innactive account")))
))

;lista de cuentas
(define accounts
  (list (new Account% [balance 100] [active #t])
        (new Account% [balance 200] [active #f])
        (new Account% [balance 10] [active #t])
        (new Account% [balance 1300] [active #t])
        (new Account% [balance 1] [active #f])))

;filtrar cuentas activas
(define (active-accounts accounts)
  (filter (lambda (account)
            (send account is-active))
          accounts))
  

;generar reporte
(define (generate-report accounts)
  (for-each (lambda (account)
              (displayln (format "Balance: ~a, Active: ~a"
                                 (send account get-balance)
                                 (send account is-active))))
            accounts))

;(active-accounts accounts)
;(define accounts2 (active-accounts accounts))
;(generate-report accounts)
