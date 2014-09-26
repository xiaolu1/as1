as1
===

Usage:
This is the readme file for my first assignment TODO list application.
This application can add, archive, email or delete the TODO items.
It will require the user to type on the eidttext and press the button save to add item in the todoList.
When the users press the delete button, it will delete all items that are checked in the todoList and the archivedList.
When the users press the archive button, it will put all checked todo items into the archivedList and put all checked archived items into the todoList.
When the users press email, it will get to a new page and ask the user for the emaill address. Then the app will call an app that can send email and send email for the users. The email address is set to be the input from the users and the subject is set to be "Selected Todo items from the TODO list application". And the body of email will be the checked todo items and archived items before the user press the email buttom.
The user can change views between todo view, archived view and summary view.
The todo view will show the todoList for the user and the archived view will show the archivedList for the user.
The summary view will show the count for different types of items.
The items will save automatically after the user change it.

Notice:
Pay attention to the usage of the check box, the checked item will not be unchecked as the user change views. The functions delete, archive and email will include all checked items.
The save button will always save the input in the todoList even if it is not in the todo view.


Source of the code:
In this assignment, I use the code of our lab. The part of the TodoItem, part of TodoActivity and most part of FileDataManager was was writen based on LonelyTwitterGson.

I also use the Stephen Just's code for the TodoCheckboxListener. Below is the license.

The MIT License (MIT)

Copyright (c) 2014 Stephen Just

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


The method of sending email with the app was writen based on http://stackoverflow.com/questions/2197741/how-can-i-send-emails-from-my-android-application
