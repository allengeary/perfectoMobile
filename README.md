# Library Information
The Framework libraries were designed to be an extensible, plugable set of libraries to meet the purposes of development and QA teams in various levels of automation adoption. Automation engineers and pick which libraries suite their needs or choose to implement the entire set as a whole end to end solution. Development as n open source library, all of the library source code can be found at http://github.com/allengeary/perfectoMobile.git. Please refer to the information regarding the individual library implementations below.  For more detailed documentation, refer to https://allengeary.github.io/perfectoMobile

# Device Management
The Device Management framework is responsible for acquisition of devices, locking and parallel execution. This library allows access to mobile devices and desktop browsers alike all through a single interface set. Along with device access it also provide artifact production, reporting and integration points for external systems. Using the default Test NG implementation a develop can quickly gain access to a rich feature set out of the box

# Gesturing and Device Actions
The Gesturing and Device Action library provides simplified access to various device specific actions in a technology agnostic way. From a gesturing standpoint, the system allows for a plugable implementation of standard gestures with the Perfecto implementation being the standard. With a concentration of best practices, the gesturing libraries use Percentage based gesturing in place of point based allowing for great device acceptance.
In addition to Gesturing, the Device Action set offers easy access to a set of commonly used functionality that is mobile specific such as Installation of application, device reboots and pressing specific keys on the keyboard. 

# Object Repositories and Data Management
The Page and Data Management libraries off a rich set of functionality for dealing with Page Objects and data along with various Keyword driven testing implementations. This library provides the page object model, data driven testing, content management for multi lingual support, enhanced smart caching for script performance) and a rich XML keyword driven framework

# Integrations
The Integrations library offers a set of interfaces for access RESTful API's. The API calls are wrapped up into a east to use interface set backed bu an Invocation Handler that does all of the heavy lifting. The Perfecto REST API has been implemented to allow for advanced functionality with simple calls. Adding to the library only requires interfaces chances as the code behind the scenes generates the calls and extracts the data



