Standalone Deployment
=====================

There are Vagrant and Ansible scripts in this project that will deploy the Search-FDA app to a local Vagrant Ubuntu 14.04 image.
The ansible scripts could also be used to run against an existing off-the-shelf Ubuntu machine.


Dependencies
------------

The dependencies for deployment to Vagrant:

1. Vagrant (URL for Vagrant)
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
   pip install virtualenv
   pip install virtualenvwrapper
   ```

   If you decide to use Virtualenv please read the documentation (http://virtualenvwrapper.readthedocs.org/en/latest/) to
   use it for managing Python library dependencies.

4. Install Ansible

   If you are using Virtualenv:

   ```
   mkvirtualenv -r requirements.txt searchfda
   ```

   If you are not using Virtualenv:

   ```
   pip install -r requirements.txt
   ```


Installation
------------

You will use Vagrant to create a standalone virtual machine. Vagrant will automatically run Ansible scripts
to install a MySQL 5.6 database, Oracle Java 8, and Tomcat 7.

It will then deploy the application to the Tomcat 7 container and run it on port 8080.

Make sure to complete the initial configuration first!

```
grails compile && grails war target/ROOT.war    # Or copy the ROOT.war from another location into the target/ directory.

# Stop any existing processes running on port 8080.

# If you are using the virtualenv wrapper, enable the python virtualenv that you created in the configuration steps.
workon searchfda

vagrant up

# When you are done deploying you can deactivate the virtualenv wrapper environment. The Vagrant box and application will still run.
deactivate
```

Operations
----------

Go to http://localhost:8080 to run the application.

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