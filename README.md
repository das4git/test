JMS
Написано несколько реализаций обмена сообщений средствами
ActiveMQ

#Результаты записываются в файл note.txt

1 Персистентные сообщения и неперсистентные 
NonPersistentMode.java
PersistentMode.java

2 Разные режима подтвеждрения - CLIENT_ACKNOWLEDGE, DUPS_OK_ACKNOWLEDGE
ClientAcknowledgeMode.java
DupsOkAcknowledgeMode.java

3 Транзакционный режим 
TransactionalMode.java

* Для обмена сообщений по протоколу tcp нужно предварательно установить ActiveMQ и запустить через терминал.  
