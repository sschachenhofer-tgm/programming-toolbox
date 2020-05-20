import requests

from abc import ABC, abstractmethod


class MainModel:
    """
    A base model class for converting between currencies. This class uses an exchangable Converter object for the
    actual conversion - this makes it possible to switch between online exchange rates and offline exchange rates at
    runtime.
    """

    def __init__(self, converter):
        """
        Initialize the MainModel and set the Converter object that should be used.

        :param converter: The Converter object to be used. Converter objects need to subclass AbstractConverter.
        """
        self.converter = converter

    def convert(self, value, from_currency, to_currencies):
        """
        Convert money from a currency to other currencies.

        This method converts the specified amount of money from the currency specified by ``from_currency`` to
        all the currencies specified in ``to_currencies`` and return the result as HTML-formatted text.
        The actual conversion is done by the underlying Converter class (a subclass of AbstractConverter).

        :param value: The value, in the currency specified by ``from_currency``
        :param from_currency: A string specifying the original currency, using the standard currency symbols
            (e.g. USD for US Dollar)
        :param to_currencies: A string specifying the target currencies. The string should consist of all target
            currency symbols separated by a comma.
        :return: A string containing the HTML-formatted output text.
        """
        return self.converter.convert(value, from_currency, to_currencies)

    def set_converter(self, converter):
        """
        Set a new Converter instance for the MainModel object to use.

        MainModel supports different converters (all classes that subclass AbstractConverter). This function allows
        switching the converter at runtime.

        :param converter: The new converter to use. This needs to be a subclass of AbstractConverter
        """
        self.converter = converter


class AbstractConverter(ABC):
    """
    An abstract base class for all Converter classes.
    """

    api_url = "https://api.exchangeratesapi.io/latest"  # The endpoint of the REST API to be used for requests

    # The following strings are template strings for the HTML output the Converter objects return. The actual values
    # can be inserted into the strings using the .format() method of the str class.
    base_html_template = "<b>{val:.2f} {symbol!s}</b> entsprechen<ul>"
    target_html_template = "<li><b>{val:.2f} {symbol!s}</b> (Kurs: {rate:f})</li>"
    status_html_template = "</ul>Stand: {date}"

    @abstractmethod
    def convert(self, value, from_currency, to_currencies):
        """
        Convert money from a currency to other currencies.

        This method converts the specified amount of money from the currency specified by ``from_currency`` to
        all the currencies specified in ``to_currencies`` and returns the result as HTML-formatted text.
        Subclasses of AbstractConverter need to override this method.

        :param value: The value, in the currency specified by ``from_currency``
        :param from_currency: A string specifying the original currency, using the standard currency symbols
            (e.g. USD for US Dollar)
        :param to_currencies: A string specifying the target currencies. The string should consist of all target
            currency symbols separated by commas.
        :return: A string containing the HTML-formatted output text.
        """
        pass


class LiveDataConverter(AbstractConverter):
    """
    An implementation of AbstractConverter that uses online exchange rates from a REST API service.

    The current exchange rates are retrieved from the REST API at exchangeratesapi.io.
    """

    def __init__(self):
        """
        Initialize the LiveDataConverter.
        """
        super().__init__()

    def convert(self, value, from_currency, to_currencies):
        """
        Convert money from a currency to other currencies.

        This method converts the specified amount of money from the currency specified by ``from_currency`` to
        all the currencies specified in ``to_currencies`` and returns the result as HTML-formatted text.
        Exchange rates are queried in real-time from the REST API provided at exchangeratesapi.io.

        :param value: The value, in the currency specified by ``from_currency``
        :param from_currency: A string specifying the original currency, using the standard currency symbols
            (e.g. USD for US Dollar)
        :param to_currencies: A string specifying the target currencies. The string should consist of all target
            currency symbols separated by commas.
        :return: A string containing the HTML-formatted output text.
        """

        # Remove whitespace from the specified currencies
        base = from_currency.strip()
        targets = to_currencies.replace(" ", "")

        # The GET parameters used for the request to the API
        params = {
            "base": base,
            "symbols": targets
        }

        # Request the required exchange rates from the API
        resp = requests.get(AbstractConverter.api_url, params=params)

        if resp.status_code == 200:
            # If the request was successful, convert to the specified currencies using the response data

            json = resp.json()  # Parse the JSON response data

            # Insert the base currency into the result string
            html_str = AbstractConverter.base_html_template.format(val=value, symbol=base)

            # Loop through all target currencies and convert
            for target_currency in targets.split(sep=","):
                # Convert to the new currency using the exchange rate from the API
                target_value = value * json["rates"][target_currency]

                # Append the result of converting to target_currency to the result string
                html_str += AbstractConverter.target_html_template.format(
                    val=target_value, symbol=target_currency, rate=json["rates"][target_currency])

            # Append the state of the exchange rates to the result string
            html_str += AbstractConverter.status_html_template.format(date=json["date"])

            return html_str

        elif resp.status_code == 400 and "error" in resp.json():
            # In case of bad request parameters, the API returns an error message
            raise RuntimeError(f"HTTP-Abfrage fehlgeschlagen: {resp.json()['error']}")
        else:
            raise RuntimeError(f"HTTP-Abfrage mit Statuscode {resp.status_code} fehlgeschlagen")


