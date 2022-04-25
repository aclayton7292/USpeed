# **Project Portfolio**

# **USpeed**

**Table of Contents**

1. [Introduction](introduction)

1. [Background](background)

1. [Project Requirements](project-requirments)

1. [Design Decisions](design-decisions)

1. [Diagrams](diagrams)

1. [Code Snippets](code-snippets)

## **Introduction**
---
<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKs7RSUFDZYTFxcqV2flxoJa9r-w5vJbFKMw&usqp=CAU"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStO-flYDzXFhrKSiv7PpvVL_RQl_c8Rtm0Ng&usqp=CAU" style="width: 50%">

This was a senior capstone project taking advantage of a Raspberry Pi as well as a SenseHat in order to monitor speed and location for users to monitor and view.

## **Background**
---
The intial reasoning to develop this project was due to an influx on injuries and accidents on college campuses due to higher speeds from electronic vehicles. A proposed solution for the GCU campus was to implement a speed limit, with the issue of speed not being known for most longboards and scooters being brought up as one of the problems. With this in mind I set out to provide the solution in a portable package with some extra features so that it would stand above other products.

## **Project Requirements**
---
- [x] RestApi Backend for user modules

- [x] RestApi Backend for routing modules

- [x] Multi-Threaded Functionality for the raspberry pi

- [x] Simple user experience

- [x] User controlled toggle for tracking services

- [x] Connecting to back-end api from the raspberry pi

- [x] Visable Display for speed on the raspberry pi

- [x] Raspberry Pi runs self contained(I.e on battery power)

- [x] Previously taken routes can be viewed on a google map

## **Design Decisions**
---

| Technology | Reasoning |
| :-------: | :-------: |
| RaspberryPi 4B | This was chosen to avoid any memory issues you could find with an arduino. |
| SenseHat | This was used for the integrated accelerometer and other sensors that can be used in the future. | 
| Adafruit Ultimate GPS | This was chosen as it has a high accuracy rating and has a integrated antena | 
| Spring Boot | Spring boot was the back end of choice for this project as it is a widly used and highly advantagous framework with plenty of features to build on. |
| Python3 | Python was used for the operation script as it works well with embedded systems and is a great scripting language. |
| VNC Viewer | VNC Viewer was used to connect to the raspberry pi to develop on it. This allowed for the pi to remain headless. | 
| Google Api | The google api is used to display the routes and is free up to a certain amount of marks | 
| MySQL | MySQL was chosen for the database because of comfort level as well as it being a relational database.
<details>
    <summary><b>Design Decisions Summary</b></summary>
    The technology chosen was all to help compliment eachother and were all technologies that worked well when paired togther. The RaspberryPi SenseHat was specifically designed to work on the Pi 4B and Python has libraries for the operation of the said hat. The Adafruit Ultimate GPS also was specifically made to operate with the raspberry pi, as they have another model that used pin outs instead of a standard usb connection, making the integration much simpler. Raspbian(The default OS on the pi) also has a VNC integration that makes it easy to connect to from all your machines. Spring Boot was used to create the api back end for the web site because it is an excellent framework that supports rest api creation as well as offers great security features. MySQL was used because a relational database was needed to keep the users and their routes together. Lastly the google api was used for the map creation as it is simple and cheap to use. It is standard javascript and free credits are received every month so it allows for the application to run cheaply. 
</details>

---
## **Diagrams**

### Database design

![ER Diagram](https://raw.githubusercontent.com/aclayton7292/Portfolio/main/ER_Diagram.png)
<details>
    <summary><b>Database summary</b></summary>   
    The database was designed in a way that a user can store as many routes as they would like but each user has to be their own unique identity. Though the routes table has gone through several itterations, with it needing to be able to contain many routes that have to be kept together by a certain route number and/or a route id that is all stored under a user id foreign key.
</details>

---

### Physical Diagram

![Physical Diagram](https://raw.githubusercontent.com/aclayton7292/Portfolio/main/Physical_Diagram.png)
<details>
    <summary><b>Physical summary</b></summary>   
    The physical layer diagram depicts the chain that the code follows through the physical sides of things. With the raspberry pi passing data through port 80 or port 443 for more security, landing through the wifi on the desktop application/hosting server.
</details>

---

### Logical Diagram
![Logical Diagram](https://raw.githubusercontent.com/aclayton7292/Portfolio/main/Logical_Diagram.png)
<details>
    <summary><b>Logical summary</b></summary>   
    The logical diagram here dipicts how information is going to be passed through the different layers of applications. With the raspberry pi dipiction on top passing the json data through the api call to the spring boot application. At the same time it will aslo pass the velocity data from the accelerometer to the display on the raspberry pi. With the data being passed over port 80 or 443 for added security.
</details>

---

### UML Class Diagram
![UML Diagram](https://raw.githubusercontent.com/aclayton7292/Portfolio/main/UML.png)
<details>
    <summary><b>UML summary</b></summary>   
    The UML class diagram here is dipicting the two classes that exsist on the back end with all their functions and typing. It shows how the services interact with the models and what all the functions are typed, what they consume, as well as what they are returning.
</details>

---

### **Code Snippets**

#### Acceleration monitoring
```py
#function for the sense hat acceleration monitoring
def acceleration():
    sense.clear()   
    print("Start or stop")     
    if is_running:      
        while is_running:
            start = timer()
            global init_speed
            accel = sense.get_accelerometer_raw()
            end = timer()
            T = (end-start)
            curr_speed = (init_speed + ((accel['x'] * 9.81) * T)) * 2.237
            # print(curr_speed)
            init_speed = round(curr_speed)
            show_number(round(curr_speed), 255, 255, 255)               
            print(curr_speed)
            sleep(0.4)
    else:
        print("stopping accel") 
```
This is the main function for monitoring and displaying the speed for the end user. It is using a boolean state that is set on a button press in order to keep running. It then gets the acceleration in g's from the sense hat and is converted to MPH and then displayed through a different function.

---
 
#### GPS Monitoring
```py
#function for the gps monitoring
def gps():
    with open("route.json", "a") as out_file:
        out_file.truncate(0)
        out_file.write("[")
        while is_running:
            try:
                global session
                report = session.next()
                if report['class'] == 'TPV':
                    if hasattr(report, 'time'):
                        time = report.time
                        print(time)
                    if hasattr(report, 'lat'):
                        lat = report.lat
                        print(lat)
                    if hasattr(report, 'lon'):
                        lon = report.lon
                        print(lon)
                    print(report)
                    route = {                        
                        "lat": lat,
                        "lng": lon,
                        "time": time
                    }                              
                    json.dump(route, out_file, indent = True)
                    out_file.write(", \n")                              
                    sleep(6)
            except KeyError:
                pass
            except KeyboardInterrupt:
                quit()
            except StopIteration:
                session = None
                print("GPSD has terminated")       
        out_file.truncate(out_file.tell() - 3)        
        out_file.write("]")
```
This is the second main function on the raspberry pi that is running concurrently with the acceleration. This function is still running on the boolean state set by a button and is still gathering data. Though this time it is gathering data from the GPS not the sense hat. It is then saving that data to a file, the data being the latitude, longitude, and time stamp. With some file manipulation to add some extra characters to adhere to proper json format.
