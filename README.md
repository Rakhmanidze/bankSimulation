# Bank Simulation

## Project Overview
The **Bank Simulation** project simulates essential banking operations such as account management, payment card creation, transaction handling, and statistical analysis. It supports multicurrency transactions, customer state management (active, suspended), and employee action tracking. The system provides detailed reports on account transactions and other key statistics.

## Key Features
- **Account Management**:
  - Create, update, and delete customer accounts.
  - Manage account states (active, frozen).
  
- **Payment Card Management**:
  - Create and delete payment cards linked to accounts.
  
- **Transaction Handling**:
  - Perform multicurrency payment transfers with commission rates.
  - Generate transaction statistics (monthly and yearly).
  
- **Customer State Management**:
  - Simulate customer states (active, suspended).
  
- **Employee Action Tracking**:
  - Track employee actions related to customer account management and transactions.

- **Reporting**:
  - Monthly and yearly transaction statistics.
  - Generate detailed command-line reports displaying transaction data and statistics.

## Design Patterns
The project uses several design patterns to ensure scalability, flexibility, and maintainability:
- **Builder**: For creating complex entities like `Customer` and `Transaction`.
- **State**: For managing the states of `Customer` (active, suspended) and `Account` (active, frozen).
- **Mediator**: Facilitates interaction between employees and customers, handling operations like account modifications.
- **Strategy**: Used for:
  - Commission calculations for transactions.
  - Currency conversion between USD, CZK, and EUR.
  - Monthly and yearly statistics calculation.
- **Factory**: For creating commission strategies used in transactions.

## Main Entities
- **Account**: Represents a bank account linked to a customer and can have states like active or frozen.
- **Customer**: Represents a bank customer who can own multiple accounts and can have states such as active or suspended.
- **Employee**: Represents a bank employee who performs operations on customer accounts.
- **PaymentCard**: A payment card associated with a bank account.
- **Bank**: Represents the bank, with properties like name and address.
- **EmployeeAction**: Tracks the actions of employees on customer accounts.

## Reports
The simulation generates the following reports directly in the command line:
- **Monthly Statistics Report**: Displays transaction counts and values per account for a given month.
- **Yearly Statistics Report**: Aggregates the monthly data for the entire year.
- **Transaction and Account Reports**: Detailed information about account transactions, including associated costs.

## Usage

### Steps to Run the Simulation:

1. **Set Up Database**:
   - Ensure PostgreSQL is running.
   - Set up the required database and configure the database connection settings (e.g., username, password, URL).

2. **Run Configuration**:
   - The project includes configuration files (e.g., `Configuration1.java`, `Configuration2.java`) that initialize services, entities, and the simulation environment.

3. **Start the Simulation**:
   - Use the main configuration class to start the simulation:
     ```java
     Configuration1 configuration1 = new Configuration1();
     configuration1.startBankSimulation();
     ```

4. **Generating Reports**:
   - As the simulation runs, reports will be displayed in the command line, showing account statistics and other relevant data.

## Conclusion
The **Bank Simulation** project provides a modular and extensible system for simulating banking operations. By employing design patterns like Builder, State, Mediator, Strategy, and Factory, the project is scalable and easy to maintain. The simulation generates detailed reports that provide valuable insights into banking operations and customer activity.