class OfflineConverter(AbstractConverter):
    """
    An implementation of AbstractConverter that uses offline exchange rates.
    """

    # Exchange rates from EUR to all other supported currencies (exchange rates from other currencies are currently not
    # supported)
    rates = {
        "CAD": 1.4426,
        "HKD": 8.5368,
        "ISK": 135.1,
        "PHP": 56.553,
        "DKK": 7.4662,
        "HUF": 334.83,
        "CZK": 25.816,
        "AUD": 1.6126,
        "RON": 4.7496,
        "SEK": 10.6958,
        "IDR": 15456.94,
        "INR": 77.1615,
        "BRL": 4.5288,
        "RUB": 70.7557,
        "HRK": 7.411,
        "JPY": 117.59,
        "THB": 33.315,
        "CHF": 1.0847,
        "SGD": 1.506,
        "PLN": 4.3782,
        "BGN": 1.9558,
        "TRY": 6.1491,
        "CNY": 7.7784,
        "NOK": 9.8953,
        "NZD": 1.7375,
        "ZAR": 16.5576,
        "USD": 1.0889,
        "MXN": 21.4522,
        "ILS": 3.7877,
        "GBP": 0.88573,
        "KRW": 1304.83,
        "MYR": 4.5592
    }

    def __init__(self):
        """
        Initialize the new OfflineConverter.
        """
        pass

    def convert(self, value, from_currency, to_currencies):
        """
        Convert money from a currency to other currencies.

        This method converts the specified amount of money from the currency specified by ``from_currency`` to
        all the currencies specified in ``to_currencies`` and returns the result as HTML-formatted text.
        The exchange rates are stored offline and are not updated regularly.

        :param value: The value, in the currency specified by ``from_currency``
        :param from_currency: A string specifying the original currency, using the standard currency symbols
            (e.g. USD for US Dollar)
        :param to_currencies: A string specifying the target currencies. The string should consist of all target
            currency symbols separated by commas.
        :return: A string containing the HTML-formatted output text.
        """

        if from_currency == "EUR":
            # Currently, the only currency supported as the base currency is Euro.
            # TODO: Convert from other base currencies as well

            # Insert the base currency into the result string
            html_str = AbstractConverter.base_html_template.format(val=value, symbol=from_currency)

            # Insert the base currency into the result string
            for target_currency in to_currencies.split(sep=","):
                if target_currency in self.rates:
                    # Convert to the new currency using the exchange rate from the API
                    target_value = value * self.rates[target_currency]
                else:
                    # Raise an error if the target currency is not available offline (or doesn't exist)
                    raise KeyError(f"Die Zielwährung {target_currency} wird nicht unterstützt")

                # Append the result of converting to target_currency to the result string
                html_str += AbstractConverter.target_html_template.format(
                    val=target_value, symbol=target_currency, rate=self.rates[target_currency])

            # Append the state of the exchange rates to the result string
            html_str += AbstractConverter.status_html_template.format(date="2019-10-01")

            return html_str
        else:
            # If exchange rates from the base currency are not available offline, raise an Error
            raise RuntimeError(f"Für das Konvertieren von {from_currency} in andere Währungen sind keine Offline-Daten "
                               + "vorhanden")
