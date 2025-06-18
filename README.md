# 🧾 Order State Transition - Saga Pattern with Kafka

This diagram illustrates how the order lifecycle is managed using the **Saga pattern** across distributed microservices with **Kafka** as the event bus.

## 📌 Architecture Overview

- **Microservices involved:**
    - `Order Service` (Saga Coordinator)
    - `Payment Service`
    - `Restaurant Service`

- **Communication:**
    - Asynchronous, via Kafka topics
    - Each service manages its own ACID-compliant transaction and publishes events

## 🧩 Services and Responsibilities

| Service | Responsibility |
|--------|----------------|
| **Order Service** | Initiates the order flow, coordinates the saga, manages order state transitions |
| **Payment Service** | Processes payments and sends response back |
| **Restaurant Service** | Confirms or rejects restaurant order preparation |

## 🔁 Order State Transitions

| Event | Transition |
|-------|------------|
| Order created | `PENDING` |
| Payment successful | `PENDING → PAID` |
| Restaurant approved | `PAID → APPROVED` |
| Payment/approval failed | `→ CANCELLING → CANCELLED` |

## 📬 Kafka Topics

| Topic Name | Description |
|------------|-------------|
| `payment-request-topic` | Sent by `Order Service` to initiate payment |
| `payment-response-topic` | Sent by `Payment Service` with payment result |
| `restaurant-approval-request-topic` | Sent by `Order Service` to request approval |
| `restaurant-approval-response-topic` | Sent by `Restaurant Service` with approval result |

## 🛡️ Saga Pattern Flow (Orchestration)

1. `Order Service` creates a new order → sets state to `PENDING`
2. Sends message to `payment-request-topic`
3. `Payment Service` processes the payment
    - On success → replies to `payment-response-topic` → order moves to `PAID`
    - On failure → saga is cancelled → moves to `CANCELLED`
4. After payment success, sends message to `restaurant-approval-request-topic`
5. `Restaurant Service` confirms or rejects the order:
    - If approved → order moves to `APPROVED`
    - If rejected → saga cancelled → order moves to `CANCELLED`

## 🗃️ Database Transactions

Each service maintains **ACID transactions** in its own database. Consistency is maintained **eventually**, not through distributed transactions.

