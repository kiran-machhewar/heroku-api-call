# JavaWebAppTemplateHeroku

This template could be used to create webservices which are hosted on heroku.

##Getting started
To create the project in your local machine and connect to heroku use below commands:  
Go to the the workspace. Create a folder with the name of project. Go to the created directory and run below commands
```
git init
git clone https://github.com/kiran-machhewar/JavaWebAppTemplateHeroku.git
git remote add heroku <heroku git location>
git add .
git commmit -m "Initial commit"
git push -u origin master
```
After this heroku will detect java application with embedded tomcat and it will deploy applicaiton. 
Once the deployment is done you will see the application home page url printed on command you can open that and check your application.

##Adding external jars required for applicaiton which are not present in maven public repository.

In order to use the jar files created specifically for your application or jar files which are 
not present in public maven repository below steps could be followed.


##For introducing your jar file in your private repo below three items are required.
1. groupid     : eg. com.example here in this application I would be using com.kmforce  
2. artifactid  : eg. myJar.jar  
3. version     : eg. 1.0

##Getting started
In order to add external jar to the repo first create the repo folder in the start directory of project it would be like
++src  
++repo  
++pom.xml  
Now run the below command 
mvn deploy:deploy-file -Durl=file:///path/to/yourproject/repo/ -Dfile=mylib-1.0.jar -DgroupId=com.example -DartifactId=mylib -Dpackaging=jar -Dversion=1.0

E.g: mvn deploy:deploy-file -Durl=file:///D:\Workspace\HerokuProject\HelloWorld3\devcenter-embedded-tomcat\repo -Dfile=myJar.jar -DgroupId=com.kmforce -DartifactId=myJar -Dpackaging=jar -Dversion=1.0

After running this command your repo should have been updated to reflect myJar.jar file.  

Till now you have created your own repository but need to update the pom.xml so that it would consider using your local repository. Add below lines to your pom.xml
```
<repositories>
    <!--other repositories if any-->
    <repository>
        <id>project.local</id>
        <name>project</name>
        <url>file:${project.basedir}/repo</url>
    </repository>
</repositories>
```
If repositories is not exist please create one in pom.xmml.  

Add below lines to your pom.xml so that maven would know from where the jar has to be referenced.
```
<dependency>
    <groupId>com.example</groupId>
    <artifactId>mylib</artifactId>
    <version>1.0</version>
</dependency>
```
E.g:
```
<dependency>
    <groupId>com.kmforce</groupId>
    <artifactId>myJar</artifactId>
    <version>1.0</version>
</dependency>
```
