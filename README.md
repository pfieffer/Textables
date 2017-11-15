# Textables 

> uses volley library to parse JSON from "https://raw.githubusercontent.com/OTGApps/Textables/master/resources/content.json"

### To test:
* Type `git clone https://github.com/pfieffer/Textables.git` or `git clone git@github.com:pfieffer/Textables.git` in your terminal or git bash
* Open the project in Android Studio and run.

### Basic overview of working mechanism
* Makes a `GET` request on the URL mentioned above.
* Parses the JSON Array found and sets them to our model object `Textable.java` and the object is used to set the string `name` and `art` to an `ArrayList`.
* The `ArrayList` is finally set to the `ArrayAdapter`, which in turn is set to a `ListView`.
* The `SetOnItemLongClickListener` is applied to the `ListView` to copy the content of the current listitem into the clipboard.


### The screenshots to the app are:

[![Screenshot_20171115-130044.jpg](https://s17.postimg.org/tpumxsgwf/Screenshot_20171115-130044.jpg)](https://postimg.org/image/472akrxcb/)

[![Screenshot_20171115-130055.jpg](https://s17.postimg.org/qvrhkcu5r/Screenshot_20171115-130055.jpg)](https://postimg.org/image/e4dbdukdn/)

