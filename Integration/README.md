
General
--------
The gate way server is a TCP server that knows to handle keep alive request from CAP system.
In addition when there is a new system event from center mind gate way knows to convert system event model of CenterMind to CAP model which is a byte array.


MODULES
--------
Gateway – man in the middle between CenterMind to CAP , know to get system event from queue as json / or mail and push them to CAP tcp client
TcpServer – the spring boot tcpServer – listen to CAP keepAlive request / ack messages , and push new events to CAP
Build – bundle all dependencies and produced a .zip file for deployment.
jmsClient – module for development simulate system event sending via rabittmq


Setup mail configuration on CenterMind deployment
---------------------------------------------------
1.	Login Setting  Mail Configuration
    	Mail from set to - centermind@cmdomain.com
    	Ip Address – set the ip address of mail server (james 2.3.0 )
    	Port – smtp prot , default 25
    	Do telnet smtp test from center mind server , check you can send mail from centermind deployment to mail server .
2.	Set account/user mail address
    	Setting  Permission Users (tab)select the account  Email(tab)
    	Set mail to a valid mail account predefined in mail server
    	Currently mail account is test@cmdomain.com
3.	Register user to be notify for events
    	Setting EventNotificationUsers(tab)  select user
    	Asign to the user the required events that he should be notify about.



Build And Run
---------------------------
1.	Extract the zip file - gateway-1.0-SNAPSHOT.zip under C:\\
2.	Home directory of gateway is “gateway” where you extract the zip

3.	Cd to gateway\bin
4.	Execute installMailServer.bat – this will create the mail server as windows service
5.	Start the service - James Mail Server 2.3.0
6.	Execute run.bat – to start the gateway tcp server
7.	Configuration is under – gateway\etc
8.	Log file is under – gateway\logs
9.	Initiate the event in centerMind / sent a mock mail  check cap simulator got it .

