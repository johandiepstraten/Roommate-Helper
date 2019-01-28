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

# day 6

Today I wrote all code to post and request data for users and groups from the online json file.
Due to a problem with an updated version of android I couldn't run the app with these new functionalities on my phone yet.
Therefore I'm afraid it will be a bit buggy tomorrow.

# day 7

Today I managed to get the app running on my phone.
I spent most of the day debugging small irregulaties in my code.
It's a slow but steady process with good perspective.
I decided to put the group_id in the user class instead of the group name. This way multiple groups can have the same name.
users without a group will get group_id 0 because the API won't assign that id number. This way all users with no group will be sent to the "join group activity" where they can join an existing group or create a new one.
I also decided that each group class will contain a list with user_id's. This is more error proof than the usage of usernames.
Lastly I'm having some troubles with passing on the class of the logged in user by intents between all activities.

# day 8

Eye surgery.
Sick day because of surgery.

# day 9

Rehabilitating from eye surgery.

# day 10

Rehabilitating from eye surgery.

# day 11

Rehabilitating from eye surgery.

# day 12

Over the next few days I'll have to work extra hard to compensate for the last days that I missed. Today I fixed all bugs in the so far written code. The logged in user is now passed on correctly through the entire app. This way it stays "logged in". I also managed to let the user create or join groups if the user is not allready in a group. A group can be joined if the user knows the username and password of the group. This is not ideal but letting users join groups by email requests was to time consuming. I decided to not implement a functionality that sends users push notifications once certain changes in the group are made. Mostly due to the fact that this would require the app to make requests to the online database even when the app is not actively running. Another option would have been to implement some sort of local database complementary to the online database. This would both be possible if not for the limited amount of time. 

# day 13

Now that users can be created and assigned to group I added the functionality to add and delete items from a shared grocery list. If a user adds an item, everyone in the group sees it and is able to delete it from the list once bought. This grocery list is displayed to the user as a listview with an adapter in both the main screen of the app as the "Grocery" screen where items can also be added and deleted. Furthermore, I've written all code for the "account activity" and the "info activity". The user can see and edit information about its account and see general information about the app respectively. Also, I started coding the part of the app where users can see their group and add tasks to it. I completed the functionality where users can leave their current group. In that case the user is sent straight to the screen in which he or she has to join/create a new group. I still have some dificulties implementing the tasks, because the current structure in which tasks are represented in the class (as an arraylist of objects) is not compatible with my online database. So I still have a lot of problems with the post and request activities regarding tasks.

# day 14

Hackathon day

# day 15

I changed the way I post and request the arraylist of tasks in the group. In the post activity everything is converted to a list of strings within a list of tasks. At the request activity everything is converted back. This way I can still work with the tasks as class objects in the rest of my code. Furthermore, I made a formula through which all tasks are equally divided and rotated between the users. The initial time is saved when a task is created. Everytime the app is started the current time is compared with the initial time. This way it is possible to assign the task to the right group member and hide a task temporarily when it is finished for that moment. Now, in the main page, only the tasks that the user still has to perform are displayed. All tasks and their progress can be viewed and managed in the group activity. Lastly, I added an alertbuilderdialog to double check if the user is sure when it asks to leave the group or log out.

# day 16

Today I made the last minor changes in the functionality of the app. I added a functionality that automatically logs in a user when it creates a new account. I updated the layout with clearer title colors, round buttons and icons in the navigation drawer. Some other students tested the app for bugs and found out that the app crashes when a comma is given in the input of a task or grocery item. This is because commas are seen as seperators in lists. I updated my code with more controls on user input to prevent these errors.

