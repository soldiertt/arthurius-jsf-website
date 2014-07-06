JBOSS 7 Config :
================
MYSQL
=====
jboss-as-7.1.1.Final/modules/com/mysql/main
|__mysql-connector-java-5.1.22-bin.jar
|__module.xml

	<?xml version="1.0" encoding="UTF-8"?>
	<module xmlns="urn:jboss:module:1.0" name="com.mysql">
	  <resources>
	     <resource-root path="mysql-connector-java-5.1.22-bin.jar"/>
	  </resources>
	  <dependencies>
	    <module name="javax.api"/>
	  </dependencies>
	</module>

jboss-as-7.1.1.Final/standalone/configuration/standalone.xml
...
 <datasource jndi-name="java:jboss/datasources/MySqlDS" pool-name="MySqlDS">
    <connection-url>jdbc:mysql://localhost:3306/arthurius</connection-url>
    <driver>com.mysql</driver>
    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
    <pool>
        <min-pool-size>10</min-pool-size>
        <max-pool-size>100</max-pool-size>
        <prefill>true</prefill>
    </pool>
    <security>
        <user-name>root</user-name>
        <password>fizzye99</password>
    </security>
    <statement>
        <prepared-statement-cache-size>32</prepared-statement-cache-size>
        <share-prepared-statements>true</share-prepared-statements>
    </statement>
</datasource>
...
<driver name="com.mysql" module="com.mysql">
    <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
</driver>

Authentification
================
jboss-as-7.1.1.Final/standalone/configuration/standalone.xml

<security-domain name="arthAuth">
    <authentication>
        <login-module code="Database" flag="required">
            <module-option name="dsJndiName" value="java:jboss/datasources/MySqlDS"/>
            <module-option name="principalsQuery" value="select password from User where userName=?"/>
            <module-option name="rolesQuery" value="select userRole, 'Roles' from UserRole where userName=?"/>
            <module-option name="hashAlgorithm" value="SHA-256"/>
            <module-option name="hashEncoding" value="base64"/>
        </login-module>
    </authentication>
</security-domain>
 
Module com.sun.net
==================
(matching dependency in MANIFEST.MF file)
jboss-as-7.1.1.Final/modules/com/sun/net/main
|__jsse.jar (taken from any java sdk)
|__module.xml

<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="com.sun.net">
    <properties>
        <property name="jboss.api" value="private"/>
    </properties>
    <resources>
        <resource-root path="jsse.jar"/>
    </resources>
</module>
