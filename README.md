# Cash Register Simulator

A cash register simulator that uses an interactive menu to allows users to:

- Upload items via plain text files
- Purchase items 
- Checkout customers

The program also generates a receipt file upon checkout.

#### To Build:
Download the java source code and run the following command in the same directory:
```
javac *.java
```
![Screenshot](docs/images/compile.png)

#### Run:
```
java CashRegisterDriver
```

![Screenshot](docs/images/start.png)

#### Use:
Upload inventory items and cashier data by providing the path to the source file:

Inventory ([file format](src/Inventory.txt))
![Screenshot](docs/images/uploaded-inventory.png)

Cashiers (Optional) ([file format](src/Cashier.txt))
![Screenshot](docs/images/uploaded-cashiers.png)