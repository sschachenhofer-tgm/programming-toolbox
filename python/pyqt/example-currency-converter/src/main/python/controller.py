import model

from layout import Ui_MainWindow

from PyQt5.QtWidgets import QMainWindow
from requests.exceptions import ConnectionError


class Controller(QMainWindow):
    """
    The PyQt controller class, responsible for processing GUI events.

    This class subclasses QMainWindow to work with the layout.py file (which was created from a .ui file using pyuic5).
    Without subclassing QMainWindow, this class wouldn't have access to the GUI components (such as buttons, input
    fields etc.)
    """

    def __init__(self):
        """
        Initialize the controller
        """
        super().__init__()

        self.view = Ui_MainWindow()  # Instantiate the view class
        self.view.setupUi(self)  # Set up the UI

        # Connect signals and slots
        # Reset button clears all input fields and the output
        self.view.reset_button.clicked.connect(self.view.value_input.clear)
        self.view.reset_button.clicked.connect(self.view.from_currency_input.clear)
        self.view.reset_button.clicked.connect(self.view.to_currency_input.clear)
        self.view.reset_button.clicked.connect(self.view.result_display.clear)
        
        self.view.exit_button.clicked.connect(self.close)  # Exit button closes the window (and quits the application)
        self.view.live_data.stateChanged['int'].connect(self.use_live_data)  # state_changed checkbox switches modes
        self.view.convert_button.clicked.connect(self.convert)  # Convert button calls convert() method of controller
        
        
        # Instantiate the model using an instance of LiveDataConverter (for using online exchange rates)
        self.model = model.MainModel(model.LiveDataConverter())

    def convert(self):
        """
        Convert money from a currency to other currencies.

        This method reads the user input from the UI and converts the value from the specified base currency to all the
        specified target currencies. The result is displayed as HTML formatted text in the text field.
        """

        # Read the values from the input fields
        base_value = self.view.value_input.value()
        base_currency = self.view.from_currency_input.text().strip()  # Remove leading and trailing whitespace
        target_currencies = self.view.to_currency_input.text().replace(" ", "")  # Remove whitespace

        try:
            # Convert to the target currencies and display the result in the TextEdit
            html_str = self.model.convert(base_value, base_currency, target_currencies)
            self.view.result_display.setHtml(html_str)
            self.view.statusBar.showMessage("Umrechnung ausgeführt")
        except RuntimeError as e:
            # Catch RuntimeErrors and display the error messages as status message
            self.view.statusBar.showMessage(str(e))
        except KeyError as e:
            # Catch KeyErrors and display the error messages as status message
            self.view.statusBar.showMessage(str(e))
        except ConnectionError as e:
            # Catch ConnectionErrors and display the error messages as status message
            self.view.statusBar.showMessage(
                "Beim Abrufen der Wechselkurse ist ein Fehler aufgetreten - bitte überprüfe deine Internetverbindung")

    def use_live_data(self, checkbox_state):
        """
        Switch between using current exchange rates and offline exchange rates for conversion.

        If live data should be used, the exchange rates are retrieved from exchangeratesapi.io. The application however
        also supports using exchange rates that were cached offline. These may be not as accurate.

        :param checkbox_state: The state of the "Live Data" checkbox. Since the checkbox is not a tristate checkbox,
            there are two possible values (0 and 2). 0 (unchecked) means that the offline exchange rates should be used.
            2 (checked) means that the exchange rates should be queried from exchangeratesapi.io.
        """
        if checkbox_state == 0:
            # Switch to offline data (using an instance of OfflineConverter)
            self.model.set_converter(model.OfflineConverter())
            self.view.statusBar.showMessage("Es werden Offline-Daten verwendet")

        elif checkbox_state == 2:
            # Switch to online exchange rate data (using an instance of LiveDataConverter)
            self.model.set_converter(model.LiveDataConverter())
            self.view.statusBar.showMessage("Es werden aktuelle Online-Daten verwendet")
