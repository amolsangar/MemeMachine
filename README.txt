BUILD STEPS

The steps are quite similar to previously completed assignments.

1. Extract the zip file and open the project in Android Studio


2. I have built and tested the application using the below configuration and would suggest the same settings.
 
4.7 WXGA API 23
Marshmallow 6.0

Android -
compileSdk 33
minSdk 23
targetSdk 32



3. I have used jitpack repository for zoom-in and zoom-out of images. Make sure the below code block is present in ROOT build.gradke file -

buildscript {
    repositories {
        maven { url "https://www.jitpack.io" }
    }
}



4. Make sure the following dependencies are properly installed. While building the app these should be automatically taken care of but please check that these are present in MODULE build.gradle file -

// dependency for zoom-in and zoom-out of images	
implementation 'com.github.chrisbanes:PhotoView:2.0.0'

// dependency for loading data from json file.
implementation  'com.android.volley:volley:1.2.1'

// dependency for loading image from url.
implementation 'com.squareup.picasso:picasso:2.71828'

// dependency for creating a circle image.
implementation 'de.hdodenhof:circleimageview:3.1.0'



5. settings.gradle has the following code block present where maven url is added - 

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}



6. Run the app after starting the emulator



7. After running the app, on first run it will ask for the following permissions - 

STORAGE PERMISSION - For downloading the 'Build Your Own' images
USAGE PERMISSION - For notification and time spent tracking

Please ALLOW both these permissions.

INTERNET ACCESS is also required as images are fetched from APIs.



POINTS TO NOTE
1. Download function was not working on my personal device due to unknown reason. Although it works fine on the emulator and stores the files in MemeMachine folder in ROOT directory.
2. Notification wont popup if you put values apart from numbers in 'Time Spent' field within Settings.



REFERENCES
- Custom RecyclerView in Android
https://www.geeksforgeeks.org/how-to-build-an-instagram-like-custom-recyclerview-in-android/
https://www.youtube.com/watch?v=RCSH7U-Hcew

- Navigation Drawer 
https://medium.com/android-beginners/getting-started-with-navigation-drawer-in-android-material-design-382ab03b97dd

- Dropdown Menu
https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860
https://www.youtube.com/watch?v=Es5UFII4oak
https://learningprogramming.net/mobile/android/create-dynamically-popup-menu-in-android/

- Settings
https://developer.android.com/develop/ui/views/components/settings
https://developer.android.com/develop/ui/views/components/settings/customize-your-settings

- Time Tracking 
https://medium.com/@afrinsulthana/building-an-app-usage-tracker-in-android-fe79e959ab26

- Image Saving
https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android

- Spinner dropdown
https://www.digitalocean.com/community/tutorials/android-spinner-drop-down-list

- Saving Image Locally
https://stackoverflow.com/questions/32799353/saving-image-from-url-using-picasso

- Share Image
https://stackoverflow.com/questions/13970100/android-share-image-in-imageview-without-saving-in-sd-card

- Keyboard hide when clicked outside
https://stackoverflow.com/questions/36889141/hide-android-soft-keyboard-in-fragment-when-clicked-on-outside

- Extra
https://stackoverflow.com/questions/36100187/how-to-start-fragment-from-an-activity
https://icon.kitchen/
https://stackoverflow.com/questions/6650398/android-imageview-zoom-in-and-zoom-out

