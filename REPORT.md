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

// vertel wat voor communicatie manieren er met de json zijn.
// vertel hoe users en groups aan elkaar zijn verbonden.
// vertel hoe de juiste group en user worden opgehaald en doorgegeven.


## Challenges: 

## Changes:
