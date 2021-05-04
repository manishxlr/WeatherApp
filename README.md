# WeatherApp
WeatherApp is an application to browse live weather condition in any particular city around the world. Some of the features except searching for the weather conditions, include creating/managing a list of "favorite" destinations and caching data of your "favorite" cities.
APIs used are from [openweathermap.org](https://openweathermap.org/current). The application has been implemented in MVVM architecture using Kotlin language and Retrofit Library for network calls.

The UI will be like the screenshots attached below.

App Flow :
* Clicking on search bar will open the Search Page.
* Clicking on item of search page will redirect back to home page with weather of selected city showing.
* Clicking on Plus icon will add the current showing city to favourites (if not added already).
* Clicking on favourite cities will refresh its data.
* Clicking on cross of favourite cities will remove the city.

<img src="https://user-images.githubusercontent.com/41373854/116959646-cfcb4880-acbb-11eb-8637-3e3fb2e56c07.jpeg" width="300" height="600">   <img src="https://user-images.githubusercontent.com/41373854/116959658-da85dd80-acbb-11eb-9efc-97715c0d0156.jpeg" width="300" height="600">


## Libraries/Tools Used
* Shared Preferences
* Retrofit
* ViewModel
* LiveData
* OkHttp
* Gson
* Spannable


**Icons used are downloaded from [https://www.flaticon.com/](https://www.flaticon.com/)**
