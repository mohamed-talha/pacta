# PACTA : Privacy-Aware Contact-Tracing App   
Tracing the contacts with people identified as carrying the coronavirus is essential to control the spread of the COVID-19 pandemic. In many countries, tracing is a recursive procedure based on the memory of people tested positive. In other countries, Contact-Tracing Apps have been developed such as TraceTogether, StopCovid, COVIDSafe, etc. While these applications often meet the requirements of applicable Privacy regulations, the number of people who agree to use them is too low. This is due to people's lack of confidence in these applications regarding their privacy. The current prototype, called PACTA, is based on a new approach to trace contacts without resorting to any Personally Identifiable Information. For more details on our approach, please refer to this article: [article publishing  is in progress].  

## Tools  
This prototype is a REST API developed using Java1.8, maven3.6.3, SpringBoot on a Hadoop platform (version 2.7.7) and based on a HBase database (version 1.1.0).   

## HBase tabels
Here are the commands to create the necessary HBase tables (account and contact):  
* create 'account', 'contact', 'infection'
* create 'contact', 'sender'

## Web services
The API offers the following web services:  
* /account/getAccount(GET): Get an account details
* /account/createAccount(POST):	Create a new account
* /contact/newContact(POST):	Report a new contact between users
* /infection/reportInfection(POST):	Report a new infection for a user
