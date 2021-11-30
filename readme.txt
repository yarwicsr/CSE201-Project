App Shop
Created by Sophia Yarwick, Cameron Gonzalez, Brenden Snopel, Yidong Yao, Johnny Sayle
2021

---------- General App Description ----------

App Shop is an java-based application that allows users to: 

- browse thorugh applications
- search for applications
- log in
- comment on applications
- delete your own comments
- request new applications to include in the full application

There are also Moderator and Administrator roles that are allowed to: 
- Delete comments (mod and admin)
- Accept application requests (admin)
- Decline applicaiton requests (admin)
- Comment on application requests (admin)
- Create new accounts (admin)

The application consists of multiple pages including: 
- Log in page
- All applications page
- Single application page (one for each application in the program)
- App Request page (admin access only)

---------- App Implementation Information ----------

This application purely uses java for it's implementation.  The GUI is created using java's graphics functionality. The application list, application request list, and user information is stored in text files and read in and out the application when needed. Comments would also use this functionality, but given the amount of applications in the program this feature has not been implemented yet. As of 11/16/2021, comments are not saved in between different runnings of the program. As of 11/16/2021, administrators are able to accept or deny app requests, but users do not have the ability to submit application requests. The app also has a page to display all applications.  The search bar also offers a filtering option as it only shows apps that match your search. 

---------- App Usage Information ----------
Users can run this app and 'log in' without a profile and view apps and the app list, as well as search for apps.  They can also make anonymous comments.  Users that have an account have the same functionalities.  Moderators can delete all user comments, as can Administrators.  Administrators also have the create account and app request functionalities.  