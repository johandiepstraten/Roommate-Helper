# Final Report programmeerproject Apps Johan Diepstraten 1077490

## Abstract:
I designed an App called "Roommate Helper". This app lets you create a group with your roommates. 
You can define task and state how often you want them to be performed. 
The app will divide them equally and show users what task they have to do and when. 
Roommates can also keep track of a shared grocery list.

## Problem: 
Living together with friends or other students is a lot of fun. 
However, with everyone living their own lives, the house/shared rooms can get chaotic.
Living in a messy environment can be unhealthy, therefore it's important to work together.
But it can be hard to keep track of who needs to do what.

## Solution:
The Roommate Helper app allows you to make groups with your roommates and define the tasks that need to be done in your common rooms. 
You can decide how often tasks need to be done and the app will equally divide them among all group members.
Each group member gets to see their current task and can sign off when the task is completed.
The app also allows roommates to keep track of a shared grocery list.
Everyone can add items to the grocery list and delete these items once bought.

![alt text](https://github.com/johandiepstraten/Roommate-Helper/blob/master/doc/overviewActivitysmall.png)

Screenshot of the mainscreen of the app. 
This screen gives the user an overview of the current tasks that are assigned to him/her and displays the groceries that need to be bought.

## Technical design:

Every user has its own account. This account is linked to a group. 
When a user is not linked to a group yet, it will be asked to either join an existing group or create a new one. 
Both users and groups are protected by passwords. So a user can join a group when he/she knows the password.
A user can only be connected to one group at the same time. 
When a user leaves the group, the app will give the option to join or create another group.
Once in a group the user sees an overview of his/her task that are not completed yet and of all groceries that need to be bought for the group
A menu opens up by clicking the menu icon in the upper left corner or sliding your finger from the left side of the screen to the middle.
This menu can be accessed in all activities of the app and allows the user to access the following functionalities of the app: The overview activity, the groceries activity, the group activity, the account activity and the info activity.
The overview activity is the screen with the previously mentioned overview. 
If the user clicks on a tasks he/she can indicate that this task is finished.
If the user clicks on an item in the grocerylist, the app will rederect him/her to the groceries activity.
In this activity the user can add new items to the list and delete items that were bought. This list can be viewed by the entire group.
In the group activity screen, users see an overview of all tasks that are defined by the group. 
Users see which tasks are allready completed and how many days are left to perform the remaining tasks.
Users can also add or remove tasks from the group in this section of the app.
In the account activity, the user see some information about itself and is able to change his/her username and password.
Users can also log out from this activity.
Lastly, the info activity gives a brief summary about the app and tells users how to use it.

![alt text](https://github.com/johandiepstraten/Roommate-Helper/blob/master/doc/Roommate_Diagram.png)

In this picture all screens are viewed together with the navigation between them.
Above every screen is displayed what adapters and helpers functionalities are used to communicate with the database and show the right data to the user.
All data is stored in an online JSON file. The data is stored in two seperate JSON files, one for every object of the users class and one for every object of the groups class.
As can be seen at the buttom of the picture, the app also makes use of a third "Task" class. 
Because tasks are inseperable from their groups, they are uploaded in the groups JSON file as well. As a list of class objects.
Throughout the app, three ways of communicating with the online JSON file are used. Post helpers upload new instances of a class to the file, put helpers alter existing instances and Request activities download all information from the given fill and return it as a list of class objects.

Every user and every group has a unique id. This way groups and users can be connected to each other.
In every user object the id of its group is saved and in every class object exists a list of user id's. 
Consequently, for every user, the right group can be loaded and, for every group, the tasks can be divided among the right users.
In every task the id of the current responsible user is saved as well. If users are not yet connected to a group, or left their group, their linked group id is set to "0". Because the group id "0" does not exist in the online JSON file, users who are not in a group can be distinguished and therefore be sent to the activities where they can either join or create a group.

When a user logs in, the user class object with his or her information is loaded together with the group object. These objects are passed on throughout the entire app so that every activity has access to the needed information. 

As soon as a task is created, the current time is saved in its class instance. Everytime a user logs in to the app the current time will be taken again. These two timestamps, together with the frequency of how often a task is expected to be performed, allows the app to calculate which user is responsible for which task at that current moment. If a user completes a task, the current time is saved as well. This way the responsible user only has to do the task once within the given time frame.

## Challenges: 

During the process of creating the app I faced several challanges that caused me to make changes in the app. 
The biggest challenge was to implement correct communication between the application and the online database. First I wanted to use a Firebase API to manage this interaction, but because this proposed great diffuculties time- and codewise I was forced to use a known online database. Since I had a relatively small amount of classes I had to design their structure wisely. Because putting the correct group object within each user object was devious, time consuming and difficult to implement in my database, I decided to keep track of users and groups seperately and link them to each other solely by their id numbers. Furthermore, users can only be assigned to one group instead of multiple ones. Also, joining a group requires the user to know the name and password of the group instead of accepting an invite to the group by email.

The original plan for the application included push notifications that told users when it was time to perform a new task or if they had limited time left to complete a task. Now these push notifications are left out of the app and users have to check for their tasks by opening up the application. Lastly, users can now only see what tasks are completed and what the remaining time is to perform the other tasks. They only see for what tasks they themselves are responsible. Not which group member is responsible for the other tasks.

## Changes:
