Movy App
=================================

This Android app is for those who have in movies a hobby (Movie + Hobby = Movy) as they can follow the latest info about their favorite movies.

## Usage
To be able to execute this project, simply download it from GitHub and import it in Android Studio: Gradle and the IDE will, then, be able to import and load all necessary dependencies and particularities of the project.

To install the app, execute the Run command in the IDE menu.

## Dependencies
A few external libraries where used in this project to make development easier:
### The powerful combination: Retrofit + Gson + OkHttp
To help handle HTTP requests to the TheMovieDatabase API Retrofit was added;

To help handle HTTP response data parse a Retrofit extension called Gson was added;

And, finally, to enable cache and its management, OkHttp was added to the project.

### Picasso
To make image download easier, Picasso was added to the project as it can load and image, and cache it, directly in Android components.

### Dagger
To help manage dependencies injection.

### Card View
Android library that enables use of lastest material design component.

### Recycler View
Android library that enables use of data containers that can be scrolled.

### Espresso
UI test library to help validate business rules and app behavior. Just remember to disable Android animations on the devices being tested so they can't interfere.

### Espresso Contrib
UI test library to help handle RecyclerView actions when testing views.
