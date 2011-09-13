# A Tapestry and Gorm sample application

Tapestry is a pretty neat component/event oriented web framework on the java platform.
Gorm is basically hibernate on steroids. What if you combine these two ? ... And you throw
in a nice smattering of groovy rather than java (unless there is a specific need for java).
How far will it get you in a crud type application ?

## Technologies so far
* Tapestry 5.2.6 - For all things web (with a jquery twist, rather than the std prototype based flavour)
* Gorm 1.3.7 - A really nice ORM, which is available outside of Grails
* Gradle - Is there any other way to build ?
* Spock - The test framework of choice
* Logback - For logging (with slf4j bridging from all kinds of logging dependencies)


## Roadmap
Not planning on putting massive efforts into it, but it will serve as a decent example for upcoming blog postings at
http://codewader.blogspot.com



## Get it up and running
Prereqs:
* Clone this repo (duh)
* Gradle 1.0-milestone 3 installed and working

To run it
    $ gradle jettyRun



