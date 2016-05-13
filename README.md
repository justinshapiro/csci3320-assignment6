PA6 WRITEUP
-----------------

Application: Basic Calculator (based off of the iPhone calculator layout)
Written by Justin Shapiro for CSCI 3320

-----------
A. The App
-----------

The app is fully completed, and in my opinion it goes above and beyond what the requirements for this assignment are. The app is almost a replica of the iPhone calculator design. I wanted to learn Android Programming by doing this assignment, so therefore I decided to create a polished UI similar to that of the iPhone's calculator and to implement advanced features such as floating point arithmetic and integer negation. The experience of going above and beyond for this assignment will only help me as my group and I get into serious Android development within the next several weeks. 

This app is free of bugs, and works fine on all emulators I tested. I put the app on my Nexus 5 phone and it works perfectly. Only when you emulate the app on very old phones such as the Nexus One is where slight changes in the layout occur, but nevertheless the app remains functional. I didn't plan on putting an Exit button in the app, but since the requirements for this assignment mentioned it, I decided to put it in. Usage of this calculator should be intuitive to any level of user.

The actual Java code behind the app is not the most efficient nor organized code. It took a lot of playing around with different methods in order to get the program to do what I wanted it to do, so there may be some signs of inefficiency in CalcActivity.java. Particularly, the entire program is driven by Boolean flags that indicate whether or not a particular action has occurred. There was originally only supposed to be two of these flags, but the program ended up with six. Being reliant on comparisons and Boolean flags isn't my preferred method of programming, and as I start working on the final project I will be thinking harder about more reasonable ways to manipulate the code. 

---------------------------------------
B. Description of Android Constructs 
---------------------------------------

The most prominently used object in this app was the Button. Five rows of four Buttons each were stacked using embedded Linear Layouts. The width and height of the Buttons were set with either wrap_content or match_parent to ensure that the Buttons draw themselves based on the screen size of the device they are being run on. I also used the TextView object to display the resultant calculations. In order to get the TextView in the correct layout on the screen, I had to nest it within a ScrollPane whose height needed to be manually set against my wishes. The Buttons were linked to XML files in the Drawable folder to give them stylish features such as gradients, strokes, and click colors.

When it came time to link all these objects together in the Java code, each object was instantiated with their associated R.ids using the findViewById function with a Button typecast. An OnClickListener was used to detect button presses. Within the instantiation of the OnClickListener object, the function onClick was overridden. The contents of onClick are the most important part of this program, as it calls all helper functions and performs a fair amount of computation within the embedded function itself. After the onClick method was implemented, each button was assigned the associated OnClickListener object.

--------------------------------------------------
C. General Thoughts About Android Programming
--------------------------------------------------

First off, I really enjoy the Android Studio software. I love the IDEs that InteliJ makes such as PyCharm, CLion, and Android Studio. Because I already had familiarity using an InteliJ product from last semester in Intermediate Programming where we used CLion, it was easy for me to pick up the basic workflow of using the software. 

In terms of Android programming in general, I was a bit overwhelmed at first by all the different files and the fact that I had to manipulate XML code and link XML files to each other and in the Java code. However, as I started creating my Linear Layouts and Buttons, everything started making sense. Overall I find Android programming more intuitive than Java programming with Swing. At least for this calculator, the only Swing-like operation was the implementation of the OnClickListener. The greatest challenge here was not in the Android world itself, but implementing general calculator functionality.

Now that I have successfully completed my first, useful Android application, I am eager to keep working with Android Studio to develop my final project and learn more aspects of this new platform.
