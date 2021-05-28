# Readme

- Name: Yitao Ma
- Student ID: s3843689
- Course Name: Further Programming (2110) COSC2391

# Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
 - main.model
 - main.ui
Packaging for test:
 - test.model

## How to clone the project using intelliJIDEA and RUN the application
1- Download IntelliJ IDEA Ultimate Version (You had to apply for student license)

2- Open IntelliJ IDEA, select "File" from the top menu, select "New" and select "Project from Version Control"  

3- Copy your Github classroom repository and paste into URL, click on "Clone".
 Your project will be cloned and open in your IntelliJ IDEA window.
 
 However, you still need to add the SQLite jar file to your project so you can have access to your database. Follow next steps for adding the Jar file:
 
1- Download the SQLite JDBC jar file from week 7 Canvas module.

2- In your project under project root, make a new directory called lib and move the jar file into lib folder

3- Open IntelliJ IDEA, click on "File", open "Project Structure"

4- Under "Project Setting", select "Libraries"

5- Click + button, choose Java, and navigate to your project folder, then Lib folder, choose "sqlite-jdbc-3.34.0.jar", and click on "open"

6- Click on Apply and then OK to close the window

Now you are ready to Run the Application.

Simply right click on Main.java and choose Run.
Congratulations!

Login info:

Username: test

Password: test


## Prepare other content

Readme files are made for developers (including you), but also could be used for the final users.
So while you are writing your readme files please consider a few things:

1. What is about?
    - Your name and student number and course name on the top
    - Describe the content of your project or repository
    - Explain things the users would have a hard time understanding right away
2. What steps need to be taken?
    - Any specific steps for running your application, what is the main class?
    - Is there any requirements or dependencies?
    - After the installation, how they compile or run the code?
3. Execution examples
    - You could provide examples of execution with code and screenshots
4. Function Notes:
   - For Login: Depend on employee's role, login as employee or admin (see different function).
   - Register only for Employee not Admin.
   - To test Employee function, use Username: test AND Password: test
   - To test Admin, use Username: admin AND Password: admin
   - Lock down: Period of operation. So, no date check. If a seat is locked down. All booking records AND corresponding 
   Whitelist records for this locked downed seat will be canceled/deleted.
   - Admin can achieve COVID-19 lockdown AND COVID-19 condition by manually lockdown selected seats.
   - Deactivate Account: If an employee account is deactivated, this employee can not log in/reset password/
   booking/manage booking,etc. Can not do all activities/operations. 
   - If a normal employee become admin then we still keep their previously booking
   records, but he/she can not make any operation related to employee (booking/manage booking,etc).
   - Account deleted: If an account has been deleted, all records in Booking and Whitelist
   will also been deleted if have any.
5. Database notes:
   - Whitelist: If Booking table have a record inserted, Whitelist table also
   insert same record automatically, but the date column is increment 1 day. Purpose is to prevent
   the user to book the same sit that has been booked previously. 
   So, it is to check which seat the user can not book at each date.
   - Modify Whitelist record is un-necessary! If record changed in the Whitelist,
   will impact the function. DO NOT MODIFY IT.
     
   - (IMPORTANT!) So, For function: "Admin can manually change the Whitelist".
   It is actually change the Seat table (Seats that is locked down) in the database instead of Whitelist table
   in the database.  


other things you could add:

- Table of content
- Test cases
- Know bugs
- Things that have not been working or complete



### References and tutorials for Readme (Markdown)
- **IntelliJ IDEA MarkDown guide**. jetbrains.com/help/idea/markdown.html
- **Choose an open source license**. Github. Available at: https://choosealicense.com/
- **Getting started with writing and formatting on Github**. Github. Available at: https://help.github.com/articles/getting-started-with-writing-and-formatting-on-github/
- **Markdown here cheatsheet**. Markdown Here Wiki. Available at: https://github.com/adam-p/markdown-here/wiki/Markdown-Here-Cheatsheet
- **Markdown quick reference**. Wordpress. Available at: https://en.support.wordpress.com/markdown-quick-reference/
- **readme-template**. Dan Bader. Github. Available at: https://github.com/dbader/readme-template
- Writing READMEs. **Udacity**. Available at: https://classroom.udacity.com/courses/ud777/
