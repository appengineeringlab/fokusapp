Pomodoro App
==================

The pomodoro app is part of my *#1App1Month* challenge where I try to learn android by creating one application per month.\
If you would like to support me consider [grabbing a coffe
<img src="https://image.flaticon.com/icons/svg/2261/2261640.svg" alt="alt text" width="24px" height="24px">.](https://www.paypal.me/appengineeringlab)

## Contents

- [More about challenge](#more-about-challenge)
- [Technologies and concepts used](#technologies-and-concepts-used)
- [Pomodoro App](#pomodoro-app)
- [License](#license)

## More about challenge
The challenge should last 3-6 months and provide the same amount of applications finished and published on the play store. I should learn all the important concepts that android is offering and be able to apply them efficiently in new situations. Every application should introduce some new concepts, so not all the apps will have everything, for example, some apps won't have tests, some apps won't have local database, others won't have network access, etc. 

## Technologies and concepts used
I have used:
- [Dagger2](https://dagger.dev/) as depedency injection framework. You can check the configuration in package called ***di***. I have some redundant code and some dependencies aren't provided through their interfaces, unit testing should solve this.
- [Room](https://developer.android.com/topic/libraries/architecture/room) + [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) for managing local database. You can check it package called  ***data***. I have also lightly touched with [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- [Material.IO](https://material.io/) I have used some material android components, like bottom app bar which you can see in *MainActivity*.

## Pomodoro App
<img src="https://drive.google.com/uc?export=view&id=1JXHQWxEWsOOR-COYbMcndNHs9At8lvv7" alt="Pomodoro-App" width="216px" height="444px">

Pomodoro app is simple time tracker you can use to achieve your daily goals. You have sessions, usually 25 minutes long, after each session you have short break usually 5 minitues long, and for example 4 sessions creates one round after which you can have long break which is usually as long as one session. You are able to set how many sessions you want, how many rounds, length of session and each break.\
In the menu their is Settings option that is used for tweaking all mentioned above.\
There is also support developers menu option which opens web browser and leads to paypal.me page.\
The last option in menu is contact which opens email app for sending message to [appengineeringlab](http://www.labappengineering.com).

## Licence 
Please refer to this repo [licence file](LICENSE).
