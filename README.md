# 🏷️ **Inventory Management System**

## **📖 Overview**
The **Inventory Management System** is a microservices-based web application designed to efficiently manage products, inventory, and orders. It is divided into three main microservices: **Product Service**, **Inventory Service**, and **Order Service**. Each service is modular and communicates via REST APIs, providing scalability and ease of management.

This system has two user roles if you implement a frontend application for the backend:
- **👩‍💼 Admin**: Manages products and inventory.
- **👨‍💻 User**: Places orders and views product availability.

---

## **🛠️ Services**
The system is composed of the following microservices:

### **1️⃣ Product Service**
Manages product details and availability:
- 📝 Create, update, and fetch product details.
- 🔄 Automatically updates product availability based on inventory changes.

### **2️⃣ Inventory Service**
Manages product stock levels:
- ➕ Add or update stock for products.
- 📊 Tracks real-time stock levels.
- 🔄 Automatically updates the product's availability when stock changes.

### **3️⃣ Order Service**
Handles user orders:
- 🛒 Place new orders.
- 🔻 Deduct stock levels upon successful order placement.
- ✅ Checks product availability before order confirmation.

---

## **✨ Features**
### **👩‍💼 Admin Functionalities**
1. **Product Management**:
   - ➕ Add new products.
   - 🛠️ View and update product details.
   - 🚦 Toggle product availability manually.
2. **Inventory Management**:
   - ➕ Add or update stock levels.
   - 📊 View current stock for all products.
3. **Automation**:
   - 🔴 Updates product availability to `false` when stock reaches zero.
   - 🟢 Updates product availability to `true` when stock is added.

### **👨‍💻 User Functionalities**
1. **Browse Products**:
   - 🔍 View all available products with their details.
2. **Place Orders**:
   - 🛒 Add products to the cart and place an order.
   - 🔻 Automatically reduces stock levels in the inventory.
3. **Order History**:
   - 📝 View past orders and their statuses.

---

## **🌐 REST API Integration**
The services communicate via REST APIs, which ensures:
- 🛠️ **Loose coupling between services**.
- 📈 **Scalability and modularity**.
- 🔗 **Easy integration** with frontend clients or other systems.

---

## **🤔 Why We Use RestTemplate/RestClient**
- **🔄 Inter-Service Communication**: 
  - `RestTemplate` or `WebClient` is used for synchronous communication between microservices. For example:
    - The **Order Service** calls the **Inventory Service** to check stock availability.
    - The **Inventory Service** updates the **Product Service** about stock changes.
- **💡 Ease of Integration**:
  - REST APIs enable seamless integration of services and allow adding or updating services without significant changes to the system.
- **⚠️ Error Handling**:
  - Provides mechanisms to handle service errors and timeouts effectively.

---

# **📋 API Endpoints Documentation**

This document provides an overview of all the endpoints in the **Inventory Management System**, categorized by their respective services.

---

## **1️⃣ Product Service**

**Base URL**: `http://localhost:8084`

| 🔍 **HTTP Method** | **Endpoint**          | **Description**                              |
|---------------------|-----------------------|----------------------------------------------|
| 🟢 `POST`           | `/products`          | Creates a new product with default availability set to `false`. |
| 🔵 `GET`            | `/products`          | Retrieves a list of all products.            |
| 🔵 `GET`            | `/products/{id}`     | Retrieves details of a product by its ID.    |
| 🟡 `PUT`            | `/products/{id}`     | Updates details of an existing product.      |
| 🔴 `DELETE`         | `/products/{id}`     | Deletes a product by its ID.                 |

---

## **2️⃣ Inventory Service**

**Base URL**: `http://localhost:8086`

| 🔍 **HTTP Method** | **Endpoint**          | **Description**                              |
|---------------------|-----------------------|----------------------------------------------|
| 🟢 `POST`           | `/inventory`         | Adds stock for a product. Updates product availability to `true` if stock > 0. |
| 🟡 `PUT`            | `/inventory/{id}`    | Updates stock for a product.                |
| 🔵 `GET`            | `/inventory`         | Retrieves a list of all inventory records.   |
| 🔵 `GET`            | `/inventory/{productId}` | Retrieves inventory details for a specific product by its ID. |

---

## **3️⃣ Order Service**

**Base URL**: `http://localhost:8088`

| 🔍 **HTTP Method** | **Endpoint**          | **Description**                              |
|---------------------|-----------------------|----------------------------------------------|
| 🟢 `POST`           | `/orders`            | Places a new order. Deducts stock and updates product availability if stock reaches zero. |
| 🔵 `GET`            | `/orders`            | Retrieves a list of all orders.              |
| 🔵 `GET`            | `/orders/{id}`       | Retrieves details of a specific order by its ID. |

---

## **📚 How to Use**

1. **👩‍💼 Admin Endpoints**:
   - Use the Product Service and Inventory Service endpoints to manage products and stock.
   - Example: Add a new product using `/products`, then add stock using `/inventory`.

2. **👨‍💻 User Endpoints**:
   - Use the Order Service endpoints to browse available products and place orders.
   - Example: Check product availability via `/products` and place an order using `/orders`.

3. **🔗 Inter-Service Communication**:
   - The services are interconnected:
     - **Order Service** updates stock via **Inventory Service**.
     - **Inventory Service** notifies **Product Service** to update availability.

---
