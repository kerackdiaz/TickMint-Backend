# TickMint Backend

TickMint es una plataforma empresarial para la venta de tickets validados por NFT, desarrollada para agencias de eventos. La plataforma permite a los usuarios comprar, vender y transferir tickets, que se generan como NFTs a través de Smart Contracts. Esta solución facilita la gestión de eventos, la autenticación de tickets y la comunicación entre usuarios y agencias.

## Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Microservicios](#microservicios)
- [Requisitos Previos](#requisitos-previos)
- [Gestión de Ramas](#gestión-de-ramas)
- [Licencia](#licencia)

## Características

- **Usuarios**: 
  - Creación de cuenta.
  - Compra de tickets para eventos.
  - Generación de NFTs para tickets comprados.
  - Transferencia y venta de tickets.
  - Gestión de wallets para ver NFTs y saldo.
  
- **Agencias**: 
  - Publicación de eventos y tickets.
  - Revisión de balance de ventas.
  - Envío de notificaciones a usuarios sobre eventos.

- **Admin**: 
  - Configuración del porcentaje de ganancia de cada ticket.
  - Validación de cuentas de usuarios.
  - Gestión de usuarios.
  - Monitoreo de la plataforma (utilizando Spring Boot Actuator).

## Tecnologías

- **Backend**:
  - Java 17
  - Spring Boot
  - Spring Cloud (Eureka, Config, Gateway)
  - Spring Security
  - JWT
  - OAuth 2.0
  - MongoDB (para eventos)
  - PostgreSQL (para otras entidades)
  - Docker
  - Kubernetes
  - AWS EKS

- **Frontend**:
  - React

- **Blockchain**:
  - Web3j

## Microservicios

1. **Discover Server**: Servicio de descubrimiento usando Eureka y Actuator.
2. **AuthService**: Autenticación y autorización con JWT, Spring Security y OAuth 2.0.
3. **UserService**: Gestión de usuarios.
4. **GatewayService**: Gateway API.
5. **TicketService**: Gestión de tickets y eventos.
6. **TransactionService**: Manejo de transacciones.
7. **MailService**: Envío de correos electrónicos.
8. **NotificationService**: Envío de notificaciones a los usuarios.
9. **AccessService**: Gestión del escaneo de tickets en eventos.
10. **TransferService**: Transferencia de tickets entre usuarios.
11. **NFTService**: Generación y gestión de NFTs a partir de smart contracts.
12. **PaymentService**: Integración de la pasarela de pago y gestión de wallets de usuarios.

## Requisitos Previos

- JDK 17
- Docker
- Kubernetes
- AWS CLI configurado
- Kubectl configurado para EKS

## Gestión de Ramas

- `main`: Rama principal con el código de producción.
- `develop`: Rama de desarrollo con las últimas características y correcciones.
- `feature/*`: Ramas de características para el desarrollo de nuevas funcionalidades.
- `release/*`: Ramas de lanzamiento para preparar nuevas versiones.
- `hotfix/*`: Ramas de corrección para solucionar errores críticos en producción.


## Licencia

Este repositorio es de uso exclusivo de 3+1R® y TickMint™. Todos los derechos reservados.
