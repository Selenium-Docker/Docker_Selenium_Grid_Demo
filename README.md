# Docker_Selenium_Grid_Demo - https://github.com/vivekqa1/Docker_Selenium_Grid_Demo

# https://www.linkedin.com/pulse/how-integrate-selenium-grid-docker-vivek-parmar%3FtrackingId=mG4BkXUEQ7u4hkQVANu3xw%253D%253D/?trackingId=mG4BkXUEQ7u4hkQVANu3xw%3D%3D

##This article will explain to you how to configure a docker selenium grid architecture for an automation project and we are going to look into what are the base method that requires to trigger execution and what setup we have to do to configure the docker selenium grid using selenium hub and node images.

##We are going to divide complete implementation into two-part.

- Setup of selenium grid with docker (grid console)

- Code configuration to trigger execution on docker selenium grid (git repository)


###Steps to configure the setup of selenium grid with docker
- Download docker from the docker official website. Docker Official Website

- Install docker to the system.

- Install selenium-hub and chrome, firefox, and edge nodes. Now to do this you will require images of the hub and nodes and if you don't have the images below commands will download the latest image of the hub and nodes from the docker hub. 

- The Hub and Nodes will be created in the same network and they will recognize each other by their container name. A Docker network needs to be created as a first step. we are creating a "grid" network using the below command.

- To create a docker network run the below command to the terminal. Once the network creates we are going to run the hub and nodes in the same network.

####			docker network create grid


- To start the selenium hub run the below command to the terminal


####			docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:4.1.1-20211217

- To start chrome, firefox and edge nodes run the below commands to the terminal

- To Start Chrome Node:

####	        docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-chrome:4.1.1-20211217


- To Start Firefox Node: 

####	        docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-firefox:4.1.1-20211217

- To Start Edge Node:

####	        docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub --shm-size="2g" -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-edge:4.1.1-20211217


- Once selenium grid docker setup is completed in the terminal we can see all images that were downloaded and all containers that are up and running to get find the docker command please refer "Useful Docker commands" section of the article.

 - Once the docker selenium grid is up and running let's create a demo project with core classes to trigger execution on docker selenium grip and we can actually see the execution running on chrome, firefox, and edge nodes.


Let's create a demo maven project and add core classes and relevant tests to execute the selenium test on the docker selenium grid.
https://github.com/Selenium-Docker/Docker_Selenium_Grid_Demo



### Useful Docker commands 
- To check all images: docker images
- To check All running containers: docker ps -a
- To see the list of docker networks: docker network ls
- Start all containers: docker start $(docker ps -a -q)
- Stop all containers: docker kill $(docker ps -q)
- Remove all containers: docker rm $(docker ps -a -q)

### Useful resources on the internet
- https://github.com/SeleniumHQ/docker-selenium
- https://www.lambdatest.com/blog/run-selenium-tests-in-docker/
- https://www.vinsguru.com/selenium-webdriver-file-downloads-uploads-using-docker-grids/

To Execute project below is the maven command

#### mvn clean install -Denv.remote.url=https://www.google.com/ -Denv.browser.name=chrome -Dapp.env=local -Dapp.runOnDocker=true

  