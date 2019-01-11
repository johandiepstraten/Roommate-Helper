# day 1

Today I finished my proposal. 
I decided that the main aim of my app will be to let roommates create a group together in which they can share tasks.
What kind of tasks they can share depends on how fast the rest of the programming process will be.
For now I'll focus on sharing cleaning tasks and having a shared groceries list.
Furthermore I decided what the main screens of the app will be and put them into a visual.

# day 2

Today I finished the design document. 
I decided what classes I want to use: user, group & tasks. 
The grocery list will be a list of strings so a class probably won't be needed.
I also decided how I want to use the API and where in the app I want to use adapters.
I put everything in a visualisation.

# day 3

Today I started programming the app itself.
I spend most time creating a navigation drawer which is a menu that can always we accessed at the left side of the screen.
I decided to use activities instead of fragments for the different screens.
This requiers some duplicate code for the navigation drawer but will save time because I don't have to struggle with fragments.
I also decide to use the same API we used for the "trivia app" during last course instead of the Firebase API. Also for time related reasons.

# day 4

Today I created all screens and linked them to each other.
So right now the entire framework of the app is complete.
I also added some extra screens I realised I needed: Edit profile, login, create account, join group and create group.
I designed the login activity to be the new mainactivity. 
So users will have to log in or create an account before they can access the rest of the app.

# day 5

Today I changed the structure of the classes.
Each user can be assigned to one group.
If the user is not in a group yet it can join an existing group or create a new one.
Groups will have a unique name and password as well.
I created a server with the CS50 IDE.
I am figuring out how to store user data in an online json file so that users can create an account and use this account to log in. 
