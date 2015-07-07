Standalone Deployment
=====================

There are two sets of deployment scripts available.

The first set uses Docker and Docker-Compose to build two docker containers -- a Mysql database container and an application
container. The application container will contain Tomcat and your war file.

The second set of scripts uses Ansible and Vagrant to deploy the application on Tomcat to a local Vagrant instance, to show the
steps for a bare metal install. See the Vagrant / Ansible section on this page for specific instructions.

Both sets of scripts expect you to provide the WAR file from the grails project. Please see each section for details.

DOCKER DEPLOYMENT
=================

Dependencies
------------

The requirements for docker deployment:

1. Docker (https://docs.docker.com/installation/)
2. Docker-Compose (https://docs.docker.com/compose/install/)
3. SearchFDA WAR file.


Deployment
----------
```
# Build the war file and place it in the docker/tomcat folder.
grails compile && grails war docker/tomcat/ROOT.war

cd docker

docker-compose up
```

Operations
----------

You can go to http://localhost:8888 or http://<docker-ip>:8888 to reach the app, depending upon whether your docker configuration
uses localhost or a Docker IP address. If you are using Boot2Docker, you may need to find your boot2docker instance IP address.
You can find it with the following command:

```
boot2docker ip
```


VAGRANT / ANSIBLE DEPLOYMENT
============================
There are Vagrant and Ansible scripts in this project that will deploy the Search-FDA app to a local Vagrant Ubuntu 14.04 image.
The ansible scripts could also be used to run against an existing off-the-shelf Ubuntu machine. This ansible build deploys
both a Mysql database back-end and Tomcat on the same machine.

Dependencies
------------

The dependencies for deployment to Vagrant:

1. Vagrant
2. Python 2.7
3. Ansible
4. SearchFDA WAR file.

Initial Configuration
---------------------

This process assumes you already can create a WAR file in the project using Grails, or can copy the WAR file from another location.

1. Install Vagrant (https://www.vagrantup.com/)
2. Install Python 2.7 (https://www.python.org/download/releases/2.7/)
3. (recommended) Install Python VirtualEnv to manage python libraries

  ```
   sudo pip install virtualenv
   sudo pip install virtualenvwrapper
   ```

   If you decide to use Virtualenv please read the documentation (http://virtualenvwrapper.readthedocs.org/en/latest/) to
   use it for managing Python library dependencies.

4. Install Ansible

   If you are using Virtualenv:

   ```
   mkvirtualenv -r requirements.txt checkfda
   ```

   If you are not using Virtualenv:

   ```
   sudo pip install -r requirements.txt
   ```


Deployment
----------

You will use Vagrant to create a standalone virtual machine. Vagrant will automatically run Ansible scripts
to install a MySQL 5.6 database, Oracle Java 8, and Tomcat 7.

It will then deploy the application to the Tomcat 7 container and run it on port 8888.

Make sure to complete the initial configuration first!

```
grails compile && grails war target/ROOT.war    # Or copy the ROOT.war from another location into the main project target/ directory.

# Stop any existing processes running on port 8888.

# If you are using the virtualenv wrapper, enable the python virtualenv that you created in the configuration steps.
workon checkfda

vagrant up

# When you are done deploying you can deactivate the virtualenv wrapper environment. The Vagrant box and application will still run.
deactivate
```

Operations
----------

Go to http://localhost:8888 to run the application.

If you need to access the Vagrant virtual machine, you can SSH into it. You will have sudo access:

```
vagrant ssh
```

To stop the Vagrant box:

```
vagrant halt
```

To stop and remove the Vagrant box:

```
vagrant destroy
```

To redeploy everything from scratch, you can use the following command:

```
vagrant destroy && vagrant up
```


