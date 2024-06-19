 # LuxCoin Converter
Challenge proposed by alura as a project to put into practice what was learned in the java course - object orientation
LuxCoin Converter is a simple currency conversion application that uses two APIs to provide updated exchange rates and full currency names. The application allows users to convert values between supported currencies.

## Features

- Real-time currency conversion using the ExchangeRate API.
- Fetching full currency names using the Open Exchange Rates API.
- Support for a fixed list of currencies:
  - USD - United States Dollar
  - EUR - Euro
  - BRL - Brazilian Real
  - JPY - Japanese Yen
  - CAD - Canadian Dollar
  - CZK - Czech Koruna
  - INR - Indian Rupee
  - CNY - Chinese Yuan
  - CUP - Cuban Peso
  - AOA - Angolan Kwanza

## Requirements

- Java 8 or higher
- An internet connection to access the APIs

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/luxcoin-converter.git
    ```
2. Navigate to the project directory:
    ```bash
    cd luxcoin-converter
    ```
3. Compile the project:
    ```bash
    javac -d bin src/com/curso_alura/coinconverter/*.java src/com/curso_alura/coinconverter/http/*.java src/com/curso_alura/coinconverter/service/*.java
    ```
4. Run the project:
    ```bash
    java -cp bin com.curso_alura.coinconverter.main.CurrencyConverter
    ```

## Usage

1. Run the program.
2. Follow the menu instructions to select the source and target currencies.
3. Enter the amount to be converted.
4. The program will display the converted amount.

## Project Structure

The project is organized as follows:

```plaintext
luxcoin-converter/
├── src/
│   ├── com/curso_alura/coinconverter/
│   │   ├── main/
│   │   │   └── CurrencyConverter.java
│   │   ├── http/
│   │   │   ├── HttpClient.java
│   │   │   ├── HttpResponse.java
│   │   │   └── HttpJsonResponse.java
│   │   └── service/
│   │       └── CurrencyService.java
├── README.md
└── .gitignore
```

`` CurrencyConverter.java ``
- The main class containing the main method and the user interaction logic.

`` HttpClient.java ``
- Class responsible for sending HTTP requests.

`` HttpResponse.java ``
- Interface for managing API responses.

`` HttpJsonResponse.java ``
- Implementation of the HttpResponse interface for handling JSON responses.

`` CurrencyService.java ``
- Service class containing business logic for fetching currency names and converting values.

## API Configuration
 The project uses two APIs:

**ExchangeRate API:** To get exchange rates.<br/> 
**Open Exchange Rates:** To get full currency names.
### Getting API Keys
**ExchangeRate API:**

Sign up at [ExchangeRate API](https://www.exchangerate-api.com/)) to get your API key.
Replace YOUR-API-KEY in the CurrencyService.java file with your API key.<br/> 
<br/> 
**Open Exchange Rates:**

Sign up at [Open Exchange Rates](https://openexchangerates.org/) to get your App ID.
Replace YOUR_APP_ID in the CurrencyService.java file with your App ID.

## Contribution
Contributions are welcome! Feel free to open issues and pull requests.

## License
This project is licensed under the terms of the MIT license. See the LICENSE file for more details.
