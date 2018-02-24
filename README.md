# Madrid Shopping
Práctica de Android Avanzado con Kotlin 4 del V KeepCoding Startup Engineering Master Bootcamp (2017)

## Goals
Create a mobile application to display information of Shops in Madrid, even when the user has no Internet connection. Shops should be shown in a Map.

## Requirements
1. When starting the App for the first time, if there's Internet connection it will download all information from the Shops access
point (see below), including all images.
2. The App will cache everything locally: images, data, etc. Even images of the maps. See below for tips.
3. Cache is never invalidated, so once everything has been saved, set a flag and never ever access to the network again.
4. If there's no Internet connection a message will be shown to the user.
5. While caching the App will show an Activity indicator or other loader. Until you finish caching you don't get to the Main menu.
6. The app will have a main menu screen where we'll add one button & a logo. The button takes us to the list of shops.
7. The list of Shops will show in the upper 50% screen a map with one pin for each shop.
8. The list of Shops will show in the lower 50% screen. Logo to the left, background image taking all the row, shop name in the front. Tapping a row takes us to the detail shop screen.
9. All info should be read from a Core Data database.
10. If you tap on a pin in the map a callout will open with the logo + shop name. Taping the callout takes us to the detail shop screen.
11. The map will be always centered in madrid, showing also the user location.
12. All data is at least in Spanish & English: should cache all and display in Spanish (if that's our phone's language) or English otherwise
13. Shop detail screen should show shop name, description, address, and a map showing the shops's location without any pin.


## Installation
To install the application clone this repository and open the project with Android Studio
```
$ git clone https://github.com/smarrerof/kc-android2
```
Create the "values resource file" google_maps_api_key (other names are valid as well)
Add the key GOOGLE_MAPS_API_KEY and put your google maps api key there. For instance
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="GOOGLE_MAPS_API_KEY">...</string>
</resources>
```

Run and enjoy the app in your favorite emulator or in your device.


## 
1. SQLite is used as cache. Shops and actvities are saved in SQLite when the app runs for the firt time. Logo, images and static map for each Shop/Activity are cached by [Picasso](http://square.github.io/picasso/).
2. Shop/Activity list and detail is shown with only one Activity. 

### Demo
![MadridShops Demo](https://github.com/smarrerof/kc-android2/blob/master/android_advaced_demo.gif?raw=true)


