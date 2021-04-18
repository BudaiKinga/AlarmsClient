Prerequirements:

The system was developed in order to monitor stock data (from a predefined list) by sending requests to Alphavantage.
The system has two main components: AlarmsClient and AlarmsDataDispatcher. The communication between these two is done using asynchronous
messaging via ActiveMQ. The messaging queue should be started before launching the two application. For more details:
http://activemq.apache.org/installation.html

After installation of Active MQ, navigate to "bin" directory and type command: .\activemq.bat start

The system uses MySQL to persist data, thus it is neccessary to have MySQL server launched. The credentials should be configured in application.properties.
The schema's name is "world". If it is not desired to create manually ensure that at first run the application.properties contains the
 following: spring.jpa.hibernate.ddl-auto = create
The mailing service should be configure in application.properties.
The AlarmsDataDispatcher will send requests periodically (api key need to be configured in AlphaVantageConnector class) based on a pre-configured time
property data.update.executor.rate in application.properties. No more than 500 calls per day are allowed and no more than 5 calls per minute are 
allowed.
Messaging scheme:
https://drive.google.com/file/d/1AcvnoNHRCRXLlRfZSERgE79RWIVgIiGy/view?usp=sharing

Supported use cases:

After launching the AlarmsClient and AlarmsDataDispatcher applications the client will be running on localhost:4444.

Registration: by accessing http://localhost:4444/login the user can log in or can perform registration

Define Alarm: after a successfull login (validations are performed) the user can add a stock from predefined list to monitor. After (and only after) adding a stock to be monitored an alarm can be defined for it. Upon defining an alarm a request is sent to the dispatcher to provide initial price data. 

Manage Alarms: the list of alarms are visible upon successfull login. The user can modify target percentage or name of an alarm. The user can also delete the alarm.

Send e-mail notification: if the target variance is reached, based on credentials in application.propertes an email is sent with details to the user. After this the alarm becomes inactive.

The worklog of development can be found at:
https://docs.google.com/spreadsheets/d/1RuucS8c9tV8ihlFZIoq9GjmKJJl09Zx5MNPBNS6E0iI/edit?usp=sharing
