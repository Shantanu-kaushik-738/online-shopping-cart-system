# Project Report: Online Shopping Cart System

## 1. Introduction

The Online Shopping Cart System is a beginner-level Java console application. It allows a user to view products, add products to a cart, remove the last added item, sort cart items, and checkout.

## 2. Problem statement

Create a console shopping cart in Java. Products are loaded from `products.txt` and stored in a HashMap. Cart entries are stored in a custom linked list. A Stack remembers the order in which entries are added so the latest addition can be removed. The cart can be viewed, sorted, checked out, and saved to a file.

## 3. Objectives

- Understand product lookup using HashMap.
- Create a linked list using a node class.
- Use Stack to implement last-in, first-out removal.
- Apply bubble sort to sort item totals in descending order.
- Divide the program into small methods.
- Read and write data using text files.

## 4. Data structures

### 4.1 HashMap

A HashMap stores pairs of keys and values. In this project, the product ID is the key and the Product object is the value. For example, `P101` maps to the Notebook product.

### 4.2 Linked list

A linked list is made of nodes. Each `CartNode` stores a product, quantity, and reference to the next node. `head` points to the first node; `tail` points to the last node.

### 4.3 Stack

A Stack follows LIFO (Last In, First Out). When a cart node is added, its reference is pushed onto the Stack. To remove the last added item, the program pops the Stack and removes that node from the linked list.

### 4.4 Bubble sort

Bubble sort compares two neighboring values. If the left item's total is smaller than the right item's total, their positions are swapped. Repeating these comparisons puts the largest item total first.

## 5. File handling

`products.txt` stores product details in `ID|Name|Price` format. `cart.txt` stores cart entries in `ID|Quantity` format. Product data is read at startup. Cart data is loaded at startup and saved when the user exits or completes checkout. Files are opened with try-with-resources so they are always closed and the data is really written to disk. Invalid lines in either file are skipped instead of stopping the program.

## 6. Main methods

| Method | Purpose |
|---|---|
| `loadProducts()` | Reads products from the text file. |
| `viewProducts()` | Displays products. |
| `addToCart()` | Checks product ID and quantity, then adds an item. |
| `viewCart()` | Displays cart items and total bill. |
| `removeLastAddedItem()` | Removes the latest addition using Stack. |
| `sortCart()` | Sorts item totals from high to low. |
| `checkout()` | Displays the total and empties the cart. |
| `loadCart()` | Restores saved cart data. |
| `saveCart()` | Writes current cart data to the file. |

## 7. Test cases

| Test | Input / action | Expected output |
|---|---|---|
| 1 | Add P101, quantity 2 | `Added: Notebook x2 = 100` |
| 2 | View an empty cart | `Your cart is empty.` and `Total: 0` |
| 3 | Add P999 | `Product P999 not found.` |
| 4 | Add P101 x2 and P103 x1, sort, checkout | `Added: Bag x1 = 800`; `1. Bag \| 800`, `2. Notebook \| 100`; `Order placed. Total: 900` |
| 5 | Add quantity 0 or a negative quantity | Quantity validation message |

## 8. Complexity

Let `n` be the number of cart entries.

| Operation | Approximate time |
|---|---:|
| Find product in HashMap | O(1) average |
| Add item at linked-list tail | O(1) |
| Remove latest added item | O(n), because the linked list is searched |
| View cart / calculate total | O(n) |
| Bubble sort | O(n²) |

## 9. How to compile and run

Run from the main project folder:

```bash
javac -d out src/Main.java
java -cp out Main
```

## 10. Conclusion

This project gives a practical introduction to HashMap, linked lists, Stack, bubble sort, methods, and file handling. The program is divided into small methods and includes the required shopping-cart operations and test scenarios.
