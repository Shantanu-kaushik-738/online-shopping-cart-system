# Online Shopping Cart System

A simple menu-driven console project made with core Java. It demonstrates `HashMap`, a custom linked list, `Stack`, bubble sort, methods, and text-file handling.

## 1. What does this project do?

A customer can:
1. See the available products.
2. Add a product and quantity to the cart.
3. See cart items and the total bill.
4. Remove the last item they added.
5. Sort cart items from highest item total to lowest.
6. Checkout and clear the cart.
7. Save the cart and exit.

## 2. Project folder structure

```text
online-shopping-cart-system/
├── src/
│   └── Main.java          # Main program and supporting classes
├── docs/
│   └── PROJECT_REPORT.md  # Project report
├── products.txt           # Product catalogue
├── cart.txt               # Saved cart (created automatically on exit)
├── README.md
└── .gitignore
```

## 3. Java concepts in simple words

| Concept | How this project uses it |
|---|---|
| `HashMap` | Finds a product quickly using its ID, such as `P101`. |
| Linked list | Stores cart items as nodes connected using `next`. |
| `Stack` | Remembers added cart nodes. `pop()` removes the latest addition first. |
| Bubble sort | Compares neighboring item totals and sorts high to low. |
| Methods | Keeps tasks separate, such as `addToCart()` and `checkout()`. |
| File handling | Reads products from `products.txt` and saves the cart in `cart.txt`. |

### How the cart works

Each cart node contains:
- A `Product` reference
- The quantity
- A `next` reference to the next node

`head` points to the first node and `tail` points to the last node. When a new item is added, it is attached after `tail`.

The Stack stores references to those same nodes. When the user chooses remove, `pop()` gives the most recently added node. The program then searches for that node in the linked list and unlinks it.

## 4. Product file format

`products.txt` contains one product per line:

```text
P101|Notebook|50
P102|Pen|10
P103|Bag|800
```

The three values are product ID, product name, and price. Keep this file in the main project folder (the same folder from which you run the program).

## 5. How to run

Install JDK 8 or later. Open a terminal in the project folder and run:

```bash
javac -d out src/Main.java
java -cp out Main
```

Do not run the program from inside `src`; run it from the main project folder so it can find the text files.

## 6. Menu options

| Number | What it does |
|---|---|
| 1 | View products |
| 2 | Add to cart |
| 3 | View cart |
| 4 | Remove last added item |
| 5 | Sort by item total, high to low |
| 6 | Checkout |
| 7 | Save and exit |

Quantity must be a positive whole number. For example, `2` is valid; `0`, `-1`, and `2.5` are invalid.

## 7. Given test cases

**Test 1 — Add Notebook x2**

- Choose option `2`.
- Enter product ID `P101`.
- Enter quantity `2`.
- Expected: `Added: Notebook x2 = 100`.

**Test 2 — View an empty cart**

- Choose option `3` before adding anything.
- Expected: `Your cart is empty.` and `Total: 0`.

**Test 3 — Invalid product**

- Choose option `2` and enter `P999`.
- Expected: `Product P999 not found.`

**Test 4 — Sort and checkout**

- Add `P101`, quantity `2` (100).
- Add `P103`, quantity `1` (800).
- Expected after adding Bag: `Added: Bag x1 = 800`.
- Choose option `5`.
- Expected order: `1. Bag | 800`, then `2. Notebook | 100`.
- Choose option `6`.
- Expected: `Order placed. Total: 900`.

## 8. Files and saving

- `products.txt` is read when the program starts.
- The cart is saved in `cart.txt` when option 7 is selected.
- The saved cart is loaded the next time the program starts.
- Checkout clears the cart and saves the empty cart.


## 10. A good way to understand the code

Read `src/Main.java` in this order:
1. `Product` — understand what product data is stored.
2. `CartNode` — understand one linked-list node.
3. `Cart.add()` — see how a node is added.
4. `Cart.removeLastAdded()` — see how Stack and linked list work together.
5. `Cart.sortHighToLow()` — understand the bubble sort.
6. `main()` — understand how the menu calls the methods.
